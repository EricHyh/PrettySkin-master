package com.hyh.prettyskin.demo.multiitem;


import android.view.View;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Administrator
 * @description
 * @data 2017/5/19
 */

public abstract class ItemHolder<T> extends RecyclerView.ViewHolder {

    private List<T> mList;
    private T mData;

    public ItemHolder(View itemView) {
        super(itemView);
    }

    protected boolean isFullSpan(int position) {
        return false;
    }

    protected List<T> getList() {
        return mList;
    }

    protected int getItemPosition() {
        if (mList == null) return -1;
        return mList.indexOf(mData);
    }

    public T getData() {
        return mData;
    }

    void onBindViewHolder(List<T> list, int position) {
        this.mList = list;
        if (mList != null && position < mList.size()) {
            this.mData = list.get(position);
        }
        bindDataAndEvent();
    }

    protected abstract void bindDataAndEvent();

    protected void onRecycled() {
    }

    protected void onViewAttachedToWindow() {
    }

    protected void onViewDetachedFromWindow() {
    }

    protected void onScrollStateChanged(int newState) {
    }

    protected void onScrolled(int scrollState) {
    }

    protected void onDestroy() {
    }
}