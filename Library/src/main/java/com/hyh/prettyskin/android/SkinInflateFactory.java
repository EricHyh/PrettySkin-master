package com.hyh.prettyskin.android;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.core.ISkin;
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
                    String attrName = attrInfo[0];
                    String attrValueKey = attrInfo[1];
                    Object defaultAttrValue = getDefaultAttrValue(context, view, attrs, attrName);
                    SkinView skinView = new SkinView(view, attrName, attrValueKey, defaultAttrValue);
                    PrettySkin.getInstance().addSkinAttrItem(skinView);
                    ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
                    if (currentSkin != null) {
                        int valueType = currentSkin.getValueType(attrValueKey);
                        Object currentAttrValue = currentSkin.getAttrValue(attrValueKey);
                        skinView.notifySkinChanged(valueType, currentAttrValue);
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

    private Object getDefaultAttrValue(Context context, View view, AttributeSet attrs, String attrName) {



        if (attrs == null || TextUtils.isEmpty(attrName)) {
            return null;
        }
        Object attrValue = null;
        boolean isAttrParsed = false;
        if (TextUtils.equals(attrName, "background")) {
            attrValue = view.getBackground();
            isAttrParsed = true;
        } else if (TextUtils.equals(attrName, "textColor") && view instanceof TextView) {
            TextView textView = (TextView) view;
            attrValue = textView.getTextColors();
            isAttrParsed = true;
        }
        if (!isAttrParsed) {
            int attributeCount = attrs.getAttributeCount();
            if (attributeCount > 0) {
                for (int index = 0; index < attributeCount; index++) {
                    String attributeName = attrs.getAttributeName(index);
                    if (TextUtils.equals(attributeName, attrName)) {
                        String attributeValue = attrs.getAttributeValue(index);
                        if (!TextUtils.isEmpty(attributeName)) {
                            if (attributeValue.startsWith("#")) {
                                attrValue = Color.parseColor(attributeValue);
                            } else if (attributeValue.startsWith("@")) {
                                int attributeResourceValue = attrs.getAttributeResourceValue(index, 0);
                                if (attributeResourceValue != 0) {
                                    String resourceTypeName = context.getResources().getResourceTypeName(attributeResourceValue);
                                    if ("color".equalsIgnoreCase(resourceTypeName)) {
                                        attrValue = context.getResources().getDrawable(attributeResourceValue);
                                    } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                        attrValue = context.getResources().getDrawable(attributeResourceValue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return attrValue;
    }
}
