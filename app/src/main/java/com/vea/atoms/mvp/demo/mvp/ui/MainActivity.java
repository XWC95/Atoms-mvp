package com.vea.atoms.mvp.demo.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.demo.R;
import com.vea.atoms.mvp.demo.di.component.DaggerDemoComponent;
import com.vea.atoms.mvp.demo.mvp.contract.MainContract;
import com.vea.atoms.mvp.demo.mvp.presenter.MainPersenter;
import com.vea.atoms.mvp.di.component.AppComponent;

public class MainActivity extends BaseActivity<MainPersenter> implements MainContract.View {

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDemoComponent
            .builder()
            .appComponent(appComponent)
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

    @Override
    public void getUserSuccess() {
        ARouter.getInstance().build("/gank/MainActivity").navigation();
    }
}
