package com.vea.atoms.mvp.app;

import android.support.annotation.NonNull;

import com.vea.atoms.mvp.di.component.AppComponent;


/**
 * ================================================
 * 框架要求框架中的每个 {@link android.app.Application} 都需要实现此类, 以满足规范
 *
 * @see BaseApplication
 * Created by vea on 2018/8/20
 * ================================================
 */
public interface IApp {

    @NonNull
    AppComponent getAppComponent();

}
