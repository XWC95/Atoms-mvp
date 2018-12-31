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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.utils.AtomsUtils;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * MVP Fragment基类
 * <p>
 * Created by Vea on 2016/11/24.
 * ================================================
 */
public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    @Inject
    protected T mPresenter;

    private Unbinder mUnBinder;

    private BaseActivity mBaseActivity;

    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupFragmentComponent(AtomsUtils.obtainAppComponentFromContext(getActivity()));
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        mUnBinder = ButterKnife.bind(this, view);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    protected boolean useEventBus() {
        return true;
    }

    protected abstract int getLayoutId();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void showMessage(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoading() {
        mBaseActivity.showLoading();
    }

    @Override
    public void hideLoading() {
        mBaseActivity.hideLoading();
    }

    @Override
    public void closePage() {

    }
}
