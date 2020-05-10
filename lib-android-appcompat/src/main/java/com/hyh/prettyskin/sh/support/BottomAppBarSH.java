package com.hyh.prettyskin.sh.support;

import android.content.res.ColorStateList;
import android.support.design.bottomappbar.BottomAppBar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;


public class BottomAppBarSH extends V7ToolbarSH {

    public BottomAppBarSH() {
        this(android.support.design.R.attr.bottomAppBarStyle);
    }

    public BottomAppBarSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public BottomAppBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return
                (
                        (view instanceof BottomAppBar)
                                && (TextUtils.equals(attrName, "backgroundTint")
                                || TextUtils.equals(attrName, "fabCradleMargin")
                                || TextUtils.equals(attrName, "fabCradleRoundedCornerRadius")
                                || TextUtils.equals(attrName, "fabCradleVerticalOffset")
                                || TextUtils.equals(attrName, "fabAlignmentMode")
                                || TextUtils.equals(attrName, "hideOnScroll"))
                ) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        BottomAppBar bottomAppBar = (BottomAppBar) view;
        switch (attrName) {
            case "backgroundTint": {
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, bottomAppBar.getBackgroundTint());
            }
            case "fabCradleMargin": {
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, bottomAppBar.getFabCradleMargin());
            }
            case "fabCradleRoundedCornerRadius": {
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, bottomAppBar.getFabCradleRoundedCornerRadius());
            }
            case "fabCradleVerticalOffset": {
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, bottomAppBar.getCradleVerticalOffset());
            }
            case "fabAlignmentMode": {
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, bottomAppBar.getFabAlignmentMode());
            }
            case "hideOnScroll": {
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, bottomAppBar.getHideOnScroll());
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        BottomAppBar bottomAppBar = (BottomAppBar) view;
        switch (attrName) {
            case "backgroundTint": {
                bottomAppBar.setBackgroundTint(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "fabCradleMargin": {
                bottomAppBar.setFabCradleMargin(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "fabCradleRoundedCornerRadius": {
                bottomAppBar.setFabCradleRoundedCornerRadius(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "fabCradleVerticalOffset": {
                bottomAppBar.setCradleVerticalOffset(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "fabAlignmentMode": {
                bottomAppBar.setFabAlignmentMode(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "hideOnScroll": {
                bottomAppBar.setHideOnScroll(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}