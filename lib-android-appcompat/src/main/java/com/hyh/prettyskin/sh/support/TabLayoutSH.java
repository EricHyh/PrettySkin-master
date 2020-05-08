package com.hyh.prettyskin.sh.support;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ViewGroupSH;
import com.hyh.prettyskin.utils.AttrValueHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/15
 */
public class TabLayoutSH extends ViewGroupSH {

    private final Class<?> mStyleableClass = android.support.design.R.styleable.class;
    private final String mStyleableName = "TabLayout";

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("tabIndicatorHeight");
        mSupportAttrNames.add("tabIndicatorColor");
        mSupportAttrNames.add("tabIndicator");
        mSupportAttrNames.add("tabIndicatorGravity");
        mSupportAttrNames.add("tabIndicatorFullWidth");

        //mSupportAttrNames.add("tabPadding");
        //mSupportAttrNames.add("tabPaddingStart");
        //mSupportAttrNames.add("tabPaddingTop");
        //mSupportAttrNames.add("tabPaddingEnd");
        //mSupportAttrNames.add("tabPaddingBottom");
        //mSupportAttrNames.add("tabTextAppearance");

        mSupportAttrNames.add("tabTextColor");
        mSupportAttrNames.add("tabSelectedTextColor");
        mSupportAttrNames.add("tabIconTint");
        //mSupportAttrNames.add("tabIconTintMode");
        mSupportAttrNames.add("tabRippleColor");

        /*mSupportAttrNames.add("tabIndicatorAnimationDuration");
        mSupportAttrNames.add("tabMinWidth");
        mSupportAttrNames.add("tabMaxWidth");
        mSupportAttrNames.add("tabContentStart");*/

        mSupportAttrNames.add("tabMode");
        mSupportAttrNames.add("tabGravity");
        mSupportAttrNames.add("tabInlineLabel");
        mSupportAttrNames.add("tabUnboundedRipple");
    }

    private TypedArray mTypedArray;

    public TabLayoutSH() {
        super(android.support.design.R.attr.tabStyle);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(
                set,
                android.support.design.R.styleable.TabLayout,
                mDefStyleAttr,
                mDefStyleRes);
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
                case "tabTextColor": {
                    ColorStateList tabTextColors = tabLayout.getTabTextColors();
                    int color = attrValue.getTypedValue(int.class, 0);
                    if (tabTextColors == null) {
                        tabLayout.setTabTextColors(ColorStateList.valueOf(color));
                    } else {
                        int selectedColor = tabTextColors.getColorForState(new int[]{android.R.attr.state_selected}, color);
                        tabLayout.setTabTextColors(color, selectedColor);
                    }
                    break;
                }
                case "tabSelectedTextColor": {
                    ColorStateList tabTextColors = tabLayout.getTabTextColors();
                    int color = attrValue.getTypedValue(int.class, 0);
                    if (tabTextColors == null) {
                        tabLayout.setTabTextColors(color, color);
                    } else {
                        tabLayout.setTabTextColors(tabTextColors.getDefaultColor(), color);
                    }
                    break;
                }
                case "tabIconTint": {
                    tabLayout.setTabIconTint(attrValue.getTypedValue(ColorStateList.class, null));
                    break;
                }
                case "tabRippleColor": {
                    tabLayout.setTabRippleColor(attrValue.getTypedValue(ColorStateList.class, null));
                    break;
                }
                case "tabMode": {
                    tabLayout.setTabMode(attrValue.getTypedValue(int.class, 1));
                    break;
                }
                case "tabGravity": {
                    tabLayout.setTabGravity(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "tabInlineLabel": {
                    tabLayout.setInlineLabel(attrValue.getTypedValue(boolean.class, false));
                    break;
                }
                case "tabUnboundedRipple": {
                    tabLayout.setUnboundedRipple(attrValue.getTypedValue(boolean.class, false));
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}