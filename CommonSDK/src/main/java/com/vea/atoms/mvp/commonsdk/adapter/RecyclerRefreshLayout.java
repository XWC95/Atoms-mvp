package com.vea.atoms.mvp.commonsdk.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

@SuppressWarnings("unused")
public class RecyclerRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecycleView;

    private int mTouchSlop;

    private SuperRefreshLayoutListener listener;

    private boolean mIsOnLoading = false;

    private boolean mCanLoadMore = true;

    private boolean mHasMore = true;

    private int mYDown;

    private int mLastY;

    public RecyclerRefreshLayout(Context context) {
        this(context, null);
    }

    public RecyclerRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        if (listener != null && !mIsOnLoading) {
            listener.onRefreshing();
        } else
            setRefreshing(false);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mRecycleView == null) {
            getRecycleView();
        }
    }

    /**
     * 获取RecyclerView，后续支持AbsListView
     */
    private void getRecycleView() {
        int child = getChildCount();
        if (child > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                mRecycleView = (RecyclerView) childView;
                mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (canLoad() && mCanLoadMore) {
                            loadData();
                        }
                    }
                });
            }
        }
    }


    public void setNoMoreData() {
        this.mHasMore = false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部
     *
     * @return isCanLoad
     */
    private boolean canLoad() {
        return isScrollBottom() && !mIsOnLoading && isPullUp() && mHasMore;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (listener != null) {
            setOnLoading(true);
            listener.onLoadMore();
        }
    }

    /**
     * 是否是上拉操作
     *
     * @return isPullUp
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 设置正在加载
     *
     * @param loading 设置正在加载
     */
    public void setOnLoading(boolean loading) {
        mIsOnLoading = loading;
        if (!mIsOnLoading) {
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isScrollBottom() {
        return (mRecycleView != null && mRecycleView.getAdapter() != null)
                && getLastVisiblePosition() == (mRecycleView.getAdapter().getItemCount() - 1);
    }

    /**
     * 加载结束记得调用
     */
    public void onComplete() {
        setOnLoading(false);
        setRefreshing(false);
        mHasMore = true;
    }

    /**
     * 是否可加载更多
     *
     * @param mCanLoadMore 是否可加载更多
     */
    public void setCanLoadMore(boolean mCanLoadMore) {
        this.mCanLoadMore = mCanLoadMore;
    }

    /**
     * 是否有更多数据
     *
     * @return 是否有更多数据
     */
    public boolean isHasMore() {
        return mHasMore;
    }

    /**
     * 是否有更多数据
     *
     * @param mHasMore 是否有更多数据
     */
    public void setHasMore(boolean mHasMore) {
        this.mHasMore = mHasMore;
    }

    /**
     * 获取RecyclerView可见的最后一项
     *
     * @return 可见的最后一项position
     */
    public int getLastVisiblePosition() {
        int position;
        if (mRecycleView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mRecycleView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecycleView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mRecycleView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (mRecycleView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecycleView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = mRecycleView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions 获得最大的位置
     * @return 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    /**
     * 添加加载和刷新
     *
     * @param listener add the listener for SuperRefreshLayout
     */
    public void setSuperRefreshLayoutListener(SuperRefreshLayoutListener listener) {
        this.listener = listener;
    }


    public interface SuperRefreshLayoutListener {
        void onRefreshing();

        void onLoadMore();
    }


}