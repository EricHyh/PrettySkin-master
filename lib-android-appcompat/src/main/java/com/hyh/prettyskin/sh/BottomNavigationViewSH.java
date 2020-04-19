package com.hyh.prettyskin.sh;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.utils.AttrValueHelper;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationViewSH extends ViewGroupSH {

    private final Class mStyleableClass = android.support.design.R.styleable.class;

    private final String mStyleableName = "BottomNavigationView";

    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("itemIconTint");
        mSupportAttrNames.add("itemIconSize");
        //mSupportAttrNames.add("itemTextAppearanceActive");
        mSupportAttrNames.add("itemTextColor");
        mSupportAttrNames.add("labelVisibilityMode");
        mSupportAttrNames.add("itemHorizontalTranslationEnabled");
        mSupportAttrNames.add("itemBackground");
    }


    public BottomNavigationViewSH() {
        super(android.support.design.R.attr.bottomNavigationStyle);
        Class<BottomNavigationView> bottomNavigationViewClass = BottomNavigationView.class;
    }

    public BottomNavigationViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public BottomNavigationViewSH(int defStyleAttr, int defStyleRes) {
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
                android.support.design.R.styleable.BottomNavigationView,
                mDefStyleAttr,
                android.support.design.R.style.Widget_Design_BottomNavigationView);
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
            BottomNavigationView bottomNavigationView = (BottomNavigationView) view;
            switch (attrName) {
                case "itemIconTint": {
                    bottomNavigationView
                            .setItemIconTintList(attrValue.getTypedValue(ColorStateList.class, null));
                    break;
                }
                case "itemIconSize": {
                    int size = view.getResources().getDimensionPixelSize(
                            android.support.design.R.dimen.design_bottom_navigation_icon_size);
                    bottomNavigationView
                            .setItemIconSize(attrValue.getTypedValue(int.class, size));
                    break;
                }
                case "labelVisibilityMode": {
                    bottomNavigationView
                            .setLabelVisibilityMode(attrValue.getTypedValue(int.class, -1));
                    break;
                }
                case "itemHorizontalTranslationEnabled": {
                    bottomNavigationView
                            .setItemHorizontalTranslationEnabled(attrValue.getTypedValue(boolean.class, true));
                    break;
                }
                case "itemBackground": {
                    bottomNavigationView
                            .setItemBackground(attrValue.getTypedValue(Drawable.class, null));
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}
