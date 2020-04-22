package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/20
 */

public class TextViewSH extends ViewSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "TextView";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    private TypedArray mAppearanceTypedArray;

    {
        mSupportAttrNames.add("textAppearance");
        //mSupportAttrNames.add("editable");
        //mSupportAttrNames.add("inputMethod");
        //mSupportAttrNames.add("numeric");
        //mSupportAttrNames.add("digits");
        //mSupportAttrNames.add("phoneNumber");
        //mSupportAttrNames.add("autoText");
        //mSupportAttrNames.add("capitalize");
        //mSupportAttrNames.add("bufferType");
        //mSupportAttrNames.add("selectAllOnFocus");
        mSupportAttrNames.add("autoLink");
        mSupportAttrNames.add("linksClickable");
        mSupportAttrNames.add("drawableLeft");
        mSupportAttrNames.add("drawableTop");
        mSupportAttrNames.add("drawableRight");
        mSupportAttrNames.add("drawableBottom");
        //mSupportAttrNames.add("drawableStart");
        //mSupportAttrNames.add("drawableEnd");
        mSupportAttrNames.add("drawableTint");
        mSupportAttrNames.add("drawableTintMode");
        mSupportAttrNames.add("drawablePadding");
        mSupportAttrNames.add("maxLines");
        mSupportAttrNames.add("maxHeight");
        mSupportAttrNames.add("lines");
        mSupportAttrNames.add("height");
        mSupportAttrNames.add("minLines");
        mSupportAttrNames.add("minHeight");
        mSupportAttrNames.add("maxEms");
        mSupportAttrNames.add("maxWidth");
        mSupportAttrNames.add("ems");
        mSupportAttrNames.add("width");
        mSupportAttrNames.add("minEms");
        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("gravity");
        mSupportAttrNames.add("hint");
        mSupportAttrNames.add("text");
        mSupportAttrNames.add("scrollHorizontally");
        mSupportAttrNames.add("singleLine");
        mSupportAttrNames.add("ellipsize");
        mSupportAttrNames.add("marqueeRepeatLimit");
        mSupportAttrNames.add("includeFontPadding");
        mSupportAttrNames.add("cursorVisible");
        mSupportAttrNames.add("maxLength");
        mSupportAttrNames.add("textScaleX");
        mSupportAttrNames.add("freezesText");
        mSupportAttrNames.add("shadowColor");
        mSupportAttrNames.add("shadowDx");
        mSupportAttrNames.add("shadowDy");
        mSupportAttrNames.add("shadowRadius");
        mSupportAttrNames.add("enabled");
        mSupportAttrNames.add("textColorHighlight");
        mSupportAttrNames.add("textColor");
        mSupportAttrNames.add("textColorHint");
        mSupportAttrNames.add("textColorLink");
        mSupportAttrNames.add("textSize");
        mSupportAttrNames.add("typeface");
        mSupportAttrNames.add("textStyle");

        //Android-26 新增
        mSupportAttrNames.add("autoSizeTextType");
        mSupportAttrNames.add("autoSizeMinTextSize");
        mSupportAttrNames.add("autoSizeMaxTextSize");
        mSupportAttrNames.add("autoSizeStepGranularity");
        mSupportAttrNames.add("autoSizePresetSizes");

        //Android-28 新增
        mSupportAttrNames.add("firstBaselineToTopHeight");
        mSupportAttrNames.add("lastBaselineToBottomHeight");
        mSupportAttrNames.add("lineHeight");
    }


    public TextViewSH() {
        super();
    }

    public TextViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public TextViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof TextView && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);

        int[] appearanceAttrs = Reflect.from(mStyleableClass).filed("TextViewAppearance", int[].class).get(null);
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
        }
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parse(view, set, attrName);
        } else {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            AttrValue attrValue = AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
            if (attrValue != null) {
                return attrValue;
            }
            return parseTextAppearance(view, attrName);
        }
    }

    private AttrValue parseTextAppearance(View view, String attrName) {
        int styleableIndex = AttrValueHelper.getTextAppearanceStyleableIndex(mStyleableClass, attrName);
        return AttrValueHelper.getAttrValue(view, mAppearanceTypedArray, styleableIndex);
    }

    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
        if (mAppearanceTypedArray != null) {
            mAppearanceTypedArray.recycle();
            mAppearanceTypedArray = null;
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof TextView) {
            Context themeContext = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (themeContext == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            TextView textView = (TextView) view;
            switch (attrName) {
                case "textAppearance": {
                    int textAppearance = attrValue.getTypedValue(int.class, -1);
                    if (textAppearance != -1) {
                        textView.setTextAppearance(themeContext, textAppearance);
                    }
                    break;
                }
                /*case "editable":
                case "inputMethod":
                case "numeric":
                case "digits":
                case "phoneNumber":
                case "autoText":
                case "capitalize":
                case "bufferType":
                case "selectAllOnFocus": {
                    //暂不实现
                    break;
                }*/
                case "autoLink": {
                    int autoLink = attrValue.getTypedValue(int.class, 0);
                    textView.setAutoLinkMask(autoLink);
                    break;
                }
                case "linksClickable": {
                    boolean linksClickable = attrValue.getTypedValue(boolean.class, true);
                    textView.setLinksClickable(linksClickable);
                    break;
                }
                case "drawableLeft": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = attrValue.getTypedValue(Drawable.class, null);
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = compoundDrawables[3];
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableTop": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = attrValue.getTypedValue(Drawable.class, null);
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = compoundDrawables[3];
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableRight": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = attrValue.getTypedValue(Drawable.class, null);
                    Drawable drawableBottom = compoundDrawables[3];
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableBottom": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = attrValue.getTypedValue(Drawable.class, null);
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                /*case "drawableStart":
                case "drawableEnd": {
                    //暂不实现
                    break;
                }*/
                case "drawableTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                        textView.setCompoundDrawableTintList(tint);
                    }
                    break;
                }
                case "drawableTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                        textView.setForegroundTintMode(tintMode);
                    }
                    break;
                }
                case "drawablePadding": {
                    int padding = attrValue.getTypedValue(int.class, 0);
                    textView.setCompoundDrawablePadding(padding);
                    break;
                }
                case "maxLines": {
                    int maxLines = attrValue.getTypedValue(int.class, Integer.MAX_VALUE);
                    textView.setMaxLines(maxLines);
                    break;
                }
                case "maxHeight": {
                    int maxPixels = attrValue.getTypedValue(int.class, Integer.MAX_VALUE);
                    textView.setMaxHeight(maxPixels);
                    break;
                }
                case "lines": {
                    textView.setLines(attrValue.getTypedValue(int.class, -1));
                    break;
                }
                case "height": {
                    textView.setHeight(attrValue.getTypedValue(int.class, -1));
                    break;
                }
                case "minLines": {
                    int minLines = attrValue.getTypedValue(int.class, 0);
                    textView.setMinLines(minLines);
                    break;
                }
                case "minHeight": {
                    int minHeight = attrValue.getTypedValue(int.class, 0);
                    textView.setMinHeight(minHeight);
                    break;
                }
                case "maxEms": {
                    int maxEms = attrValue.getTypedValue(int.class, Integer.MAX_VALUE);
                    textView.setMaxEms(maxEms);
                    break;
                }
                case "maxWidth": {
                    int maxWidth = attrValue.getTypedValue(int.class, Integer.MAX_VALUE);
                    textView.setMaxWidth(maxWidth);
                    break;
                }
                case "ems": {
                    textView.setEms(attrValue.getTypedValue(int.class, -1));
                    break;
                }
                case "width": {
                    textView.setWidth(attrValue.getTypedValue(int.class, -1));
                    break;
                }
                case "minEms": {
                    int minEms = attrValue.getTypedValue(int.class, 0);
                    textView.setMinEms(minEms);
                    break;
                }
                case "minWidth": {
                    int minWidth = attrValue.getTypedValue(int.class, 0);
                    textView.setMinWidth(minWidth);
                    break;
                }
                case "gravity": {
                    int gravity = attrValue.getTypedValue(int.class, Gravity.NO_GRAVITY);
                    textView.setGravity(gravity);
                    break;
                }
                case "hint": {
                    CharSequence hint = attrValue.getTypedValue(CharSequence.class, null);
                    textView.setHint(hint);
                    break;
                }
                case "text": {
                    CharSequence text = attrValue.getTypedValue(CharSequence.class, null);
                    textView.setText(text);
                    break;
                }
                case "scrollHorizontally": {
                    boolean whether = attrValue.getTypedValue(boolean.class, false);
                    textView.setHorizontallyScrolling(whether);
                    break;
                }
                case "singleLine": {
                    boolean singleLine = attrValue.getTypedValue(boolean.class, false);
                    textView.setSingleLine(singleLine);
                    break;
                }
                case "ellipsize": {
                    boolean isSingleLine = Reflect.from(TextView.class)
                            .method("isSingleLine", boolean.class)
                            .invoke(textView);
                    TextUtils.TruncateAt where = (isSingleLine && textView.getKeyListener() == null) ? TextUtils.TruncateAt.END : null;
                    where = attrValue.getTypedValue(TextUtils.TruncateAt.class, where);
                    textView.setEllipsize(where);
                    break;
                }
                case "marqueeRepeatLimit": {
                    int marqueeLimit = attrValue.getTypedValue(int.class, 3);
                    textView.setMarqueeRepeatLimit(marqueeLimit);
                    break;
                }
                case "includeFontPadding": {
                    boolean includepad = attrValue.getTypedValue(boolean.class, true);
                    textView.setIncludeFontPadding(includepad);
                    break;
                }
                case "cursorVisible": {
                    boolean visible = attrValue.getTypedValue(boolean.class, true);
                    textView.setCursorVisible(visible);
                    break;
                }
                case "maxLength": {
                    int maxlength = attrValue.getTypedValue(int.class, -1);
                    if (maxlength >= 0) {
                        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
                    } else {
                        textView.setFilters(new InputFilter[0]);
                    }
                    break;
                }
                case "textScaleX": {
                    float size = 1.0f;
                    size = attrValue.getTypedValue(float.class, size);
                    textView.setTextScaleX(size);
                    break;
                }
                case "freezesText": {
                    boolean freezesText = attrValue.getTypedValue(boolean.class, false);
                    textView.setFreezesText(freezesText);
                    break;
                }
                case "shadowColor": {
                    float radius = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        radius = textView.getShadowRadius();
                    } else {
                        radius = Reflect.from(TextView.class)
                                .filed("mShadowRadius", float.class)
                                .get(textView);
                    }
                    float dx = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dx = textView.getShadowDx();
                    } else {
                        dx = Reflect.from(TextView.class)
                                .filed("mShadowDx", float.class)
                                .get(textView);
                    }
                    float dy = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dy = textView.getShadowDy();
                    } else {
                        dy = Reflect.from(TextView.class)
                                .filed("mShadowDy", float.class)
                                .get(textView);
                    }
                    int color = attrValue.getTypedValue(int.class, 0);
                    textView.setShadowLayer(radius, dx, dy, color);
                    break;
                }
                case "shadowDx": {
                    float radius = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        radius = textView.getShadowRadius();
                    } else {
                        radius = Reflect.from(TextView.class)
                                .filed("mShadowRadius", float.class)
                                .get(textView);
                    }
                    float dx = attrValue.getTypedValue(float.class, 0.0f);
                    float dy = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dy = textView.getShadowDy();
                    } else {
                        dy = Reflect.from(TextView.class)
                                .filed("mShadowDy", float.class)
                                .get(textView);
                    }
                    int color = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        color = textView.getShadowColor();
                    } else {
                        color = Reflect.from(TextView.class)
                                .filed("mShadowColor", int.class)
                                .get(textView);
                    }
                    textView.setShadowLayer(radius, dx, dy, color);
                    break;
                }
                case "shadowDy": {
                    float radius = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        radius = textView.getShadowRadius();
                    } else {
                        radius = Reflect.from(TextView.class)
                                .filed("mShadowRadius", float.class)
                                .get(textView);
                    }
                    float dx = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dx = textView.getShadowDx();
                    } else {
                        dx = Reflect.from(TextView.class)
                                .filed("mShadowDx", float.class)
                                .get(textView);
                    }
                    float dy = attrValue.getTypedValue(float.class, 0.0f);
                    int color = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        color = textView.getShadowColor();
                    } else {
                        color = Reflect.from(TextView.class)
                                .filed("mShadowColor", int.class)
                                .get(textView);
                    }
                    textView.setShadowLayer(radius, dx, dy, color);
                    break;
                }
                case "shadowRadius": {
                    float radius = attrValue.getTypedValue(float.class, 0.0f);
                    float dx = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dx = textView.getShadowDx();
                    } else {
                        dx = Reflect.from(TextView.class)
                                .filed("mShadowDx", float.class)
                                .get(textView);
                    }
                    float dy = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        dy = textView.getShadowDy();
                    } else {
                        dy = Reflect.from(TextView.class)
                                .filed("mShadowDy", float.class)
                                .get(textView);
                    }
                    int color = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        color = textView.getShadowColor();
                    } else {
                        color = Reflect.from(TextView.class)
                                .filed("mShadowColor", int.class)
                                .get(textView);
                    }
                    textView.setShadowLayer(radius, dx, dy, color);
                    break;
                }
                case "enabled": {
                    boolean enabled = textView.isEnabled();
                    enabled = attrValue.getTypedValue(boolean.class, enabled);
                    textView.setEnabled(enabled);
                    break;
                }
                case "textColorHighlight": {
                    int highlightColor = attrValue.getTypedValue(int.class, 0x6633B5E5);
                    textView.setHighlightColor(highlightColor);
                    break;
                }
                case "textColor": {
                    ColorStateList color = attrValue.getTypedValue(ColorStateList.class, ColorStateList.valueOf(0xFF000000));
                    textView.setTextColor(color);
                    break;
                }
                case "textColorHint": {
                    ColorStateList color = attrValue.getTypedValue(ColorStateList.class, null);
                    textView.setHintTextColor(color);
                    break;
                }
                case "textColorLink": {
                    ColorStateList color = attrValue.getTypedValue(ColorStateList.class, null);
                    textView.setLinkTextColor(color);
                    break;
                }
                case "textSize": {
                    float size = textView.getTextSize();
                    size = attrValue.getTypedValue(float.class, size);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                    break;
                }
                case "typeface": {
                    Typeface typeface = attrValue.getTypedValue(Typeface.class, null);
                    textView.setTypeface(typeface);
                    break;
                }
                case "textStyle": {
                    int textStyle = attrValue.getTypedValue(int.class, -1);
                    textView.setTypeface(textView.getTypeface(), textStyle);
                    break;
                }
                case "autoSizeTextType": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        textView.setAutoSizeTextTypeWithDefaults(attrValue.getTypedValue(int.class, TextView.AUTO_SIZE_TEXT_TYPE_NONE));
                    }
                    break;
                }
                case "autoSizeMinTextSize": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        int autoSizeMinTextSize = attrValue.getTypedValue(int.class, -1);
                        if (autoSizeMinTextSize > 0) {
                            int autoSizeMaxTextSize = textView.getAutoSizeMaxTextSize();
                            int autoSizeStepGranularity = textView.getAutoSizeStepGranularity();
                            textView.setAutoSizeTextTypeUniformWithConfiguration(
                                    autoSizeMinTextSize,
                                    autoSizeMaxTextSize,
                                    autoSizeStepGranularity,
                                    TypedValue.COMPLEX_UNIT_PX);
                        }
                    }
                    break;
                }
                case "autoSizeMaxTextSize": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        int autoSizeMaxTextSize = attrValue.getTypedValue(int.class, -1);
                        if (autoSizeMaxTextSize > 0) {
                            int autoSizeMinTextSize = textView.getAutoSizeMinTextSize();
                            int autoSizeStepGranularity = textView.getAutoSizeStepGranularity();
                            textView.setAutoSizeTextTypeUniformWithConfiguration(
                                    autoSizeMinTextSize,
                                    autoSizeMaxTextSize,
                                    autoSizeStepGranularity,
                                    TypedValue.COMPLEX_UNIT_PX);
                        }
                    }
                    break;
                }
                case "autoSizeStepGranularity": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        int autoSizeStepGranularity = attrValue.getTypedValue(int.class, -1);
                        if (autoSizeStepGranularity > 0) {
                            int autoSizeMinTextSize = textView.getAutoSizeMinTextSize();
                            int autoSizeMaxTextSize = textView.getAutoSizeMaxTextSize();
                            textView.setAutoSizeTextTypeUniformWithConfiguration(
                                    autoSizeMinTextSize,
                                    autoSizeMaxTextSize,
                                    autoSizeStepGranularity,
                                    TypedValue.COMPLEX_UNIT_PX);
                        }
                    }
                    break;
                }
                case "autoSizePresetSizes": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        int[] presetSizes = attrValue.getTypedValue(int[].class, null);
                        if (presetSizes != null) {
                            textView.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, TypedValue.COMPLEX_UNIT_PX);
                        }
                    }
                    break;
                }
                case "firstBaselineToTopHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        int value = attrValue.getTypedValue(int.class, -1);
                        if (value > 0) {
                            textView.setFirstBaselineToTopHeight(value);
                        }
                    }
                    break;
                }
                case "lastBaselineToBottomHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        int value = attrValue.getTypedValue(int.class, -1);
                        if (value > 0) {
                            textView.setLastBaselineToBottomHeight(value);
                        }
                    }
                    break;
                }
                case "lineHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        int value = attrValue.getTypedValue(int.class, -1);
                        if (value > 0) {
                            textView.setLineHeight(value);
                        }
                    }
                    break;
                }
            }
        }
    }
}