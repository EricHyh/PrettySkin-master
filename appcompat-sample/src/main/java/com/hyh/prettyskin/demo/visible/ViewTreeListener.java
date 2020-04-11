package com.hyh.prettyskin.demo.visible;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.demo.receiver.ScreenListener;
import com.hyh.prettyskin.demo.receiver.ScreenReceiver;
import com.hyh.prettyskin.demo.utils.PhoneStateUtil;
import com.hyh.prettyskin.demo.utils.ThreadUtil;
import com.hyh.prettyskin.demo.utils.ViewUtil;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Administrator
 * @description
 * @data 2019/7/17
 */

public class ViewTreeListener implements View.OnAttachStateChangeListener,
        ViewTreeObserver.OnGlobalLayoutListener,
        /*ViewTreeObserver.OnPreDrawListener,*/
        ViewTreeObserver.OnScrollChangedListener, ScreenListener, OnWindowFocusChangeListener {

    private final WeakReference<View> mViewRef;
    private boolean mIsScreenOn;

    private DetectTask mWindowFocusChangeTask = new DetectTask();

    ViewTreeListener(View view) {
        this.mViewRef = new WeakReference<>(view);
        view.addOnAttachStateChangeListener(this);
        mIsScreenOn = PhoneStateUtil.isScreenOn(view.getContext());
        if (ViewUtil.isAttachedToWindow(view)) {
            ScreenReceiver.getInstance(view.getContext()).addListener(this);
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
            //view.getViewTreeObserver().addOnPreDrawListener(this);
            view.getViewTreeObserver().addOnScrollChangedListener(this);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                WindowFocusChangeListenerWrapper.addWindowFocusChangeListener(view, this);
            } else {
                WindowFocusChangeListenerView.addWindowFocusChangeListener(view, (ViewGroup) view.getRootView(), this);
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        ScreenReceiver.getInstance(v.getContext()).addListener(this);
        v.getViewTreeObserver().addOnGlobalLayoutListener(this);
        //v.getViewTreeObserver().addOnPreDrawListener(this);
        v.getViewTreeObserver().addOnScrollChangedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            WindowFocusChangeListenerWrapper.addWindowFocusChangeListener(v, this);
        } else {
            WindowFocusChangeListenerView.addWindowFocusChangeListener(v, (ViewGroup) v.getRootView(), this);
        }

        detect();
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        ScreenReceiver.getInstance(v.getContext()).removeListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        //v.getViewTreeObserver().removeOnPreDrawListener(this);
        v.getViewTreeObserver().removeOnScrollChangedListener(this);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            WindowFocusChangeListenerView.removeWindowFocusChangeListener(v, this);
        }

        onDetached();


    }

    @Override
    public void onGlobalLayout() {
        detect();
    }

    /*@Override
    public boolean onPreDraw() {
        detect();
        return true;
    }*/

    @Override
    public void onScrollChanged() {
        detect();
    }

    private void detect() {
        View view = mViewRef.get();
        if (view == null) return;
        if (!mIsScreenOn) {//黑屏的时候不检测View的可见状态
            mIsScreenOn = PhoneStateUtil.isScreenOn(view.getContext());
            return;
        }
        Object tag = view.getTag(R.id.view_visible_detector_id);
        if (tag instanceof ViewVisibleDetector) {
            ((ViewVisibleDetector) tag).onDetect();
        }
    }

    private void onDetached() {
        View view = mViewRef.get();
        if (view == null) return;
        Object tag = view.getTag(R.id.view_visible_detector_id);
        if (tag instanceof ViewVisibleDetector) {
            ((ViewVisibleDetector) tag).onViewDetached();
        }
    }

    @Override
    public void onScreenOn(Context context, BroadcastReceiver receiver, Intent intent) {
        mIsScreenOn = true;
        View view = mViewRef.get();
        if (view == null) {
            ScreenReceiver.getInstance(context).removeListener(this);
            return;
        }
        detect();
    }

    @Override
    public void onScreenOff(Context context, BroadcastReceiver receiver, Intent intent) {
        mIsScreenOn = false;
        View view = mViewRef.get();
        if (view == null) {
            ScreenReceiver.getInstance(context).removeListener(this);
        } else {
            Object tag = view.getTag(R.id.view_visible_detector_id);
            if (tag instanceof ViewVisibleDetector) {
                ((ViewVisibleDetector) tag).onScreenOff();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        detect();

        mWindowFocusChangeTask.remove();
        mWindowFocusChangeTask.postDelay();
    }


    private class DetectTask implements Runnable {

        @Override
        public void run() {
            detect();
        }

        void postDelay() {
            ThreadUtil.postUiThreadDelayed(this, (long) 500);
        }

        void remove() {
            ThreadUtil.removeUiThreadRunnable(this);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    static class WindowFocusChangeListenerWrapper implements ViewTreeObserver.OnWindowFocusChangeListener {

        private static final int TAG_ID = ViewUtil.generateViewId();

        public static void addWindowFocusChangeListener(View view, OnWindowFocusChangeListener listener) {
            Object tag = view.getTag(TAG_ID);
            if (tag instanceof WindowFocusChangeListenerWrapper) {
                WindowFocusChangeListenerWrapper wrapper = (WindowFocusChangeListenerWrapper) tag;
                wrapper.setWindowFocusChangeListener(listener);
                view.getViewTreeObserver().removeOnWindowFocusChangeListener(wrapper);
                view.getViewTreeObserver().addOnWindowFocusChangeListener(wrapper);
            } else {
                WindowFocusChangeListenerWrapper wrapper = new WindowFocusChangeListenerWrapper();
                wrapper.setWindowFocusChangeListener(listener);
                view.getViewTreeObserver().addOnWindowFocusChangeListener(wrapper);
            }
        }

        private OnWindowFocusChangeListener mWindowFocusChangeListener;


        public void setWindowFocusChangeListener(OnWindowFocusChangeListener windowFocusChangeListener) {
            mWindowFocusChangeListener = windowFocusChangeListener;
        }

        @Override
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            OnWindowFocusChangeListener windowFocusChangeListener = mWindowFocusChangeListener;
            if (windowFocusChangeListener != null) {
                windowFocusChangeListener.onWindowFocusChanged(hasWindowFocus);
            }
        }
    }


    private static class WindowFocusChangeListenerView extends View {

        private static final int VIEW_ID = ViewUtil.generateViewId();
        private static final int TAG_ID = ViewUtil.generateViewId();

        public static void addWindowFocusChangeListener(View view, ViewGroup decor, OnWindowFocusChangeListener listener) {
            View listenerView = decor.findViewById(VIEW_ID);
            if (!(listenerView instanceof WindowFocusChangeListenerView)) {
                WindowFocusChangeListenerView windowFocusChangeListenerView = new WindowFocusChangeListenerView(decor.getContext());
                windowFocusChangeListenerView.addWindowFocusChangeListener(listener);
                decor.addView(windowFocusChangeListenerView);
                view.setTag(TAG_ID, windowFocusChangeListenerView);
            } else {
                WindowFocusChangeListenerView windowFocusChangeListenerView = (WindowFocusChangeListenerView) listenerView;
                windowFocusChangeListenerView.addWindowFocusChangeListener(listener);
                view.setTag(TAG_ID, windowFocusChangeListenerView);
            }
        }

        public static void removeWindowFocusChangeListener(View view, OnWindowFocusChangeListener listener) {
            Object tag = view.getTag(TAG_ID);
            if (tag instanceof WindowFocusChangeListenerView) {
                WindowFocusChangeListenerView windowFocusChangeListenerView = (WindowFocusChangeListenerView) tag;
                windowFocusChangeListenerView.removeWindowFocusChangeListener(listener);
            }
        }

        List<OnWindowFocusChangeListener> mWindowFocusChangeListeners = new CopyOnWriteArrayList<>();

        public WindowFocusChangeListenerView(Context context) {
            super(context);
            setId(VIEW_ID);
            setLayoutParams(new ViewGroup.LayoutParams(1, 1));
            setBackgroundColor(Color.TRANSPARENT);
        }

        public void addWindowFocusChangeListener(OnWindowFocusChangeListener windowFocusChangeListener) {
            if (!mWindowFocusChangeListeners.contains(windowFocusChangeListener)) {
                mWindowFocusChangeListeners.add(windowFocusChangeListener);
            }
        }

        public void removeWindowFocusChangeListener(OnWindowFocusChangeListener windowFocusChangeListener) {
            mWindowFocusChangeListeners.remove(windowFocusChangeListener);
        }

        @Override
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
            for (OnWindowFocusChangeListener windowFocusChangeListener : mWindowFocusChangeListeners) {
                windowFocusChangeListener.onWindowFocusChanged(hasWindowFocus);
            }
        }
    }
}