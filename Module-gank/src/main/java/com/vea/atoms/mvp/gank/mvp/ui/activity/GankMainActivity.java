package com.vea.atoms.mvp.gank.mvp.ui.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.base.ViewPagerAdapterFragment;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.mvp.ui.fragment.TechFragment;

import butterknife.BindView;

@Route(path = "/gank/MainActivity")
public class GankMainActivity extends BaseActivity {

    private ViewPagerAdapterFragment mAdapter;

    String[] tabTitle = new String[]{"Android", "福利"};
    String[] tabTag = new String[]{"wo", "cao"};

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
        mAdapter.addTab(tabTitle[0], tabTag[0], TechFragment.class, null);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);
    }
}
