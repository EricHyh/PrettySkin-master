package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/7
 */

public class SwitchSH extends CompoundButtonSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "Switch";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("thumb");
        mSupportAttrNames.add("track");
        mSupportAttrNames.add("textOn");
        mSupportAttrNames.add("textOff");
        mSupportAttrNames.add("showText");
        mSupportAttrNames.add("thumbTextPadding");
        mSupportAttrNames.add("switchMinWidth");
        mSupportAttrNames.add("switchPadding");
        mSupportAttrNames.add("splitTrack");
        mSupportAttrNames.add("thumbTint");
        mSupportAttrNames.add("thumbTintMode");
        mSupportAttrNames.add("trackTint");
        mSupportAttrNames.add("trackTintMode");
        mSupportAttrNames.add("switchTextAppearance");
    }

    public SwitchSH() {
        this(ViewAttrUtil.getDefStyleAttr_internal("switchStyle"));//com.android.internal.R.attr.switchStyle
    }

    public SwitchSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public SwitchSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof Switch && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }


    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parse(view, set, attrName);
        } else {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
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
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof CompoundButton) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            Switch switchView = (Switch) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "thumb": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Drawable thumb = ViewAttrUtil.getDrawable(resources, type, value);
                        switchView.setThumbDrawable(thumb);
                    }
                    break;
                }
                case "track": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Drawable track = ViewAttrUtil.getDrawable(resources, type, value);
                        switchView.setTrackDrawable(track);
                    }
                    break;
                }
                case "textOn": {
                    CharSequence textOn = ViewAttrUtil.getCharSequence(resources, type, value);
                    switchView.setTextOn(textOn);
                    break;
                }
                case "textOff": {
                    CharSequence textOff = ViewAttrUtil.getCharSequence(resources, type, value);
                    switchView.setTextOff(textOff);
                    break;
                }
                case "showText": {
                    boolean showText = ViewAttrUtil.getBoolean(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        switchView.setShowText(showText);
                    } else {

                        Boolean isShowText = Reflect.from(Switch.class)
                                .filed("mShowText", boolean.class)
                                .get(switchView);
                        if (isShowText != showText) {
                            Reflect.from(Switch.class)
                                    .filed("mShowText", boolean.class)
                                    .set(switchView, showText);
                            switchView.requestLayout();
                        }
                    }
                    break;
                }
                case "thumbTextPadding": {
                    int thumbTextPadding = ViewAttrUtil.getInt(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        switchView.setThumbTextPadding(thumbTextPadding);
                    } else {
                        Reflect.from(Switch.class)
                                .filed("mThumbTextPadding", int.class)
                                .set(switchView, thumbTextPadding);
                        switchView.requestLayout();
                    }
                    break;
                }
                case "switchMinWidth": {
                    int switchMinWidth = ViewAttrUtil.getInt(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        switchView.setSwitchMinWidth(switchMinWidth);
                    } else {
                        Reflect.from(Switch.class)
                                .filed("mSwitchMinWidth", int.class)
                                .set(switchView, switchMinWidth);
                        switchView.requestLayout();
                    }
                    break;
                }
                case "switchPadding": {
                    int switchPadding = ViewAttrUtil.getInt(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        switchView.setSwitchPadding(switchPadding);
                    } else {
                        Reflect.from(Switch.class)
                                .filed("mSwitchPadding", int.class)
                                .set(switchView, switchPadding);
                        switchView.requestLayout();
                    }
                    break;
                }
                case "splitTrack": {
                    boolean splitTrack = ViewAttrUtil.getBoolean(resources, type, value);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        switchView.setSplitTrack(splitTrack);
                    } else {
                        Reflect.from(Switch.class)
                                .filed("mSplitTrack", boolean.class)
                                .set(switchView, splitTrack);
                        switchView.invalidate();
                    }
                    break;
                }
                case "thumbTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ColorStateList thumbTint = ViewAttrUtil.getColorStateList(resources, type, value);
                        switchView.setThumbTintList(thumbTint);
                    }
                    break;
                }
                case "thumbTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        switchView.setThumbTintMode(tintMode);
                    }
                    break;
                }
                case "trackTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ColorStateList trackTint = ViewAttrUtil.getColorStateList(resources, type, value);
                        switchView.setTrackTintList(trackTint);
                    }
                    break;
                }
                case "trackTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        switchView.setTrackTintMode(tintMode);
                    }
                    break;
                }
                case "switchTextAppearance": {
                    int switchTextAppearance = -1;
                    if (value != null) {
                        switchTextAppearance = (int) value;
                    }
                    if (switchTextAppearance != -1) {
                        switchView.setSwitchTextAppearance(context, switchTextAppearance);
                    }
                    break;
                }
            }
        }
    }
}
