package com.android.dagger.di.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dagger.R;
import com.android.dagger.network.main.MainApi;
import com.android.dagger.ui.main.posts.PostsFragment;
import com.android.dagger.ui.main.posts.PostsRecyclerAdapter;
import com.android.dagger.util.VerticalSpacingItemDecoration;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostsRecyclerAdapter postsRecyclerAdapter(){
        return new PostsRecyclerAdapter();
    }
    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return  retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static VerticalSpacingItemDecoration provideItemDecoration(){
        return new VerticalSpacingItemDecoration(15);
    }

    @MainScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(Application application){
        return new LinearLayoutManager(application);
    }
}
