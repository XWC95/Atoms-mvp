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
package com.vea.atoms.mvp.base;


/**
 * ================================================
 * 框架要求框架中的每个 Presenter 都需要实现此类,以满足规范
 *
 * @see BasePresenter
 * Created by Vea on 2016/11/24.
 * ================================================
 */
public interface IPresenter<T extends IView> {

    void attachView(T view);

    void detachView();

}