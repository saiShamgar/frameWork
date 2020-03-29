package com.android.dagger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.android.dagger.models.User;
import com.android.dagger.ui.auth.AuthActivity;
import com.android.dagger.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG="BaseActivity";

    @Inject
     public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:
                            break;

                        case ERROR:
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                            break;

                        case AUTHENTICATED:
                          //  Toast.makeText(getApplicationContext(),"Login Success "+userAuthResource.data.getEmail(),Toast.LENGTH_SHORT).show();
                            break;

                        case NOT_AUTHENTICATED:
                            Toast.makeText(getApplicationContext(),"Not authenticated",Toast.LENGTH_SHORT).show();
                            navLoginScreen();
                            break;
                    }
                }
            }
        });
    }

    private void navLoginScreen(){
        Intent intent=new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
