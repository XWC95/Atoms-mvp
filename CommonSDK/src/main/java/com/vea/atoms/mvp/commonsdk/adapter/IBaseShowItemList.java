package com.vea.atoms.mvp.commonsdk.adapter;

import java.util.List;

/**
 * Created by xwc on 2018/4/18.
 */

public interface IBaseShowItemList<T> {

    /**
     * @param isRefresh 是否刷新
     * @param data      数据
     */
    void showData(boolean isRefresh, List<T> data);
}
