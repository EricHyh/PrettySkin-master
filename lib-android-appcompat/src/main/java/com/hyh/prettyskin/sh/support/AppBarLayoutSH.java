package com.hyh.prettyskin.sh.support;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ViewGroupSH;
import com.hyh.prettyskin.utils.AttrValueHelper;

import java.util.ArrayList;
import java.util.List;

public class AppBarLayoutSH extends ViewGroupSH {

    private final Class mStyleableClass = android.support.design.R.styleable.class;

    private final String mStyleableName = "AppBarLayout";

    private final List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("expanded");
        //mSupportAttrNames.add("elevation");
        mSupportAttrNames.add("keyboardNavigationCluster");
        mSupportAttrNames.add("touchscreenBlocksFocus");
    }

    private TypedArray mTypedArray;

    public AppBarLayoutSH() {
    }

    public AppBarLayoutSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppBarLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mTypedArray = view.getContext().obtainStyledAttributes(
                set,
                android.support.design.R.styleable.AppBarLayout,
                0,
                android.support.design.R.style.Widget_Design_AppBarLayout);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mSupportAttrNames.contains(attrName)) {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (mSupportAttrNames.contains(attrName)) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            switch (attrName) {
                case "expanded": {
                    appBarLayout.setExpanded(attrValue.getTypedValue(boolean.class, false));
                    break;
                }
                case "keyboardNavigationCluster": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        appBarLayout.setKeyboardNavigationCluster(attrValue.getTypedValue(boolean.class, false));
                    }
                    break;
                }
                case "touchscreenBlocksFocus": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        appBarLayout.setTouchscreenBlocksFocus(attrValue.getTypedValue(boolean.class, false));
                    }
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}
