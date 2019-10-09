package com.saranya.androidmvvm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.saranya.androidmvvm.R;
import com.saranya.androidmvvm.api.ApiComponent;
import com.saranya.androidmvvm.api.DaggerApiComponent;
import com.saranya.androidmvvm.dimodule.AppModule;
import com.saranya.androidmvvm.model.UserContentResponse;
import com.saranya.androidmvvm.viewmodel.UserContentViewModel;


import java.util.ArrayList;import java.util.List;



public class NewsFragment extends Fragment implements NewsFragmentView {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressbar;
    private UserContentViewModel mUserContentViewModel;
    private NewsAdapter mNewsAdapter;
    private final List<UserContentResponse.Contents> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_contents, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mProgressbar = view.findViewById(R.id.spinner);
        mNewsAdapter = new NewsAdapter(list , this.getContext());

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
                showProgressIndication();
                loadUserContents();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadUserContents();
    }

    private void loadUserContents() {
        mUserContentViewModel.getCountryModelLiveData().observe(this, new Observer<UserContentResponse>() {
            @Override
            public void onChanged(@Nullable UserContentResponse mUserContentResponseModel) {
                mUserContentResponseModel = mUserContentViewModel.getCountryModelLiveData().getValue();
                if(mUserContentResponseModel != null) {
                    bindListData(mUserContentResponseModel.getContentList());
                    list.addAll(mUserContentResponseModel.getContentList());
                    bindListData(list);
                }
            }
        });
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
    public void bindListData(List<UserContentResponse.Contents> contents) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.setLayoutManager(manager);
        if (getActivity() !=null)
        mNewsAdapter = new NewsAdapter(contents, getActivity().getApplicationContext());
        mNewsAdapter.notifyDataSetChanged();
        hideProgressIndication();
        // Stopping swipe refresh
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
