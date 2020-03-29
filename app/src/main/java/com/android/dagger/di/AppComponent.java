package com.android.dagger.di;

import android.app.Application;

import com.android.dagger.BaseApplication;
import com.android.dagger.SessionManager;
import com.android.dagger.viewModels.ViewModelProviderFactory;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

}

