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
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/11/4.
 */

public class ProgressBarSH extends ViewSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "ProgressBar";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

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
        this(ViewAttrUtil.getDefStyleAttr_internal("progressBarStyle"));//com.android.internal.R.attr.progressBarStyle
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
                Resources resources = null;
                if (context != null) {
                    resources = context.getResources();
                }
                Object value = attrValue.getValue();
                switch (attrName) {
                    case "progressDrawable": {
                        Drawable progressDrawable = ViewAttrUtil.getDrawable(resources, type, value);
                        if (progressDrawable != null
                                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                                && ViewAttrUtil.needsTileify(progressDrawable)) {
                            progressBar.setProgressDrawableTiled(progressDrawable);
                        } else {
                            progressBar.setProgressDrawable(progressDrawable);
                        }
                        break;
                    }
                    case "indeterminateDuration": {
                        //TODO 暂不支持
                        break;
                    }
                    case "minWidth": {
                        int minWidth = 24;
                        if (value != null) {
                            minWidth = (int) value;
                        }
                        Reflect.from(ProgressBar.class)
                                .filed("mMinWidth", int.class)
                                .set(progressBar, minWidth);
                        progressBar.requestLayout();
                        break;
                    }
                    case "maxWidth": {
                        int maxWidth = 48;
                        if (value != null) {
                            maxWidth = (int) value;
                        }
                        Reflect.from(ProgressBar.class)
                                .filed("mMaxWidth", int.class)
                                .set(progressBar, maxWidth);
                        progressBar.requestLayout();
                        break;
                    }
                    case "minHeight": {
                        int minHeight = 24;
                        if (value != null) {
                            minHeight = (int) value;
                        }
                        Reflect.from(ProgressBar.class)
                                .filed("mMinHeight", int.class)
                                .set(progressBar, minHeight);
                        progressBar.requestLayout();
                        break;
                    }
                    case "maxHeight": {
                        int maxHeight = 48;
                        if (value != null) {
                            maxHeight = (int) value;
                        }
                        Reflect.from(ProgressBar.class)
                                .filed("mMaxHeight", int.class)
                                .set(progressBar, maxHeight);
                        progressBar.requestLayout();
                        break;
                    }
                    case "indeterminateBehavior": {
                        //TODO 暂不支持
                        break;
                    }
                    case "interpolator": {
                        int interpolatorId = android.R.anim.linear_interpolator;
                        Interpolator interpolator = null;
                        switch (type) {
                            case ValueType.TYPE_REFERENCE: {
                                interpolator = AnimationUtils.loadInterpolator(context, interpolatorId);
                                break;
                            }
                            case ValueType.TYPE_OBJECT: {
                                if (value instanceof Interpolator) {
                                    interpolator = (Interpolator) value;
                                }
                                break;
                            }
                        }
                        progressBar.setInterpolator(interpolator);
                        break;
                    }
                    case "min": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            int min = 0;
                            if (value != null) {
                                min = (int) value;
                            }
                            progressBar.setMin(min);
                        }
                        break;
                    }
                    case "max": {
                        int max = 100;
                        if (value != null) {
                            max = (int) value;
                        }
                        progressBar.setMax(max);
                        break;
                    }
                    case "progress": {
                        int progress = 0;
                        if (value != null) {
                            progress = (int) value;
                        }
                        progressBar.setProgress(progress);
                        break;
                    }
                    case "secondaryProgress": {
                        int secondaryProgress = 0;
                        if (value != null) {
                            secondaryProgress = (int) value;
                        }
                        progressBar.setSecondaryProgress(secondaryProgress);
                        break;
                    }
                    case "indeterminateDrawable": {
                        Drawable indeterminateDrawable = ViewAttrUtil.getDrawable(resources, type, value);
                        progressBar.setIndeterminateDrawable(indeterminateDrawable);
                        break;
                    }
                    case "indeterminateOnly": {
                        boolean indeterminateOnly = false;
                        if (value != null) {
                            indeterminateOnly = (boolean) value;
                        }

                        Reflect.from(ProgressBar.class)
                                .filed("mOnlyIndeterminate", boolean.class)
                                .set(progressBar, indeterminateOnly);
                        progressBar.setIndeterminate(progressBar.isIndeterminate());
                        break;
                    }
                    case "indeterminate": {
                        boolean indeterminate = false;
                        if (value != null) {
                            indeterminate = (boolean) value;
                        }
                        progressBar.setIndeterminate(indeterminate);
                        break;
                    }
                    case "mirrorForRtl": {
                        boolean mirrorForRtl = false;
                        if (value != null) {
                            mirrorForRtl = (boolean) value;
                        }
                        Reflect.from(ProgressBar.class)
                                .filed("mMirrorForRtl", boolean.class)
                                .set(progressBar, mirrorForRtl);
                        break;
                    }
                    case "progressTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                            progressBar.setProgressTintMode(tintMode);
                        }
                        break;
                    }
                    case "progressTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                            progressBar.setProgressTintList(tint);
                        }
                        break;
                    }
                    case "progressBackgroundTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                            progressBar.setProgressBackgroundTintMode(tintMode);
                        }
                        break;
                    }
                    case "progressBackgroundTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                            progressBar.setProgressBackgroundTintList(tint);
                        }
                        break;
                    }
                    case "secondaryProgressTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                            progressBar.setSecondaryProgressTintMode(tintMode);
                        }
                        break;
                    }
                    case "secondaryProgressTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                            progressBar.setSecondaryProgressTintList(tint);
                        }
                        break;
                    }
                    case "indeterminateTintMode": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                            progressBar.setIndeterminateTintMode(tintMode);
                        }
                        break;
                    }
                    case "indeterminateTint": {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                            progressBar.setIndeterminateTintList(tint);
                        }
                        break;
                    }
                }
            }
        }
    }
}
