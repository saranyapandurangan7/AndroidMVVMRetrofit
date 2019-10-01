package com.saranya.androidmvvm.API;

import com.saranya.androidmvvm.model.UserContentResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface UserContentService {

    @GET
    Observable<UserContentResponse> fetchUsersContent(@Url String url);
}
