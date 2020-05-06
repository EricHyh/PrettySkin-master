package com.hyh.prettyskin.sh;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/28.
 */

public class ImageViewSH extends ViewSH {

    private Class<?> mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("src");
        mSupportAttrNames.add("baselineAlignBottom");
        mSupportAttrNames.add("baseline");
        mSupportAttrNames.add("adjustViewBounds");
        mSupportAttrNames.add("maxWidth");
        mSupportAttrNames.add("maxHeight");
        mSupportAttrNames.add("scaleType");
        mSupportAttrNames.add("tint");
        mSupportAttrNames.add("tintMode");
        mSupportAttrNames.add("drawableAlpha");
        mSupportAttrNames.add("cropToPadding");
    }

    public ImageViewSH() {
        super();
    }

    public ImageViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ImageViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof ImageView && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "ImageView";
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
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            switch (attrName) {
                case "src": {
                    Drawable drawable = null;
                    Bitmap bitmap = null;
                    switch (type) {
                        case ValueType.TYPE_REFERENCE:
                        case ValueType.TYPE_DRAWABLE:
                        case ValueType.TYPE_LAZY_DRAWABLE: {
                            drawable = attrValue.getTypedValue(Drawable.class, null);
                            break;
                        }
                        case ValueType.TYPE_OBJECT: {
                            bitmap = attrValue.getTypedValue(Bitmap.class, null);
                            break;
                        }
                    }
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageDrawable(drawable);
                    }
                    break;
                }
                case "baselineAlignBottom": {
                    boolean baselineAlignBottom = attrValue.getTypedValue(boolean.class, false);
                    imageView.setBaselineAlignBottom(baselineAlignBottom);
                    break;
                }
                case "baseline": {
                    int baseline = attrValue.getTypedValue(int.class, -1);
                    imageView.setBaseline(baseline);
                    break;
                }
                case "adjustViewBounds": {
                    boolean adjustViewBounds = attrValue.getTypedValue(boolean.class, false);
                    imageView.setAdjustViewBounds(adjustViewBounds);
                    break;
                }
                case "maxWidth": {
                    int maxWidth = attrValue.getTypedValue(int.class, Integer.MAX_VALUE);
                    imageView.setMaxWidth(maxWidth);
                    break;
                }
                case "maxHeight": {
                    int maxHeight = attrValue.getTypedValue(int.class, Integer.MAX_VALUE);
                    imageView.setMaxHeight(maxHeight);
                    break;
                }
                case "scaleType": {
                    ImageView.ScaleType scaleType = attrValue.getTypedValue(ImageView.ScaleType.class, ImageView.ScaleType.FIT_CENTER);
                    imageView.setScaleType(scaleType);
                    break;
                }
                case "tint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                        imageView.setImageTintList(tint);
                    }
                    break;
                }
                case "tintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                        imageView.setImageTintMode(tintMode);
                    }
                    break;
                }
                case "drawableAlpha": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        int drawableAlpha = attrValue.getTypedValue(int.class, 255);
                        imageView.setImageAlpha(drawableAlpha);
                    }
                    break;
                }
                case "cropToPadding": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        boolean cropToPadding = attrValue.getTypedValue(boolean.class, false);
                        imageView.setCropToPadding(cropToPadding);
                    }
                    break;
                }
            }
        }
    }
}
