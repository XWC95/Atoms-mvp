package com.vea.atoms.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * ================================================
 * MVP activity基类
 *
 * Created by vea on 2016/11/24.
 * ================================================
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements BaseView {

    @Inject
    protected T mPresenter;

    private Unbinder mUnBinder;

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

        setupActivityComponent();
        if (mPresenter != null)
            mPresenter.attachView(this);

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

    protected void setupActivityComponent() {

    }

    protected abstract int getLayoutId();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
