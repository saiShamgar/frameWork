package com.android.dagger.di.main;

import com.android.dagger.ui.main.posts.PostsFragment;
import com.android.dagger.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();
}


