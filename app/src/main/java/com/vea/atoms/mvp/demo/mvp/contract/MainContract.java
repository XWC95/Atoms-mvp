package com.vea.atoms.mvp.demo.mvp.contract;

import com.vea.atoms.mvp.base.IPresenter;
import com.vea.atoms.mvp.base.IView;
import com.vea.atoms.mvp.demo.mvp.model.entity.User;

import java.util.List;

/**
 * Created by vea on 2018/8/24.
 */

public interface MainContract {


    interface View extends IView {

        void getUserSuccess(List<User> users, boolean isRefresh);

        void getUserFailure(boolean isRefresh);
    }

    interface Presenter extends IPresenter<View> {

        void getUser(int lastIdQueried, boolean isRefresh);
    }


}
