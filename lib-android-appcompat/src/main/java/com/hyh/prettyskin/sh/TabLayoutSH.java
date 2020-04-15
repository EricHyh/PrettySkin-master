package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.TypedArray;
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
    private TypedArray mAppearanceTypedArray;

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


        //styleable.TabLayout_tabTextAppearance, style.TextAppearance_Design_Tab
        if (mTypedArray != null) {

            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, "tabTextAppearance");
            if (styleableIndex != -1) {
               /* R.style.TextAppearance_Design_Tab;
                ViewAttrUtil.getInternalStyleAttr()
                mTypedArray.getResourceId(styleableIndex,)*/
            }



        }

       /* int[] appearanceAttrs = Reflect.from(mStyleableClass).filed("TextViewAppearance", int[].class).get(null);
        TypedArray typedArray = context.obtainStyledAttributes(set,
                appearanceAttrs,
                mDefStyleAttr,
                mDefStyleRes);
        if (typedArray != null) {
            Integer textAppearanceIndex = Reflect.from(mStyleableClass).filed("TextViewAppearance_textAppearance", int.class).get(null);
            if (textAppearanceIndex != null) {
                int appearanceId = typedArray.getResourceId(textAppearanceIndex, -1);
                if (appearanceId != -1) {
                    mAppearanceTypedArray = context.obtainStyledAttributes(appearanceId,
                            Reflect.from(mStyleableClass).filed("TextAppearance", int[].class).get(null));
                }
            }
            typedArray.recycle();
        }*/
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        return super.parse(view, set, attrName);
    }

    @Override
    public void finishParse() {
        super.finishParse();

    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
