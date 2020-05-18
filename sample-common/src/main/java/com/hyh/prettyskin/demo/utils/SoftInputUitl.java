package com.hyh.prettyskin.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by Eric_He on 2016/10/11.
 */

public class SoftInputUitl {

    public static void hideOrShow(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);//强制显示键盘
        }
    }

    public static void useDefault(Activity activity) {
        ((InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSoftInputShow(View rootView) {
        if (rootView == null) {
            return false;
        }
        Context context = rootView.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && !imm.isActive()) {
            return false;
        }
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        int visibleHeight = rect.bottom - rect.top;
        int screenHeight = DisplayUtil.getScreenHeight(context);
        int diffHeight = screenHeight - visibleHeight;
        return diffHeight > (screenHeight / 3.0f + 0.5f);
    }

    public static void addSoftInputListener(View view, SoftInputListener listener) {
        if (view != null && listener != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
        }
    }

    public static void removeSoftInputListener(View view, SoftInputListener listener) {
        view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }

    public static void resolveActivityLeaked(Context destroyedContext) {
        if (destroyedContext == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) destroyedContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field viewField;
        Object immView;
        try {
            boolean needToDestroy = false;
            for (String param : arr) {
                viewField = imm.getClass().getDeclaredField(param);
                if (!viewField.isAccessible()) {
                    viewField.setAccessible(true);
                }
                immView = viewField.get(imm);
                if (immView instanceof View) {
                    View v_get = (View) immView;
                    needToDestroy = hasDestroyedContext(v_get, destroyedContext);
                    if (needToDestroy) {
                        break;
                    }
                }
            }
            if (needToDestroy) {
                for (String param : arr) {
                    viewField = imm.getClass().getDeclaredField(param);
                    if (!viewField.isAccessible()) {
                        viewField.setAccessible(true);
                    }
                    immView = viewField.get(imm);
                    if (immView instanceof View) {
                        viewField.set(imm, null); // 置空，破坏掉path to gc节点
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static boolean hasDestroyedContext(View v_get, Context destroyedContext) {
        Context context = v_get.getContext();
        return context == destroyedContext;
    }


    public abstract static class SoftInputListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private WeakReference<View> mViewReference;

        private boolean mIsSoftInputShow;

        public SoftInputListener(View view) {
            this.mViewReference = new WeakReference<>(view);
        }

        @Override
        public void onGlobalLayout() {
            View view = mViewReference.get();
            if (view == null) {
                return;
            }
            View rootView = view.getRootView();
            if (rootView == null) {
                return;
            }

            Context context = rootView.getContext();
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            int visibleHeight = rect.bottom - rect.top;
            int screenHeight = DisplayUtil.getScreenHeight(context);
            int diffHeight = screenHeight - visibleHeight;
            if (diffHeight > (screenHeight / 3.0f + 0.5f)) {
                if (!mIsSoftInputShow) {
                    onSoftInputShow();
                }
                mIsSoftInputShow = true;
            } else {
                if (mIsSoftInputShow) {
                    onSoftInputHide();
                }
                mIsSoftInputShow = false;
            }
        }

        protected abstract void onSoftInputShow();

        protected abstract void onSoftInputHide();
    }
}