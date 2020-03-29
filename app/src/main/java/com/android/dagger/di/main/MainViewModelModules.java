package com.android.dagger.di.main;

import androidx.lifecycle.ViewModel;

import com.android.dagger.di.ViewModelKey;
import com.android.dagger.ui.main.posts.PostsViewModel;
import com.android.dagger.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModules {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel profileViewModel);
}
