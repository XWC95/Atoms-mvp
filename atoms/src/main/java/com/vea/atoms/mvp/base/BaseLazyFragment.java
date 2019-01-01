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
package com.vea.atoms.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * ================================================
 * MVP 懒加载 Fragment基类
 * <p>
 * Created by Vea on 2016/11/24.
 * ================================================
 */
public abstract class BaseLazyFragment<T extends IPresenter> extends BaseFragment<T> {

    /**
     * 控件是否已经初始化
     */
    private boolean isCreateView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isCreateView = true;
        if (getUserVisibleHint()) {
            initPrepare();
        }
    }

    /**
     * 此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initPrepare();
        }
    }

    private synchronized void initPrepare() {
        if (!isCreateView) {
            return;
        }
        onUserVisible();
    }

    /**
     * 当视图初始化，并且对用户可见的时候去真正的加载数据
     */
    protected abstract void onUserVisible();
}
