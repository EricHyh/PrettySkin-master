package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class SwitchCompatSH extends CompoundButtonSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("android.support.v7.appcompat.R$styleable");
        mStyleableName = "SwitchCompat";
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

    public SwitchCompatSH() {
        this(android.support.v7.appcompat.R.attr.switchStyle);
    }

    public SwitchCompatSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public SwitchCompatSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof SwitchCompat && mSupportAttrNames.contains(attrName)
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
        } else if (view instanceof SwitchCompat) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            SwitchCompat switchCompat = (SwitchCompat) view;
            switch (attrName) {
                case "thumb": {
                    Drawable thumb = attrValue.getTypedValue(Drawable.class, null);
                    switchCompat.setThumbDrawable(thumb);
                    break;
                }
                case "track": {
                    Drawable track = attrValue.getTypedValue(Drawable.class, null);
                    switchCompat.setTrackDrawable(track);
                    break;
                }
                case "textOn": {
                    CharSequence textOn = attrValue.getTypedValue(CharSequence.class, null);
                    switchCompat.setTextOn(textOn);
                    break;
                }
                case "textOff": {
                    CharSequence textOff = attrValue.getTypedValue(CharSequence.class, null);
                    switchCompat.setTextOff(textOff);
                    break;
                }
                case "showText": {
                    boolean showText = attrValue.getTypedValue(boolean.class, false);
                    switchCompat.setShowText(showText);
                    break;
                }
                case "thumbTextPadding": {
                    int thumbTextPadding = attrValue.getTypedValue(int.class, 0);
                    switchCompat.setThumbTextPadding(thumbTextPadding);
                    break;
                }
                case "switchMinWidth": {
                    int switchMinWidth = attrValue.getTypedValue(int.class, 0);
                    switchCompat.setSwitchMinWidth(switchMinWidth);
                    break;
                }
                case "switchPadding": {
                    int switchPadding = attrValue.getTypedValue(int.class, 0);
                    switchCompat.setSwitchPadding(switchPadding);
                    break;
                }
                case "splitTrack": {
                    boolean splitTrack = attrValue.getTypedValue(boolean.class, false);
                    switchCompat.setSplitTrack(splitTrack);
                    break;
                }
                case "thumbTint": {
                    ColorStateList thumbTint = attrValue.getTypedValue(ColorStateList.class, null);
                    switchCompat.setThumbTintList(thumbTint);
                    break;
                }
                case "thumbTintMode": {
                    PorterDuff.Mode thumbTintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                    switchCompat.setThumbTintMode(thumbTintMode);
                    break;
                }
                case "trackTint": {
                    ColorStateList trackTint = attrValue.getTypedValue(ColorStateList.class, null);
                    switchCompat.setTrackTintList(trackTint);
                    break;
                }
                case "trackTintMode": {
                    PorterDuff.Mode trackTintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                    switchCompat.setTrackTintMode(trackTintMode);
                    break;
                }
                case "switchTextAppearance": {
                    int textAppearance = attrValue.getTypedValue(int.class, -1);
                    if (textAppearance != -1) {
                        switchCompat.setTextAppearance(context, textAppearance);
                    }
                    break;
                }
            }
        }
    }
}
