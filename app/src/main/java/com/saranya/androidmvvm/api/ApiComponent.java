package com.saranya.androidmvvm.api;

import com.saranya.androidmvvm.dimodule.ApiModule;
import com.saranya.androidmvvm.dimodule.AppModule;
import com.saranya.androidmvvm.fragment.NewsFragment;
import com.saranya.androidmvvm.viewmodel.UserContentViewModel;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, ApiModule.class})

public interface ApiComponent {

    void inject(NewsFragment fragment);

    void inject(ApiModule repository);

    void inject(UserContentViewModel userContentViewModel);

}