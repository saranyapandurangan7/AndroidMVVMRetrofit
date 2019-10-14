package com.saranya.androidmvvm.dimodule;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.saranya.androidmvvm.api.ApiComponent;
import com.saranya.androidmvvm.api.DaggerApiComponent;
import com.saranya.androidmvvm.model.UserContentResponse;
import com.saranya.androidmvvm.rx.RXClient;

import javax.inject.Inject;

import dagger.Module;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public
class ApiModule {

    private UserContentResponse mUserContentResponseModel;

    @Inject
    public ApiModule() {
        ApiComponent appComponent = DaggerApiComponent
                .builder()
                .appModule(new AppModule())
                .build();
        appComponent.inject(this);
    }

    @Inject
    RXClient rxClient;

    public LiveData<UserContentResponse> getCountryInfo() {
        final MutableLiveData<UserContentResponse> data = new MutableLiveData<>();
        rxClient.fetchCountryDetails().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserContentResponse>() {

            @Override
            public void onCompleted() {
                data.postValue(mUserContentResponseModel);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserContentResponse countryModel) {
                mUserContentResponseModel = countryModel;
            }
        });
        return data;
    }
}
