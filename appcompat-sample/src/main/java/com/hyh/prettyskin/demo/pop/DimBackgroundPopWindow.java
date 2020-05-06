package com.hyh.prettyskin.demo.pop;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


/**
 * @author Administrator
 * @description
 * @data 2018/6/29
 */

public abstract class DimBackgroundPopWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    private ViewGroup mCurrentBackgroundParent;
    private View mBackgroundView;
    private OnDismissListener mOnDismissListener;
    private ObjectAnimator mAlphaAnimator;

    {
        super.setOnDismissListener(this);
    }

    public DimBackgroundPopWindow(Context context) {
        super(context);
    }

    public DimBackgroundPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DimBackgroundPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DimBackgroundPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DimBackgroundPopWindow() {
        super();
    }

    public DimBackgroundPopWindow(View contentView) {
        super(contentView);
    }

    public DimBackgroundPopWindow(int width, int height) {
        super(width, height);
    }

    public DimBackgroundPopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public DimBackgroundPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showDimBackground(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showDimBackground(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        showDimBackground(anchor);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showDimBackground(parent);
    }

    private void showDimBackground(View view) {
        ViewGroup backgroundParent = getBackgroundParent() == null ? (ViewGroup) view.getRootView() : getBackgroundParent();
        if (backgroundParent == null) {
            return;
        }
        if (mCurrentBackgroundParent == null) {
            if (mBackgroundView == null) {
                mBackgroundView = new View(backgroundParent.getContext());
                mBackgroundView.setBackgroundColor(0x66000000);
                mAlphaAnimator = ObjectAnimator.ofFloat(mBackgroundView, "alpha", 0.0f, 1.0f).setDuration(getAnimDuration());
                mAlphaAnimator.start();
            }
            mCurrentBackgroundParent = backgroundParent;
            mCurrentBackgroundParent.addView(mBackgroundView);
            mAlphaAnimator.start();
        }
    }

    protected void startDismissAnim() {
        mAlphaAnimator.reverse();
    }

    protected long getAnimDuration() {
        return 0;
    }

    protected ViewGroup getBackgroundParent() {
        return null;
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss() {
        if (mCurrentBackgroundParent != null) {
            mCurrentBackgroundParent.removeView(mBackgroundView);
            mCurrentBackgroundParent = null;
        }
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss();
        }
    }
}