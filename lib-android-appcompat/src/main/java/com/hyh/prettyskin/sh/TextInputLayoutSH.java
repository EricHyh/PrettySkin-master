package com.hyh.prettyskin.sh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.internal.ViewUtils;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;

/**
 * @author Administrator
 * @description
 * @data 2020/4/20
 */
public class TextInputLayoutSH extends ViewGroupSH {

    private TypedArray mTypedArray;

    public TextInputLayoutSH() {
        this(android.support.design.R.attr.textInputStyle);
    }

    public TextInputLayoutSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public TextInputLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof TextInputLayout) &&
                (
                        TextUtils.equals(attrName, "hint")
                                || TextUtils.equals(attrName, "hintAnimationEnabled")

                                //|| TextUtils.equals(attrName, "boxCollapsedPaddingTop")

                                || TextUtils.equals(attrName, "boxCornerRadiusTopStart")
                                || TextUtils.equals(attrName, "boxCornerRadiusTopEnd")
                                || TextUtils.equals(attrName, "boxCornerRadiusBottomStart")
                                || TextUtils.equals(attrName, "boxCornerRadiusBottomEnd")

                                || TextUtils.equals(attrName, "boxBackgroundColor")
                                || TextUtils.equals(attrName, "boxStrokeColor")
                                || TextUtils.equals(attrName, "boxBackgroundMode")
                                || TextUtils.equals(attrName, "textColorHint")

                                //|| TextUtils.equals(attrName, "hintTextAppearance")
                                //|| TextUtils.equals(attrName, "errorTextAppearance")
                                //|| TextUtils.equals(attrName, "helperTextTextAppearance")
                                //|| TextUtils.equals(attrName, "counterTextAppearance")
                                //|| TextUtils.equals(attrName, "counterOverflowTextAppearance")

                                || TextUtils.equals(attrName, "hintEnabled")
                                || TextUtils.equals(attrName, "errorEnabled")
                                || TextUtils.equals(attrName, "helperTextEnabled")
                                || TextUtils.equals(attrName, "counterEnabled")

                                || TextUtils.equals(attrName, "helperText")
                                || TextUtils.equals(attrName, "counterMaxLength")

                                || TextUtils.equals(attrName, "passwordToggleEnabled")
                                || TextUtils.equals(attrName, "passwordToggleDrawable")
                                || TextUtils.equals(attrName, "passwordToggleContentDescription")
                                || TextUtils.equals(attrName, "passwordToggleTint")
                                || TextUtils.equals(attrName, "passwordToggleTintMode")
                )) || super.isSupportAttrName(view, attrName);
    }


    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(
                set,
                android.support.design.R.styleable.TextInputLayout,
                mDefStyleAttr,
                android.support.design.R.style.Widget_Design_TextInputLayout);
    }

    @SuppressLint({"PrivateResource", "RestrictedApi"})
    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        Context context = view.getContext();
        TextInputLayout inputLayout = (TextInputLayout) view;
        switch (attrName) {
            case "hint": {
                CharSequence hint = inputLayout.getHint();
                return new AttrValue(context, ValueType.TYPE_STRING, hint);
            }
            case "hintAnimationEnabled": {
                boolean hintAnimationEnabled = inputLayout.isHintAnimationEnabled();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, hintAnimationEnabled);
            }
            case "boxCornerRadiusTopStart": {
                float boxCornerRadiusTopStart = inputLayout.getBoxCornerRadiusTopStart();
                return new AttrValue(context, ValueType.TYPE_FLOAT, boxCornerRadiusTopStart);
            }
            case "boxCornerRadiusTopEnd": {
                float boxCornerRadiusTopEnd = inputLayout.getBoxCornerRadiusTopEnd();
                return new AttrValue(context, ValueType.TYPE_FLOAT, boxCornerRadiusTopEnd);
            }
            case "boxCornerRadiusBottomStart": {
                float boxCornerRadiusBottomStart = inputLayout.getBoxCornerRadiusBottomStart();
                return new AttrValue(context, ValueType.TYPE_FLOAT, boxCornerRadiusBottomStart);
            }
            case "boxCornerRadiusBottomEnd": {
                float boxCornerRadiusBottomEnd = inputLayout.getBoxCornerRadiusBottomEnd();
                return new AttrValue(context, ValueType.TYPE_FLOAT, boxCornerRadiusBottomEnd);
            }
            case "boxBackgroundColor": {
                int boxBackgroundColor = inputLayout.getBoxBackgroundColor();
                return new AttrValue(context, ValueType.TYPE_COLOR_INT, boxBackgroundColor);
            }
            case "boxStrokeColor": {
                int boxStrokeColor = inputLayout.getBoxStrokeColor();
                return new AttrValue(context, ValueType.TYPE_COLOR_INT, boxStrokeColor);
            }
            case "boxBackgroundMode": {
                int boxBackgroundMode = 0;
                if (mTypedArray != null) {
                    boxBackgroundMode = mTypedArray.getInt(android.support.design.R.styleable.TextInputLayout_boxBackgroundMode, 0);
                }
                return new AttrValue(context, ValueType.TYPE_INT, boxBackgroundMode);
            }
            case "textColorHint": {
                ColorStateList textColorHint = inputLayout.getDefaultHintTextColor();
                return new AttrValue(context, ValueType.TYPE_COLOR_STATE_LIST, textColorHint);
            }
            case "hintEnabled": {
                boolean enabled = inputLayout.isHintEnabled();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, enabled);
            }
            case "errorEnabled": {
                boolean enabled = inputLayout.isErrorEnabled();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, enabled);
            }
            case "helperTextEnabled": {
                boolean enabled = inputLayout.isHelperTextEnabled();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, enabled);
            }
            case "counterEnabled": {
                boolean enabled = inputLayout.isCounterEnabled();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, enabled);
            }
            case "helperText": {
                CharSequence helperText = inputLayout.getHelperText();
                return new AttrValue(context, ValueType.TYPE_STRING, helperText);
            }
            case "counterMaxLength": {
                int counterMaxLength = inputLayout.getCounterMaxLength();
                return new AttrValue(context, ValueType.TYPE_INT, counterMaxLength);
            }
            case "passwordToggleEnabled": {
                boolean enabled = inputLayout.isPasswordVisibilityToggleEnabled();
                return new AttrValue(context, ValueType.TYPE_BOOLEAN, enabled);
            }
            case "passwordToggleDrawable": {
                Drawable drawable = inputLayout.getPasswordVisibilityToggleDrawable();
                return new AttrValue(context, ValueType.TYPE_DRAWABLE, drawable);
            }
            case "passwordToggleContentDescription": {
                CharSequence description = inputLayout.getPasswordVisibilityToggleContentDescription();
                return new AttrValue(context, ValueType.TYPE_STRING, description);
            }
            case "passwordToggleTint": {
                ColorStateList passwordToggleTint = null;
                int index = android.support.design.R.styleable.TextInputLayout_passwordToggleTint;
                if (mTypedArray != null && mTypedArray.hasValue(index)) {
                    passwordToggleTint = mTypedArray.getColorStateList(index);
                }
                return new AttrValue(context, ValueType.TYPE_COLOR_STATE_LIST, passwordToggleTint);
            }
            case "passwordToggleTintMode": {
                PorterDuff.Mode mode = null;
                int index = android.support.design.R.styleable.TextInputLayout_passwordToggleTintMode;
                if (mTypedArray != null && mTypedArray.hasValue(index)) {
                    int i = mTypedArray.getInt(index, -1);
                    mode = ViewUtils.parseTintMode(i, null);
                }
                return new AttrValue(context, ValueType.TYPE_OBJECT, mode);
            }
            default: {
                return super.parse(view, set, attrName);
            }
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
        TextInputLayout inputLayout = (TextInputLayout) view;
        switch (attrName) {
            case "hint": {
                inputLayout.setHint(attrValue.getTypedValue(CharSequence.class, null));
                break;
            }
            case "hintAnimationEnabled": {
                inputLayout.setHintAnimationEnabled(attrValue.getTypedValue(boolean.class, true));
                break;
            }
            case "boxCornerRadiusTopStart": {
                float boxCornerRadiusTopStart = attrValue.getTypedValue(float.class, 0.0f);
                float boxCornerRadiusTopEnd = inputLayout.getBoxCornerRadiusTopEnd();
                float boxCornerRadiusBottomStart = inputLayout.getBoxCornerRadiusBottomStart();
                float boxCornerRadiusBottomEnd = inputLayout.getBoxCornerRadiusBottomEnd();
                inputLayout.setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd);
                break;
            }
            case "boxCornerRadiusTopEnd": {
                float boxCornerRadiusTopStart = inputLayout.getBoxCornerRadiusTopStart();
                float boxCornerRadiusTopEnd = attrValue.getTypedValue(float.class, 0.0f);
                float boxCornerRadiusBottomStart = inputLayout.getBoxCornerRadiusBottomStart();
                float boxCornerRadiusBottomEnd = inputLayout.getBoxCornerRadiusBottomEnd();
                inputLayout.setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd);
                break;
            }
            case "boxCornerRadiusBottomStart": {
                float boxCornerRadiusTopStart = inputLayout.getBoxCornerRadiusTopStart();
                float boxCornerRadiusTopEnd = inputLayout.getBoxCornerRadiusTopEnd();
                float boxCornerRadiusBottomStart = attrValue.getTypedValue(float.class, 0.0f);
                float boxCornerRadiusBottomEnd = inputLayout.getBoxCornerRadiusBottomEnd();
                inputLayout.setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd);
                break;
            }
            case "boxCornerRadiusBottomEnd": {
                float boxCornerRadiusTopStart = inputLayout.getBoxCornerRadiusTopStart();
                float boxCornerRadiusTopEnd = inputLayout.getBoxCornerRadiusTopEnd();
                float boxCornerRadiusBottomStart = inputLayout.getBoxCornerRadiusBottomStart();
                float boxCornerRadiusBottomEnd = attrValue.getTypedValue(float.class, 0.0f);
                inputLayout.setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd);
                break;
            }
            case "boxBackgroundColor": {
                inputLayout.setBoxBackgroundColor(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "boxStrokeColor": {
                inputLayout.setBoxStrokeColor(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "boxBackgroundMode": {
                inputLayout.setBoxBackgroundMode(attrValue.getTypedValue(int.class, 0));
                break;
            }
            case "textColorHint": {
                inputLayout.setDefaultHintTextColor(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "hintEnabled": {
                inputLayout.setHintEnabled(attrValue.getTypedValue(boolean.class, true));
                break;
            }
            case "errorEnabled": {
                inputLayout.setErrorEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "helperTextEnabled": {
                inputLayout.setHelperTextEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "counterEnabled": {
                inputLayout.setCounterEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "helperText": {
                inputLayout.setHelperText(attrValue.getTypedValue(CharSequence.class, null));
                break;
            }
            case "counterMaxLength": {
                inputLayout.setCounterMaxLength(attrValue.getTypedValue(int.class, -1));
                break;
            }
            case "passwordToggleEnabled": {
                inputLayout.setPasswordVisibilityToggleEnabled(attrValue.getTypedValue(boolean.class, false));
                break;
            }
            case "passwordToggleDrawable": {
                inputLayout.setPasswordVisibilityToggleDrawable(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "passwordToggleContentDescription": {
                inputLayout.setPasswordVisibilityToggleContentDescription(attrValue.getTypedValue(CharSequence.class, null));
                break;
            }
            case "passwordToggleTint": {
                inputLayout.setPasswordVisibilityToggleTintList(attrValue.getTypedValue(ColorStateList.class, null));
                break;
            }
            case "passwordToggleTintMode": {
                inputLayout.setPasswordVisibilityToggleTintMode(attrValue.getTypedValue(PorterDuff.Mode.class, null));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}