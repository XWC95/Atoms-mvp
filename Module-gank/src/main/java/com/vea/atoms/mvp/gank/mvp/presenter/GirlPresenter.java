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
package com.vea.atoms.mvp.gank.mvp.presenter;

import com.vea.atoms.mvp.base.BasePresenter;
import com.vea.atoms.mvp.commonsdk.adapter.ShowItemList;
import com.vea.atoms.mvp.gank.mvp.contract.GirlContract;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankBaseResponse;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;
import com.vea.atoms.mvp.gank.mvp.model.service.GankService;
import com.vea.atoms.mvp.gank.mvp.ui.fragment.GirlFragment;
import com.vea.atoms.mvp.integration.IRepositoryManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ================================================
 *
 * @see GirlFragment
 * Created by Vea on 2016/11/24
 * ================================================
 */
public class GirlPresenter extends BasePresenter<GirlContract.View> implements GirlContract.Presenter {

    private int mCurrentPager = 1;

    private IRepositoryManager repositoryManager;

    @Inject
    public GirlPresenter(IRepositoryManager repositoryManager, GirlContract.View view) {
        super(view);
        this.repositoryManager = repositoryManager;
    }

    @Override
    public void getGankData(boolean isRefresh) {

        if (isRefresh) {
            mCurrentPager = 1;
        } else {
            mCurrentPager++;
        }

        repositoryManager.obtainRetrofitService(GankService.class)
                .getTechList(ShowItemList.NUM_OF_PAGE, mCurrentPager)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {

                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                }).subscribe(new Observer<GankBaseResponse<List<GankItemBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }

            @Override
            public void onNext(GankBaseResponse<List<GankItemBean>> data) {
                if (!data.getError()) {
                    getView().getGankDataSuccess(data.getResults(), isRefresh);
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                getView().getGankDataFailure(isRefresh);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
