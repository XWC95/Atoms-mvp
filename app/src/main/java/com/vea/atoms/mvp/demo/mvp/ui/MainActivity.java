package com.vea.atoms.mvp.demo.mvp.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vea.atoms.mvp.app.BaseApplication;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.demo.R;
import com.vea.atoms.mvp.demo.di.component.DaggerDemoComponent;
import com.vea.atoms.mvp.demo.di.module.DemoModule;
import com.vea.atoms.mvp.demo.mvp.contract.MainContract;
import com.vea.atoms.mvp.demo.mvp.presenter.MainPersenter;

public class MainActivity extends BaseActivity<MainPersenter> implements MainContract.View {

    @Override
    protected void setupActivityComponent() {

        DaggerDemoComponent
            .builder()
            .appComponent(BaseApplication.getInstance().getAppComponent())
            .build()
            .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getUser(1, 10);
    }
}
