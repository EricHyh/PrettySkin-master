package com.hyh.prettyskin.utils;

import android.os.Process;
import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.SkinView;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author Administrator
 * @description
 * @data 2019/4/2
 */

public class ViewReferenceUtil {

    private static final ReferenceQueue<View> sReferenceQueue = new ReferenceQueue<>();

    static {
        new CleanupThread().start();
    }

    public static Reference<View> createViewReference(SkinView skinView, View view) {
        return new ViewReference(skinView, view, sReferenceQueue);
    }

    static class ViewReference extends WeakReference<View> {

        private WeakReference<SkinView> skinViewRef;

        ViewReference(SkinView skinView, View referent, ReferenceQueue<? super View> q) {
            super(referent, q);
            this.skinViewRef = new WeakReference<>(skinView);
        }
    }

    private static class CleanupThread extends Thread {

        CleanupThread() {
            setDaemon(true);
            setName("PrettySkin-refQueue");
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                ViewReference remove = null;
                try {
                    // Prior to Android 5.0, even when there is no local variable, the result from
                    // remove() & obtainMessage() is kept as a stack local variable.
                    // We're forcing this reference to be cleared and replaced by looping every second
                    // when there is nothing to do.
                    // This behavior has been tested and reproduced with heap dumps.
                    remove = (ViewReference) sReferenceQueue.remove(2000);
                } catch (InterruptedException e) {
                    Logger.e("CleanupThread interrupted ", e);
                    break;
                } catch (Exception e) {
                    Logger.e("CleanupThread error ", e);
                }
                if (remove != null) {
                    WeakReference<SkinView> skinViewRef = remove.skinViewRef;
                    if (skinViewRef != null) {
                        SkinView skinView = skinViewRef.get();
                        if (skinView != null) {
                            try {
                                PrettySkin.getInstance().removeSkinView(skinView);
                            } catch (Exception e) {
                                Logger.e("CleanupThread remove skinView error ", e);
                            }
                        }
                    }
                }
            }
        }
    }
}