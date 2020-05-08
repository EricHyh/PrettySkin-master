package com.hyh.prettyskin.demo.utils;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @description
 * @data 2019/1/14
 */

public class ViewUtil {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        }
    }

    public static void setVisibility(View view, int visibility) {
        if (view == null || view.getVisibility() == visibility) return;
        view.setVisibility(visibility);
    }

    public static boolean isViewInScreen(View view) {
        if (view == null
                || view.getVisibility() != View.VISIBLE
                || view.getWindowToken() == null) {
            return false;
        }
        if (!view.getLocalVisibleRect(new Rect())) {
            return false;
        }
        ViewParent parent = view.getParent();
        if (parent == null) {
            return view == view.getRootView();
        } else {
            return isParentAlive(parent);
        }
    }

    public static boolean isParentAlive(ViewParent parent) {
        if (parent instanceof View) {
            View parentView = (View) parent;
            if (parentView.getWindowToken() == null
                    || parentView.getVisibility() != View.VISIBLE) {
                return false;
            } else {
                ViewParent grandParent = parentView.getParent();
                if (grandParent == null) {
                    return parentView == parentView.getRootView();
                } else {
                    return isParentAlive(grandParent);
                }
            }
        } else {
            return true;
        }
    }

    public static float getVisibleScale(View view) {
        if (!isViewInScreen(view)) return 0.0f;
        Rect rect = new Rect();
        if (!view.getGlobalVisibleRect(rect)) return 0.0f;
        long rectArea = (long) rect.height() * (long) rect.width();
        long viewArea = (long) view.getHeight() * (long) view.getWidth();
        if (rectArea == 0 || viewArea == 0) return 0.0f;
        return rectArea * 1.0f / viewArea;
    }

    public static float getVerticalVisibleScale(View view) {
        if (!isViewInScreen(view)) return 0.0f;
        Rect rect = new Rect();
        if (!view.getGlobalVisibleRect(rect)) return 0.0f;
        return rect.height() * 1.0f / view.getHeight();
    }

    public static int getVerticalVisibleHeight(View view) {
        if (!isViewInScreen(view)) return 0;
        Rect rect = new Rect();
        if (!view.getGlobalVisibleRect(rect)) return 0;
        return rect.height();
    }

    public static float getHorizontalVisibleScale(View view) {
        if (!isViewInScreen(view)) return 0.0f;
        Rect rect = new Rect();
        if (!view.getGlobalVisibleRect(rect)) return 0.0f;
        return rect.width() * 1.0f / view.getWidth();
    }

    public static int getHorizontalVisibleHeight(View view) {
        if (!isViewInScreen(view)) return 0;
        Rect rect = new Rect();
        if (!view.getGlobalVisibleRect(rect)) return 0;
        return rect.width();
    }

    public static boolean isParent(View child, View parent) {
        ViewParent curParent = child.getParent();
        if (!(curParent instanceof ViewGroup)) {
            return false;
        }
        if (curParent == parent) {
            return true;
        }
        child = (View) curParent;
        return isParent(child, parent);
    }

    public static boolean isAttachedToWindow(View view) {
        if (view == null) return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return view.isAttachedToWindow();
        } else {
            return view.getWindowToken() != null;
        }
    }

    public static void removeFromParent(View view) {
        if (view == null) return;
        ViewParent parent = view.getParent();
        if (!(parent instanceof ViewGroup)) return;
        ViewGroup viewGroup = (ViewGroup) parent;
        viewGroup.removeView(view);
    }
}