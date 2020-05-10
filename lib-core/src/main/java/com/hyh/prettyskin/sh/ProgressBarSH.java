package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;



public class ProgressBarSH extends ViewSH {

    private Class<?> mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("progressDrawable");
        //mSupportAttrNames.add("indeterminateDuration");
        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("maxWidth");
        mSupportAttrNames.add("minHeight");
        mSupportAttrNames.add("maxHeight");
        //mSupportAttrNames.add("indeterminateBehavior");
        mSupportAttrNames.add("interpolator");
        mSupportAttrNames.add("min");
        mSupportAttrNames.add("max");
        mSupportAttrNames.add("progress");
        mSupportAttrNames.add("secondaryProgress");
        mSupportAttrNames.add("indeterminateDrawable");
        mSupportAttrNames.add("indeterminateOnly");
        mSupportAttrNames.add("indeterminate");
        mSupportAttrNames.add("mirrorForRtl");
        mSupportAttrNames.add("progressTintMode");
        mSupportAttrNames.add("progressTint");
        mSupportAttrNames.add("progressBackgroundTintMode");
        mSupportAttrNames.add("progressBackgroundTint");
        mSupportAttrNames.add("secondaryProgressTintMode");
        mSupportAttrNames.add("secondaryProgressTint");
        mSupportAttrNames.add("indeterminateTintMode");
        mSupportAttrNames.add("indeterminateTint");
    }

    public ProgressBarSH() {
        this(ViewAttrUtil.getInternalStyleAttr("progressBarStyle"));//com.android.internal.R.attr.progressBarStyle
    }

    public ProgressBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ProgressBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof ProgressBar && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "ProgressBar";
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
            if (view instanceof ProgressBar) {
                ProgressBar progressBar = (ProgressBar) view;
                Context context = attrValue.getThemeContext();
                int type = attrValue.getType();
                if (context == null && type == ValueType.TYPE_REFERENCE) {
                    return;
                }
                switch (attrName) {
                    case "progressDrawable": {
                        Drawable progressDrawable = attrValue.getTypedValue(Drawable.class, null);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                && ViewAttrUtil.needsTileify(progressDrawable)) {
                            progressBar.setProgressDrawableTiled(progressDrawable);
                        } else {
                            progressBar.setProgressDrawable(progressDrawable);
                        }
                        break;
                    }
                    case "minWidth": {
                        int minWidth = attrValue.getTypedValue(int.class, 24);
                        Reflect.from(ProgressBar.class)
                                .filed("mMinWidth", int.class)
                                .set(progressBar, minWidth);
                        progressBar.requestLayout();
                        break;
                    }
                    case "maxWidth": {
                        int maxWidth = attrValue.getTypedValue(int.class, 48);
                        Reflect.from(ProgressBar.class)
                                .filed("mMaxWidth", int.class)
                                .set(progressBar, maxWidth);
                        progressBar.requestLayout();
                        break;
                    }
                    case "minHeight": {
                        int minHeight = attrValue.getTypedValue(int.class, 24);
                        Reflect.from(ProgressBar.class)
                                .filed("mMinHeight", int.class)
                                .set(progressBar, minHeight);
                        progressBar.requestLayout();
                        break;
                    }
                    case "maxHeight": {
                        int maxHeight = attrValue.getTypedValue(int.class, 48);
                        Reflect.from(ProgressBar.class)
                                .filed("mMaxHeight", int.class)
                                .set(progressBar, maxHeight);
                        progressBar.requestLayout();
                        break;
                    }
                    case "interpolator": {
                        Interpolator interpolator = AnimationUtils.loadInterpolator(context, android.R.anim.linear_interpolator);
                        interpolator = attrValue.getTypedValue(Interpolator.class, interpolator);
                        progressBar.setInterpolator(interpolator);
                        break;
                    }
                    case "min": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            int min = attrValue.getTypedValue(int.class, 0);
                            progressBar.setMin(min);
                        }
                        break;
                    }
                    case "max": {
                        int max = attrValue.getTypedValue(int.class, 100);
                        progressBar.setMax(max);
                        break;
                    }
                    case "progress": {
                        int progress = attrValue.getTypedValue(int.class, 0);
                        progressBar.setProgress(progress);
                        break;
                    }
                    case "secondaryProgress": {
                        int secondaryProgress = attrValue.getTypedValue(int.class, 0);
                        progressBar.setSecondaryProgress(secondaryProgress);
                        break;
                    }
                    case "indeterminateDrawable": {
                        Drawable indeterminateDrawable = attrValue.getTypedValue(Drawable.class, null);
                        progressBar.setIndeterminateDrawable(indeterminateDrawable);
                        break;
                    }
                    case "indeterminateOnly": {
                        boolean indeterminateOnly = attrValue.getTypedValue(boolean.class, false);
                        Reflect.from(ProgressBar.class)
                                .filed("mOnlyIndeterminate", boolean.class)
                                .set(progressBar, indeterminateOnly);
                        progressBar.setIndeterminate(progressBar.isIndeterminate());
                        break;
                    }
                    case "indeterminate": {
                        boolean indeterminate = attrValue.getTypedValue(boolean.class, false);
                        progressBar.setIndeterminate(indeterminate);
                        break;
                    }
                    case "mirrorForRtl": {
                        boolean mirrorForRtl = attrValue.getTypedValue(boolean.class, false);
                        Reflect.from(ProgressBar.class)
                                .filed("mMirrorForRtl", boolean.class)
                                .set(progressBar, mirrorForRtl);
                        break;
                    }
                    case "progressTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                            progressBar.setProgressTintMode(tintMode);
                        }
                        break;
                    }
                    case "progressTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                            progressBar.setProgressTintList(tint);
                        }
                        break;
                    }
                    case "progressBackgroundTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                            progressBar.setProgressBackgroundTintMode(tintMode);
                        }
                        break;
                    }
                    case "progressBackgroundTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                            progressBar.setProgressBackgroundTintList(tint);
                        }
                        break;
                    }
                    case "secondaryProgressTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                            progressBar.setSecondaryProgressTintMode(tintMode);
                        }
                        break;
                    }
                    case "secondaryProgressTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                            progressBar.setSecondaryProgressTintList(tint);
                        }
                        break;
                    }
                    case "indeterminateTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                            progressBar.setIndeterminateTintMode(tintMode);
                        }
                        break;
                    }
                    case "indeterminateTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                            progressBar.setIndeterminateTintList(tint);
                        }
                        break;
                    }
                }
            }
        }
    }
}
