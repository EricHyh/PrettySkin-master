package com.hyh.prettyskin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.hyh.prettyskin.android.ActivityLifecycleAdapter;
import com.hyh.prettyskin.android.SkinInflateFactory;
import com.hyh.prettyskin.core.ISkin;
import com.hyh.prettyskin.core.ISkinHandler;
import com.hyh.prettyskin.core.NativeSkinHandler;
import com.hyh.prettyskin.core.SkinAttr;
import com.hyh.prettyskin.core.SkinReplaceListener;
import com.hyh.prettyskin.core.SkinView;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
public class PrettySkin {

    public static final int REPLACE_CODE_OK = 1;
    public static final int REPLACE_CODE_NULL_SKIN = 2;
    public static final int REPLACE_CODE_ALREADY_EXISTED = 3;

    @SuppressLint("StaticFieldLeak")
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

    private Map<String, List<SkinView>> mSkinAttrItemsMap = new HashMap<>();

    private List<ISkinHandler> mSkinHandlers = new ArrayList<>();

    {
        mSkinHandlers.add(new NativeSkinHandler());
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
            application.registerActivityLifecycleCallbacks(new ActivityLifecycleAdapter() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    installViewFactory(activity);
                }
            });
        }
    }

    public synchronized ISkin getCurrentSkin() {
        return mCurrentSkin;
    }

    public synchronized List<ISkinHandler> getSkinHandlers() {
        return mSkinHandlers;
    }

    public synchronized void setContextSkinReplaceable(Context context) {
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
                ReflectUtil.setFieldValue(layoutInflater, "mFactory", skinInflateFactory);
            } else {
                SkinInflateFactory skinInflateFactory = new SkinInflateFactory(factory2);
                ReflectUtil.setFieldValue(layoutInflater, "mFactory2", skinInflateFactory);
            }
        }
    }

    public synchronized void addSkinAttrItem(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        String attrValueKey = skinView.getAttrValueKey();
        List<SkinView> skinViews = mSkinAttrItemsMap.get(attrValueKey);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            mSkinAttrItemsMap.put(attrValueKey, skinViews);
        }
        skinViews.add(skinView);
    }

    public synchronized void removeSkinAttrItem(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        String attrValueKey = skinView.getAttrValueKey();
        List<SkinView> skinViews = mSkinAttrItemsMap.get(attrValueKey);
        if (skinViews != null) {
            skinViews.remove(skinView);
        }
    }


    public synchronized void addSkinHandler(ISkinHandler skinHandler) {
        if (skinHandler == null) {
            return;
        }
        mSkinHandlers.add(skinHandler);
    }

    public synchronized void removeSkinHandler(ISkinHandler skinHandler) {
        if (skinHandler == null) {
            return;
        }
        mSkinHandlers.remove(skinHandler);
    }

    public synchronized void recoverDefaultSkin() {
        Collection<List<SkinView>> values = mSkinAttrItemsMap.values();
        if (!values.isEmpty()) {
            Iterator<List<SkinView>> iterator = values.iterator();
            while (iterator.hasNext()) {
                List<SkinView> skinViews = iterator.next();
                if (skinViews == null || skinViews.isEmpty()) {
                    iterator.remove();
                } else {
                    Iterator<SkinView> skinViewIterator = skinViews.iterator();
                    while (skinViewIterator.hasNext()) {
                        SkinView skinView = skinViewIterator.next();
                        if (skinView.isRecycled()) {
                            skinViewIterator.remove();
                        } else {
                            skinView.notifySkinRecovered();
                        }
                    }
                }
            }
        }
        mCurrentSkin = null;
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
                listener.onFailure(REPLACE_CODE_ALREADY_EXISTED);
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
            return REPLACE_CODE_ALREADY_EXISTED;
        }
        List<SkinAttr> skinAttrs = skin.getSkinAttrs();
        if (skinAttrs != null && !skinAttrs.isEmpty()) {
            for (SkinAttr skinAttr : skinAttrs) {
                int valueType = skinAttr.getValueType();
                Object attrValue = skinAttr.getAttrValue();
                String attrName = skinAttr.getAttrValueKey();
                List<SkinView> skinViews = mSkinAttrItemsMap.get(attrName);
                if (skinViews != null && !skinViews.isEmpty()) {
                    Iterator<SkinView> iterator = skinViews.iterator();
                    while (iterator.hasNext()) {
                        SkinView skinView = iterator.next();
                        if (skinView.isRecycled()) {
                            iterator.remove();
                        } else {
                            skinView.notifySkinChanged(valueType, attrValue);
                        }
                    }
                }
            }
            mCurrentSkin = skin;
            return REPLACE_CODE_OK;
        } else {
            return REPLACE_CODE_NULL_SKIN;
        }
    }

    private static class ReplaceTask extends AsyncTask<Void, SkinAttr, Integer> {

        private ISkin mSkin;

        private SkinReplaceListener mListener;

        ReplaceTask(ISkin skin, SkinReplaceListener listener) {
            this.mSkin = skin;
            this.mListener = listener;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            List<SkinAttr> skinAttrs = mSkin.getSkinAttrs();
            if (skinAttrs != null && !skinAttrs.isEmpty()) {
                for (SkinAttr skinAttr : skinAttrs) {
                    publishProgress(skinAttr);
                }
                return REPLACE_CODE_OK;
            } else {
                return REPLACE_CODE_NULL_SKIN;
            }
        }

        @Override
        protected void onProgressUpdate(SkinAttr... skinAttrs) {
            super.onProgressUpdate(skinAttrs);
            if (skinAttrs == null || skinAttrs.length <= 0) {
                return;
            }
            SkinAttr skinAttr = skinAttrs[0];
            if (skinAttr == null) {
                return;
            }
            int valueType = skinAttr.getValueType();
            Object attrValue = skinAttr.getAttrValue();
            String attrName = skinAttr.getAttrValueKey();
            List<SkinView> skinViews = PrettySkin.getInstance().mSkinAttrItemsMap.get(attrName);
            if (skinViews != null && !skinViews.isEmpty()) {
                Iterator<SkinView> iterator = skinViews.iterator();
                while (iterator.hasNext()) {
                    SkinView skinView = iterator.next();
                    if (skinView.isRecycled()) {
                        iterator.remove();
                    } else {
                        skinView.notifySkinChanged(valueType, attrValue);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (mListener != null) {
                if (integer == null) {
                    mListener.onFailure(REPLACE_CODE_NULL_SKIN);
                } else {
                    if (integer == REPLACE_CODE_OK) {
                        mListener.onSuccess();
                        PrettySkin.getInstance().mCurrentSkin = mSkin;
                    } else {
                        mListener.onFailure(integer);
                    }
                }
            }
            mSkin = null;
            mListener = null;
        }
    }
}
