package com.vea.atoms.mvp.demo.mvp.model.service;

import com.vea.atoms.mvp.demo.mvp.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


/**
 * ================================================
 * 服务端Api相关接口定义
 * <p>
 * Created by Vea on 2018/8/23.
 * ================================================
 */
public interface DemoService {

    String APP_DOMAIN = "https://api.github.com";
    String RequestSuccess = "0";


    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
}
