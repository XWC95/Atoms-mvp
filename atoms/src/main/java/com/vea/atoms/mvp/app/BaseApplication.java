package com.vea.atoms.mvp.app;

import android.app.Application;
import android.content.Context;

import com.vea.atoms.mvp.di.component.AppComponent;

/**
 * Created by xwc on 2018/8/17.
 */

public class BaseApplication extends Application implements IApp {

    private AppLifecycles mAppDelegate;

    private static BaseApplication instance;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(this, base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    public AppComponent getAppComponent() {
        return ((IApp) mAppDelegate).getAppComponent();
    }
}
