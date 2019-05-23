package com.hyh.prettyskin.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.SkinChangedListener;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.Logger;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2019/5/20
 */

public class DynamicDrawable extends Drawable implements Drawable.Callback {

    private final DrawableState mDrawableState = new DrawableState();

    private final String mAttrKey;

    private final Drawable mDefaultDrawable;

    private final InnerSkinChangedListener mSkinChangedListener;

    private Drawable mSkinDrawable;

    public DynamicDrawable(String attrKey, Drawable defaultDrawable) {
        if (defaultDrawable == null) throw new NullPointerException("defaultDrawable can't be null!");
        this.mAttrKey = attrKey;
        this.mDefaultDrawable = defaultDrawable;
        this.mSkinChangedListener = new InnerSkinChangedListener(this);
        defaultDrawable.setCallback(this);
        PrettySkin.getInstance().addSkinReplaceListener(mSkinChangedListener);
        ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
        if (currentSkin != null) {
            AttrValue attrValue = currentSkin.getAttrValue(attrKey);
            if (attrValue != null) {
                mSkinDrawable = convertAttrValueToDrawable(attrValue);
            }
        }
        onDrawableChanged();
    }

    @Override
    public void draw(Canvas canvas) {
        getCurrentDrawable().draw(canvas);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setBounds(left, top, right, bottom);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setBounds(left, top, right, bottom);
        }
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setBounds(Rect bounds) {
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setBounds(bounds);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setBounds(bounds);
        }
        super.setBounds(bounds);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mDrawableState.alpha != alpha) {
            mDrawableState.alpha = alpha;
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setAlpha(alpha);
            }
            if (mSkinDrawable != null) {
                mSkinDrawable.setAlpha(alpha);
            }
        }
    }

    @Override
    public int getAlpha() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getCurrentDrawable().getAlpha();
        } else {
            return super.getAlpha();
        }
    }

    @Override
    public void setChangingConfigurations(int configs) {
        super.setChangingConfigurations(configs);
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setChangingConfigurations(configs);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setChangingConfigurations(configs);
        }
    }

    @Override
    public Rect getDirtyBounds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getCurrentDrawable().getDirtyBounds();
        } else {
            return super.getDirtyBounds();
        }
    }

    @Override
    public void setDither(boolean dither) {
        super.setDither(dither);
        mDrawableState.dither = dither;
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setDither(dither);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setDither(dither);
        }
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        super.setFilterBitmap(filter);
        mDrawableState.filterBitmap = filter;
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setFilterBitmap(filter);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setFilterBitmap(filter);
        }
    }

    @Override
    public boolean isFilterBitmap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getCurrentDrawable().isFilterBitmap();
        } else {
            return super.isFilterBitmap();
        }
    }

    @Override
    public boolean onLayoutDirectionChanged(int layoutDirection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getCurrentDrawable().onLayoutDirectionChanged(layoutDirection);
        } else {
            return super.onLayoutDirectionChanged(layoutDirection);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mDrawableState.colorFilter = colorFilter;
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setColorFilter(colorFilter);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setColorFilter(colorFilter);
        }
    }

    @Override
    public void setTintList(ColorStateList tint) {
        super.setTintList(tint);
        mDrawableState.tint = tint;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setTintList(tint);
            }
            if (mSkinDrawable != null) {
                mSkinDrawable.setTintList(tint);
            }
        }
    }

    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        super.setTintMode(tintMode);
        mDrawableState.tintMode = tintMode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setTintMode(tintMode);
            }
            if (mSkinDrawable != null) {
                mSkinDrawable.setTintMode(tintMode);
            }
        }
    }

    @Override
    public ColorFilter getColorFilter() {
        return mDrawableState.colorFilter;
    }

    @Override
    public void setHotspot(float x, float y) {
        super.setHotspot(x, y);
        mDrawableState.hotspot = new float[]{x, y};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setHotspot(x, y);
            }
            if (mSkinDrawable != null) {
                mSkinDrawable.setHotspot(x, y);
            }
        }
    }

    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        super.setHotspotBounds(left, top, right, bottom);
        mDrawableState.hotspotBounds = new int[]{left, top, right, bottom};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setHotspotBounds(left, top, right, bottom);
            }
            if (mSkinDrawable != null) {
                mSkinDrawable.setHotspotBounds(left, top, right, bottom);
            }
        }
    }

    @Override
    public void getHotspotBounds(Rect outRect) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getCurrentDrawable().getHotspotBounds(outRect);
        } else {
            super.getHotspotBounds(outRect);
        }
    }

    @Override
    public boolean isStateful() {
        return getCurrentDrawable().isStateful();
    }

    @Override
    public boolean setState(int[] stateSet) {
        if (mDefaultDrawable != null) {
            mDefaultDrawable.setState(stateSet);
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.setState(stateSet);
        }
        return super.setState(stateSet);
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        if (mDefaultDrawable != null) {
            mDefaultDrawable.jumpToCurrentState();
        }
        if (mSkinDrawable != null) {
            mSkinDrawable.jumpToCurrentState();
        }
    }

    @Override
    public void setAutoMirrored(boolean mirrored) {
        super.setAutoMirrored(mirrored);
        mDrawableState.mirrored = mirrored;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setAutoMirrored(mirrored);
            }
            if (mSkinDrawable != null) {
                mSkinDrawable.setAutoMirrored(mirrored);
            }
        }
    }

    @Override
    public boolean isAutoMirrored() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getCurrentDrawable().isAutoMirrored();
        } else {
            return super.isAutoMirrored();
        }
    }

    @Override
    public void applyTheme(Resources.Theme t) {
        super.applyTheme(t);
        mDrawableState.theme = t;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mDefaultDrawable != null && mDefaultDrawable.canApplyTheme()) {
                mDefaultDrawable.applyTheme(t);
            }
            if (mSkinDrawable != null && mSkinDrawable.canApplyTheme()) {
                mSkinDrawable.applyTheme(t);
            }
        }
    }

    @Override
    public boolean canApplyTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getCurrentDrawable().canApplyTheme();
        } else {
            return super.canApplyTheme();
        }
    }

    @Override
    public Region getTransparentRegion() {
        return getCurrentDrawable().getTransparentRegion();
    }

    @Override
    public int getIntrinsicWidth() {
        return getCurrentDrawable().getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return getCurrentDrawable().getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        return getCurrentDrawable().getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return getCurrentDrawable().getMinimumHeight();
    }

    @Override
    public boolean getPadding(Rect padding) {
        return getCurrentDrawable().getPadding(padding);
    }

    @Override
    public void getOutline(Outline outline) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getCurrentDrawable().getOutline(outline);
        } else {
            super.getOutline(outline);
        }
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        return getCurrentDrawable().getConstantState();
    }

    @Override
    public int getOpacity() {
        return getCurrentDrawable().getOpacity();
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
                result = new ColorListDrawable(colorStateList);
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

        if (currentDrawable == mSkinDrawable) {
            mDefaultDrawable.setCallback(null);
        }
        currentDrawable.setCallback(this);

        currentDrawable.setAlpha(mDrawableState.alpha);
        currentDrawable.setDither(mDrawableState.dither);
        currentDrawable.setColorFilter(mDrawableState.colorFilter);
        currentDrawable.setFilterBitmap(mDrawableState.filterBitmap);
        currentDrawable.setChangingConfigurations(getChangingConfigurations());
        currentDrawable.setBounds(getBounds());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            currentDrawable.setTintList(mDrawableState.tint);
            currentDrawable.setTintMode(mDrawableState.tintMode);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float[] hotspot = mDrawableState.hotspot;
            int[] hotspotBounds = mDrawableState.hotspotBounds;
            if (hotspot != null) {
                currentDrawable.setHotspot(hotspot[0], hotspot[1]);
            }
            if (hotspotBounds != null) {
                currentDrawable.setHotspotBounds(hotspotBounds[0], hotspotBounds[1], hotspotBounds[2], hotspotBounds[3]);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            currentDrawable.setAutoMirrored(mDrawableState.mirrored);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Resources.Theme theme = mDrawableState.theme;
            if (theme != null && currentDrawable.canApplyTheme()) {
                currentDrawable.applyTheme(theme);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            currentDrawable.setLayoutDirection(getLayoutDirection());
        }
        currentDrawable.setLevel(getLevel());
        currentDrawable.setState(getState());
        invalidateSelf();
    }


    @Override
    public void invalidateSelf() {
        Callback callback = getCallback();
        if (callback != null && callback instanceof View) {
            View view = (View) callback;
            final Rect dirty = getDirtyBounds();
            final int scrollX = view.getScrollX();
            final int scrollY = view.getScrollY();
            view.invalidate(dirty.left + scrollX, dirty.top + scrollY,
                    dirty.right + scrollX, dirty.bottom + scrollY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.invalidateOutline();
            } else {
                Reflect.from(View.class).method("rebuildOutline").invoke(view);
            }
        } else {
            super.invalidateSelf();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Logger.d("DynamicDrawable finalize: " + this);
        PrettySkin.getInstance().removeSkinReplaceListener(mSkinChangedListener);
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
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

    private static class DrawableState {
        int alpha = 255;
        boolean dither;
        ColorFilter colorFilter;
        boolean filterBitmap;
        ColorStateList tint;
        PorterDuff.Mode tintMode;
        float[] hotspot;
        int[] hotspotBounds;
        boolean mirrored;
        Resources.Theme theme;
    }
}