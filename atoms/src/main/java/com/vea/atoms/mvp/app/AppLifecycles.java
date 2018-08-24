package com.vea.atoms.mvp.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ================================================
 * 用于代理 {@link Application} 的生命周期
 *
 * @see AppDelegate
 * Created by vea on 2018/8/20
 * ================================================
 */
public interface AppLifecycles {

    /**
     *
     * @param application 最初设计上没有这个参数，为了给插件化调用
     * @param base Context
     */
    void attachBaseContext(@NonNull Application application, @NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}