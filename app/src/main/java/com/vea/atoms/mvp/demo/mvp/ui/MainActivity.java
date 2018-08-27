package com.vea.atoms.mvp.demo.mvp.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.demo.R;
import com.vea.atoms.mvp.demo.di.component.DaggerDemoComponent;
import com.vea.atoms.mvp.demo.mvp.contract.MainContract;
import com.vea.atoms.mvp.demo.mvp.presenter.MainPersenter;
import com.vea.atoms.mvp.di.component.AppComponent;

import butterknife.BindView;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainPersenter> implements MainContract.View {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDemoComponent
            .builder()
            .appComponent(appComponent)
            .build()
            .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("MainActivity#onCreate");

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        Timber.d("initData");
        mPresenter.getUser(1, 10);
    }

    @Override
    public void getUserSuccess() {
        tv.setText("111");
        ARouter.getInstance().build("/gank/MainActivity").navigation();
    }
}
