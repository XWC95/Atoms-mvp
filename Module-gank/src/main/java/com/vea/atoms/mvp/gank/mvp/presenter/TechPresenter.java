package com.vea.atoms.mvp.gank.mvp.presenter;


import com.vea.atoms.mvp.base.BasePresenter;
import com.vea.atoms.mvp.gank.app.Constants;
import com.vea.atoms.mvp.gank.mvp.contract.TechContract;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankBaseResponse;
import com.vea.atoms.mvp.gank.mvp.model.entity.GankItemBean;
import com.vea.atoms.mvp.gank.mvp.model.service.GankService;
import com.vea.atoms.mvp.integration.IRepositoryManager;

import java.util.List;
import java.util.Timer;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by xuwc on 2016/11/30.
 */
public class TechPresenter extends BasePresenter<TechContract.View> implements TechContract.Presenter {


    private int mCurrentPager = 1;


    private IRepositoryManager repositoryManager;

    @Inject
    public TechPresenter(IRepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }
    @Override
    public void getGankData(String tech, boolean isRefresh) {

        if (isRefresh) {
            mCurrentPager = 1;
        } else {
            mCurrentPager++;
        }


        repositoryManager .obtainRetrofitService(GankService.class)
                .getTechList(tech, Constants.NUM_OF_PAGE, mCurrentPager)
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
            public void onNext(GankBaseResponse<List<GankItemBean>> listGankBaseResponse) {
                Timber.d(listGankBaseResponse.getResults().size()+"");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
