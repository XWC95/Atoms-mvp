package com.vea.atoms.mvp.gank.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vea.atoms.mvp.base.BaseFragment;
import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.gank.R;
import com.vea.atoms.mvp.gank.R2;
import com.vea.atoms.mvp.gank.di.component.DaggerGankComponent;
import com.vea.atoms.mvp.gank.mvp.contract.GirlContract;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;
import com.vea.atoms.mvp.gank.mvp.presenter.GirlPresenter;
import com.vea.atoms.mvp.commonsdk.adapter.IBaseShowItemList;
import com.vea.atoms.mvp.commonsdk.adapter.ListFactory;
import com.vea.atoms.mvp.commonsdk.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.gank.mvp.ui.adapter.GirlListAdapter;
import com.vea.atoms.mvp.commonsdk.adapter.RecyclerRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * ================================================
 * 福利图片
 *
 * Created by Vea on 2016/11/24
 * ================================================
 */
public class GirlFragment extends BaseFragment<GirlPresenter> implements GirlContract.View, RecyclerRefreshLayout.SuperRefreshLayoutListener {

    private GirlListAdapter adapter;

    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipe_refresh)
    RecyclerRefreshLayout refreshLayout;

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
        return R.layout.gank_fragment_tech;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        adapter = new GirlListAdapter(getActivity());
        adapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        refreshLayout.setSuperRefreshLayoutListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(adapter);

        refreshLayout.post(() -> {
            refreshLayout.setRefreshing(true);
            onRefreshing();
        });
    }

    @Override
    public void getGankDataSuccess(List<GankItemBean> mList, boolean isRefresh) {
        if(baseShowItemList == null){
            baseShowItemList = new ListFactory<GankItemBean>().createShowItemList(adapter, refreshLayout);
        }
        baseShowItemList.showData(isRefresh, mList);
    }

    @Override
    public void onRefreshing() {
        mPresenter.getGankData(true);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGankData(false);
    }
}
