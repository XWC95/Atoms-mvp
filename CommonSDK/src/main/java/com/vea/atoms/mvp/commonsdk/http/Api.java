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
package com.vea.atoms.mvp.commonsdk.http;

/**
 * ================================================
 * CommonSDK 的 Api 可以定义公用的关于 API 的相关常量, 比如说请求地址, 错误码等, 每个组件的 Api 可以定义组件自己的私有常量
 * <p>
 * Created by Vea on 30/03/2018 17:16
 * ================================================
 */
public interface Api {
    String APP_DOMAIN = "https://api.github.com";


}
