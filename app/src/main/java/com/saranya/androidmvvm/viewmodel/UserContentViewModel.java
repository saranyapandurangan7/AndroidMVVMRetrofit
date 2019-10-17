package com.saranya.androidmvvm.viewmodel;

import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.saranya.androidmvvm.api.ApiComponent;
import com.saranya.androidmvvm.app.MyApplication;
import com.saranya.androidmvvm.db.Contents;
import com.saranya.androidmvvm.db.DatabaseClient;
import com.saranya.androidmvvm.dimodule.ApiModule;
import com.saranya.androidmvvm.dimodule.AppModule;
import com.saranya.androidmvvm.model.UserContentResponse;
import com.saranya.androidmvvm.api.DaggerApiComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class UserContentViewModel  extends ViewModel {

    private LiveData<UserContentResponse> mUserContentResponseModel;

    @Inject
    ApiModule repository;

    public void init() {
        if (this.mUserContentResponseModel != null && mUserContentResponseModel.getValue() != null) {
            return;
        }
        ApiComponent appComponent = DaggerApiComponent
                .builder()
                .appModule(new AppModule())
                .build();
        appComponent.inject(this);
        mUserContentResponseModel = repository.getCountryInfo();
        repository.getCountryInfo().observeForever( new Observer<UserContentResponse>() {
            @Override
            public void onChanged(@Nullable UserContentResponse mUserContentResponseModel) {
                final Contents contents = new Contents();

                contents.setTitle(mUserContentResponseModel.getTitle());
                contents.setRows(mUserContentResponseModel.getContentList());

                AsyncTask.execute(new Runnable() {
                      @Override
                      public void run() {
                          //adding to database
                          DatabaseClient.getInstance(MyApplication.getAppContext()).getAppDatabase()
                                  .userContentDao()
                                  .insert(contents);
                      }
                });
            }
        });
    }

    public LiveData<UserContentResponse> getCountryModelLiveData() {
        return mUserContentResponseModel;
    }

}
