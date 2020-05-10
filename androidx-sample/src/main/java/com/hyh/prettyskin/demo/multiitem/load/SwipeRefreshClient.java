package com.hyh.prettyskin.demo.multiitem.load;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;



public class SwipeRefreshClient implements RefreshViewClient, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mRefreshLayout;
    private boolean mIsEnabled;
    private OnRefreshListener mListener;

    public SwipeRefreshClient(SwipeRefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
        mIsEnabled = refreshLayout.isEnabled();
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean executeRefresh() {
        if (mIsEnabled) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    onRefresh();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }


    @Override
    public void setPullToRefreshEnabled(boolean enabled) {
        mIsEnabled = enabled;
        mRefreshLayout.setEnabled(mIsEnabled);
    }

    @Override
    public void setTemporaryEnabled(boolean enabled) {
        if (mIsEnabled) {
            mRefreshLayout.setEnabled(enabled);
        }
    }

    @Override
    public void refreshComplete(boolean success) {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean isRefreshing() {
        return mRefreshLayout.isRefreshing();
    }

    @Override
    public void onRefresh() {
        if (mListener != null) {
            mListener.onRefresh();
        }
    }
}
