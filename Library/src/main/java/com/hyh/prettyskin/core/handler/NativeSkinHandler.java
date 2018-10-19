package com.hyh.prettyskin.core.handler;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;

/**
 * @author Administrator
 * @description
 * @data 2018/10/15
 */

public class NativeSkinHandler implements ISkinHandler {

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return TextUtils.equals("background", attrName)
                || TextUtils.equals("textColor", attrName)
                || TextUtils.equals("src", attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        return null;
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        /*if ("background".equals(attrName)) {
            if (attrValue == null) {
                view.setBackgroundDrawable(null);
            } else {
                if (attrValue instanceof Integer) {
                    Integer color = (Integer) attrValue;
                    view.setBackgroundDrawable(new ColorDrawable(color));
                } else if (attrValue instanceof ColorStateList) {
                    ColorStateList colorStateList = (ColorStateList) attrValue;
                    view.setBackgroundDrawable(new ColorDrawable(colorStateList.getDefaultColor()));
                } else if (attrValue instanceof Drawable) {
                    Drawable drawable = (Drawable) attrValue;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackground(drawable);
                    } else {
                        view.setBackgroundDrawable(drawable);
                    }
                }
            }
        } else if ("textColor".equals(attrName)) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (attrValue == null) {
                    textView.setTextColor(0);
                } else {
                    if (attrValue instanceof Integer) {
                        Integer color = (Integer) attrValue;
                        textView.setTextColor(color);
                    } else if (attrValue instanceof ColorStateList) {
                        ColorStateList colorStateList = (ColorStateList) attrValue;
                        textView.setTextColor(colorStateList);
                    }
                }
            }
        } else if ("src".equals(attrName)) {
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                if (attrValue == null) {
                    imageView.setImageDrawable(null);
                } else if (attrValue instanceof Drawable) {
                    imageView.setImageDrawable((Drawable) attrValue);
                }
            }
        }*/
    }
}
