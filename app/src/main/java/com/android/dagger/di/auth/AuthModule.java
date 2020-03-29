package com.android.dagger.di.auth;

import com.android.dagger.models.User;
import com.android.dagger.network.auth.AuthApi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

    @AuthScope
    @Provides
    @Named("auth_user")
    static User someUser(){
        return new User();
    }
}
