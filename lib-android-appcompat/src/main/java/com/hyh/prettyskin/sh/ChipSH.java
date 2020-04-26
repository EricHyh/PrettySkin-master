package com.hyh.prettyskin.sh;

import android.support.design.chip.Chip;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;

/**
 * @author Administrator
 * @description
 * @data 2020/4/23
 */
public class ChipSH extends AppCompatCheckBoxSH {

    public ChipSH() {
        this(android.support.design.R.attr.chipStyle);
    }

    public ChipSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public ChipSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
        Chip.class;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof Chip) && (
                TextUtils.equals(attrName, "chipBackgroundColor")
                        || TextUtils.equals(attrName, "chipMinHeight")
                        || TextUtils.equals(attrName, "chipCornerRadius")
                        || TextUtils.equals(attrName, "chipStrokeColor")
                        || TextUtils.equals(attrName, "chipStrokeWidth")
                        || TextUtils.equals(attrName, "rippleColor")

                        || TextUtils.equals(attrName, "text")
                        || TextUtils.equals(attrName, "textAppearance")
                        || TextUtils.equals(attrName, "ellipsize")

                        || TextUtils.equals(attrName, "chipIconVisible")
                        || TextUtils.equals(attrName, "chipIconEnabled")
                        || TextUtils.equals(attrName, "chipIcon")
                        || TextUtils.equals(attrName, "chipIconTint")
                        || TextUtils.equals(attrName, "chipIconSize")

                        || TextUtils.equals(attrName, "closeIconVisible")
                        || TextUtils.equals(attrName, "closeIcon")
                        || TextUtils.equals(attrName, "closeIconTint")
                        || TextUtils.equals(attrName, "closeIconSize")

                        || TextUtils.equals(attrName, "checkable")
                        || TextUtils.equals(attrName, "checkedIconVisible")
                        || TextUtils.equals(attrName, "checkedIconEnabled")
                        || TextUtils.equals(attrName, "checkedIcon")

                        || TextUtils.equals(attrName, "showMotionSpec")
                        || TextUtils.equals(attrName, "hideMotionSpec")

                        || TextUtils.equals(attrName, "chipStartPadding")
                        || TextUtils.equals(attrName, "chipEndPadding")

                        || TextUtils.equals(attrName, "iconStartPadding")
                        || TextUtils.equals(attrName, "iconEndPadding")

                        || TextUtils.equals(attrName, "textStartPadding")
                        || TextUtils.equals(attrName, "textEndPadding")

                        || TextUtils.equals(attrName, "closeIconStartPadding")
                        || TextUtils.equals(attrName, "closeIconEndPadding")

                        || TextUtils.equals(attrName, "maxWidth")

        )) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        Chip chip = (Chip) view;

        return super.parse(view, set, attrName);
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
