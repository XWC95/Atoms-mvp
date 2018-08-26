package com.vea.atoms.mvp.gank.mvp.ui;

import com.vea.atoms.mvp.gank.mvp.ui.adapter.BaseRecyclerAdapter;
import com.vea.atoms.mvp.gank.view.RecyclerRefreshLayout;


/**
 * Created by xwc on 2018/4/18.
 */

public class ListFactory<T> {


    public IBaseShowItemList<T> createShowItemList(BaseRecyclerAdapter<T> adapter, RecyclerRefreshLayout refresh) {
        return new ShowItemList<T>(adapter, refresh);
    }
}
