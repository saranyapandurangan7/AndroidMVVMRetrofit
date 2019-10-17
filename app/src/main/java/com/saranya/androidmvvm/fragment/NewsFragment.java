package com.saranya.androidmvvm.fragment;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saranya.androidmvvm.R;
import com.saranya.androidmvvm.api.ApiComponent;
import com.saranya.androidmvvm.api.DaggerApiComponent;
import com.saranya.androidmvvm.db.Contents;
import com.saranya.androidmvvm.db.DatabaseClient;
import com.saranya.androidmvvm.dimodule.AppModule;
import com.saranya.androidmvvm.model.UserContentResponse;
import com.saranya.androidmvvm.utils.NetworkStateReceiver;
import com.saranya.androidmvvm.viewmodel.UserContentViewModel;


import java.util.ArrayList;import java.util.List;


public class NewsFragment extends Fragment implements NewsFragmentView , NetworkStateReceiver.Listener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout mErrorLayout;
    private ProgressBar mProgressbar;
    private UserContentViewModel mUserContentViewModel;
    private NewsAdapter mNewsAdapter;
    private final List<UserContentResponse.Contents> list = new ArrayList<>();
    Contents contentsdb ;
    NetworkStateReceiver mNetworkStateReceiver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contents, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mErrorLayout = view.findViewById(R.id.error_layout);
        mProgressbar = view.findViewById(R.id.spinner);
        mNetworkStateReceiver = new NetworkStateReceiver();
        mNewsAdapter = new NewsAdapter(list , this.getContext());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(mNewsAdapter);

        contentsdb = new Contents();
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Landscape
            GridLayoutManager manager = new GridLayoutManager(this.getContext(),2);
            mRecyclerView.setLayoutManager(manager);
        }
        else {
            // Portrait
            LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
            mRecyclerView.setLayoutManager(manager);

        }
        mUserContentViewModel = ViewModelProviders.of(this).get(UserContentViewModel.class);
        ApiComponent appComponent = DaggerApiComponent
                .builder()
                .appModule(new AppModule())
                .build();
        appComponent.inject(this);

        showProgressIndication();
        mUserContentViewModel.init();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserContentViewModel.init();
                //showProgressIndication();
                getTasks();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mNetworkStateReceiver.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mNetworkStateReceiver.unregisterListener(this);
    }

    private void getTasks() {

        class GetTasks extends AsyncTask<Void, Void, List<Contents>> {

            @Override
            protected List<Contents> doInBackground(Void... voids) {
                List<Contents> taskList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .userContentDao()
                        .getAll();
                return taskList;

            }
            @Override
            protected void onPostExecute(List<Contents> tasks) {
                super.onPostExecute(tasks);
                if (!tasks.isEmpty()){
                    bindListData(tasks);
                }else {
                    showErrorMessage();
                }
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTasks();
    }


    @Override
    public void showProgressIndication() {
        mProgressbar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressbar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindListData(List<Contents> tasks) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mRecyclerView.getRecycledViewPool().clear();
        if (!list.isEmpty()){
            list.clear();
        }

        List<UserContentResponse.Contents> userslist1List = new ArrayList<>(tasks.get(0).getRows());
        for (UserContentResponse.Contents number : userslist1List) {
            if( number.getTitle()==null && number.getDescription()==null && number.getImageHref()==null ){
                tasks.get(0).getRows().remove(number);
            }
            list.add(number);
        }
        if (getActivity() !=null)
        mNewsAdapter = new NewsAdapter(list, getActivity().getApplicationContext());
        mNewsAdapter.notifyDataSetChanged();
        hideProgressIndication();
        // Stopping swipe refresh
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorMessage() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideErrorMessage() {
        mErrorLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void notifyNetworkAvailable() {
        getTasks();
    }

    @Override
    public void notifyNetworkUnAvailable() {
        getTasks();
    }
}
