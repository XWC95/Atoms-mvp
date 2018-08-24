package com.vea.atoms.mvp.base;


/**
 * ================================================
 * 框架要求框架中的每个 Presenter 都需要实现此类,以满足规范
 *
 * @see BasePresenter
 * Created by vea on 2016/11/24.
 * ================================================
 */
public interface IPresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();

}