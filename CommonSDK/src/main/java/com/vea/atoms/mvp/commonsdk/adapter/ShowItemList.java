package com.vea.atoms.mvp.commonsdk.adapter;

import java.util.List;

/**
 * Created by xwc on 2018/4/18.
 */

public class ShowItemList<T> implements IBaseShowItemList<T> {

    public static int NUM_OF_PAGE = 20;

    private BaseRecyclerAdapter<T> adapter;

    private RecyclerRefreshLayout refreshLayout;

    public ShowItemList(BaseRecyclerAdapter<T> adapter, RecyclerRefreshLayout refreshLayout) {
        this.adapter = adapter;
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void showData(boolean isRefresh, List<T> data) {
        if (isRefresh) refresh(data);
        else more(data);
    }

    private void more(List<T> data) {

        adapter.addAll(data);
        if (data.size() < NUM_OF_PAGE) {
            showNoMoreView();
        } else {
            showHasMoreViw();
        }
        refreshLayout.onComplete();
    }

    private void refresh(List<T> data) {

        adapter.clear();
        adapter.addAll(data);

        if (data.size() < NUM_OF_PAGE) {
            showNoMoreView();
        } else {
            showHasMoreViw();
        }
        refreshLayout.onComplete();
    }

    private void showNoMoreView() {
        adapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
        refreshLayout.setCanLoadMore(false);
    }

    private void showHasMoreViw() {
        refreshLayout.setCanLoadMore(true);
        adapter.setState(BaseRecyclerAdapter.STATE_LOAD_MORE, true);
    }
}
