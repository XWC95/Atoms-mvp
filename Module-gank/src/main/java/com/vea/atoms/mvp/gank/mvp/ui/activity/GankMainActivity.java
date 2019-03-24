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
package com.vea.atoms.mvp.gank.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.commonsdk.adapter.ViewPagerAdapterFragment;
import com.vea.atoms.mvp.commonsdk.core.RouterHub;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.mvp.ui.fragment.GirlFragment;

import org.vea.atoms.mvp.commonservice.IUserService;

import butterknife.BindView;
import timber.log.Timber;

/**
 * ================================================
 * 承载{@link GirlFragment}
 * <p>
 * Created by Vea on 2016/11/24
 * ================================================
 */
@Route(path = RouterHub.GANK_MAIN_ACTIVITY)
public class GankMainActivity extends BaseActivity {

    private ViewPagerAdapterFragment mAdapter;
    @BindView(R2.id.tab)
    TabLayout tablayout;
    @BindView(R2.id.viewpager)
    ViewPager viewpager;

    @Autowired
    IUserService obj;

    @Override
    protected int getLayoutId() {
        return R.layout.gank_activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        mAdapter = new ViewPagerAdapterFragment(getSupportFragmentManager(), this);
        mAdapter.addTab("福利", "tag", GirlFragment.class, null);
        viewpager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewpager);


        // 打印组件的数据传递
        if (obj != null) {
            Timber.d("--------id:" + obj.getId() + "----------AvatarUrl:" + obj.getAvatarUrl() + "----------Login:" + obj.getLogin());
        }
    }
}
