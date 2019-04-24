package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsSeekBar;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/11/4.
 */

public class AbsSeekBarSH extends ProgressBarSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "SeekBar";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("thumb");
        mSupportAttrNames.add("thumbTintMode");
        mSupportAttrNames.add("thumbTint");
        mSupportAttrNames.add("tickMark");
        mSupportAttrNames.add("tickMarkTintMode");
        mSupportAttrNames.add("tickMarkTint");
        mSupportAttrNames.add("splitTrack");
        mSupportAttrNames.add("thumbOffset");
        //mSupportAttrNames.add("useDisabledAlpha");
    }


    public AbsSeekBarSH() {
        super();
    }

    public AbsSeekBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AbsSeekBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof AbsSeekBar && mSupportAttrNames.contains(attrName)
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
        } else {
            if (view instanceof AbsSeekBar) {
                AbsSeekBar absSeekBar = (AbsSeekBar) view;
                Context context = attrValue.getThemeContext();
                int type = attrValue.getType();
                if (context == null && type == ValueType.TYPE_REFERENCE) {
                    return;
                }
                Resources resources = null;
                if (context != null) {
                    resources = context.getResources();
                }
                Object value = attrValue.getValue();
                switch (attrName) {
                    case "thumb": {
                        Drawable thumb = ViewAttrUtil.getDrawable(resources, type, value);
                        absSeekBar.setThumb(thumb);
                        break;
                    }
                    case "thumbTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                            absSeekBar.setThumbTintMode(tintMode);
                        }
                        break;
                    }
                    case "thumbTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                            absSeekBar.setThumbTintList(tint);
                        }
                        break;
                    }
                    case "tickMark": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Drawable tickMark = ViewAttrUtil.getDrawable(resources, type, value);
                            absSeekBar.setTickMark(tickMark);
                        }
                        break;
                    }
                    case "tickMarkTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                            absSeekBar.setTickMarkTintMode(tintMode);
                        }
                        break;
                    }
                    case "tickMarkTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                                absSeekBar.setTickMarkTintList(tint);
                            }
                        }
                        break;
                    }
                    case "splitTrack": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            boolean splitTrack = ViewAttrUtil.getBoolean(resources, type, value);
                            absSeekBar.setSplitTrack(splitTrack);
                        }
                        break;
                    }
                    case "thumbOffset": {
                        int thumbOffset = ViewAttrUtil.getInt(resources, type, value);
                        absSeekBar.setThumbOffset(thumbOffset);
                        break;
                    }
                    case "useDisabledAlpha": {
                        //TODO 暂未实现
                        break;
                    }
                }
            }
        }
    }
}
