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
package com.vea.atoms.mvp.di.module;


import com.vea.atoms.mvp.integration.IRepositoryManager;
import com.vea.atoms.mvp.integration.RepositoryManager;
import com.vea.atoms.mvp.integration.cache.Cache;
import com.vea.atoms.mvp.integration.cache.CacheType;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 提供一些框架必须的实例的 {@link Module}
 * <p>
 * Created by Vea on 2016/11/24.
 * ================================================
 */
@Module
public abstract class AppModule {

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);


    @Singleton
    @Provides
    static Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS);
    }

}