package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RatingBar;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

public class RatingBarSH extends AbsSeekBarSH {

    private Class<?> mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private final List<String> mSupportAttrNames = new ArrayList<>();

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

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "RatingBar";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }

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
                switch (attrName) {
                    case "numStars": {
                        int numStars = attrValue.getTypedValue(int.class, 5);
                        ratingBar.setNumStars(numStars);
                        break;
                    }
                    case "isIndicator": {
                        boolean isIndicator = attrValue.getTypedValue(boolean.class, false);
                        ratingBar.setIsIndicator(isIndicator);
                        break;
                    }
                    case "rating": {
                        float rating = attrValue.getTypedValue(float.class, -1.0f);
                        ratingBar.setRating(rating);
                        break;
                    }
                    case "stepSize": {
                        float stepSize = attrValue.getTypedValue(float.class, -1.0f);
                        ratingBar.setStepSize(stepSize);
                        break;
                    }
                }
            }
        }
    }
}
