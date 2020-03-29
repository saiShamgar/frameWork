package com.android.dagger.di.auth;

import androidx.lifecycle.ViewModel;

import com.android.dagger.di.ViewModelKey;
import com.android.dagger.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
