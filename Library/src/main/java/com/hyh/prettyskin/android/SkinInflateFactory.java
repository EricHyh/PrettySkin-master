package com.hyh.prettyskin.android;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.BasePrettySkin;
import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ISkinable;
import com.hyh.prettyskin.SkinView;
import com.hyh.prettyskin.utils.Logger;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class SkinInflateFactory implements LayoutInflater.Factory2 {


    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit.",
            "android.app."
    };

    private static final String NAMESPACE = "http://schemas.android.com/android/skin";

    private static final String SKIN_ATTRS = "skin_attrs";

    private final BasePrettySkin mPrettySkin;

    private final LayoutInflater.Factory mFactory;

    public SkinInflateFactory(BasePrettySkin prettySkin) {
        this(prettySkin, null);
    }

    public SkinInflateFactory(BasePrettySkin prettySkin, LayoutInflater.Factory factory) {
        this.mPrettySkin = prettySkin;
        this.mFactory = factory;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        if (mFactory != null) {
            if (mFactory instanceof LayoutInflater.Factory2) {
                LayoutInflater.Factory2 factory2 = (LayoutInflater.Factory2) mFactory;
                view = factory2.onCreateView(parent, name, context, attrs);
            } else {
                view = mFactory.onCreateView(name, context, attrs);
            }
        }
        boolean isSkinable = mPrettySkin.isSkinableContext(context);
        Logger.d(context.getClass().getName() + " isSkinable = " + isSkinable);
        if (isSkinable) {
            String skinAttrs = attrs.getAttributeValue(NAMESPACE, SKIN_ATTRS);
            if (!TextUtils.isEmpty(skinAttrs)) {
                if (view == null) {
                    view = createView(name, context, attrs);
                }
                if (view != null) {
                    if (view instanceof ISkinable) {
                        ISkinable skinable = (ISkinable) view;
                        if (!skinable.isSkinable(view)) {
                            return view;
                        }
                    } else if (context instanceof ISkinable) {
                        ISkinable skinable = (ISkinable) context;
                        if (!skinable.isSkinable(view)) {
                            return view;
                        }
                    }
                    //attrName --> attrKey
                    Map<String, String> attrKeyMap = getAttrKeyMap(skinAttrs);
                    if (attrKeyMap != null && !attrKeyMap.isEmpty()) {
                        //attrName --> attrValue
                        Map<String, AttrValue> defaultAttrValueMap = getDefaultAttrValueMap(view, attrs, attrKeyMap.keySet());
                        SkinView skinView = new SkinView(view, attrKeyMap, defaultAttrValueMap);
                        mPrettySkin.addSkinView(skinView);
                    }
                }
            }
        }
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        if (-1 == name.indexOf('.')) {
            for (String prefix : sClassPrefixList) {
                View view = createView(name, prefix, context, attrs);
                if (view != null) {
                    return view;
                }
            }
            return null;
        } else {
            return (View) Reflect.from(name)
                    .constructor()
                    .param(Context.class, context)
                    .param(AttributeSet.class, attrs)
                    .newInstance();
        }
    }

    private View createView(String name, String prefix, Context context, AttributeSet attrs) {
        Class<?> viewClass = Reflect.classForName(prefix + name);
        if (viewClass == null) {
            return null;
        }
        return (View) Reflect.from(viewClass)
                .constructor()
                .param(Context.class, context)
                .param(AttributeSet.class, attrs)
                .newInstance();
    }

    //background=ma_btn_bg|textColor=ma_btn_text_color
    private Map<String, String> getAttrKeyMap(String skinAttrs) {
        if (!skinAttrs.matches("(.+=.+\\|)*(.+=.+)")) {
            Logger.e("parse skin attrs error: [" + skinAttrs + "] is not matched [attrName=attrValueKey|attrName=attrValueKey]");
            return null;
        }
        String[] attrArr = skinAttrs.split("\\|");
        Map<String, String> attrMap = new HashMap<>(attrArr.length);
        for (String attr : attrArr) {
            String[] attrInfo = attr.split("=");
            String attrName = attrInfo[0].trim();
            String attrKey = attrInfo[1].trim();
            attrMap.put(attrName, attrKey);
        }
        return attrMap;
    }

    private Map<String, AttrValue> getDefaultAttrValueMap(View view, AttributeSet attrs, Collection<String> attrNames) {
        ISkinHandler skinHandler = mPrettySkin.getSkinHandler(view);
        if (skinHandler == null) {
            return Collections.emptyMap();
        }
        Map<String, AttrValue> attrValueMap = new HashMap<>(attrNames.size());
        skinHandler.prepareParse(view, attrs);
        for (String attrName : attrNames) {
            AttrValue attrValue = skinHandler.parse(view, attrs, attrName);
            Logger.d("getDefaultAttrValueMap attrName = " + attrName + ", attrValue = " + attrValue);
            attrValueMap.put(attrName, attrValue);
        }
        skinHandler.finishParse();
        return attrValueMap;
    }
}
