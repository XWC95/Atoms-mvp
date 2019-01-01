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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.utils.AtomsUtils;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * MVP activity基类
 * <p>
 * Created by Vea on 2016/11/24.
 * ================================================
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    @Inject
    protected T mPresenter;

    private Unbinder mUnBinder;
    protected View mLoadingView;
    private AVLoadingIndicatorView mAVLoadingIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int layoutResID = getLayoutId();
        //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
        if (layoutResID != 0) {
            setContentView(layoutResID);
            //绑定到butterknife
            mUnBinder = ButterKnife.bind(this);
        }
        setupActivityComponent(AtomsUtils.obtainAppComponentFromContext(this));
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        mUnBinder.unbind();
    }

    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void showLoading() {
        if (mLoadingView == null) {
            if (getLoadingLayoutId() != 0 && getLoadingViewId() != 0) {
                initLoadingView();
            }
        }

        if (mLoadingView != null) {
            mLoadingView.bringToFront();
            mLoadingView.setVisibility(View.VISIBLE);
            mAVLoadingIndicatorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            mAVLoadingIndicatorView.setVisibility(View.GONE);
        }
    }

    protected int getLoadingLayoutId() {
        return 0;
    }

    protected int getLoadingViewId() {
        return 0;
    }

    private void initLoadingView() {
        mLoadingView = LayoutInflater.from(this).inflate(
                getLoadingLayoutId(), null);
        mAVLoadingIndicatorView = mLoadingView.findViewById(getLoadingViewId());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mLoadingView.setOnTouchListener((view, motionEvent) -> true);
        mAVLoadingIndicatorView.show();
        addContentView(mLoadingView, params);
    }

    protected abstract int getLayoutId();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
