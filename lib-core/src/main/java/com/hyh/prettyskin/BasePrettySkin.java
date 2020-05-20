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
import com.hyh.prettyskin.sh.SkinHandlerMaps;
import com.hyh.prettyskin.utils.SkinLogger;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;


public class BasePrettySkin {

    public static final int REPLACE_CODE_OK = 1;
    public static final int REPLACE_CODE_NULL_SKIN = 2;
    public static final int REPLACE_CODE_ERROR_SKIN = 3;
    public static final int REPLACE_CODE_REPETITIVE_SKIN = 4;

    final ChangeSkinHandler mChangeSkinHandler = new ChangeSkinHandler();

    final ListenerHandler mListenerHandler = new ListenerHandler(this);

    final PrettySkinActivityLifecycle mPrettySkinActivityLifecycle = new PrettySkinActivityLifecycle(this);

    final List<SkinView> mSkinAttrItems = new CopyOnWriteArrayList<>();

    final SkinHandlerMaps mSkinHandlerMaps = new SkinHandlerMaps();

    private final List<ContextReference> mSkinableContextList = new CopyOnWriteArrayList<>();

    private final List<Integer> mSkinableContextHashList = new CopyOnWriteArrayList<>();

    private final List<SkinChangedListener> mListeners = new CopyOnWriteArrayList<>();

    private boolean mParseDefaultAttrValueEnabled = false;

    private ISkin mCurrentSkin;

    public void addSkinHandler(Class<? extends View> viewClass, ISkinHandler skinHandler) {
        mSkinHandlerMaps.addCustomSkinHandler(viewClass, skinHandler);
    }

    public void addSkinHandler(ISkinHandlerMap map) {
        if (map == null) return;
        mSkinHandlerMaps.addSkinHandlerMap(map);
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
        return mSkinHandlerMaps.get(viewClass);
    }

    public boolean setContextSkinable(Context context) {
        return installViewFactory(context, false);
    }

    public boolean setContextSkinable(Context context, boolean force) {
        return installViewFactory(context, force);
    }

    public void setLayoutInflaterSkinable(LayoutInflater layoutInflater) {
        Context context = layoutInflater.getContext();
        addSkinableContext(context);
        LayoutInflater.Factory factory = layoutInflater.getFactory();
        if (factory == null) {
            layoutInflater.setFactory2(new SkinInflateFactory(this));
        } else {
            if (factory instanceof SkinInflateFactory) {
                return;
            }
            LayoutInflater.Factory2 factory2 = layoutInflater.getFactory2();
            if (factory2 == null) {
                SkinInflateFactory skinInflateFactory = new SkinInflateFactory(this, factory);
                Reflect.from(LayoutInflater.class)
                        .filed("mFactory", LayoutInflater.Factory.class)
                        .set(layoutInflater, skinInflateFactory);
            } else {
                SkinInflateFactory skinInflateFactory = new SkinInflateFactory(this, factory2);
                Reflect.from(LayoutInflater.class)
                        .filed("mFactory2", LayoutInflater.Factory.class)
                        .set(layoutInflater, skinInflateFactory);
            }
        }
    }

    synchronized boolean installViewFactory(Context context, boolean force) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        Context layoutInflaterContext = layoutInflater.getContext();

