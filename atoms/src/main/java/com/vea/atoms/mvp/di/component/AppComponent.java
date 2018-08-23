package com.vea.atoms.mvp.di.component;

import android.app.Application;

import com.vea.atoms.mvp.app.AppDelegate;
import com.vea.atoms.mvp.di.modul.AppModule;
import com.vea.atoms.mvp.di.modul.ClientModule;
import com.vea.atoms.mvp.di.modul.GlobalConfigModule;
import com.vea.atoms.mvp.integration.IRepositoryManager;
import com.vea.atoms.mvp.integration.cache.Cache;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by xuwc on 2016/11/24.
 */

@Singleton
@Component(modules = {AppModule.class,ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {

    Application application();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();


    OkHttpClient okHttpClient();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Cache<String, Object> extras();

    //用于创建框架所需缓存对象的工厂
    Cache.Factory cacheFactory();


    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
