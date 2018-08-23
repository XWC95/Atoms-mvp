package com.vea.atoms.mvp.http;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vea on 2016/11/24.
 */
public class RetrofitHelper {

    private static OkHttpClient sOkHttpClient = null;
    private static onlyApis sOnlyApis = null;

    public RetrofitHelper() {
        init();
    }

    private void init() {
        initOkHttp();
        sOnlyApis = getOnlyApiService();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        if (BuildConfig.DEBUG) {
//            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            builder.addInterceptor(loggingInterceptor);
//        }
//        // http://www.jianshu.com/p/93153b34310e
//        File cacheFile = new File(Constants.PATH_CACHE);
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//        Interceptor cacheInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!SystemUtil.isNetworkConnected()) {
//                    request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//                }
//                Response response = chain.proceed(request);
//                if (SystemUtil.isNetworkConnected()) {
//                    int maxAge = 0;
//                    // 正常访问同一请求接口（多次访问同一接口），给10秒缓存，超过时间重新发送请求，否则取缓存数据
//                    response.newBuilder()
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .removeHeader("Pragma")
//                        .build();
//                } else {
//                    // 无网络时，设置超时为4周
//                    int maxStale = 60 * 60 * 24 * 28;
//                    response.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .removeHeader("Pragma")
//                        .build();
//                }
//                return response;
//            }
//        };
//        Interceptor parameters = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                HttpUrl originalHttpUrl = original.url();
//                HttpUrl url = originalHttpUrl.newBuilder()
//                    .addQueryParameter("token", SPUtil.getToken())
//                    .build();
//                Request.Builder requestBuilder = original.newBuilder()
//                    .url(url);
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        };
//        builder.addNetworkInterceptor(cacheInterceptor);
//        builder.addInterceptor(parameters);
//        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(6, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    private static onlyApis getOnlyApiService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(sOnlyApis.HOST)
            .client(sOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
        return retrofit.create(onlyApis.class);
    }

    /**
     * 初始化通用的观察者
     *
     * @param observable 观察者
     */
    public ResourceSubscriber startObservable(Flowable observable, ResourceSubscriber subscriber) {
        return (ResourceSubscriber) observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(subscriber);
    }


}
