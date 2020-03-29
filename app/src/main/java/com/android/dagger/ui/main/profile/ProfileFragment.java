package com.android.dagger.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.dagger.R;
import com.android.dagger.databinding.FragmentProfileBinding;
import com.android.dagger.models.User;
import com.android.dagger.ui.auth.AuthResource;
import com.android.dagger.viewModels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private ProfileViewModel profileViewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private static final String TAG="ProfileFragment";

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG,"onViewCreated : Profile was created");

        profileViewModel= ViewModelProviders.of(this,viewModelProviderFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers(){
        profileViewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        profileViewModel.getAuthenticatedUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource!=null){
                    switch (userAuthResource.status){
                        case AUTHENTICATED:{
                            setUserDetails(userAuthResource.data);
                            break;
                        }
                        case ERROR:{
                            setErrorDetails(userAuthResource.message);
                            break;
                        }
                    }
                }
            }
        });

    }

    private void setErrorDetails(String message) {
        binding.email.setText(message);
        binding.username.setText("Error");
        binding.website.setText("Error");
    }

    private void setUserDetails(User data) {
        binding.email.setText(data.getEmail());
        binding.username.setText(data.getUsername());
        binding.website.setText(data.getWebsite());
    }
}
