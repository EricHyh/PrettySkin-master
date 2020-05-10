package com.hyh.prettyskin.sh.support;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.support.design.chip.ChipGroup;
import android.support.design.internal.FlowLayoutSH;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.utils.AttrValueHelper;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("RestrictedApi")
public class ChipGroupSH extends FlowLayoutSH {

    private final Class mStyleableClass = android.support.design.R.styleable.class;
    private final String mStyleableName = "ChipGroup";
    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("chipSpacing");
        mSupportAttrNames.add("chipSpacingHorizontal");
        mSupportAttrNames.add("chipSpacingVertical");
        mSupportAttrNames.add("singleLine");
        mSupportAttrNames.add("singleSelection");
    }

    public ChipGroupSH() {
        this(android.support.design.R.attr.chipGroupStyle);
    }

    public ChipGroupSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public ChipGroupSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof ChipGroup) && (mSupportAttrNames.contains(attrName)))
                || super.isSupportAttrName(view, attrName);
    }


    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mTypedArray = view.getContext().obtainStyledAttributes(
                set,
                android.support.design.R.styleable.ChipGroup,
                mDefStyleAttr,
                android.support.design.R.style.Widget_MaterialComponents_ChipGroup);
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
        ChipGroup chipGroup = (ChipGroup) view;
        switch (attrName) {
            case "chipSpacing": {
                chipGroup.setChipSpacing(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "chipSpacingHorizontal": {
                chipGroup.setChipSpacingHorizontal(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "chipSpacingVertical": {
                chipGroup.setChipSpacingVertical(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "singleLine": {
                chipGroup.setSingleLine(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "singleSelection": {
                chipGroup.setSingleSelection(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}
