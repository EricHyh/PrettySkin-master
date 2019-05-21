package com.hyh.prettyskin.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.SkinChangedListener;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.Logger;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2019/5/20
 */

public class DynamicDrawable extends Drawable {

    private final String mAttrKey;

    private final Drawable mDefaultDrawable;

    private final InnerSkinChangedListener mSkinChangedListener;

    private Drawable mSkinDrawable;

    public DynamicDrawable(String attrKey, Drawable defaultDrawable) {
        this.mAttrKey = attrKey;
        this.mDefaultDrawable = defaultDrawable;
        this.mSkinChangedListener = new InnerSkinChangedListener(this);
        PrettySkin.getInstance().addSkinReplaceListener(mSkinChangedListener);

        ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
        if (currentSkin != null) {
            AttrValue attrValue = currentSkin.getAttrValue(attrKey);
            if (attrValue != null) {
                mSkinDrawable = convertAttrValueToDrawable(attrValue);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable = getCurrentDrawable();
        drawable.draw(canvas);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setBounds(left, top, right, bottom);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setBounds(left, top, right, bottom);
        }
    }


    @Override
    public void setAlpha(int alpha) {
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setAlpha(alpha);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setColorFilter(colorFilter);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        Drawable drawable = getCurrentDrawable();
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return PixelFormat.UNKNOWN;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Logger.d("DynamicDrawable finalize: " + this);
        PrettySkin.getInstance().removeSkinReplaceListener(mSkinChangedListener);
    }

    private Drawable convertAttrValueToDrawable(AttrValue attrValue) {
        Drawable result = null;
        int type = attrValue.getType();
        Object value = attrValue.getValue();
        switch (type) {
            case ValueType.TYPE_REFERENCE: {
                Context themeContext = attrValue.getThemeContext();
                if (themeContext != null) {
                    result = themeContext.getResources().getDrawable((Integer) value);
                }
                break;
            }
            case ValueType.TYPE_DRAWABLE: {
                result = (Drawable) value;
                break;
            }
            case ValueType.TYPE_COLOR_INT: {
                result = new ColorDrawable((Integer) value);
                break;
            }
            case ValueType.TYPE_COLOR_STATE_LIST: {
                ColorStateList colorStateList = (ColorStateList) value;
                result = new ColorDrawable(colorStateList.getDefaultColor());
                break;
            }
        }
        return result;
    }

    private Drawable getCurrentDrawable() {
        if (mSkinDrawable != null) return mSkinDrawable;
        return mDefaultDrawable;
    }

    private void onSkinChanged(ISkin skin) {
        AttrValue attrValue = skin.getAttrValue(mAttrKey);
        if (attrValue != null) {
            mSkinDrawable = convertAttrValueToDrawable(attrValue);
            onDrawableChanged();
        }
    }

    private void onSkinAttrChanged(ISkin skin, List<String> changedAttrKeys) {
        if (changedAttrKeys.contains(mAttrKey)) {
            AttrValue attrValue = skin.getAttrValue(mAttrKey);
            if (attrValue != null) {
                mSkinDrawable = convertAttrValueToDrawable(attrValue);
                onDrawableChanged();
            }
        }
    }

    private void onSkinRecovered() {
        mSkinDrawable = null;
        onDrawableChanged();
    }

    private void onDrawableChanged() {
        Drawable currentDrawable = getCurrentDrawable();
        currentDrawable.setBounds(getBounds());
        currentDrawable.setState(getState());
        currentDrawable.setCallback(getCallback());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            currentDrawable.setLayoutDirection(getLayoutDirection());
        }
        currentDrawable.setChangingConfigurations(getChangingConfigurations());
        invalidateSelf();
    }

    private static class InnerSkinChangedListener implements SkinChangedListener {

        private WeakReference<DynamicDrawable> mDrawableRef;

        InnerSkinChangedListener(DynamicDrawable drawable) {
            mDrawableRef = new WeakReference<>(drawable);
        }

        @Override
        public void onSkinChanged(ISkin skin) {
            DynamicDrawable dynamicDrawable = mDrawableRef.get();
            if (dynamicDrawable != null) {
                dynamicDrawable.onSkinChanged(skin);
            }
        }

        @Override
        public void onSkinAttrChanged(ISkin skin, List<String> changedAttrKeys) {
            DynamicDrawable dynamicDrawable = mDrawableRef.get();
            if (dynamicDrawable != null) {
                dynamicDrawable.onSkinAttrChanged(skin, changedAttrKeys);
            }
        }

        @Override
        public void onSkinRecovered() {
            DynamicDrawable dynamicDrawable = mDrawableRef.get();
            if (dynamicDrawable != null) {
                dynamicDrawable.onSkinRecovered();
            }
        }
    }
}