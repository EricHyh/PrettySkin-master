package com.hyh.prettyskin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyh.prettyskin.android.ActivityLifecycleAdapter;
import com.hyh.prettyskin.android.SkinInflateFactory;
import com.hyh.prettyskin.core.ISkin;
import com.hyh.prettyskin.core.SkinAttr;
import com.hyh.prettyskin.core.SkinReplaceListener;
import com.hyh.prettyskin.core.SkinView;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.core.handler.ntv.ButtonSkinHandler;
import com.hyh.prettyskin.core.handler.ntv.TextViewSkinHandler;
import com.hyh.prettyskin.core.handler.ntv.ViewSkinHandler;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
@SuppressWarnings("all")
public class PrettySkin {

    public static final int REPLACE_CODE_OK = 1;
    public static final int REPLACE_CODE_NULL_SKIN = 2;
    public static final int REPLACE_CODE_ALREADY_EXISTED = 3;


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

    private List<SkinView> mSkinAttrItems = new ArrayList<>();

    private Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new HashMap<>();


    {
        mSkinHandlerMap.put(View.class, new ViewSkinHandler());
        mSkinHandlerMap.put(TextView.class, new TextViewSkinHandler());
        mSkinHandlerMap.put(Button.class, new ButtonSkinHandler());
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

    public synchronized void addSkinHandler(Class<? extends View> viewClass, ISkinHandler skinHandler) {
        mSkinHandlerMap.put(viewClass, skinHandler);
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
        mSkinAttrItems.add(skinView);
    }

    public synchronized void removeSkinAttrItem(SkinView skinView) {
        if (skinView == null) {
            return;
        }
        mSkinAttrItems.remove(skinView);
    }

    public synchronized void recoverDefaultSkin() {
        if (!mSkinAttrItems.isEmpty()) {
            Iterator<SkinView> iterator = mSkinAttrItems.iterator();
            while (iterator.hasNext()) {
                SkinView skinView = iterator.next();
                if (skinView == null || skinView.isRecycled()) {
                    iterator.remove();
                } else {
                    skinView.notifySkinRecovered();
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
                replaceSkinAttr(skinAttr);
            }
            mCurrentSkin = skin;
            return REPLACE_CODE_OK;
        } else {
            return REPLACE_CODE_NULL_SKIN;
        }
    }

    private void replaceSkinAttr(SkinAttr skinAttr) {
        String attrValueKey = skinAttr.getAttrValueKey();
        Object attrValue = skinAttr.getAttrValue();
        Iterator<SkinView> iterator = mSkinAttrItems.iterator();
        while (iterator.hasNext()) {
            SkinView skinView = iterator.next();
            if (skinView == null || skinView.isRecycled()) {
                iterator.remove();
            } else if (skinView.hasAttrValueKey(attrValueKey)) {
                skinView.notifySkinChanged(skinAttr);
            }
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
            PrettySkin.getInstance().replaceSkinAttr(skinAttr);
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
