package com.hyh.prettyskin.sh.androidx;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;


public class MaterialButtonSH extends AppCompatButtonSH {

    public MaterialButtonSH() {
        this(com.google.android.material.R.attr.materialButtonStyle);
    }

    public MaterialButtonSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public MaterialButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof MaterialButton) &&
                (
                        TextUtils.equals(attrName, "iconPadding")
                                || TextUtils.equals(attrName, "iconTintMode")
                                || TextUtils.equals(attrName, "iconTint")
                                || TextUtils.equals(attrName, "icon")
                                || TextUtils.equals(attrName, "iconGravity")
                                || TextUtils.equals(attrName, "iconSize")
                )) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        Context context = view.getContext();
        MaterialButton button = (MaterialButton) view;
        switch (attrName) {
            case "iconPadding": {
                int iconPadding = button.getIconPadding();
                return new AttrValue(context, ValueType.TYPE_INT, iconPadding);
            }
            case "iconTintMode": {
                PorterDuff.Mode iconTintMode = button.getIconTintMode();
                return new AttrValue(context, ValueType.TYPE_OBJECT, iconTintMode);
            }
            case "iconTint": {
                ColorStateList iconTint = button.getIconTint();
                return new AttrValue(context, ValueType.TYPE_COLOR_STATE_LIST, iconTint);
            }
            case "icon": {
                Drawable icon = button.getIcon();
                return new AttrValue(context, ValueType.TYPE_DRAWABLE, icon);
            }
            case "iconGravity": {
                int iconGravity = button.getIconGravity();
                return new AttrValue(context, ValueType.TYPE_INT, iconGravity);
            }
            case "iconSize": {
                int iconSize = button.getIconSize();
                return new AttrValue(context, ValueType.TYPE_INT, iconSize);
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        MaterialButton button = (MaterialButton) view;
        switch (attrName) {
            case "iconPadding": {
                button.setIconPadding(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "iconTintMode": {
                button.setIconTintMode(attrValue.getTypedValue(PorterDuff.Mode.class, PorterDuff.Mode.SRC_IN));
                break;
            }
            case "iconTint": {
                button.setIconTint(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "icon": {
                button.setIcon(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "iconGravity": {
                button.setIconGravity(attrValue.getTypedValue(int.class, MaterialButton.ICON_GRAVITY_START));
                break;
            }
            case "iconSize": {
                button.setIconSize(attrValue.getTypedValue(int.class, 0));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}