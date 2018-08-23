package com.vea.atoms.mvp.app;

import android.support.annotation.NonNull;

import com.vea.atoms.mvp.di.component.AppComponent;

/**
 * Created by xuwc on 2018/8/18.
 */
public interface IApp {

    @NonNull
    AppComponent getAppComponent();

}
