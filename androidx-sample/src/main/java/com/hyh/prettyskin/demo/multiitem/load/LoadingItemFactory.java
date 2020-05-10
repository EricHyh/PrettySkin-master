package com.hyh.prettyskin.demo.multiitem.load;

import android.view.View;
import android.view.ViewGroup;

import com.hyh.prettyskin.demo.multiitem.EmptyDataItemFactory;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class LoadingItemFactory extends EmptyDataItemFactory {

    private static final String TAG = "LoadingItemFactory";

    private RecyclerView mRecyclerView;

    private ScrollViewClient mScrollViewClient;

    private LastVisibleItemFinder mLastVisibleItemFinder;

    private Boolean mPullToRefreshEnabled;

    private boolean mLoadMoreEnabled;

    private boolean mIsInLoadMore;

    private boolean mIsNoMore;

    private boolean mLastLoadMoreFailed;

    private LoadingListener mLoadingListener;

    private IFootView mFootView;

    private int mAdvanceItemCount;

    private RefreshViewClient mRefreshView;

    private int mOldLoadState = LoadState.LOAD_STATE_IDLE;

    public LoadingItemFactory(IFootView footView) {
        this(footView, 0);
    }

    public LoadingItemFactory(IFootView footView, int advanceItemCount) {
        super();
        this.mFootView = footView;
        this.mAdvanceItemCount = advanceItemCount < 0 ? 0 : advanceItemCount;
    }

    public void bindScrollListener(RecyclerView recyclerView) {
        bindScrollListener(recyclerView, new RecyclerScrollViewClient(recyclerView), new DefaultLastVisibleItemFinder());
    }

    public void bindScrollListener(RecyclerView recyclerView, ScrollViewClient scrollViewClient) {
        bindScrollListener(recyclerView, scrollViewClient, new DefaultLastVisibleItemFinder());
    }

    public void bindScrollListener(RecyclerView recyclerView, LastVisibleItemFinder finder) {
        bindScrollListener(recyclerView, new RecyclerScrollViewClient(recyclerView), finder);
    }

    public void bindScrollListener(RecyclerView recyclerView, ScrollViewClient scrollViewClient, LastVisibleItemFinder finder) {
        this.mRecyclerView = recyclerView;
        this.mScrollViewClient = scrollViewClient;
        this.mLastVisibleItemFinder = finder;

        mScrollViewClient.setScrollListener(new LoadMore(this));
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    public void bindRefreshListener(RefreshViewClient refreshView) {
        this.mRefreshView = refreshView;
        if (mRefreshView != null && mPullToRefreshEnabled != null) {
            mRefreshView.setPullToRefreshEnabled(mPullToRefreshEnabled);
        }
        refreshView.setOnRefreshListener(new RefreshViewClient.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mLoadingListener != null) {
                    mLoadingListener.onRefresh();
                }
            }
        });
    }


    public void setPullToRefreshEnabled(boolean enabled) {
        mPullToRefreshEnabled = enabled;
        if (mRefreshView != null) {
            mRefreshView.setPullToRefreshEnabled(enabled);
        }
    }

    public void setLoadMoreEnabled(boolean enabled) {
        mLoadMoreEnabled = enabled;
        if (enabled) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    onScrolled(mRecyclerView, mRecyclerView.getScrollState());
                }
            });
        }
    }

    public void setNoMore(boolean noMore) {
        mIsNoMore = noMore;
    }

    public void loadMoreComplete(boolean isSuccess) {
        if (isSuccess) {
            if (mFootView != null) {
                if (mOldLoadState != LoadState.LOAD_STATE_LOAD_SUCCESS) {
                    mFootView.onLoadStateChanged(mOldLoadState, LoadState.LOAD_STATE_LOAD_SUCCESS);
                }
                mOldLoadState = LoadState.LOAD_STATE_LOAD_SUCCESS;
            }
        } else {
            if (mFootView != null) {
                if (mOldLoadState != LoadState.LOAD_STATE_LOAD_FAILURE) {
                    mFootView.onLoadStateChanged(mOldLoadState, LoadState.LOAD_STATE_LOAD_FAILURE);
                }
                mOldLoadState = LoadState.LOAD_STATE_LOAD_FAILURE;
            }
            mLastLoadMoreFailed = true;
        }
        mIsInLoadMore = false;
        if (mRefreshView != null) {
            mRefreshView.setTemporaryEnabled(true);
        }
    }

    public void refreshComplete(boolean success) {
        if (success) {
            mLastLoadMoreFailed = false;
        }
        if (mRefreshView != null) {
            mRefreshView.refreshComplete(success);
        }
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                onScrolled(mRecyclerView, mRecyclerView.getScrollState());
            }
        });
    }

    public boolean executeRefresh() {
        return mRefreshView != null && mRefreshView.executeRefresh();
    }

    public void executeLoadMore() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                onScrolled(mRecyclerView, RecyclerView.SCROLL_STATE_IDLE);
            }
        });
    }

    @NonNull
    @Override
    protected View createItemView(ViewGroup parent) {
        return mFootView.onCreateView(this);
    }

    @Override
    protected void initView(View parent, View view) {
    }

    private void onScrolled(RecyclerView recyclerView, int scrollState) {
        if (!mLoadMoreEnabled) {
            return;
        }
        if (mLoadingListener == null || mIsInLoadMore) {
            return;
        }
        if (mLastLoadMoreFailed && scrollState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) return;

        final int lastVisibleItemPosition = mLastVisibleItemFinder.find(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        int loadMoreItemPosition = layoutManager.getItemCount() - 1 - mAdvanceItemCount;
        loadMoreItemPosition = Math.max(loadMoreItemPosition, 0);

        if (layoutManager.getChildCount() > 0
                && lastVisibleItemPosition >= loadMoreItemPosition
                && layoutManager.getItemCount() >= layoutManager.getChildCount()) {
            if (mIsNoMore) {
                if (mOldLoadState != LoadState.LOAD_STATE_NO_MORE) {
                    if (mFootView != null) {
                        mFootView.onLoadStateChanged(mOldLoadState, LoadState.LOAD_STATE_NO_MORE);
                    }
                    mOldLoadState = LoadState.LOAD_STATE_NO_MORE;
                }
            } else {
                if (isRefreshing()) {
                    if (mOldLoadState != LoadState.LOAD_STATE_REFRESHING) {
                        if (mFootView != null) {
                            mFootView.onLoadStateChanged(mOldLoadState, LoadState.LOAD_STATE_REFRESHING);
                        }
                        mOldLoadState = LoadState.LOAD_STATE_REFRESHING;
                    }
                } else {
                    mLastLoadMoreFailed = false;
                    mIsInLoadMore = true;
                    if (mOldLoadState != LoadState.LOAD_STATE_LOADING) {
                        if (mFootView != null) {
                            mFootView.onLoadStateChanged(mOldLoadState, LoadState.LOAD_STATE_LOADING);
                        }
                        mOldLoadState = LoadState.LOAD_STATE_LOADING;
                    }
                    if (mRefreshView != null) {//准备加载更多，禁止刷新数据
                        mRefreshView.setTemporaryEnabled(false);
                    }
                    mLoadingListener.onLoadMore();
                }
            }
        }
    }

    public boolean isRefreshing() {
        return mRefreshView != null && mRefreshView.isRefreshing();
    }

    public boolean isLoadingMore() {
        return mIsInLoadMore;
    }

    private static class LoadMore extends RecyclerView.OnScrollListener {

        WeakReference<LoadingItemFactory> mLoadMoreModule;

        long mLastScrolledTimeMillis;

        private LoadMore(LoadingItemFactory loadingModule) {
            mLoadMoreModule = new WeakReference<>(loadingModule);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LoadingItemFactory loadingModule = mLoadMoreModule.get();
            if (loadingModule != null) {
                loadingModule.onScrolled(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            long currentTimeMillis = System.currentTimeMillis();
            long timeInterval = Math.abs(currentTimeMillis - mLastScrolledTimeMillis);
            if (timeInterval < 100) return;
            mLastScrolledTimeMillis = currentTimeMillis;
            LoadingItemFactory loadingModule = mLoadMoreModule.get();
            if (loadingModule != null) {
                loadingModule.onScrolled(recyclerView, recyclerView.getScrollState());
            }
        }
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();

    }

    public interface LastVisibleItemFinder {

        int find(RecyclerView recyclerView);

    }

    public static class DefaultLastVisibleItemFinder implements LastVisibleItemFinder {

        @Override
        public int find(RecyclerView recyclerView) {
            int lastVisibleItemPosition;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            return lastVisibleItemPosition;
        }

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }
}