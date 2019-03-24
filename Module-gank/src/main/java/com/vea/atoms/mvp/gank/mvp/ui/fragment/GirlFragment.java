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
package com.vea.atoms.mvp.gank.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vea.atoms.mvp.base.BaseFragment;
import com.vea.atoms.mvp.commonsdk.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.commonsdk.adapter.IBaseShowItemList;
import com.vea.atoms.mvp.commonsdk.adapter.ListFactory;
import com.vea.atoms.mvp.commonsdk.adapter.RecyclerRefreshLayout;
import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.di.component.DaggerGankComponent;
import com.vea.atoms.mvp.gank.mvp.contract.GirlContract;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;
import com.vea.atoms.mvp.gank.mvp.presenter.GirlPresenter;
import com.vea.atoms.mvp.gank.mvp.ui.adapter.GirlListAdapter;
import com.vea.atoms.mvp.utils.AtomsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * ================================================
 * 福利图片
 * <p>
 * Created by Vea on 2016/11/24
 * ================================================
 */
public class GirlFragment extends BaseFragment<GirlPresenter> implements GirlContract.View, RecyclerRefreshLayout.SuperRefreshLayoutListener {

    @Inject
    GirlListAdapter adapter;


    // 如果不是必须要使用还可以使用Lzay
//    @Inject
//    Lazy<GirlListAdapter> adapter;

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.swipe_refresh)
    RecyclerRefreshLayout refreshLayout;

    private IBaseShowItemList<GankItemBean> baseShowItemList;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

        DaggerGankComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.gank_fragment_tech;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        initRecycleView();
        initRefreshLayout();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecycleView() {

        AtomsUtils.configRecyclerView(recyclerView, new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化下拉刷新加载更多
     */
    private void initRefreshLayout() {
        refreshLayout.setSuperRefreshLayoutListener(this);
        refreshLayout.post(() -> {
            refreshLayout.setRefreshing(true);
            onRefreshing();
        });
    }

    @Override
    public void getGankDataSuccess(List<GankItemBean> mList, boolean isRefresh) {
        if (baseShowItemList == null) {
            baseShowItemList = new ListFactory<GankItemBean>().createShowItemList(adapter, refreshLayout);
        }
        baseShowItemList.showData(isRefresh, mList);
    }

    @Override
    public void getGankDataFailure(boolean isRefresh) {
        refreshLayout.setRefreshing(false);
        if (isRefresh) {
            AtomsUtils.makeText(getActivity(), "刷新失败");
        } else {
            adapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    @Override
    public void onRefreshing() {
        mPresenter.getGankData(true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGankData(false);
    }

    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }
}
