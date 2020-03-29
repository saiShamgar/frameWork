package com.android.dagger;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.android.dagger.models.User;
import com.android.dagger.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG="SessionManager";
    private MediatorLiveData<AuthResource<User>> cacheUser=new MediatorLiveData<>();

    @Inject
    public SessionManager(){

    }

    public void authenticationWithId(final LiveData<AuthResource<User>> source){
        if (cacheUser!=null){
            cacheUser.setValue(AuthResource.loading((User)null));
            cacheUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cacheUser.setValue(userAuthResource);
                    cacheUser.removeSource(source);
                }
            });
        }
    }

    public void logout(){
        Log.d(TAG,"logout: logging out");
        cacheUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return cacheUser;
    }

}
