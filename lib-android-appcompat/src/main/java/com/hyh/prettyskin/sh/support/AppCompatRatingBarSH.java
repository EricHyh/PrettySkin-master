package com.hyh.prettyskin.sh.support;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.sh.RatingBarSH;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class AppCompatRatingBarSH extends RatingBarSH {


    public AppCompatRatingBarSH() {
        this(android.support.v7.appcompat.R.attr.ratingBarStyle);
    }

    public AppCompatRatingBarSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatRatingBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }


    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        ProgressBar progressBar = (ProgressBar) view;
        if (TextUtils.equals(attrName, "progressDrawable")) {
            Drawable drawable = progressBar.getProgressDrawable();
            return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawable);
        } else if (TextUtils.equals(attrName, "indeterminateDrawable")) {
            Drawable drawable = progressBar.getIndeterminateDrawable();
            return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawable);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        ProgressBar progressBar = (ProgressBar) view;
        if (TextUtils.equals(attrName, "progressDrawable")) {
            progressBar.setProgressDrawable(attrValue.getTypedValue(Drawable.class, null));
        } else if (TextUtils.equals(attrName, "indeterminateDrawable")) {
            progressBar.setIndeterminateDrawable(attrValue.getTypedValue(Drawable.class, null));
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}