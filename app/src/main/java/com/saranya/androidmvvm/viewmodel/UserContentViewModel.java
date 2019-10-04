package com.saranya.androidmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.saranya.androidmvvm.api.ApiComponent;
import com.saranya.androidmvvm.api.DaggerApiComponent;
import com.saranya.androidmvvm.dimodule.ApiModule;
import com.saranya.androidmvvm.dimodule.AppModule;
import com.saranya.androidmvvm.model.UserContentResponse;

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
    }

    public LiveData<UserContentResponse> getCountryModelLiveData() {
        return mUserContentResponseModel;
    }

}
