package com.saranya.androidmvvm.api;

import com.saranya.androidmvvm.model.UserContentResponse;

import retrofit2.http.GET;
import rx.Observable;


public interface UserContentService {

    @GET("facts.json")
    Observable<UserContentResponse> fetchUsersContent();
}
