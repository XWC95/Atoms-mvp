/*
 * Copyright 2017 Vea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vea.atoms.mvp.gank.mvp.contract;

import android.app.Activity;

import com.vea.atoms.mvp.base.IPresenter;
import com.vea.atoms.mvp.base.IView;
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

        /**
         * @param isRefresh 刷新为true，加载更多为false
         */
        void getGankDataFailure(boolean isRefresh);

        Activity getFragmentActivity();
    }

    interface Presenter extends IPresenter<View> {

        void getGankData(boolean isRefresh);
    }
}
