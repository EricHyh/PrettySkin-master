package com.hyh.prettyskin.demo.multiitem;

import android.view.View;

/**
 * @author Administrator
 * @description
 * @data 2017/5/22
 */

public abstract class EmptyDataItemFactory extends SingleDataItemFactory<Object> {

    public EmptyDataItemFactory() {
        super(new Object());
    }

    @Override
    protected void initView(View parent, View view) {
    }

    @Override
    protected void bindDataAndEvent(Object obj) {
    }
}