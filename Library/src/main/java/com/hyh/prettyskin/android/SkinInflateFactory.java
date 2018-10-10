package com.hyh.prettyskin.android;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.core.SkinView;

import java.lang.reflect.Constructor;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class SkinInflateFactory implements LayoutInflater.Factory2 {

    private static final String NAMESPACE = "http://schemas.android.com/android/skin";
    private static final String SKIN_ATTRS = "skin_attrs";

    private LayoutInflater.Factory mFactory;

    public SkinInflateFactory() {
    }

    public SkinInflateFactory(LayoutInflater.Factory factory) {
        mFactory = factory;
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
        String skinAttrs = attrs.getAttributeValue(NAMESPACE, SKIN_ATTRS);
        if (!TextUtils.isEmpty(skinAttrs)) {
            if (view == null) {
                view = createView(name, context, attrs);
            }
            if (view != null) {
                String[] attrArr = skinAttrs.split("\\|");
                for (String attr : attrArr) {
                    String[] attrInfo = attr.split("=");
                    PrettySkin.getInstance().addSkinAttrItem(new SkinView(view, attrInfo[0], attrInfo[1]));
                }
            }
        }
        return view;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        try {
            if (-1 == name.indexOf('.')) {
                name = "android.widget." + name;
            }
            Class<?> viewClass = getClass().getClassLoader().loadClass(name);
            Constructor<?> constructor = viewClass.getConstructor(Context.class, AttributeSet.class);
            return (View) constructor.newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }
}
