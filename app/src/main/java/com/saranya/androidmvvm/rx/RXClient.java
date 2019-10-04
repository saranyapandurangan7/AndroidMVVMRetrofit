package com.saranya.androidmvvm.rx;

import com.saranya.androidmvvm.api.UserContentService;
import com.saranya.androidmvvm.model.UserContentResponse;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RXClient  {

    private UserContentService service;

    @Inject
    public RXClient(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).
                client(okHttpClient).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(UserContentService.class);
    }

    public Observable<UserContentResponse> fetchCountryDetails() {
        return service.fetchUsersContent();
    }
}
