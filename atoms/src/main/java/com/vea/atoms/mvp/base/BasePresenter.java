package com.vea.atoms.mvp.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ================================================
 * Presenter基类
 *
 * Created by vea on 2016/11/24.
 * ================================================
 */
public class BasePresenter<T extends BaseView> implements IPresenter<T> {


    protected WeakReference<T> mView;

    CompositeDisposable mDisposables;


    @Override
    public void attachView(T view) {
        mView = new WeakReference<T>(view);
    }

    @Override
    public void detachView() {
        mView.clear();
        mView = null;
        dispose();
    }

    public T getView() {
        if (mView != null) {
            return mView.get();
        }
        return null;
    }

    //取消所有的订阅
    public void dispose() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    protected void addSubscription(Disposable disposable) {
        if (disposable == null) return;
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    protected void disposeSubscription(Disposable disposable) {
        if (disposable == null) return;
        if (mDisposables == null) return;
        disposable.dispose();
        mDisposables.delete(disposable);
    }
}
