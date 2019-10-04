package com.saranya.androidmvvm.dimodule;


import com.saranya.androidmvvm.rx.RXClient;
import com.saranya.androidmvvm.utils.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public
class AppModule {

    @Provides
    ApiModule provideRepository(){
        return new ApiModule();
    }

    @Provides
    @Singleton
    RXClient provideRXClient(@Named("serviceURL") String url){
        return new RXClient(url);
    }

    @Provides
    @Named("serviceURL")
    String provideServerUrl() {
        return Constants.BASE_URL;
    }


}
