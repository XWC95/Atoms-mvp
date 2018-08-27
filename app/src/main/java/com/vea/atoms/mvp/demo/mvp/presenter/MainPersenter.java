package com.vea.atoms.mvp.demo.mvp.presenter;

import com.vea.atoms.mvp.base.BasePresenter;
import com.vea.atoms.mvp.demo.mvp.contract.MainContract;
import com.vea.atoms.mvp.demo.mvp.model.entity.User;
import com.vea.atoms.mvp.demo.mvp.model.service.DemoService;
import com.vea.atoms.mvp.integration.IRepositoryManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Administrator on 2018/8/23.
 */
public class MainPersenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private IRepositoryManager repositoryManager;

    @Inject
    public MainPersenter(IRepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    public void getUser(int lastIdQueried, int perPage) {

        repositoryManager.obtainRetrofitService(DemoService.class)
            .getUsers(lastIdQueried, 10)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(disposable -> {
                // 订阅之前常用于显示loading
                // TODO
            }).subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())  // 在主线程中回调订阅事件
            .doFinally(() -> {
                // 结束之后隐藏LoadingView
            })
            .subscribe(new Observer<List<User>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    addSubscription(d);
                }

                @Override
                public void onNext(List<User> users) {
                    Timber.d(users.size() + "");
                    getView().getUserSuccess();
                }

                @Override
                public void onError(Throwable e) {
                    Timber.d(e);
                }

                @Override
                public void onComplete() {

                }
            });
    }
}