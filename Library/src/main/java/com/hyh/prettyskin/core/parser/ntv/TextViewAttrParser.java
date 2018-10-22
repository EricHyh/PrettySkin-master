package com.hyh.prettyskin.core.parser.ntv;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.parser.XmlAttrParser;
import com.hyh.prettyskin.utils.AttrUtil;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */
public class TextViewAttrParser implements XmlAttrParser {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        //mSupportAttrNames.add("editable");
        //mSupportAttrNames.add("inputMethod");
        //mSupportAttrNames.add("numeric");
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
        mSupportAttrNames.add("maxLines");
        mSupportAttrNames.add("minLines");
        mSupportAttrNames.add("maxHeight");
        mSupportAttrNames.add("minHeight");
        mSupportAttrNames.add("lines");
        mSupportAttrNames.add("height");
        mSupportAttrNames.add("maxEms");
        mSupportAttrNames.add("minEms");
        mSupportAttrNames.add("maxWidth");
        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("ems");
        mSupportAttrNames.add("width");
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
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
        mSupportAttrNames.add("digits");
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof TextView && mSupportAttrNames.contains(attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        Context context = view.getContext();
        if (context == null && !(view instanceof TextView)) {
            return null;
        }
        TextView textView = (TextView) view;
        AttrValue attrValue = null;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        switch (attrName) {
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
                type = ValueType.TYPE_INT;
                value = textView.getAutoLinkMask();
                break;
            }
            case "linksClickable": {
                type = ValueType.TYPE_BOOLEAN;
                value = textView.getLinksClickable();
                break;
            }
            case "drawableLeft": {
                type = ValueType.TYPE_DRAWABLE;
                value = textView.getCompoundDrawables()[0];
                break;
            }
            case "drawableTop": {
                type = ValueType.TYPE_DRAWABLE;
                value = textView.getCompoundDrawables()[1];
                break;
            }
            case "drawableRight": {
                type = ValueType.TYPE_DRAWABLE;
                value = textView.getCompoundDrawables()[2];
                break;
            }
            case "drawableBottom": {
                type = ValueType.TYPE_DRAWABLE;
                value = textView.getCompoundDrawables()[3];
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
                    type = ValueType.TYPE_COLOR_STATE_LIST;
                    value = textView.getCompoundDrawableTintList();
                }
                break;
            }
            case "drawableTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_OBJECT;
                    value = textView.getCompoundDrawableTintMode();
                }
                break;
            }
            case "maxLines": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMaxLines();
                }
                break;
            }
            case "minLines": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMinLines();
                }
                break;
            }
            case "maxHeight": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMaxHeight();
                } else {
                    try {
                        type = ValueType.TYPE_INT;
                        value = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                    } catch (Exception e) {
                        value = Integer.MAX_VALUE;
                    }
                }
                break;
            }
            case "minHeight": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMinHeight();
                } else {
                    try {
                        type = ValueType.TYPE_INT;
                        value = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                    } catch (Exception e) {
                        value = 0;
                    }
                }
                break;
            }
            case "lines": {
                int maxLines;
                int minLines;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    maxLines = textView.getMaxLines();
                } else {
                    try {
                        maxLines = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                    } catch (Exception e) {
                        maxLines = Integer.MAX_VALUE;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    minLines = textView.getMinLines();
                } else {
                    try {
                        minLines = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                    } catch (Exception e) {
                        minLines = 0;
                    }
                }
                if (maxLines != -1 && maxLines == minLines) {
                    type = ValueType.TYPE_INT;
                    value = maxLines;
                }
                break;
            }
            case "height": {
                int maxHeight;
                int minHeight;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    maxHeight = textView.getMaxHeight();
                } else {
                    try {
                        maxHeight = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                    } catch (Exception e) {
                        maxHeight = Integer.MAX_VALUE;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    minHeight = textView.getMinHeight();
                } else {
                    try {
                        minHeight = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                    } catch (Exception e) {
                        minHeight = 0;
                    }
                }
                if (maxHeight != -1 && maxHeight == minHeight) {
                    type = ValueType.TYPE_INT;
                    value = maxHeight;
                }
                break;
            }
            case "maxEms": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMaxEms();
                } else {
                    try {
                        type = ValueType.TYPE_INT;
                        value = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                    } catch (Exception e) {
                        value = Integer.MAX_VALUE;
                    }
                }
                break;
            }
            case "minEms": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMinEms();
                } else {
                    try {
                        type = ValueType.TYPE_INT;
                        value = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                    } catch (Exception e) {
                        value = 0;
                    }
                }
                break;
            }
            case "maxWidth": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMaxWidth();
                } else {
                    try {
                        type = ValueType.TYPE_INT;
                        value = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                    } catch (Exception e) {
                        value = Integer.MAX_VALUE;
                    }
                }
                break;
            }
            case "minWidth": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = textView.getMinWidth();
                } else {
                    try {
                        type = ValueType.TYPE_INT;
                        value = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                    } catch (Exception e) {
                        value = 0;
                    }
                }
                break;
            }
            case "ems": {
                int maxEms;
                int minEms;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    maxEms = textView.getMaxEms();
                } else {
                    try {
                        maxEms = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                    } catch (Exception e) {
                        maxEms = Integer.MAX_VALUE;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    minEms = textView.getMinEms();
                } else {
                    try {
                        minEms = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                    } catch (Exception e) {
                        minEms = 0;
                    }
                }
                if (maxEms != -1 && maxEms == minEms) {
                    type = ValueType.TYPE_INT;
                    value = maxEms;
                }
                break;
            }
            case "width": {
                int maxWidth;
                int minWidth;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    maxWidth = textView.getMaxWidth();
                } else {
                    try {
                        maxWidth = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                    } catch (Exception e) {
                        maxWidth = Integer.MAX_VALUE;
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    minWidth = textView.getMinWidth();
                } else {
                    try {
                        minWidth = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                    } catch (Exception e) {
                        minWidth = 0;
                    }
                }
                if (maxWidth != -1 && maxWidth == minWidth) {
                    type = ValueType.TYPE_INT;
                    value = maxWidth;
                }
                break;
            }
            case "gravity": {
                type = ValueType.TYPE_INT;
                value = textView.getGravity();
                break;
            }
            case "hint": {
                type = ValueType.TYPE_STRING;
                value = textView.getHint();
                break;
            }
            case "text": {
                type = ValueType.TYPE_STRING;
                value = textView.getText();
                break;
            }
            case "scrollHorizontally": {
                try {
                    type = ValueType.TYPE_BOOLEAN;
                    value = (boolean) ReflectUtil.getFieldValue(textView, "mHorizontallyScrolling");
                } catch (Exception e) {
                    value = false;
                }
                break;
            }
            case "singleLine": {
                try {
                    type = ValueType.TYPE_BOOLEAN;
                    value = (boolean) ReflectUtil.getFieldValue(textView, "mSingleLine");
                } catch (Exception e) {
                    value = false;
                }
                break;
            }
            case "ellipsize": {
                type = ValueType.TYPE_OBJECT;
                value = textView.getEllipsize();
                break;
            }
            case "marqueeRepeatLimit": {
                type = ValueType.TYPE_INT;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getMarqueeRepeatLimit();
                } else {
                    try {
                        value = (int) ReflectUtil.getFieldValue(textView, "mMarqueeRepeatLimit");
                    } catch (Exception e) {
                        value = 3;
                    }
                }
                break;
            }
            case "includeFontPadding": {
                type = ValueType.TYPE_BOOLEAN;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getIncludeFontPadding();
                } else {
                    try {
                        value = (boolean) ReflectUtil.getFieldValue(textView, "mIncludePad");
                    } catch (Exception e) {
                        value = true;
                    }
                }
                break;
            }
            case "cursorVisible": {
                type = ValueType.TYPE_BOOLEAN;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.isCursorVisible();
                } else {
                    try {
                        Object editor = ReflectUtil.getFieldValue(textView, "mEditor");
                        if (editor != null) {
                            value = (boolean) ReflectUtil.getFieldValue(editor, "mCursorVisible");
                        } else {
                            value = true;
                        }
                    } catch (Exception e) {
                        value = true;
                    }
                }
                break;
            }
            case "maxLength": {
                type = ValueType.TYPE_OBJECT;
                value = textView.getFilters();
                break;
            }
            case "textScaleX": {
                type = ValueType.TYPE_FLOAT;
                value = textView.getTextScaleX();
                break;
            }
            case "freezesText": {
                type = ValueType.TYPE_BOOLEAN;
                value = textView.getFreezesText();
                break;
            }
            case "shadowColor": {
                type = ValueType.TYPE_COLOR_INT;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getShadowColor();
                } else {
                    try {
                        value = (int) ReflectUtil.getFieldValue(textView, "mShadowColor");
                    } catch (Exception e) {
                        value = 0;
                    }
                }
                break;
            }
            case "shadowDx": {
                type = ValueType.TYPE_FLOAT;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getShadowDx();
                } else {
                    try {
                        value = (float) ReflectUtil.getFieldValue(textView, "mShadowDx");
                    } catch (Exception e) {
                        value = 0.0f;
                    }
                }
                break;
            }
            case "shadowDy": {
                type = ValueType.TYPE_FLOAT;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getShadowDy();
                } else {
                    try {
                        value = (float) ReflectUtil.getFieldValue(textView, "mShadowDy");
                    } catch (Exception e) {
                        value = 0.0f;
                    }
                }
                break;
            }
            case "shadowRadius": {
                type = ValueType.TYPE_FLOAT;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getShadowRadius();
                } else {
                    try {
                        value = (float) ReflectUtil.getFieldValue(textView, "mShadowRadius");
                    } catch (Exception e) {
                        value = 0.0f;
                    }
                }
                break;
            }
            case "enabled": {
                type = ValueType.TYPE_BOOLEAN;
                value = textView.isEnabled();
                break;
            }
            case "textColorHighlight": {
                type = ValueType.TYPE_COLOR_INT;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    value = textView.getHighlightColor();
                } else {
                    try {
                        value = (int) ReflectUtil.getFieldValue(textView, "mHighlightColor");
                    } catch (Exception e) {
                        value = 0x6633B5E5;
                    }
                }
                break;
            }
            case "textColor": {
                type = ValueType.TYPE_COLOR_STATE_LIST;
                value = textView.getTextColors();
                break;
            }
            case "textColorHint": {
                type = ValueType.TYPE_COLOR_STATE_LIST;
                value = textView.getHintTextColors();
                break;
            }
            case "textColorLink": {
                type = ValueType.TYPE_COLOR_STATE_LIST;
                value = textView.getLinkTextColors();
                break;
            }
            case "textSize": {
                type = ValueType.TYPE_FLOAT;
                value = textView.getTextSize();
                break;
            }
            case "typeface": {
                type = ValueType.TYPE_OBJECT;
                value = textView.getTypeface();
                break;
            }
            case "textStyle": {
                AttrUtil.getAttrValueFromAttributeSet(context, set, attrName);
                break;
            }
            /*case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }
            case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }
            case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }
            case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }
            case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }
            case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }
            case "typeface": {
                attrValue = textView.getTypeface();
                break;
            }*/
        }
        return attrValue;
    }
}
