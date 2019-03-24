package com.vea.atoms.mvp.demo.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.vea.atoms.mvp.base.BaseActivity;
import com.vea.atoms.mvp.commonsdk.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.commonsdk.adapter.IBaseShowItemList;
import com.vea.atoms.mvp.commonsdk.adapter.ListFactory;
import com.vea.atoms.mvp.commonsdk.adapter.RecyclerRefreshLayout;
import com.vea.atoms.mvp.commonsdk.core.RouterHub;
import com.vea.atoms.mvp.demo.R;
import com.vea.atoms.mvp.demo.di.component.DaggerDemoComponent;
import com.vea.atoms.mvp.demo.mvp.adapter.ManListAdapter;
import com.vea.atoms.mvp.demo.mvp.contract.MainContract;
import com.vea.atoms.mvp.demo.mvp.model.entity.User;
import com.vea.atoms.mvp.demo.mvp.presenter.MainPresenter;
import com.vea.atoms.mvp.di.component.AppComponent;
import com.vea.atoms.mvp.utils.AtomsUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ================================================
 * 首页Activity，展示图片
 * <p>
 * Created by Vea on 2018/8/23.
 * ================================================
 */
@Route(path = RouterHub.APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, RecyclerRefreshLayout.SuperRefreshLayoutListener {

    private ManListAdapter adapter;

    private IBaseShowItemList<User> baseShowItemList;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    RecyclerRefreshLayout refreshLayout;

    @BindView(R.id.image_mouth)
    ImageView imageMouth;

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
        ARouter.getInstance().inject(this);

        initRecycleView();
        initRefreshLayout();
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

    /**
     * 初始化RecyclerView
     */
    private void initRecycleView() {
        adapter = new ManListAdapter(this);
        adapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);

        AtomsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getUserSuccess(List<User> users, boolean isRefresh) {
        if (users != null && users.size() > 0) {
            if (baseShowItemList == null) {
                baseShowItemList = new ListFactory<User>().createShowItemList(adapter, refreshLayout);
            }
            baseShowItemList.showData(isRefresh, users);
        }
    }

    @Override
    public void getUserFailure(boolean isRefresh) {
        refreshLayout.setRefreshing(false);
        if (isRefresh) {
            AtomsUtils.makeText(this, "刷新失败");
        } else {
            adapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);
        }
    }

    @Override
    public void onRefreshing() {
        mPresenter.getUser(1, true);
    }

    @Override
    public void onLoadMore() {
        // 总size-1 再减去底布局-1
        int id = adapter.getItem(adapter.getItemCount() - 1 - 1).getId();
        mPresenter.getUser(id, false);
    }

    @OnClick({R.id.image_mouth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_mouth:
                if (adapter.getItems() != null && adapter.getItems().size() > 10) {

                    /**
                     * 展示组件之间数据传递
                     * 一般情况下并不需要传递整个user对象，如果只需要某个key，name
                     * 只需设置withString(k,v)即可。 传递对象可使用withObject
                     */
                    User item = adapter.getItem(0);
                    ARouter.getInstance()
                            .build(RouterHub.GANK_MAIN_ACTIVITY)
                            .withObject("obj", item)
                            .navigation();
                }
                break;
        }
    }
}
