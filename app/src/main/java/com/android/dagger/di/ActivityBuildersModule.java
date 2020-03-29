package com.android.dagger.di;

import com.android.dagger.di.auth.AuthModule;
import com.android.dagger.di.auth.AuthScope;
import com.android.dagger.di.auth.AuthViewModelsModule;
import com.android.dagger.di.main.MainFragmentsBuilderModule;
import com.android.dagger.di.main.MainModule;
import com.android.dagger.di.main.MainScope;
import com.android.dagger.di.main.MainViewModelModules;
import com.android.dagger.ui.auth.AuthActivity;
import com.android.dagger.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = {
            AuthViewModelsModule.class,
            AuthModule.class,
    })
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {
            MainFragmentsBuilderModule.class,
            MainViewModelModules.class,
            MainModule.class
    })
    abstract MainActivity contributeMainActivity();


}
