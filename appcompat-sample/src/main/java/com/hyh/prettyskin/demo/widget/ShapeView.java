package com.hyh.prettyskin.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.R;


/**
 * @author Administrator
 * @description
 * @data 2020/4/14
 */
public class ShapeView extends View {

    private int mShape;
    private float mBorderWidth;
    private Paint mPaint;

    private final RectF mRectF = new RectF();

    private final Path mPath = new Path();

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeView);
        mShape = typedArray.getInt(R.styleable.ShapeView_shape, 0);
        int borderColor = typedArray.getColor(R.styleable.ShapeView_border_color, 0);
        mBorderWidth = typedArray.getDimension(R.styleable.ShapeView_border_width, 0.0f);
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(borderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mShape) {
            case 1: {
                drawRing(canvas);
                break;
            }
            case 2: {
                drawTriangle(canvas);
                break;
            }
            default: {
                drawRectangle(canvas);
                break;
            }
        }
    }

    @SuppressWarnings("all")
    private void drawRectangle(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        float halfBorderWidth = mBorderWidth * 0.5f;

        mRectF.set(halfBorderWidth, halfBorderWidth, width - halfBorderWidth, height - halfBorderWidth);
        canvas.drawRect(mRectF, mPaint);
    }

    private void drawRing(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        float halfBorderWidth = mBorderWidth * 0.5f;

        float cx = width * 0.5f;
        float cy = height * 0.5f;
        float radius = Math.min(cx, cy) - halfBorderWidth;
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    private void drawTriangle(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mPath.reset();

        //mBorderWidth*Math.sqrt(5)*0.5
        //(0.25*(Math.sqrt(5)+1)*mBorderWidth)

        mPath.moveTo(width * 0.5f, (float) (mBorderWidth * Math.sqrt(5) * 0.5));
        mPath.lineTo((float) (width - (0.25 * (Math.sqrt(5) + 1) * mBorderWidth)), (float) (height - 0.5 * mBorderWidth));
        mPath.lineTo((float) (0.25 * (Math.sqrt(5) + 1) * mBorderWidth), (float) (height - 0.5 * mBorderWidth));
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }


    public void setShape(int shape) {
        mShape = shape;
        postInvalidate();
    }

    public void setBorderColor(int borderColor) {
        mPaint.setColor(borderColor);
        postInvalidate();
    }

    public void setBorderWidth(float borderWidth) {
        mBorderWidth = borderWidth;
        mPaint.setStrokeWidth(borderWidth);
        postInvalidate();
    }
}