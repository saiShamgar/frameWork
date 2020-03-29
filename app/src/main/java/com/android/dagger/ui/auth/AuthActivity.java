package com.android.dagger.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.dagger.R;
import com.android.dagger.databinding.ActivityAuthBinding;
import com.android.dagger.models.User;
import com.android.dagger.ui.main.MainActivity;
import com.android.dagger.viewModels.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {


    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    @Named("app_user")
    User userNumber1;

    @Inject
    @Named("auth_user")
    User userNumber2;

    private ActivityAuthBinding binding;
    private AuthViewModel viewModel;

    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this, R.layout.activity_auth);
       viewModel=ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);

        Log.d(TAG, "onCreate: " + userNumber1);
        Log.d(TAG, "onCreate: " + userNumber2);
       setLogo();
       setListeners();
       subscribeObservers();
    }

    private void setListeners() {
        binding.loginButton.setOnClickListener(this);
    }

    private void setLogo() {
        requestManager.load(logo).into(binding.loginLogo);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==binding.loginButton.getId()){
            attempLogin();

        }

    }

    private void attempLogin() {
        if (TextUtils.isEmpty(binding.userIdInput.getText().toString())){
            return;
        }

        viewModel.authenticateWithId(Integer.parseInt(binding.userIdInput.getText().toString()));
    }

    private void subscribeObservers(){
        viewModel.onbserveAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:
                            showProgressBar(true);
                            break;

                        case ERROR:
                            showProgressBar(false);
                            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                            break;

                        case AUTHENTICATED:
                            showProgressBar(false);
                            loginSuccess();
                            break;

                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            Toast.makeText(getApplicationContext(),"Not authenticated",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }
        });
    }

    private void showProgressBar(boolean isVisible){
        if (isVisible){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void loginSuccess(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
