package com.hyh.prettyskin.demo.multiitem.load;


import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Administrator
 * @description
 * @data 2019/6/13
 */

public class RecyclerScrollViewClient implements ScrollViewClient {

    private final RecyclerView mRecyclerView;

    public RecyclerScrollViewClient(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Override
    public void setScrollListener(RecyclerView.OnScrollListener listener) {
        mRecyclerView.addOnScrollListener(listener);
    }
}