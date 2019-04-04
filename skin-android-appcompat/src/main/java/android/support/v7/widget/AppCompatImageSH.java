package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TintableImageSourceView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/13
 */
@SuppressLint("RestrictedApi")
public class AppCompatImageSH implements ISkinHandler {

    private int mDefStyleAttr;

    public AppCompatImageSH(int defStyleAttr) {
        mDefStyleAttr = defStyleAttr;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof ImageView
                && view instanceof TintableImageSourceView
                && (TextUtils.equals(attrName, "srcCompat")
                || TextUtils.equals(attrName, "tint")
                || TextUtils.equals(attrName, "tintMode"));
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {

    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        switch (attrName) {
            case "srcCompat": {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    Drawable drawable = imageView.getDrawable();
                    if (drawable != null) {
                        attrValue = new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawable);
                    }
                }
                break;
            }
            case "tint": {
                if (view instanceof TintableImageSourceView) {
                    TintableImageSourceView tintableImageSourceView = (TintableImageSourceView) view;
                    ColorStateList supportImageTintList = tintableImageSourceView.getSupportImageTintList();
                    if (supportImageTintList != null) {
                        attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_STATE_LIST, supportImageTintList);
                    }
                }
                break;
            }
            case "tintMode": {
                if (view instanceof TintableImageSourceView) {
                    TintableImageSourceView tintableImageSourceView = (TintableImageSourceView) view;
                    PorterDuff.Mode supportBackgroundTintMode = tintableImageSourceView.getSupportImageTintMode();
                    if (supportBackgroundTintMode != null) {
                        attrValue = new AttrValue(view.getContext(), ValueType.TYPE_OBJECT, supportBackgroundTintMode);
                    }
                }
                break;
            }
        }
        return attrValue;
    }

    @Override
    public void finishParse() {

    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
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
            case "srcCompat": {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
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
                }
                break;
            }
            case "tint": {
                if (view instanceof TintableImageSourceView) {
                    TintableImageSourceView tintableImageSourceView = (TintableImageSourceView) view;
                    ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                    tintableImageSourceView.setSupportImageTintList(tint);
                }
                break;
            }
            case "tintMode": {
                if (view instanceof TintableImageSourceView) {
                    TintableImageSourceView tintableImageSourceView = (TintableImageSourceView) view;
                    PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                    tintableImageSourceView.setSupportImageTintMode(tintMode);
                }
                break;
            }
        }
    }
}
