package com.hyh.prettyskin.demo.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Administrator
 * @description
 * @data 2019/7/31
 */

public class ActivityLifecycleHelper {

    private static final ActivityLifecycleHelper sInstance = new ActivityLifecycleHelper();
    private String mTopStartedActivityName;

    public static ActivityLifecycleHelper getInstance() {
        return sInstance;
    }

    private Integer mStartedActivityHash;

    private List<Runnable> mActivityStartedListener = new CopyOnWriteArrayList<>();

    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacksList = new CopyOnWriteArrayList<>();

    private Map<Class<? extends Activity>, List<Application.ActivityLifecycleCallbacks>> mActivityLifecycleCallbacksMap = new ConcurrentHashMap<>();

    private ActivityLifecycleHelper() {
    }

    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
    }

    public void listenerActivityStarted(Runnable runnable) {
        if (runnable != null && !mActivityStartedListener.contains(runnable)) {
            mActivityStartedListener.add(runnable);
        }
    }

    public void unListenerActivityStarted(Runnable runnable) {
        if (runnable == null) return;
        mActivityStartedListener.remove(runnable);
    }

    public boolean hasStartedActivity() {
        return mStartedActivityHash != null;
    }

    public void listenerActivity(Class<? extends Activity> activityClass, Application.ActivityLifecycleCallbacks callbacks) {
        if (activityClass == null || callbacks == null) return;
        List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activityClass);
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
            list.add(callbacks);
            mActivityLifecycleCallbacksMap.put(activityClass, list);
        } else {
            if (!list.contains(callbacks)) {
                list.add(callbacks);
            }
        }
    }

    public void unListenerActivity(Class<? extends Activity> activityClass, Application.ActivityLifecycleCallbacks callbacks) {
        if (activityClass == null || callbacks == null) return;
        List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activityClass);
        if (list != null) {
            list.remove(callbacks);
        }
    }

    public void listenerAllActivity(Application.ActivityLifecycleCallbacks callbacks) {
        if (callbacks == null || mActivityLifecycleCallbacksList.contains(callbacks)) return;
        mActivityLifecycleCallbacksList.add(callbacks);
    }

    public void unListenerAllActivity(Application.ActivityLifecycleCallbacks callbacks) {
        if (callbacks == null) return;
        mActivityLifecycleCallbacksList.remove(callbacks);
    }

    public String getTopStartedActivityName() {
        return mTopStartedActivityName;
    }

    private class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivityCreated(activity, savedInstanceState);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivityCreated(activity, savedInstanceState);
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            mTopStartedActivityName = activity.getClass().getName();
            mStartedActivityHash = System.identityHashCode(activity);
            for (Runnable runnable : mActivityStartedListener) {
                runnable.run();
            }

            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivityStarted(activity);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivityStarted(activity);
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivityResumed(activity);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivityResumed(activity);
                }
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivityPaused(activity);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivityPaused(activity);
                }
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            String name = activity.getClass().getName();
            if (TextUtils.equals(name, mTopStartedActivityName)) {
                mTopStartedActivityName = null;
            }
            Integer startedActivityHash = mStartedActivityHash;
            if (startedActivityHash != null && System.identityHashCode(activity) == startedActivityHash) {
                mStartedActivityHash = null;
            }

            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivityStopped(activity);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivityStopped(activity);
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivitySaveInstanceState(activity, outState);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivitySaveInstanceState(activity, outState);
                }
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            String name = activity.getClass().getName();
            if (TextUtils.equals(name, mTopStartedActivityName)) {
                mTopStartedActivityName = null;
            }
            Integer startedActivityHash = mStartedActivityHash;
            if (startedActivityHash != null && System.identityHashCode(activity) == startedActivityHash) {
                mStartedActivityHash = null;
            }

            List<Application.ActivityLifecycleCallbacks> list = mActivityLifecycleCallbacksMap.get(activity.getClass());
            if (list != null && !list.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                    activityLifecycleCallbacks.onActivityDestroyed(activity);
                }
            }

            if (!mActivityLifecycleCallbacksList.isEmpty()) {
                for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycleCallbacksList) {
                    callbacks.onActivityDestroyed(activity);
                }
            }
        }
    }
}