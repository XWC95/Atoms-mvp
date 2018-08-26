package com.vea.atoms.mvp.gank.mvp.contract;


import com.vea.atoms.mvp.base.IView;
import com.vea.atoms.mvp.base.IPresenter;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;

import java.util.List;

/**
 * Created by xuwc on 2016/11/30.
 */
public interface TechContract {

    interface View extends IView {

        void getGankDataSuccess(List<GankItemBean> mList, boolean isRefresh);

    }

    interface Presenter extends IPresenter<View> {

        void getGankData(String tech, boolean isRefresh);

    }
}
