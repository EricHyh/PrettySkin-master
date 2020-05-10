package com.google.android.material.internal;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.sh.ViewGroupSH;


@SuppressLint("RestrictedApi")
public class FlowLayoutSH extends ViewGroupSH {

    public FlowLayoutSH() {
    }

    public FlowLayoutSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public FlowLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof FlowLayout) && (
                TextUtils.equals(attrName, "lineSpacing")
                        || TextUtils.equals(attrName, "itemSpacing")
        )) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        FlowLayout flowLayout = (FlowLayout) view;
        switch (attrName) {
            case "lineSpacing": {
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, flowLayout.getLineSpacing());
            }
            case "itemSpacing": {
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, flowLayout.getItemSpacing());
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        FlowLayout flowLayout = (FlowLayout) view;
        switch (attrName) {
            case "lineSpacing": {
                flowLayout.setLineSpacing(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "itemSpacing": {
                flowLayout.setItemSpacing(attrValue.getTypedValue(int.class, 0));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
            }
        }
    }
}
