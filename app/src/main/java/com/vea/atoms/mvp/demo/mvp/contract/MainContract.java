package com.vea.atoms.mvp.demo.mvp.contract;

import com.vea.atoms.mvp.base.IPresenter;
import com.vea.atoms.mvp.base.BaseView;

/**
 * Created by vea on 2018/8/24.
 */

public interface MainContract {


    interface View extends BaseView {
    }

    interface Presenter extends IPresenter<View> {

        void getUser(int lastIdQueried, int perPage);
    }


}
