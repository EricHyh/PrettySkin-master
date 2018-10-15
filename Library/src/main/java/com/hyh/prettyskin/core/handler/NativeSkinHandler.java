package com.hyh.prettyskin.core.handler;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    public void replace(View view, String attrName, Object attrValue) {
        if ("background".equals(attrName)) {
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
        }
    }
}
