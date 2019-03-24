/*
 * Copyright 2017 Vea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vea.atoms.mvp.di.component;

import android.app.Application;
import android.content.Context;

import com.vea.atoms.mvp.app.AppDelegate;
import com.vea.atoms.mvp.di.module.AppModule;
import com.vea.atoms.mvp.di.module.ClientModule;
import com.vea.atoms.mvp.di.module.GlobalConfigModule;
import com.vea.atoms.mvp.integration.IRepositoryManager;
import com.vea.atoms.mvp.integration.cache.Cache;
import com.vea.atoms.mvp.utils.AtomsUtils;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * ================================================
 * 可通过 {@link AtomsUtils#obtainAppComponentFromContext(Context)} 拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * <p>
 * Created by Vea on 2016/11/24.
 * ================================================
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ClientModule.class,
        GlobalConfigModule.class,
})
public interface AppComponent {

    Application application();

    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    OkHttpClient okHttpClient();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Cache<String, Object> extras();

    //用于创建框架所需缓存对象的工厂
    Cache.Factory cacheFactory();

    void injectDelegate(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
