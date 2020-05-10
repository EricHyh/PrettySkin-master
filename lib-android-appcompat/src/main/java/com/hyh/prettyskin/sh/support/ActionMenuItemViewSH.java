package com.hyh.prettyskin.sh.support;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ActionMenuItemView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.reflect.Reflect;


@SuppressLint("PrivateResource")
public class ActionMenuItemViewSH extends AppCompatCheckedTextViewSH {

    private TypedArray mTypedArray;

    public ActionMenuItemViewSH() {
    }

    public ActionMenuItemViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ActionMenuItemViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return (view instanceof ActionMenuItemView && TextUtils.equals(attrName, "minWidth"))
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mTypedArray = view.getContext().obtainStyledAttributes(set,
                R.styleable.ActionMenuItemView, 0, 0);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = super.parse(view, set, attrName);
        switch (attrName) {
            case "minWidth": {
                int minWidth = mTypedArray.getDimensionPixelSize(
                        R.styleable.ActionMenuItemView_android_minWidth, 0);
                attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, minWidth);
                break;
            }
        }
        return attrValue;
    }

    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
        if (view instanceof ActionMenuItemView) {
            Object value = attrValue.getValue();
            if (value instanceof Integer) {
                switch (attrName) {
                    case "minWidth": {
                        Reflect.from(view.getClass())
                                .filed("mMinWidth", int.class)
                                .set(view, (int) value);
                        view.requestLayout();
                        break;
                    }
                }
            }
        }
    }
}