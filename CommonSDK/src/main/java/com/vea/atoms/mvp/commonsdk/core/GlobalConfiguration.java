/*
 * Copyright 2018 Vea
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
package com.vea.atoms.mvp.commonsdk.core;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.vea.atoms.mvp.app.AppLifecycles;
import com.vea.atoms.mvp.commonsdk.BuildConfig;
import com.vea.atoms.mvp.commonsdk.http.Api;
import com.vea.atoms.mvp.di.module.GlobalConfigModule;
import com.vea.atoms.mvp.http.log.RequestInterceptor;
import com.vea.atoms.mvp.integration.ConfigModule;
import com.vea.atoms.mvp.utils.AtomsUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;


/**
 * ================================================
 * CommonSDK 的 GlobalConfiguration 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * Created by Vea on 30/03/2018 17:16
 * ================================================
 */
public final class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.DEBUG) { //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(Api.APP_DOMAIN)

                //想支持多 BaseUrl, 以及运行时动态切换任意一个 BaseUrl, 请使用 https://github.com/JessYanCoding/RetrofitUrlManager
                //如果 BaseUrl 在 App 启动时不能确定, 需要请求服务器接口动态获取, 请使用以下代码
                //以下方式是 Arms 框架自带的切换 BaseUrl 的方式, 在整个 App 生命周期内只能切换一次, 若需要无限次的切换 BaseUrl, 以及各种复杂的应用场景还是需要使用 RetrofitUrlManager 框架
                //以下代码只是配置, 还要使用 Okhttp (AppComponent中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration.sDomain
                //切记整个过程必须在第一次调用 Retrofit 接口之前完成, 如果已经调用过 Retrofit 接口, 此种方式将不能切换 BaseUrl
//                .baseurl(new BaseUrl() {
//                    @Override
//                    public HttpUrl url() {
//                        return HttpUrl.parse(sDomain);
//                    }
//                })

                //可根据当前项目的情况以及环境为框架某些部件提供自定义的缓存策略, 具有强大的扩展性
//                .cacheFactory(new Cache.Factory() {
//                    @NonNull
//                    @Override
//                    public Cache build(CacheType type) {
//                        switch (type.getCacheTypeId()){
//                            case CacheType.EXTRAS_TYPE_ID:
//                                return new IntelligentCache(500);
//                            case CacheType.CACHE_SERVICE_CACHE_TYPE_ID:
//                                return new Cache(type.calculateCacheSize(context));//自定义 Cache
//                            default:
//                                return new LruCache(200);
//                        }
//                    }
//                })

                //若觉得框架默认的打印格式并不能满足自己的需求, 可自行扩展自己理想的打印格式 (以下只是简单实现)
//                .formatPrinter(new FormatPrinter() {
//                    @Override
//                    public void printJsonRequest(Request request, String bodyString) {
//                        Timber.i("printJsonRequest:" + bodyString);
//                    }
//
//                    @Override
//                    public void printFileRequest(Request request) {
//                        Timber.i("printFileRequest:" + request.url().toString());
//                    }
//
//                    @Override
//                    public void printJsonResponse(long chainMs, boolean isSuccessful, int code,
//                                                  String headers, MediaType contentType, String bodyString,
//                                                  List<String> segments, String message, String responseUrl) {
//                        Timber.i("printJsonResponse:" + bodyString);
//                    }
//
//                    @Override
//                    public void printFileResponse(long chainMs, boolean isSuccessful, int code, String headers,
//                                                  List<String> segments, String message, String responseUrl) {
//                        Timber.i("printFileResponse:" + responseUrl);
//                    }
//                })

                // 这里提供一个全局处理 Http 请求和响应结果的处理类,可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))

                .retrofitConfiguration((appContext, retrofitBuilder) -> {//这里可以自己自定义配置Retrofit的参数, 甚至您可以替换框架配置好的 OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)
//                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用fastjson替代gson
                })
                .okhttpConfiguration((appContext, okhttpBuilder) -> {//这里可以自己自定义配置Okhttp的参数
//                    okhttpBuilder.sslSocketFactory(); //支持 Https,详情请百度

                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 不需要可以删掉。详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(okhttpBuilder);
                    //cache
                    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
                        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                        cacheBuilder.maxStale(365,TimeUnit.DAYS);
                        CacheControl cacheControl = cacheBuilder.build();
                        Request request = chain.request();
                        if(!AtomsUtils.isNetWorkAvailable(appContext)){
                            request = request.newBuilder()
                                .cacheControl(cacheControl)
                                .build();
                        }
                        Response originalResponse = chain.proceed(request);
                        if (AtomsUtils.isNetWorkAvailable(appContext)) {
                            int maxAge = 0; // read from cache
                            return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "public ,max-age=" + maxAge)
                                .build();
                        } else {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                            return originalResponse.newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                        }
                    };
                    //cache url
                    File httpCacheDirectory = new File(appContext.getCacheDir(), "responses");
                    int cacheSize = 10 * 1024 * 1024; // 10 MiB
                    Cache cache = new Cache(httpCacheDirectory, cacheSize);
                    okhttpBuilder .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                        .cache(cache).build();
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {

            @Override
            public void attachBaseContext(@NonNull Application application, @NonNull Context base) {

            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);

                }
                ARouter.init(application); // 尽可能早,推荐在Application中初始化
            }

            @Override
            public void onTerminate(@NonNull Application application) {

            }
        });
    }


}
