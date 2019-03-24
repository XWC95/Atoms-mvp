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
package com.vea.atoms.mvp.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ================================================
 * 用于代理 {@link Application} 的生命周期
 *
 * @see AppDelegate
 * Created by Vea on 2018/8/20
 * ================================================
 */
public interface AppLifecycles {

    /**
     * @param application 最初设计上没有这个参数，为了给插件化调用。如何插件化可参考360RePlugin
     * @param base        Context
     */
    void attachBaseContext(@NonNull Application application, @NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}