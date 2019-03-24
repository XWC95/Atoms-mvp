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
package com.vea.atoms.mvp.gank.di.component;

import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.di.scope.ActivityScope;
import com.vea.atoms.mvp.gank.di.module.GankModule;
import com.vea.atoms.mvp.gank.mvp.contract.GirlContract;
import com.vea.atoms.mvp.gank.mvp.ui.fragment.GirlFragment;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * 展示 Component 的用法
 * <p>
 * Created by Vea on 09/04/2016 11:17
 * ================================================
 */
@ActivityScope
@Component(modules = GankModule.class, dependencies = AppComponent.class)
public interface GankComponent {

    void inject(GirlFragment activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GankComponent.Builder view(GirlContract.View view);

        GankComponent.Builder appComponent(AppComponent appComponent);

        GankComponent build();
    }
}
