package com.vea.atoms.mvp.gank.mvp.contract;

import com.vea.atoms.mvp.base.IView;
import com.vea.atoms.mvp.base.IPresenter;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;
import com.vea.atoms.mvp.gank.mvp.presenter.GirlPresenter;
import com.vea.atoms.mvp.gank.mvp.ui.fragment.GirlFragment;

import java.util.List;

/**
 * ================================================
 * 约定协议
 *
 * @see GirlPresenter
 * @see GirlFragment
 * Created by Vea on 2016/11/24
 * ================================================
 */
public interface GirlContract {

    interface View extends IView {

        void getGankDataSuccess(List<GankItemBean> mList, boolean isRefresh);
    }

    interface Presenter extends IPresenter<View> {

        void getGankData(boolean isRefresh);
    }
}
