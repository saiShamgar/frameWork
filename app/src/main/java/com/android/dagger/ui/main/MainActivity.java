package com.android.dagger.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.dagger.BaseActivity;
import com.android.dagger.R;
import com.android.dagger.databinding.ActivityMainBinding;
import com.android.dagger.ui.main.posts.PostsFragment;
import com.android.dagger.ui.main.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG="MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        init();

    }

    private void init(){
        NavController navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navView,navController);
        binding.navView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                sessionManager.logout();
                return true;

            case android.R.id.home:{
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                else {
                    return false;
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile:{
                NavOptions navOptions=new NavOptions.Builder()
                        .setPopUpTo(R.id.main,true)
                        .build();
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.profileScreen,
                        null,
                        navOptions);
                break;
            }
            case R.id.nav_posts:{
                if (isValidDestination(R.id.postsScreen))
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.postsScreen);
                break;
            }
        }

        menuItem.setChecked(true);
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment),binding.drawerLayout);
    }

    private boolean isValidDestination(int destination){
            return destination!=Navigation.findNavController(this,R.id.nav_host_fragment).getCurrentDestination().getId();
    }
}
