package com.vea.atoms.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * MVP Fragment基类
 *
 * Created by vea on 2016/11/24.
 * ================================================
 */
public abstract class BaseFragment<T extends IPresenter> extends Fragment implements BaseView {

    @Inject
    protected T mPresenter;

    private Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        mUnBinder = ButterKnife.bind(this, view);
        setupFragmentComponent();
        initData(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    protected void setupFragmentComponent() {

    }

    protected abstract int getLayoutId();

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    public void showMessage(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
