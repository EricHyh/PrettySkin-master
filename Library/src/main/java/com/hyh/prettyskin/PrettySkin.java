package com.hyh.prettyskin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;

import com.hyh.prettyskin.android.SkinInflateFactory;
import com.hyh.prettyskin.core.ISkin;
import com.hyh.prettyskin.core.SkinAttr;
import com.hyh.prettyskin.core.SkinReplaceListener;
import com.hyh.prettyskin.core.SkinView;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.core.handler.ISkinHandlerMap;
import com.hyh.prettyskin.core.handler.ntv.NativeSkinHandlerMap;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
//@SuppressWarnings("all")
public class PrettySkin {

    public static final int REPLACE_CODE_OK = 1;
    public static final int REPLACE_CODE_NULL_SKIN = 2;
    public static final int REPLACE_CODE_ERROR_SKIN = 3;
    public static final int REPLACE_CODE_REPETITIVE_SKIN = 4;

    private static PrettySkin sPrettySkin;

    public static PrettySkin getInstance() {
        if (sPrettySkin != null) {
            return sPrettySkin;
        }
        synchronized (PrettySkin.class) {
            if (sPrettySkin == null) {
                sPrettySkin = new PrettySkin();
            }
            return sPrettySkin;
        }
    }

    private Context mContext;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private final PrettySkinActivityLifecycle mPrettySkinActivityLifecycle = new PrettySkinActivityLifecycle();

    private final Map<String, List<SkinView>> mSkinAttrItemMap = new HashMap<>();

