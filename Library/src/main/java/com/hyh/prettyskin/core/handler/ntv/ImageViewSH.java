package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/28.
 */

public class ImageViewSH extends ViewSH {


    private List<String> mSupportAttrNames = new ArrayList<>();

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
        return "ImageView";
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
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            Object value = attrValue.getValue();
            switch (attrName) {
                case "src": {
                    Drawable drawable = null;
                    Bitmap bitmap = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_REFERENCE: {
                                drawable = resources.getDrawable((Integer) value);
                                break;
                            }
                            case ValueType.TYPE_DRAWABLE: {
                                drawable = (Drawable) value;
                                break;
                            }
                            case ValueType.TYPE_OBJECT: {
                                if (value instanceof Bitmap) {
                                    bitmap = (Bitmap) value;
                                }
                                break;
                            }
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
                    boolean baselineAlignBottom = false;
                    if (value != null) {
                        baselineAlignBottom = (boolean) value;
                    }
                    imageView.setBaselineAlignBottom(baselineAlignBottom);
                    break;
                }
                case "baseline": {
                    int baseline = -1;
                    if (value != null) {
                        baseline = (int) value;
                    }
                    imageView.setBaseline(baseline);
                    break;
                }
                case "adjustViewBounds": {
                    boolean adjustViewBounds = false;
                    if (value != null) {
                        adjustViewBounds = (boolean) value;
                    }
                    imageView.setAdjustViewBounds(adjustViewBounds);
                    break;
                }
                case "maxWidth": {
                    int maxWidth = Integer.MAX_VALUE;
                    if (value != null) {
                        maxWidth = (int) value;
                    }
                    imageView.setMaxWidth(maxWidth);
                    break;
                }
                case "maxHeight": {
                    int maxHeight = Integer.MAX_VALUE;
                    if (value != null) {
                        maxHeight = (int) value;
                    }
                    imageView.setMaxHeight(maxHeight);
                    break;
                }
                case "scaleType": {
                    int index = -1;
                    ImageView.ScaleType scaleType = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_INT: {
                                index = (int) value;
                                break;
                            }
                            case ValueType.TYPE_OBJECT: {
                                if (value instanceof ImageView.ScaleType) {
                                    scaleType = (ImageView.ScaleType) value;
                                }
                                break;
                            }
                        }
                    }
                    if (index >= 0) {
                        scaleType = ViewAttrUtil.getImageScaleType(index);
                    }
                    if (scaleType == null) {
                        scaleType = ImageView.ScaleType.FIT_CENTER;
                    }
                    imageView.setScaleType(scaleType);
                    break;
                }
                case "tint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                        imageView.setImageTintList(tint);
                    }
                    break;
                }
                case "tintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                        imageView.setImageTintMode(tintMode);
                    }
                    break;
                }
                case "drawableAlpha": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        int drawableAlpha = 255;
                        if (value != null) {
                            drawableAlpha = (int) value;
                        }
                        imageView.setImageAlpha(drawableAlpha);
                    }
                    break;
                }
                case "cropToPadding": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        boolean cropToPadding = false;
                        if (value != null) {
                            cropToPadding = (boolean) value;
                        }
                        imageView.setCropToPadding(cropToPadding);
                    }
                    break;
                }
            }
        }
    }
}
