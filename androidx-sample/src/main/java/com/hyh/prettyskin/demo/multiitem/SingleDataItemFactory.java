package com.hyh.prettyskin.demo.multiitem;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * @author Administrator
 * @description
 * @data 2017/5/22
 */

public abstract class SingleDataItemFactory<T> extends MultiItemFactory<T> {

    private static final String TAG = "SingleDataItemFactory";

    public SingleDataItemFactory(T t) {
        initData(t);
    }

    private void initData(T t) {
        ArrayList<T> list = new ArrayList<>();
        list.add(t);
        setDataList(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ItemHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHolder<T> itemHolder = null;
        View itemView;
        if (viewType == this.hashCode()) {
            itemView = createItemView(parent);
            itemHolder = new ItemHolder<T>(itemView) {

                @Override
                protected void bindDataAndEvent() {
                    SingleDataItemFactory.this.bindDataAndEvent(getData());
                }

                @Override
                protected boolean isFullSpan(int position) {
                    return true;
                }
            };
        }
        if (itemHolder == null) {
            itemHolder = new EmptyHolder(new View(getContext()));
        }
        initView(parent, itemHolder.itemView);
        return itemHolder;
    }

    @NonNull
    protected abstract View createItemView(ViewGroup parent);

    protected abstract void initView(View parent, View view);

    protected abstract void bindDataAndEvent(T t);

    @Override
    protected int getItemViewType(int position) {
        return this.hashCode();
    }

    @Override
    protected int getSpanSize(int spanCount, int position) {
        return spanCount;
    }
}