package com.hyh.prettyskin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.hyh.prettyskin.android.ActivityLifecycleAdapter;
import com.hyh.prettyskin.android.SkinInflateFactory;
import com.hyh.prettyskin.core.ISkin;
import com.hyh.prettyskin.core.SkinAttr;
import com.hyh.prettyskin.core.SkinReplaceListener;
import com.hyh.prettyskin.core.SkinView;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
public class PrettySkin {

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

    public void setContextSkinReplaceable(Context context) {
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

    public void addSkinAttrItem(SkinView item) {
        String attrValueKey = item.getAttrValueKey();
        List<SkinView> skinViews = mSkinAttrItemsMap.get(attrValueKey);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            mSkinAttrItemsMap.put(attrValueKey, skinViews);
        }
        skinViews.add(item);
    }

    public void recoverDefaultSkin() {

    }

    public void replaceSkinAsync(ISkin skin, SkinReplaceListener listener) {
        List<SkinAttr> skinAttrs = skin.getSkinAttrs();
        if (skinAttrs != null && !skinAttrs.isEmpty()) {
            for (SkinAttr skinAttr : skinAttrs) {
                int valueType = skinAttr.getValueType();
                Object attrValue = skinAttr.getAttrValue();
                String attrName = skinAttr.getAttrName();
                List<SkinView> skinViews = mSkinAttrItemsMap.get(attrName);
                if (skinViews != null && !skinViews.isEmpty()) {
                    for (SkinView skinView : skinViews) {
                        skinView.notifySkinChanged(valueType, attrValue);
                    }
                }
            }
        }
    }


    public int replaceSkinSync(ISkin skin) {
        List<SkinAttr> skinAttrs = skin.getSkinAttrs();
        if (skinAttrs != null && !skinAttrs.isEmpty()) {
            for (SkinAttr skinAttr : skinAttrs) {
                int valueType = skinAttr.getValueType();
                Object attrValue = skinAttr.getAttrValue();
                String attrName = skinAttr.getAttrName();
                List<SkinView> skinViews = mSkinAttrItemsMap.get(attrName);
                if (skinViews != null && !skinViews.isEmpty()) {
                    for (SkinView skinView : skinViews) {
                        skinView.notifySkinChanged(valueType, attrValue);
                    }
                }
            }
        }
        return 0;
    }
}
