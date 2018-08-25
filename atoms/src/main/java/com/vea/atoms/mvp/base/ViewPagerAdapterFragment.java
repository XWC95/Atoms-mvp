package com.vea.atoms.mvp.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;

import com.vea.atoms.mvp.bean.ViewPageInfo;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * FragmentPagerAdapter是Android-support-v4支持包里面出现的一个新的适配器，继承自PagerAdapter，
 * 是专门用来给支持包中出现的ViewPager进行数据适配的。

 PagerAdapter在之前的文章中进行过简单的介绍，不记得怎么使用的可以先去看一下。

 FragmentPagerAdapter，见名知意，这个适配器就是用来实现Fragment在ViewPager里面进行滑动切换的，
 因此，如果我们想实现Fragment的左右滑动，可以选择ViewPager和FragmentPagerAdapter实现。

 FragmentPagerAdapter拥有自己的缓存策略，当和ViewPager配合使用的时候，
 会缓存当前Fragment以及左边一个、右边一个，一共三个Fragment对象。

 假如有三个Fragment,那么在ViewPager初始化之后，3个fragment都会加载完成，
 中间的Fragment在整个生命周期里面只会加载一次，当最左边的Fragment处于显示状态，最右边的Fragment由于超出缓存范围，
 会被销毁，当再次滑到中间的Fragment的时候，最右边的Fragment会被再次初始化。

 在当前版本来说，最适合用来做固定的较少数量的场合，
 比如说一个有3个tab标签的fragment滑动界面。FragmentPagerAdapter会对我们浏览过Fragment进行缓存，
 保存这些界面的临时状态，这样当我们左右滑动的时候，界面切换更加的流畅。
 但是，这样也会增加程序占用的内存。如果应用场景是更多的Fragment，请使用FragmentStatePagerAdapter。
 *
 *
 *
 *
 * Created by Vea on 2016/11/30.
 */
@SuppressLint("Recycle")
public class ViewPagerAdapterFragment extends FragmentStatePagerAdapter {


    private Context mContext;
    public ArrayList<ViewPageInfo> mTabs = new ArrayList<>();
    private Map<String, Fragment> mFragments = new ArrayMap<>();

    public ViewPagerAdapterFragment(FragmentManager fm , Context context) {
        super(fm);
        mContext =context;
    }

    public void addTab(String title, String tag, Class<?> clss, Bundle args) {
        ViewPageInfo viewPageInfo = new ViewPageInfo(title, tag, clss, args);
        mTabs.add(viewPageInfo);
    }


    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        ViewPageInfo info = mTabs.get(position);

        Fragment fragment = mFragments.get(info.tag);
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext, info.clss.getName(), info.args);
            // 避免重复创建而进行缓存
            mFragments.put(info.tag, fragment);
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).title;
    }
}
