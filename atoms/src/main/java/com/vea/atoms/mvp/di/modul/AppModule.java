package com.vea.atoms.mvp.di.modul;


import com.vea.atoms.mvp.integration.IRepositoryManager;
import com.vea.atoms.mvp.integration.RepositoryManager;
import com.vea.atoms.mvp.integration.cache.Cache;
import com.vea.atoms.mvp.integration.cache.CacheType;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by xuwc on 2016/11/24.
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