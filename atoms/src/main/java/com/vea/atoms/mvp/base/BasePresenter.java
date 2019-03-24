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

import com.vea.atoms.mvp.utils.Preconditions;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ================================================
 * Presenter基类
 * <p>
 * Created by Vea on 2016/11/24.
 * ================================================
 */
public class BasePresenter<T extends IView> implements IPresenter<T> {

    protected WeakReference<T> mView;

    CompositeDisposable mDisposables;


    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BasePresenter(T rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mView = new WeakReference<T>(rootView);
    }

    public BasePresenter() {

    }


    @Override
    public void attachView(T view) {
        if (mView == null) {
            mView = new WeakReference<T>(view);
        }
    }

    @Override
    public void detachView() {
        mView.clear();
        mView = null;
        dispose();
    }

    public T getView() {
        if (mView != null) {
            return mView.get();
        }
        return null;
    }

    //取消所有的订阅
    public void dispose() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    protected void addSubscription(Disposable disposable) {
        if (disposable == null) return;
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }
}