        if (layoutInflaterContext == context) {
            addSkinableContext(context);
            LayoutInflater.Factory factory = layoutInflater.getFactory();
            if (factory == null) {
                layoutInflater.setFactory2(new SkinInflateFactory(this));
            } else {
                if (factory instanceof SkinInflateFactory) {
                    return true;
                }
                LayoutInflater.Factory2 factory2 = layoutInflater.getFactory2();
                if (factory2 == null) {
                    SkinInflateFactory skinInflateFactory = new SkinInflateFactory(this, factory);
                    Reflect.from(LayoutInflater.class)
                            .filed("mFactory", LayoutInflater.Factory.class)
                            .set(layoutInflater, skinInflateFactory);
                } else {
                    SkinInflateFactory skinInflateFactory = new SkinInflateFactory(this, factory2);
                    Reflect.from(LayoutInflater.class)
                            .filed("mFactory2", LayoutInflater.Factory.class)
                            .set(layoutInflater, skinInflateFactory);
                }
            }
            return true;
        } else {
            SkinLogger.w("You need set your context skinable, but your context has not self layout inflater. You can refer to ContextThemeWrapper.");
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

    public void setParseDefaultAttrValueEnabled(boolean enabled) {
        this.mParseDefaultAttrValueEnabled = enabled;
        if (enabled) {
            Reflect.exemptAll();
        }
    }

    public boolean isParseDefaultAttrValueEnabled() {
        return mParseDefaultAttrValueEnabled;
    }

    public synchronized SkinView getSkinView(View view) {
        if (view == null) {
            return null;
        }
        int index = mSkinAttrItems.indexOf(new SkinView(view));
        SkinView skinView = null;
        if (index < mSkinAttrItems.size()) {
            skinView = mSkinAttrItems.get(index);
        }
        if (skinView != null && skinView.getView() == view) {
            return skinView;
        }
        return null;
    }

    public synchronized void addSkinView(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        mSkinAttrItems.remove(skinView);
        skinView.bindPrettySkin(this);
        mSkinAttrItems.add(skinView);
        if (mCurrentSkin != null) {
            skinView.changeSkin(mCurrentSkin);
        }
    }

    public synchronized void removeSkinView(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        mSkinAttrItems.remove(skinView);
    }

    public synchronized void removeSkinView(View view) {
        if (view == null) {
            return;
        }
        mSkinAttrItems.remove(new SkinView(view));
    }

    public synchronized void recoverDefaultSkin() {
        mCurrentSkin = null;
        if (!mSkinAttrItems.isEmpty()) {
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator(this));
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
        ReplaceTask replaceTask = new ReplaceTask(this, skin, listener);
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
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator(this));
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
            final TreeSet<SkinView> skinViews = new TreeSet<>(new SkinViewComparator(this));
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

        private final BasePrettySkin mPrettySkin;

        private ISkin mSkin;

        private SkinReplaceListener mListener;

        ReplaceTask(BasePrettySkin prettySkin, ISkin skin, SkinReplaceListener listener) {
            this.mPrettySkin = prettySkin;
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
                mPrettySkin.mCurrentSkin = mSkin;
                mPrettySkin.updateSkin(mSkin);
                mPrettySkin.mListenerHandler.postSkinChanged(mSkin);
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

        private final BasePrettySkin mPrettySkin;

        private int mTopActivityHashCode;

        PrettySkinActivityLifecycle(BasePrettySkin prettySkin) {
            this.mPrettySkin = prettySkin;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mPrettySkin.installViewFactory(activity, false);
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

        private final BasePrettySkin mPrettySkin;

        SkinViewComparator(BasePrettySkin prettySkin) {
            this.mPrettySkin = prettySkin;
        }

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
                int topActivityHashCode = mPrettySkin.mPrettySkinActivityLifecycle.mTopActivityHashCode;
                if (System.identityHashCode(activity1) == topActivityHashCode) {
                    context1_type = 0;
                } else {
                    context1_type = 2;
                }
            }
            if (context2 instanceof Activity) {
                Activity activity2 = (Activity) context2;
                int topActivityHashCode = mPrettySkin.mPrettySkinActivityLifecycle.mTopActivityHashCode;
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
            if (!(obj instanceof MessageInfo)) {
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

        private final BasePrettySkin mPrettySkin;

        ListenerHandler(BasePrettySkin prettySkin) {
            super(Looper.getMainLooper());
            this.mPrettySkin = prettySkin;
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
                    if (obj instanceof ISkin) {
                        ISkin skin = (ISkin) obj;
                        List<SkinChangedListener> listeners = mPrettySkin.mListeners;
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
                    if (obj instanceof List) {
                        List<String> changedAttrKeys = (List<String>) obj;
                        ISkin currentSkin = mPrettySkin.getCurrentSkin();
                        List<SkinChangedListener> listeners = mPrettySkin.mListeners;
                        if (!listeners.isEmpty()) {
                            for (SkinChangedListener listener : listeners) {
                                listener.onSkinAttrChanged(currentSkin, changedAttrKeys);
                            }
                        }
                    }
                    break;
                }
                case MSG_SKIN_RECOVERED: {
                    List<SkinChangedListener> listeners = mPrettySkin.mListeners;
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