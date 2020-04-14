package com.hyh.prettyskin.demo.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Administrator
 * @description
 * @data 2019/12/10
 */
public class AddDrawable extends Drawable {

    private final Paint mPaint;
    private final RectF mRectF;
    private final float mSize;

    public AddDrawable(Context context, int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mRectF = new RectF();
        mSize = context.getResources().getDisplayMetrics().density * 1.5f;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        mRectF.left = bounds.left;
        mRectF.right = bounds.right;
        mRectF.top = bounds.top + height * 0.5f - mSize * 0.5f;
        mRectF.bottom = mRectF.top + mSize;
        canvas.drawRect(mRectF, mPaint);

        mRectF.left = bounds.left + width * 0.5f - mSize * 0.5f;
        mRectF.right = mRectF.left + mSize;
        mRectF.top = bounds.top;
        mRectF.bottom = bounds.bottom;
        canvas.drawRect(mRectF, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        switch (mPaint.getColor() >>> 24) {
            case 255:
                return PixelFormat.OPAQUE;
            case 0:
                return PixelFormat.TRANSPARENT;
        }
        return PixelFormat.TRANSLUCENT;
    }
}