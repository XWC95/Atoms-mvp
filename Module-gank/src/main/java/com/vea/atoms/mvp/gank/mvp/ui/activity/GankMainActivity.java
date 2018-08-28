package com.vea.atoms.mvp.gank.mvp.ui.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.base.ViewPagerAdapterFragment;
import com.vea.atoms.mvp.commonsdk.core.RouterHub;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.mvp.ui.fragment.GirlFragment;

import butterknife.BindView;
import timber.log.Timber;

@Route(path = RouterHub.GANK_MAIN_ACTIVITY)
public class GankMainActivity extends BaseActivity {

    private ViewPagerAdapterFragment mAdapter;

    @BindView(R2.id.tab)
    TabLayout tablayout;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;

    @Override
    protected int getLayoutId() {
        return R.layout.gank_activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mAdapter = new ViewPagerAdapterFragment(getSupportFragmentManager(), this);
        mAdapter.addTab("福利", "tag", GirlFragment.class, null);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
    }
}
