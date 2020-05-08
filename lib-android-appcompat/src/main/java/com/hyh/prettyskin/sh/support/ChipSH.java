package com.hyh.prettyskin.sh.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.design.animation.MotionSpec;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipDrawable;
import android.support.design.resources.TextAppearance;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;

/**
 * @author Administrator
 * @description
 * @data 2020/4/23
 */
@SuppressLint("RestrictedApi")
public class ChipSH extends AppCompatCheckBoxSH {

    public ChipSH() {
        this(android.support.design.R.attr.chipStyle);
    }

    public ChipSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public ChipSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
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
                        || TextUtils.equals(attrName, "closeIconEnabled")
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
        ChipDrawable chipDrawable = (ChipDrawable) chip.getChipDrawable();

        switch (attrName) {
            case "chipBackgroundColor": {
                ColorStateList colorStateList = chip.getChipBackgroundColor();
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, colorStateList);
            }
            case "chipMinHeight": {
                float chipMinHeight = chip.getChipMinHeight();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, chipMinHeight);
            }
            case "chipCornerRadius": {
                float chipCornerRadius = chip.getChipCornerRadius();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, chipCornerRadius);
            }
            case "chipStrokeColor": {
                ColorStateList chipStrokeColor = chip.getChipStrokeColor();
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, chipStrokeColor);
            }
            case "chipStrokeWidth": {
                float chipStrokeWidth = chip.getChipStrokeWidth();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, chipStrokeWidth);
            }
            case "rippleColor": {
                ColorStateList rippleColor = chip.getRippleColor();
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, rippleColor);
            }
            case "text": {
                CharSequence text = null;
                if (chipDrawable != null) {
                    text = chipDrawable.getText();
                }
                return new AttrValue(view.getContext(), ValueType.TYPE_STRING, text);
            }
            case "textAppearance": {
                TextAppearance textAppearance = null;
                if (chipDrawable != null) {
                    textAppearance = chipDrawable.getTextAppearance();
                }
                return new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, textAppearance);
            }
            case "ellipsize": {
                TextUtils.TruncateAt ellipsize = chip.getEllipsize();
                return new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, ellipsize);
            }

            case "chipIconVisible": {
                boolean visible = chip.isChipIconVisible();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, visible);
            }
            case "chipIconEnabled": {
                boolean enabled = chip.isChipIconEnabled();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, enabled);
            }
            case "chipIcon": {
                Drawable chipIcon = chip.getChipIcon();
                return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, chipIcon);
            }
            case "chipIconTint": {
                ColorStateList chipIconTint = chip.getChipIconTint();
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, chipIconTint);
            }
            case "chipIconSize": {
                float chipIconSize = chip.getChipIconSize();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, chipIconSize);
            }

            case "closeIconVisible": {
                boolean closeIconVisible = chip.isCloseIconVisible();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, closeIconVisible);
            }
            case "closeIconEnabled": {
                boolean enabled = chip.isCloseIconEnabled();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, enabled);
            }
            case "closeIcon": {
                Drawable closeIcon = chip.getCloseIcon();
                return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, closeIcon);
            }
            case "closeIconTint": {
                ColorStateList closeIconTint = chip.getCloseIconTint();
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, closeIconTint);
            }
            case "closeIconSize": {
                float closeIconSize = chip.getCloseIconSize();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, closeIconSize);
            }

            case "checkedIconVisible": {
                boolean checkedIconVisible = chip.isCheckedIconVisible();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, checkedIconVisible);
            }
            case "checkedIconEnabled": {
                boolean enabled = chip.isCheckedIconEnabled();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, enabled);
            }
            case "checkedIcon": {
                Drawable checkedIcon = chip.getCheckedIcon();
                return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, checkedIcon);
            }
            case "checkable": {
                boolean checkable = chip.isCheckable();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, checkable);
            }

            case "showMotionSpec": {
                MotionSpec showMotionSpec = chip.getShowMotionSpec();
                return new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, showMotionSpec);
            }
            case "hideMotionSpec": {
                MotionSpec hideMotionSpec = chip.getHideMotionSpec();
                return new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, hideMotionSpec);
            }

            case "chipStartPadding": {
                float chipStartPadding = chip.getChipStartPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, chipStartPadding);
            }
            case "chipEndPadding": {
                float chipEndPadding = chip.getChipEndPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, chipEndPadding);
            }
            case "iconStartPadding": {
                float iconStartPadding = chip.getIconStartPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, iconStartPadding);
            }
            case "iconEndPadding": {
                float iconEndPadding = chip.getIconEndPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, iconEndPadding);
            }
            case "textStartPadding": {
                float textStartPadding = chip.getTextStartPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, textStartPadding);
            }
            case "textEndPadding": {
                float textEndPadding = chip.getTextEndPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, textEndPadding);
            }
            case "closeIconStartPadding": {
                float closeIconStartPadding = chip.getCloseIconStartPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, closeIconStartPadding);
            }
            case "closeIconEndPadding": {
                float closeIconEndPadding = chip.getCloseIconEndPadding();
                return new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, closeIconEndPadding);
            }
            case "maxWidth": {
                int maxWidth = Integer.MAX_VALUE;
                if (chipDrawable != null) {
                    maxWidth = chipDrawable.getMaxWidth();
                }
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, maxWidth);
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        Chip chip = (Chip) view;
        ChipDrawable chipDrawable = (ChipDrawable) chip.getChipDrawable();

        switch (attrName) {
            case "chipBackgroundColor": {
                chip.setChipBackgroundColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "chipMinHeight": {
                chip.setChipMinHeight(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "chipCornerRadius": {
                chip.setChipCornerRadius(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "chipStrokeColor": {
                chip.setChipStrokeColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "chipStrokeWidth": {
                chip.setChipStrokeWidth(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "rippleColor": {
                chip.setRippleColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "text": {
                if (chipDrawable != null) {
                    chipDrawable.setText(attrValue.getTypedValue(CharSequence.class, null));
                }
                break;
            }
            case "textAppearance": {
                Context themeContext = attrValue.getThemeContext();
                int resourceId = attrValue.getTypedValue(int.class, 0);
                if (themeContext != null && chipDrawable != null && resourceId != 0) {
                    TextAppearance textAppearance = new TextAppearance(themeContext, resourceId);
                    chipDrawable.setTextAppearance(textAppearance);
                }
                break;
            }
            case "ellipsize": {
                chip.setEllipsize(attrValue.getTypedValue(TextUtils.TruncateAt.class, TextUtils.TruncateAt.START));
                break;
            }
            case "chipIconVisible": {
                chip.setChipIconVisible(attrValue.getTypedValue(boolean.class, false));
                break;

            }
            case "chipIconEnabled": {
                chip.setChipIconEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "chipIcon": {
                chip.setChipIcon(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "chipIconTint": {
                chip.setChipIconTint(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "chipIconSize": {
                chip.setChipIconSize(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }

            case "closeIconVisible": {
                chip.setCloseIconVisible(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "closeIconEnabled": {
                chip.setCloseIconEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "closeIcon": {
                chip.setCloseIcon(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "closeIconTint": {
                chip.setCloseIconTint(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "closeIconSize": {
                chip.setCloseIconSize(attrValue.getTypedValue(float.class, null));
                break;
            }

            case "checkedIconVisible": {
                chip.setCheckedIconVisible(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "checkedIconEnabled": {
                chip.setCheckedIconEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "checkedIcon": {
                chip.setCheckedIcon(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "checkable": {
                chip.setCheckable(attrValue.getTypedValue(boolean.class, false));
                break;
            }

            case "showMotionSpec": {
                Context themeContext = attrValue.getThemeContext();
                int resourceId = attrValue.getTypedValue(int.class, 0);
                if (themeContext != null && resourceId != 0) {
                    chip.setShowMotionSpec(MotionSpec.createFromResource(themeContext, resourceId));
                }
                break;
            }
            case "hideMotionSpec": {
                Context themeContext = attrValue.getThemeContext();
                int resourceId = attrValue.getTypedValue(int.class, 0);
                if (themeContext != null && resourceId != 0) {
                    chip.setHideMotionSpec(MotionSpec.createFromResource(themeContext, resourceId));
                }
                break;
            }

            case "chipStartPadding": {
                chip.setChipStartPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "chipEndPadding": {
                chip.setChipEndPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "iconStartPadding": {
                chip.setIconStartPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "iconEndPadding": {
                chip.setIconEndPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "textStartPadding": {
                chip.setTextStartPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "textEndPadding": {
                chip.setTextEndPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "closeIconStartPadding": {
                chip.setCloseIconStartPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "closeIconEndPadding": {
                chip.setCloseIconEndPadding(attrValue.getTypedValue(float.class, 0.0f));
                break;
            }
            case "maxWidth": {
                chip.setMaxWidth(attrValue.getTypedValue(int.class, Integer.MAX_VALUE));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}