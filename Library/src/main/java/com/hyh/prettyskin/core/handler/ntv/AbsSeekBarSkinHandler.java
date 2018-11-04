package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsSeekBar;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/11/4.
 */

public class AbsSeekBarSkinHandler extends ProgressBarSkinHandler {

    private List<String> mSupportAttrNames = new ArrayList<>();

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


    public AbsSeekBarSkinHandler() {
        super();
    }

    public AbsSeekBarSkinHandler(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AbsSeekBarSkinHandler(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof AbsSeekBar && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parseAttrValue(view, set, attrName);
        } else {
            Class styleableClass = getStyleableClass();
            String styleableName = getStyleableName();
            return parseAttrValue(view, set, attrName, styleableClass, styleableName);
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
        return "SeekBar";
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
