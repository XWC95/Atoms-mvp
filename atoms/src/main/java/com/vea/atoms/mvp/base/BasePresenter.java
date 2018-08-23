package com.vea.atoms.mvp.base;

public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();

}