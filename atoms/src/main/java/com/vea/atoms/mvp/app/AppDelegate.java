package com.vea.atoms.mvp.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.di.component.DaggerAppComponent;
import com.vea.atoms.mvp.integration.ConfigModule;
import com.vea.atoms.mvp.integration.ManifestParser;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,因为 Java 只能单继承
 * 所以当遇到某些三方库需要继承于它的 Application 的时候,就只有自定义 Application 并继承于三方库的 Application
 * 这时就不用再继承 BaseApplication,只用在自定义Application中对应的生命周期调用AppDelegate对应的方法
 * (Application一定要实现APP接口),框架就能照常运行
 *
 * @see BaseApplication
 * ================================================
 */
public class AppDelegate implements IApp, AppLifecycles {

    private Application mApplication;
    private AppComponent mAppComponent;

    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();

    private List<ConfigModule> mModules;

    public AppDelegate(@NonNull Context context) {

        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        this.mModules = new ManifestParser(context).parse();

        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (ConfigModule module : mModules) {

            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(context, mAppLifecycles);
        }
    }

    @Override
    public void attachBaseContext(@NonNull Application application, @NonNull Context base) {

        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(application, base);
        }
    }

    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
            .builder()
            .application(mApplication)
            .build();
        mAppComponent.inject(this);

        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用, {@link AppComponent} 接口中声明的方法返回的实例, 在 {@link #getAppComponent()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}

