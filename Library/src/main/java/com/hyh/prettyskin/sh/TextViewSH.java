package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import com.hyh.prettyskin.utils.ViewAttrUtil;
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
        //mSupportAttrNames.add("lines");
        //mSupportAttrNames.add("height");
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
            Resources resources = null;
            if (themeContext != null) {
                resources = themeContext.getResources();
            }
            TextView textView = (TextView) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "textAppearance": {
                    int textAppearance = -1;
                    if (value != null) {
                        textAppearance = (int) value;
                    }
                    if (textAppearance != -1) {
                        textView.setTextAppearance(themeContext, textAppearance);
                    }
                    break;
                }
                case "editable": {
                    //TODO 暂不实现
                    break;
                }
                case "inputMethod": {
                    //TODO 暂不实现
                    break;
                }
                case "numeric": {
                    //TODO 暂不实现
                    break;
                }
                case "digits": {
                    //TODO 暂不实现
                    break;
                }
                case "phoneNumber": {
                    //TODO 暂不实现
                    break;
                }
                case "autoText": {
                    //TODO 暂不实现
                    break;
                }
                case "capitalize": {
                    //TODO 暂不实现
                    break;
                }
                case "bufferType": {
                    //TODO 暂不实现
                    break;
                }
                case "selectAllOnFocus": {
                    //TODO 暂不实现
                    break;
                }
                case "autoLink": {
                    int autoLink = 0;
                    if (value != null) {
                        autoLink = (int) value;
                    }
                    textView.setAutoLinkMask(autoLink);
                    break;
                }
                case "linksClickable": {
                    boolean linksClickable = true;
                    if (value != null) {
                        linksClickable = (boolean) value;
                    }
                    textView.setLinksClickable(linksClickable);
                    break;
                }
                case "drawableLeft": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = ViewAttrUtil.getDrawable(resources, type, value);
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = compoundDrawables[3];
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableTop": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = ViewAttrUtil.getDrawable(resources, type, value);
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = compoundDrawables[3];
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableRight": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = ViewAttrUtil.getDrawable(resources, type, value);
                    Drawable drawableBottom = compoundDrawables[3];
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableBottom": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = ViewAttrUtil.getDrawable(resources, type, value);
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableStart": {
                    //TODO 暂不实现
                    break;
                }
                case "drawableEnd": {
                    //TODO 暂不实现
                    break;
                }
                case "drawableTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                        textView.setCompoundDrawableTintList(tint);
                    }
                    break;
                }
                case "drawableTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        textView.setForegroundTintMode(tintMode);
                    }
                    break;
                }
                case "drawablePadding": {
                    int padding = 0;
                    if (value != null) {
                        padding = (int) value;
                    }
                    textView.setCompoundDrawablePadding(padding);
                    break;
                }
                case "maxLines": {
                    int maxLines = Integer.MAX_VALUE;
                    if (value != null) {
                        maxLines = (int) value;
                    }
                    textView.setMaxLines(maxLines);
                    break;
                }
                case "maxHeight": {
                    int maxPixels = Integer.MAX_VALUE;
                    if (value != null) {
                        maxPixels = (int) value;
                    }
                    textView.setMaxHeight(maxPixels);
                    break;
                }
                case "lines": {
                    //TODO 暂未实现
                    break;
                }
                case "height": {
                    //TODO 暂未实现
                    break;
                }
                case "minLines": {
                    int minLines = 0;
                    if (value != null) {
                        minLines = (int) value;
                    }
                    textView.setMinLines(minLines);
                    break;
                }
                case "minHeight": {
                    int minHeight = 0;
                    if (value != null) {
                        minHeight = (int) value;
                    }
                    textView.setMinHeight(minHeight);
                    break;
                }
                case "maxEms": {
                    int maxEms = Integer.MAX_VALUE;
                    if (value != null) {
                        maxEms = (int) value;
                    }
                    textView.setMaxEms(maxEms);
                    break;
                }
                case "maxWidth": {
                    int maxWidth = Integer.MAX_VALUE;
                    if (value != null) {
                        maxWidth = (int) value;
                    }
                    textView.setMaxWidth(maxWidth);
                    break;
                }
                case "ems": {
                    //TODO 暂未实现
                    break;
                }
                case "width": {
                    //TODO 暂未实现
                    break;
                }
                case "minEms": {
                    int minEms = 0;
                    if (value != null) {
                        minEms = (int) value;
                    }
                    textView.setMinEms(minEms);
                    break;
                }
                case "minWidth": {
                    int minWidth = 0;
                    if (value != null) {
                        minWidth = (int) value;
                    }
                    textView.setMinWidth(minWidth);
                    break;
                }
                case "gravity": {
                    int gravity = Gravity.NO_GRAVITY;
                    if (value != null) {
                        gravity = (int) value;
                    }
                    textView.setGravity(gravity);
                    break;
                }
                case "hint": {
                    CharSequence hint = ViewAttrUtil.getCharSequence(resources, type, value);
                    textView.setHint(hint);
                    break;
                }
                case "text": {
                    CharSequence text = ViewAttrUtil.getCharSequence(resources, type, value);
                    textView.setText(text);
                    break;
                }
                case "scrollHorizontally": {
                    boolean whether = false;
                    if (value != null) {
                        whether = (boolean) value;
                    }
                    textView.setHorizontallyScrolling(whether);
                    break;
                }
                case "singleLine": {
                    boolean singleLine = false;
                    if (value != null) {
                        singleLine = (boolean) value;
                    }
                    textView.setSingleLine(singleLine);
                    break;
                }
                case "ellipsize": {
                    int ellipsize = -1;
                    TextUtils.TruncateAt where = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_INT: {
                                ellipsize = (int) value;
                                break;
                            }
                            case ValueType.TYPE_OBJECT: {
                                if (value instanceof TextUtils.TruncateAt) {
                                    where = (TextUtils.TruncateAt) value;
                                }
                                break;
                            }
                        }

                    }
                    if (where == null) {
                        boolean isSingleLine = Reflect.from(TextView.class)
                                .method("isSingleLine", boolean.class)
                                .invoke(textView);
                        if (isSingleLine && textView.getKeyListener() == null && ellipsize < 0) {
                            ellipsize = 3; // END
                        }
                        where = ViewAttrUtil.getTextEllipsize(ellipsize);
                    }
                    textView.setEllipsize(where);
                    break;
                }
                case "marqueeRepeatLimit": {
                    int marqueeLimit = 3;
                    if (value != null) {
                        marqueeLimit = (int) value;
                    }
                    textView.setMarqueeRepeatLimit(marqueeLimit);
                    break;
                }
                case "includeFontPadding": {
                    boolean includepad = true;
                    if (value != null) {
                        includepad = (boolean) value;
                    }
                    textView.setIncludeFontPadding(includepad);
                    break;
                }
                case "cursorVisible": {
                    boolean visible = true;
                    if (value != null) {
                        visible = (boolean) value;
                    }
                    textView.setCursorVisible(visible);
                    break;
                }
                case "maxLength": {
                    int maxlength = -1;
                    if (value != null) {
                        maxlength = (int) value;
                    }
                    if (maxlength >= 0) {
                        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength)});
                    } else {
                        textView.setFilters(new InputFilter[0]);
                    }
                    break;
                }
                case "textScaleX": {
                    float size = 1.0f;
                    if (value != null) {
                        size = (float) value;
                    }
                    textView.setTextScaleX(size);
                    break;
                }
                case "freezesText": {
                    boolean freezesText = false;
                    if (value != null) {
                        freezesText = (boolean) value;
                    }
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
                    int color = ViewAttrUtil.getColor(resources, type, value);
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
                    float dx = ViewAttrUtil.getFloat(resources, type, value);
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
                    float dy = ViewAttrUtil.getFloat(resources, type, value);
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
                    float radius = ViewAttrUtil.getFloat(resources, type, value);
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
                    if (value != null) {
                        enabled = (boolean) value;
                    }
                    textView.setEnabled(enabled);
                    break;
                }
                case "textColorHighlight": {
                    int highlightColor = ViewAttrUtil.getColor(resources, type, value, 0x6633B5E5);
                    textView.setHighlightColor(highlightColor);
                    break;
                }
                case "textColor": {
                    ColorStateList color = ViewAttrUtil.getColorStateList(resources, type, value, 0xFF000000);
                    textView.setTextColor(color);
                    break;
                }
                case "textColorHint": {
                    ColorStateList color = ViewAttrUtil.getColorStateList(resources, type, value);
                    ;
                    textView.setHintTextColor(color);
                    break;
                }
                case "textColorLink": {
                    ColorStateList color = ViewAttrUtil.getColorStateList(resources, type, value);
                    ;
                    textView.setLinkTextColor(color);
                    break;
                }
                case "textSize": {
                    float size = textView.getTextSize();
                    if (value != null) {
                        size = (float) value;
                    }
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                    break;
                }
                case "typeface": {
                    int typefaceIndex = -1;
                    Typeface tf = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_INT: {
                                typefaceIndex = (int) value;
                                break;
                            }
                            case ValueType.TYPE_OBJECT: {
                                if (value instanceof Typeface) {
                                    tf = (Typeface) value;
                                }
                            }
                        }
                    }
                    if (typefaceIndex != -1) {
                        tf = ViewAttrUtil.getTextTypeface(typefaceIndex);
                    }
                    textView.setTypeface(tf);
                    break;
                }
                case "textStyle": {
                    int textStyle = ViewAttrUtil.getInt(resources, type, value, -1);
                    textView.setTypeface(textView.getTypeface(), textStyle);
                    break;
                }
            }
        }
    }
}