    private final List<SkinView> mSkinAttrItems = new CopyOnWriteArrayList<>();

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.putAll(new NativeSkinHandlerMap().get());
    }

    private ISkin mCurrentSkin;

    private PrettySkin() {
    }

    public synchronized void init(Context context) {
        if (mContext != null) {
            return;
        }
        mContext = context.getApplicationContext();
        installViewFactory(context);
        Application application = getApplication(context);
        if (application != null) {
            if (application != context) {
                installViewFactory(application);
            }
            application.registerActivityLifecycleCallbacks(mPrettySkinActivityLifecycle);
        }
    }

    public synchronized void addSkinHandler(Class<? extends View> viewClass, ISkinHandler skinHandler) {
        mSkinHandlerMap.put(viewClass, skinHandler);
    }

    public synchronized void addSkinHandler(ISkinHandlerMap map) {
        mSkinHandlerMap.putAll(map.get());
    }

    public synchronized ISkin getCurrentSkin() {
        return mCurrentSkin;
    }

    public ISkinHandler getSkinHandler(View view) {
        return getSkinHandler(view.getClass());
    }

    public ISkinHandler getSkinHandler(Class viewClass) {
        if (viewClass == null || viewClass.isAssignableFrom(View.class)) {
            return null;
        }
        ISkinHandler skinHandler = mSkinHandlerMap.get(viewClass);
        if (skinHandler == null) {
            return getSkinHandler(viewClass.getSuperclass());
        }
        return skinHandler;
    }

    public synchronized void setContextSkinable(Context context) {
        installViewFactory(context);
    }

    private Application getApplication(Context context) {
        Application application = null;
        if (context instanceof Application) {
            application = (Application) context;
        } else {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext instanceof Application) {
                application = (Application) applicationContext;
            }
        }
        return application;
    }

    private void installViewFactory(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LayoutInflater.Factory factory = layoutInflater.getFactory();
        if (factory == null) {
            layoutInflater.setFactory2(new SkinInflateFactory());
        } else {
            if (factory instanceof SkinInflateFactory) {
                return;
            }
            LayoutInflater.Factory2 factory2 = layoutInflater.getFactory2();
            if (factory2 == null) {
                SkinInflateFactory skinInflateFactory = new SkinInflateFactory(factory);
                Reflect.from(LayoutInflater.class)
                        .filed("mFactory", LayoutInflater.Factory.class)
                        .set(layoutInflater, skinInflateFactory);
            } else {
                SkinInflateFactory skinInflateFactory = new SkinInflateFactory(factory2);
                Reflect.from(LayoutInflater.class)
                        .filed("mFactory2", LayoutInflater.Factory.class)
                        .set(layoutInflater, skinInflateFactory);
            }
        }
    }

    public synchronized void addSkinAttrItem(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        mSkinAttrItems.add(skinView);
        if (mCurrentSkin != null) {
            skinView.changeSkin(mCurrentSkin);
        }
    }

    public synchronized void removeSkinAttrItem(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        mSkinAttrItems.remove(skinView);
    }

    public synchronized void recoverDefaultSkin() {
        mCurrentSkin = null;
        if (!mSkinAttrItems.isEmpty()) {
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator());
            Iterator<SkinView> iterator = mSkinAttrItems.iterator();
            while (iterator.hasNext()) {
                SkinView skinView = iterator.next();
                if (skinView == null || skinView.isRecycled()) {
                    iterator.remove();
                    continue;
                }
                skinViews.add(skinView);
            }
            if (!skinViews.isEmpty()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (final SkinView skinView : skinViews) {
                            skinView.recoverSkin();
                        }
                    }
                });
            }
        }
    }

    public synchronized void replaceSkinAsync(ISkin skin, SkinReplaceListener listener) {
        if (skin == null) {
            if (listener != null) {
                listener.onFailure(REPLACE_CODE_NULL_SKIN);
            }
            return;
        }
        if (mCurrentSkin != null && mCurrentSkin.equals(skin)) {
            if (listener != null) {
                listener.onFailure(REPLACE_CODE_REPETITIVE_SKIN);
            }
            return;
        }
        ReplaceTask replaceTask = new ReplaceTask(skin, listener);
        replaceTask.execute();
    }

    public synchronized int replaceSkinSync(ISkin skin) {
        if (skin == null) {
            return REPLACE_CODE_NULL_SKIN;
        }
        if (mCurrentSkin != null && mCurrentSkin.equals(skin)) {
            return REPLACE_CODE_REPETITIVE_SKIN;
        }
        if (!skin.loadSkinAttrs()) {
            return REPLACE_CODE_ERROR_SKIN;
        }
        mCurrentSkin = skin;
        updateSkin(skin);
        return REPLACE_CODE_OK;
    }

    private synchronized void updateSkin(final ISkin skin) {
        if (!mSkinAttrItems.isEmpty()) {
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator());
            Iterator<SkinView> iterator = mSkinAttrItems.iterator();
            while (iterator.hasNext()) {
                SkinView skinView = iterator.next();
                if (skinView == null || skinView.isRecycled()) {
                    iterator.remove();
                    continue;
                }
                skinViews.add(skinView);
            }
            if (!skinViews.isEmpty()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (final SkinView skinView : skinViews) {
                            skinView.changeSkin(skin);
                        }
                    }
                });
            }
        }
    }

    private static class ReplaceTask extends AsyncTask<Void, SkinAttr, Boolean> {

        private ISkin mSkin;

        private SkinReplaceListener mListener;

        ReplaceTask(ISkin skin, SkinReplaceListener listener) {
            this.mSkin = skin;
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return mSkin.loadSkinAttrs();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                PrettySkin.getInstance().mCurrentSkin = mSkin;
                PrettySkin.getInstance().updateSkin(mSkin);
            }
            if (mListener != null) {
                if (result) {
                    mListener.onSuccess();
                } else {
                    mListener.onFailure(REPLACE_CODE_ERROR_SKIN);
                }
            }
            mSkin = null;
            mListener = null;
        }
    }


    private static class PrettySkinActivityLifecycle implements Application.ActivityLifecycleCallbacks {


        private int mTopActivityHashCode;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            PrettySkin.getInstance().installViewFactory(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            mTopActivityHashCode = System.identityHashCode(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            mTopActivityHashCode = System.identityHashCode(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (mTopActivityHashCode != 0 && mTopActivityHashCode == System.identityHashCode(activity)) {
                mTopActivityHashCode = 0;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (mTopActivityHashCode != 0 && mTopActivityHashCode == System.identityHashCode(activity)) {
                mTopActivityHashCode = 0;
            }
        }
    }

    private static class SkinViewComparator implements Comparator<SkinView> {

        @Override
        public int compare(SkinView skinView1, SkinView skinView2) {
            View view1 = skinView1.getView();
            if (view1 == null) {
                return 1;
            }
            View view2 = skinView2.getView();
            if (view2 == null) {
                return -1;
            }
            Context context1 = view1.getContext();
            Context context2 = view2.getContext();
            return compare(context1, context2);
        }

        int compare(Context context1, Context context2) {
            if (context1 == null) {
                return 1;
            }
            if (context2 == null) {
                return -1;
            }
            int context1_type = 1;
            int context2_type = 1;
            if (context1 instanceof Activity) {
                Activity activity1 = (Activity) context1;
                Integer topActivityHashCode = PrettySkin.getInstance().mPrettySkinActivityLifecycle.mTopActivityHashCode;
                if (System.identityHashCode(activity1) == topActivityHashCode) {
                    context1_type = 0;
                } else {
                    context1_type = 2;
                }
            }
            if (context2 instanceof Activity) {
                Activity activity2 = (Activity) context2;
                Integer topActivityHashCode = PrettySkin.getInstance().mPrettySkinActivityLifecycle.mTopActivityHashCode;
                if (System.identityHashCode(activity2) == topActivityHashCode) {
                    context2_type = 0;
                } else {
                    context2_type = 2;
                }
            }
            if (context1_type == context2_type) {
                return 1;
            }
            return context1_type - context2_type;
        }
    }
}