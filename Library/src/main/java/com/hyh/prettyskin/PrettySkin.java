package com.hyh.prettyskin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.hyh.prettyskin.android.SkinInflateFactory;
import com.hyh.prettyskin.sh.NativeSkinHandlerMap;
import com.hyh.prettyskin.utils.Logger;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.lang.ref.WeakReference;
import java.util.Comparator;
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

    private final ChangeSkinHandler mChangeSkinHandler = new ChangeSkinHandler();

    private final ListenerHandler mListenerHandler = new ListenerHandler();

    private final PrettySkinActivityLifecycle mPrettySkinActivityLifecycle = new PrettySkinActivityLifecycle();

    private final List<SkinView> mSkinAttrItems = new CopyOnWriteArrayList<>();

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.putAll(new NativeSkinHandlerMap().get());
    }

    private final List<ContextReference> mSkinableContextList = new CopyOnWriteArrayList<>();

    private final List<SkinChangedListener> mListeners = new CopyOnWriteArrayList<>();

    private ISkin mCurrentSkin;

    private PrettySkin() {
    }

    public synchronized void init(Context context) {
        if (mContext != null) {
            return;
        }
        mContext = context.getApplicationContext();
        installViewFactory(context, false);
        Application application = getApplication(context);
        if (application != null) {
            if (application != context) {
                installViewFactory(application, false);
            }
            application.registerActivityLifecycleCallbacks(mPrettySkinActivityLifecycle);
        }
    }

    public void addSkinHandler(Class<? extends View> viewClass, ISkinHandler skinHandler) {
        mSkinHandlerMap.put(viewClass, skinHandler);
    }

    public void addSkinHandler(ISkinHandlerMap map) {
        if (map == null || map.get() == null) return;
        mSkinHandlerMap.putAll(map.get());
    }

    public void addSkinReplaceListener(SkinChangedListener listener) {
        if (listener == null || mListeners.contains(listener)) return;
        mListeners.add(listener);
    }

    public void removeSkinReplaceListener(SkinChangedListener listener) {
        mListeners.remove(listener);
    }

    public synchronized ISkin getCurrentSkin() {
        return mCurrentSkin;
    }

    public ISkinHandler getSkinHandler(View view) {
        return getSkinHandler(view.getClass());
    }

    public ISkinHandler getSkinHandler(Class viewClass) {
        if (viewClass == null || !View.class.isAssignableFrom(viewClass)) {
            return null;
        }
        ISkinHandler skinHandler = mSkinHandlerMap.get(viewClass);
        if (skinHandler == null) {
            return getSkinHandler(viewClass.getSuperclass());
        }
        return skinHandler;
    }

    public boolean setContextSkinable(Context context) {
        return installViewFactory(context, false);
    }

    public boolean setContextSkinable(Context context, boolean force) {
        return installViewFactory(context, force);
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

    private synchronized boolean installViewFactory(Context context, boolean force) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        Context layoutInflaterContext = layoutInflater.getContext();

        if (layoutInflaterContext == context) {
            addSkinableContext(context);
            LayoutInflater.Factory factory = layoutInflater.getFactory();
            if (factory == null) {
                layoutInflater.setFactory2(new SkinInflateFactory());
            } else {
                if (factory instanceof SkinInflateFactory) {
                    return true;
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
            return true;
        } else {
            Logger.w("You need set your context skinable, but your context has not self layout inflater. You can refer to ContextThemeWrapper.");
            if (force) {
                return installViewFactory(layoutInflaterContext, false);
            }
        }
        return false;
    }

    public boolean isSkinableContext(Context context) {
        ContextReference contextReference = new ContextReference(context);
        return mSkinableContextList.contains(contextReference);
    }

    private void addSkinableContext(Context context) {
        ContextReference contextReference = new ContextReference(context);
        if (!mSkinableContextList.contains(contextReference)) {
            mSkinableContextList.add(contextReference);
        }
        //清除被回收的Context
        for (ContextReference reference : mSkinableContextList) {
            if (reference.get() == null) {
                mSkinableContextList.remove(reference);
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
            for (SkinView skinView : mSkinAttrItems) {
                if (skinView == null || skinView.isRecycled()) {
                    mSkinAttrItems.remove(skinView);
                    continue;
                }
                skinViews.add(skinView);
            }
            if (!skinViews.isEmpty()) {
                mChangeSkinHandler.postRecoverSkin(skinViews);
            }
        }
        mListenerHandler.postSkinRecovered();
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
        mListenerHandler.postSkinChanged(mCurrentSkin);
        return REPLACE_CODE_OK;
    }

    public synchronized void notifySkinAttrChanged(final List<String> changedAttrKeys) {
        if (mCurrentSkin == null) return;
        if (changedAttrKeys == null || changedAttrKeys.isEmpty()) return;
        if (!mSkinAttrItems.isEmpty()) {
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator());
            for (SkinView skinView : mSkinAttrItems) {
                if (skinView == null || skinView.isRecycled()) {
                    mSkinAttrItems.remove(skinView);
                    continue;
                }
                for (String attrKey : changedAttrKeys) {
                    if (skinView.isSupportAttr(attrKey)) {
                        skinViews.add(skinView);
                        break;
                    }
                }
            }
            if (!skinViews.isEmpty()) {
                mChangeSkinHandler.postUpdateSkin(skinViews, mCurrentSkin, changedAttrKeys);
            }
        }
        mListenerHandler.postSkinAttrChanged(changedAttrKeys);
    }

    private synchronized void updateSkin(final ISkin skin) {
        if (!mSkinAttrItems.isEmpty()) {
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator());
            for (SkinView skinView : mSkinAttrItems) {
                if (skinView == null || skinView.isRecycled()) {
                    mSkinAttrItems.remove(skinView);
                    continue;
                }
                skinViews.add(skinView);
            }
            if (!skinViews.isEmpty()) {
                mChangeSkinHandler.postUpdateSkin(skinViews, skin);
            }
        }
    }

    private static class ReplaceTask extends AsyncTask<Void, Void, Boolean> {

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
                PrettySkin.getInstance().mListenerHandler.postSkinChanged(mSkin);
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
            PrettySkin.getInstance().installViewFactory(activity, false);
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

    private static class ChangeSkinHandler extends Handler {

        private static final int MSG_UPDATE_ALL = 1;
        private static final int MSG_UPDATE_SPECIFIED = 2;
        private static final int MSG_RECOVER = 3;

        ChangeSkinHandler() {
            super(Looper.getMainLooper());
        }

        void postUpdateSkin(TreeSet<SkinView> skinViews, ISkin skin) {
            Message message = obtainMessage(MSG_UPDATE_ALL);
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.skinViews = skinViews;
            messageInfo.skin = skin;
            message.obj = messageInfo;
            sendMessage(message);
        }

        void postUpdateSkin(TreeSet<SkinView> skinViews, ISkin skin, List<String> changedAttrKeys) {
            Message message = obtainMessage(MSG_UPDATE_SPECIFIED);
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.skinViews = skinViews;
            messageInfo.skin = skin;
            messageInfo.changedAttrKeys = changedAttrKeys;
            message.obj = messageInfo;
            sendMessage(message);
        }

        void postRecoverSkin(TreeSet<SkinView> skinViews) {
            Message message = obtainMessage(MSG_RECOVER);
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.skinViews = skinViews;
            message.obj = messageInfo;
            sendMessage(message);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object obj = msg.obj;
            if (obj == null || !(obj instanceof MessageInfo)) {
                return;
            }
            MessageInfo messageInfo = (MessageInfo) obj;
            int what = msg.what;
            switch (what) {
                case MSG_UPDATE_ALL: {
                    performUpdateSkin(messageInfo.skinViews, messageInfo.skin);
                    break;
                }
                case MSG_UPDATE_SPECIFIED: {
                    performUpdateSkin(messageInfo.skinViews, messageInfo.skin, messageInfo.changedAttrKeys);
                    break;
                }
                case MSG_RECOVER: {
                    performRecoverSkin(messageInfo.skinViews);
                    break;
                }
            }
        }

        private void performUpdateSkin(TreeSet<SkinView> skinViews, ISkin skin) {
            for (SkinView skinView : skinViews) {
                skinView.changeSkin(skin);
            }
        }

        private void performUpdateSkin(TreeSet<SkinView> skinViews, ISkin skin, List<String> changedAttrKeys) {
            for (SkinView skinView : skinViews) {
                skinView.changeSkin(skin, changedAttrKeys);
            }
        }

        private void performRecoverSkin(TreeSet<SkinView> skinViews) {
            for (SkinView skinView : skinViews) {
                skinView.recoverSkin();
            }
        }
    }

    private static class ListenerHandler extends Handler {

        private static final int MSG_SKIN_CHANGED = 1;
        private static final int MSG_SKIN_ATTR_CHANGED = 2;
        private static final int MSG_SKIN_RECOVERED = 3;

        ListenerHandler() {
            super(Looper.getMainLooper());
        }

        void postSkinChanged(ISkin currentSkin) {
            Message message = obtainMessage(MSG_SKIN_CHANGED);
            message.obj = currentSkin;
            sendMessage(message);
        }

        void postSkinAttrChanged(List<String> changedAttrKeys) {
            Message message = obtainMessage(MSG_SKIN_ATTR_CHANGED);
            message.obj = changedAttrKeys;
            sendMessage(message);
        }

        void postSkinRecovered() {
            sendEmptyMessage(MSG_SKIN_RECOVERED);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case MSG_SKIN_CHANGED: {
                    Object obj = msg.obj;
                    if (obj != null && obj instanceof ISkin) {
                        ISkin skin = (ISkin) obj;
                        List<SkinChangedListener> listeners = PrettySkin.getInstance().mListeners;
                        if (!listeners.isEmpty()) {
                            for (SkinChangedListener listener : listeners) {
                                listener.onSkinChanged(skin);
                            }
                        }
                    }
                    break;
                }
                case MSG_SKIN_ATTR_CHANGED: {
                    Object obj = msg.obj;
                    if (obj != null && obj instanceof List) {
                        List<String> changedAttrKeys = (List<String>) obj;
                        ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
                        List<SkinChangedListener> listeners = PrettySkin.getInstance().mListeners;
                        if (!listeners.isEmpty()) {
                            for (SkinChangedListener listener : listeners) {
                                listener.onSkinAttrChanged(currentSkin, changedAttrKeys);
                            }
                        }
                    }
                    break;
                }
                case MSG_SKIN_RECOVERED: {
                    List<SkinChangedListener> listeners = PrettySkin.getInstance().mListeners;
                    if (!listeners.isEmpty()) {
                        for (SkinChangedListener listener : listeners) {
                            listener.onSkinRecovered();
                        }
                    }
                    break;
                }
            }
        }
    }

    private static class ContextReference {

        private final WeakReference<Context> mContextRef;
        private final int mHashCode;

        ContextReference(Context context) {
            mContextRef = new WeakReference<>(context);
            mHashCode = (context == null) ? 0 : context.hashCode();
        }

        Context get() {
            return mContextRef.get();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Context thatContext = ((ContextReference) o).get();
            Context thisContext = this.get();
            return thatContext == thisContext;
        }

        @Override
        public int hashCode() {
            return mHashCode;
        }
    }

    private static class MessageInfo {

        TreeSet<SkinView> skinViews;
        ISkin skin;
        List<String> changedAttrKeys;

    }
}