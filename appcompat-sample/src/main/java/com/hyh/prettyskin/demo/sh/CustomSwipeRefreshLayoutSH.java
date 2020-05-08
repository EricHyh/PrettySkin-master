package com.hyh.prettyskin.demo.sh;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.sh.support.SwipeRefreshLayoutSH;
import com.hyh.prettyskin.utils.AttrValueHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/5/6
 */
public class CustomSwipeRefreshLayoutSH extends SwipeRefreshLayoutSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("progressBackground");
        mSupportAttrNames.add("progressColorScheme");
        mSupportAttrNames.add("progressColorSchemes");
    }

    private TypedArray mTypedArray;

    public CustomSwipeRefreshLayoutSH() {
    }

    public CustomSwipeRefreshLayoutSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CustomSwipeRefreshLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mTypedArray = view.getContext().obtainStyledAttributes(set, R.styleable.CustomSwipeRefreshLayout);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mSupportAttrNames.contains(attrName)) {
            int styleableIndex = AttrValueHelper.getStyleableIndex(R.styleable.class, "CustomSwipeRefreshLayout", attrName);
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
}