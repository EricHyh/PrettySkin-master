package com.hyh.prettyskin.demo.multiitem.load;



public interface RefreshViewClient {

    boolean executeRefresh();

    void setOnRefreshListener(OnRefreshListener listener);

    /**
     * 设置是否开启或关闭刷新功能
     */
    void setPullToRefreshEnabled(boolean enabled);

    /**
     * 在加载更多的过程中，需要禁止刷新数据
     */
    void setTemporaryEnabled(boolean enabled);

    void refreshComplete(boolean success);

    boolean isRefreshing();

    interface OnRefreshListener {

        void onRefresh();

    }
}