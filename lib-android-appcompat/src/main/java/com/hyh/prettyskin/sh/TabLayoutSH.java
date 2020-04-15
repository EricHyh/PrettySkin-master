package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/15
 */
public class TabLayoutSH extends ViewGroupSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("android.support.design.R$styleable");
        mStyleableName = "TabLayout";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("tabIndicatorHeight");
        mSupportAttrNames.add("tabIndicatorColor");
        mSupportAttrNames.add("tabIndicator");
        mSupportAttrNames.add("tabIndicatorGravity");
        mSupportAttrNames.add("tabIndicatorFullWidth");
        //mSupportAttrNames.add("tabPadding");
        mSupportAttrNames.add("tabTextAppearance");
    }

    private TypedArray mTypedArray;

    public TabLayoutSH() {
        Class<TabLayout> tabLayoutClass = TabLayout.class;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);
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
            TabLayout tabLayout = (TabLayout) view;
            switch (attrName) {
                case "tabIndicatorHeight": {
                    tabLayout.setSelectedTabIndicatorHeight(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "tabIndicatorColor": {
                    tabLayout.setSelectedTabIndicatorColor(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "tabIndicator": {
                    tabLayout.setSelectedTabIndicator(attrValue.getTypedValue(Drawable.class, null));
                    break;
                }
                case "tabIndicatorGravity": {
                    tabLayout.setTabGravity(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "tabIndicatorFullWidth": {
                    tabLayout.setTabIndicatorFullWidth(attrValue.getTypedValue(boolean.class, true));
                    break;
                }
                case "tabTextAppearance": {
                    int id = attrValue.getTypedValue(int.class, 0);

                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}
