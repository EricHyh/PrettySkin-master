package com.hyh.prettyskin.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.hyh.prettyskin.utils.reflect.Lazy;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * @author Administrator
 * @description
 * @data 2019/5/23
 */

class ColorListDrawable extends Drawable {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final ColorStateList mColorStateList;
    private final int mDefaultColor;

    private int mAlpha;
    private ColorStateList mTint;
    private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;
    private PorterDuffColorFilter mTintFilter;

    ColorListDrawable(ColorStateList colorStateList) {
        this.mColorStateList = colorStateList;
        this.mDefaultColor = mColorStateList.getDefaultColor();
    }

    @Override
    public void draw(Canvas canvas) {
        int currentColor = getCurrentColor();
        final ColorFilter colorFilter = mPaint.getColorFilter();
        if ((currentColor >>> 24) != 0 || colorFilter != null || mTintFilter != null) {
            if (colorFilter == null) {
                mPaint.setColorFilter(mTintFilter);
            }
            mPaint.setColor(currentColor);
            canvas.drawRect(getBounds(), mPaint);

            // Restore original color filter.
            mPaint.setColorFilter(colorFilter);
        }
    }

    private int getCurrentColor() {
        int colorForState = mColorStateList.getColorForState(getState(), mDefaultColor);
        int alpha = mAlpha;
        alpha += alpha >> 7;   // make it 0..256
        final int baseAlpha = colorForState >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        return (colorForState << 8 >>> 8) | (useAlpha << 24);
    }

    @Override
    public void setAlpha(int alpha) {
        if (this.mAlpha != alpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        int currentColor = getCurrentColor();
        return currentColor >>> 24;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public void setTintList(ColorStateList tint) {
        if (this.mTint != tint) {
            this.mTint = tint;
            mTintFilter = updateTintFilter(mTintFilter);
            invalidateSelf();
        }
    }

    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        if (this.mTintMode != tintMode) {
            this.mTintMode = tintMode;
            mTintFilter = updateTintFilter(mTintFilter);
            invalidateSelf();
        }
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        if (mTint != null && mTintMode != null) {
            mTintFilter = updateTintFilter(mTintFilter);
            return true;
        }
        return false;
    }

    @Override
    public boolean isStateful() {
        return mTint != null && mTint.isStateful();
    }

    public boolean hasFocusStateSpecified() {
        return mTint != null
                && Reflect.from(ColorStateList.class)
                .method("hasFocusStateSpecified", boolean.class)
                .invoke(mTint);
    }

    public void setXfermode(Xfermode mode) {
        mPaint.setXfermode(mode);
        invalidateSelf();
    }

    public Xfermode getXfermode() {
        return mPaint.getXfermode();
    }

    @Override
    public int getOpacity() {
        if (mTintFilter != null || mPaint.getColorFilter() != null) {
            return PixelFormat.TRANSLUCENT;
        }
        int colorForState = getCurrentColor();
        switch (colorForState >>> 24) {
            case 255: {
                return PixelFormat.OPAQUE;
            }
            case 0: {
                return PixelFormat.TRANSPARENT;
            }
        }
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void getOutline(Outline outline) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outline.setRect(getBounds());
            outline.setAlpha(getAlpha() / 255.0f);
        } else {
            super.getOutline(outline);
        }
    }

    private PorterDuffColorFilter updateTintFilter(final PorterDuffColorFilter tintFilter) {
        return Reflect.from(Drawable.class)
                .method("updateTintFilter", PorterDuffColorFilter.class)
                .defaultValue(new Lazy<PorterDuffColorFilter>() {
                    @Override
                    protected PorterDuffColorFilter create() {
                        if (mTint == null || mTintMode == null) {
                            return null;
                        }
                        final int color = mTint.getColorForState(getState(), Color.TRANSPARENT);
                        if (tintFilter == null) {
                            return new PorterDuffColorFilter(color, mTintMode);
                        }
                        Reflect.from(PorterDuffColorFilter.class)
                                .method("setColor")
                                .param(int.class, color)
                                .invoke(tintFilter);
                        Reflect.from(PorterDuffColorFilter.class)
                                .method("setMode")
                                .param(PorterDuff.Mode.class, mTintMode)
                                .invoke(tintFilter);
                        return tintFilter;
                    }
                })
                .param(PorterDuffColorFilter.class, mTintFilter)
                .param(ColorStateList.class, mTint)
                .param(PorterDuff.Mode.class, mTintMode)
                .invoke(this);
    }
}