package com.hyh.prettyskin.sh.support;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ViewGroupSH;
import com.hyh.prettyskin.utils.AttrValueHelper;

import java.util.ArrayList;
import java.util.List;

public class NavigationViewSH extends ViewGroupSH {

    private final Class mStyleableClass = android.support.design.R.styleable.class;

    private final String mStyleableName = "NavigationView";

    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("itemIconTint");
        //mSupportAttrNames.add("itemTextAppearance");
        mSupportAttrNames.add("itemTextColor");
        mSupportAttrNames.add("itemBackground");
        mSupportAttrNames.add("itemIconPadding");
    }

    public NavigationViewSH() {
        super(android.support.design.R.attr.navigationViewStyle);
    }

    public NavigationViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public NavigationViewSH(int defStyleAttr, int defStyleRes) {
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
                android.support.design.R.styleable.NavigationView,
                mDefStyleAttr,
                android.support.design.R.style.Widget_Design_NavigationView);
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
            NavigationView navigationView = (NavigationView) view;
            switch (attrName) {
                case "itemIconTint": {
                    navigationView.setItemIconTintList(
                            attrValue.getTypedValue(ColorStateList.class, null));
                    break;
                }
                case "itemTextColor": {
                    navigationView.setItemTextColor(
                            attrValue.getTypedValue(ColorStateList.class, null));
                    break;
                }
                case "itemBackground": {
                    navigationView.setItemBackground(attrValue.getTypedValue(Drawable.class, null));
                    break;
                }
                case "itemIconPadding": {
                    navigationView.setItemIconPadding(attrValue.getTypedValue(int.class, 0));
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}