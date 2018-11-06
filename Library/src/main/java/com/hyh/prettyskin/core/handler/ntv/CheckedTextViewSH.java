package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class CheckedTextViewSH extends TextViewSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("checkMark");
        mSupportAttrNames.add("checkMarkTintMode");
        mSupportAttrNames.add("checkMarkTint");
        //mSupportAttrNames.add("checkMarkGravity");
        mSupportAttrNames.add("checked");
    }

    public CheckedTextViewSH() {
        this(ViewAttrUtil.getDefStyleAttr("checkedTextViewStyle"));//R.attr.checkedTextViewStyle
    }

    public CheckedTextViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CheckedTextViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof CheckedTextView && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parseAttrValue(view, set, attrName);
        } else {
            Class styleableClass = getStyleableClass();
            String styleableName = getStyleableName();
            return parseAttrValue(view, set, attrName, styleableClass, styleableName);
        }
    }

    private Class getStyleableClass() {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (Exception e) {
            return null;
        }
    }

    private String getStyleableName() {
        return "CheckedTextView";
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof CheckedTextView) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            CheckedTextView checkedTextView = (CheckedTextView) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "checkMark": {
                    Drawable checkMark = ViewAttrUtil.getDrawable(resources, type, value);
                    checkedTextView.setCheckMarkDrawable(checkMark);
                    break;
                }
                case "checkMarkTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        checkedTextView.setCheckMarkTintMode(tintMode);
                    }
                    break;
                }
                case "checkMarkTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ColorStateList colorStateList = ViewAttrUtil.getColorStateList(resources, type, value);
                        checkedTextView.setCheckMarkTintList(colorStateList);
                    }
                    break;
                }
                case "checkMarkGravity": {
                    //TODO 暂未实现
                    break;
                }
                case "checked": {
                    boolean checked = ViewAttrUtil.getBoolean(resources, type, value);
                    checkedTextView.setChecked(checked);
                    break;
                }
            }
        }
    }
}
