package com.android.dagger.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.dagger.SessionManager;
import com.android.dagger.models.User;
import com.android.dagger.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG="ProfileViewModel";
    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager){
        this.sessionManager=sessionManager;
        Log.d(TAG,"Profie view model is ready");

    }

    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }
}
