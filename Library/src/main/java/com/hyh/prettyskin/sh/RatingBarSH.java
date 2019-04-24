package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;

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
 * @data 2018/11/6
 */

public class RatingBarSH extends AbsSeekBarSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "RatingBar";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("numStars");
        mSupportAttrNames.add("isIndicator");
        mSupportAttrNames.add("rating");
        mSupportAttrNames.add("stepSize");
    }

    public RatingBarSH() {
        super();
    }

    public RatingBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public RatingBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof RatingBar && mSupportAttrNames.contains(attrName)
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
            if (view instanceof RatingBar) {
                RatingBar ratingBar = (RatingBar) view;
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
                    case "numStars": {
                        int numStars = ViewAttrUtil.getInt(resources, type, value, 5);
                        ratingBar.setNumStars(numStars);
                        break;
                    }
                    case "isIndicator": {
                        boolean isIndicator = ViewAttrUtil.getBoolean(resources, type, value);
                        ratingBar.setIsIndicator(isIndicator);
                        break;
                    }
                    case "rating": {
                        float rating = ViewAttrUtil.getFloat(resources, type, value, -1);
                        ratingBar.setRating(rating);
                        break;
                    }
                    case "stepSize": {
                        float stepSize = ViewAttrUtil.getFloat(resources, type, value, -1);
                        ratingBar.setStepSize(stepSize);
                        break;
                    }
                }
            }
        }
    }
}
