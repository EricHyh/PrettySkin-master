package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ReflectUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/10/20
 */

public class TextViewSkinHandler extends ViewSkinHandler {

    public TextViewSkinHandler() {
        super();
    }

    public TextViewSkinHandler(int defStyleAttr) {
        super(defStyleAttr);
    }

    public TextViewSkinHandler(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        Class styleableClass = getStyleableClass();
        String styleableName = getStyleableName();
        AttrValue attrValue = parseAttrValue(view, set, attrName, styleableClass, styleableName);
        if (attrValue != null) {
            return attrValue;
        }
        return super.parseAttrValue(view, set, attrName);
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof TextView) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            TextView textView = (TextView) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "textAppearance": {
                    //TODO 暂不实现
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
                    Drawable drawableLeft = null;
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = compoundDrawables[3];


                    if (value != null) {
                        drawableLeft = (Drawable) value;
                    }
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableTop": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = null;
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = compoundDrawables[3];


                    if (value != null) {
                        drawableTop = (Drawable) value;
                    }
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableRight": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = null;
                    Drawable drawableBottom = compoundDrawables[3];
                    if (value != null) {
                        drawableRight = (Drawable) value;
                    }
                    textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
                    break;
                }
                case "drawableBottom": {
                    Drawable[] compoundDrawables = textView.getCompoundDrawables();
                    Drawable drawableLeft = compoundDrawables[0];
                    Drawable drawableTop = compoundDrawables[1];
                    Drawable drawableRight = compoundDrawables[2];
                    Drawable drawableBottom = null;
                    if (value != null) {
                        drawableBottom = (Drawable) value;
                    }
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
                        ColorStateList tint = null;
                        if (value != null) {
                            switch (type) {
                                case ValueType.TYPE_COLOR_INT: {
                                    tint = ColorStateList.valueOf((Integer) value);
                                    break;
                                }
                                case ValueType.TYPE_COLOR_STATE_LIST: {
                                    tint = (ColorStateList) value;
                                    break;
                                }
                                case ValueType.TYPE_REFERENCE: {
                                    tint = resources.getColorStateList((Integer) value);
                                    break;
                                }
                            }
                        }
                        textView.setCompoundDrawableTintList(tint);
                    }
                    break;
                }
                case "drawableTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = null;
                        if (value != null) {
                            int backgroundTintModeIndex = (int) value;
                            tintMode = (PorterDuff.Mode) ReflectUtil.invokeStaticMethod(Drawable.class,
                                    "parseTintMode",
                                    new Class[]{int.class, PorterDuff.Mode.class},
                                    backgroundTintModeIndex, null);
                            textView.setForegroundTintMode(tintMode);
                        }
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
                    CharSequence hint = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_STRING: {
                                hint = (CharSequence) value;
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                hint = resources.getString((int) value);
                                break;
                            }
                        }
                    }
                    textView.setHint(hint);
                    break;
                }
                case "text": {
                    CharSequence text = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_STRING: {
                                text = (CharSequence) value;
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                text = resources.getText((int) value);
                                break;
                            }
                        }
                    }
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
                        ellipsize = (int) value;
                    }
                    /*ReflectUtil.invokeMethod();
                    if (textView.isSingleLine() && textView.getKeyListener() == null && ellipsize < 0) {
                        ellipsize = 3; // END
                    }*/
                    switch (ellipsize) {
                        case 1:
                            where = TextUtils.TruncateAt.START;
                            break;
                        case 2:
                            where = TextUtils.TruncateAt.MIDDLE;
                            break;
                        case 3:
                            where = TextUtils.TruncateAt.END;
                            break;
                        case 4:
                            where = TextUtils.TruncateAt.MARQUEE;
                            break;
                    }
                    textView.setEllipsize(where);
                    break;
                }
                case "marqueeRepeatLimit": {
                    break;
                }
                case "includeFontPadding": {
                    break;
                }
                case "cursorVisible": {
                    break;
                }
                case "maxLength": {
                    break;
                }
                case "textScaleX": {
                    break;
                }
                case "freezesText": {
                    break;
                }
                case "shadowColor": {
                    break;
                }
                case "shadowDx": {
                    break;
                }
                case "shadowDy": {
                    break;
                }
                case "shadowRadius": {
                    break;
                }
                case "enabled": {
                    break;
                }
                case "textColorHighlight": {
                    break;
                }
                case "textColor": {
                    break;
                }
                case "textColorHint": {
                    break;
                }
                case "textColorLink": {
                    break;
                }
                case "textSize": {
                    break;
                }
                case "typeface": {
                    break;
                }
                case "textStyle": {
                    break;
                }
            }
        }
    }

    private Class getStyleableClass() {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private String getStyleableName() {
        return "TextView";
    }
}
