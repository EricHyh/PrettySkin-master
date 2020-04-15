package com.hyh.prettyskin.sh;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/15
 */
public class SwipeRefreshLayoutSH extends ViewGroupSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("progressBackground");
        mSupportAttrNames.add("progressColorScheme");
        mSupportAttrNames.add("progressColorSchemes");
    }


    public SwipeRefreshLayoutSH() {
    }


    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mSupportAttrNames.contains(attrName)) {
            AttrValue attrValue = null;
            switch (attrName) {
                case "progressBackground": {
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_INT, 0xFFFAFAFA);
                    break;
                }
                case "progressColorScheme": {
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_INT, Color.BLACK);
                    break;
                }
                case "progressColorSchemes": {
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, new int[]{Color.BLACK});
                    break;
                }
            }
            return attrValue;
        } else {
            return super.parse(view, set, attrName);
        }
    }


    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (mSupportAttrNames.contains(attrName)) {
            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view;
            switch (attrName) {
                case "progressBackground": {
                    swipeRefreshLayout.setProgressBackgroundColorSchemeColor(attrValue.getTypedValue(int.class, 0xFFFAFAFA));
                    break;
                }
                case "progressColorScheme": {
                    swipeRefreshLayout.setColorSchemeColors(attrValue.getTypedValue(int.class, Color.BLACK));
                    break;
                }
                case "progressColorSchemes": {
                    swipeRefreshLayout.setColorSchemeColors(attrValue.getTypedValue(int[].class, new int[]{Color.BLACK}));
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}