package com.vea.atoms.mvp.gank.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vea.atoms.mvp.base.BaseFragment;
import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.di.component.DaggerGankComponent;
import com.vea.atoms.mvp.gank.mvp.contract.TechContract;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;
import com.vea.atoms.mvp.gank.mvp.presenter.TechPresenter;
import com.vea.atoms.mvp.gank.mvp.ui.IBaseShowItemList;
import com.vea.atoms.mvp.gank.mvp.ui.ListFactory;
import com.vea.atoms.mvp.gank.mvp.ui.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.gank.mvp.ui.adapter.TechAdapter;
import com.vea.atoms.mvp.gank.view.RecyclerRefreshLayout;


import java.util.List;

import butterknife.BindView;

/**
 * Created by xuwc on 2016/11/30.
 */
public class TechFragment extends BaseFragment<TechPresenter> implements  TechContract.View, RecyclerRefreshLayout.SuperRefreshLayoutListener {


    private String tech = "Android";

    private TechAdapter mAdapter;

    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipe_refresh)
    RecyclerRefreshLayout mRefreshLayout;

    private IBaseShowItemList<GankItemBean> baseShowItemList;


    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerGankComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tech;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mAdapter = new TechAdapter(getActivity());
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.post(() -> {
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        });
    }

    @Override
    public void getGankDataSuccess(List<GankItemBean> mList, boolean isRefresh) {
        baseShowItemList = new ListFactory<GankItemBean>().createShowItemList(mAdapter, mRefreshLayout);
        baseShowItemList.showData(isRefresh, mList);
    }


    @Override
    public void onRefreshing() {
        mPresenter.getGankData(tech,true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGankData(tech,false);
    }


}
