package com.android.dagger.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.dagger.R;
import com.android.dagger.databinding.FragmentPostsBinding;
import com.android.dagger.models.Post;
import com.android.dagger.ui.main.Resource;
import com.android.dagger.util.VerticalSpacingItemDecoration;
import com.android.dagger.viewModels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    @Inject
    PostsRecyclerAdapter adapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    VerticalSpacingItemDecoration itemDecoration;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private FragmentPostsBinding binding;

    private PostsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_posts,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= ViewModelProviders.of(this,viewModelProviderFactory).get(PostsViewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
            viewModel.observePosts().observe(this, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                    if (listResource!=null){
                       switch (listResource.status){
                           case LOADING:{
                               Log.d(TAG,"onChanged: Loading");
                               break;
                           }

                           case SUCCESS: {
                               Log.d(TAG,"onChanged: got Posts");
                               adapter.setPosts(listResource.data);
                               break;
                           }
                           case ERROR:
                           {
                               Log.e(TAG,"onChanged: error");
                               break;
                           }
                       }
                    }

                }
            });
    }

    private void initRecyclerView(){
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(itemDecoration);
        binding.recyclerView.setAdapter(adapter);
    }
}
