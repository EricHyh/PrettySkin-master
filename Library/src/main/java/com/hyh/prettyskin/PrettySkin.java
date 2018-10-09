package com.hyh.prettyskin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import com.hyh.prettyskin.android.ActivityLifecycleAdapter;
import com.hyh.prettyskin.android.SkinAttrItem;
import com.hyh.prettyskin.android.SkinInflateFactory;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */
@SuppressLint("UseSparseArrays")
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

    private Map<String, List<SkinAttrItem>> mSkinAttrItemsMap = new HashMap<>();

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

    public void addSkinAttrItem(SkinAttrItem item) {
        String attrValueKey = item.getAttrValueKey();
        List<SkinAttrItem> skinAttrItems = mSkinAttrItemsMap.get(attrValueKey);
        if (skinAttrItems == null) {
            skinAttrItems = new ArrayList<>();
            mSkinAttrItemsMap.put(attrValueKey, skinAttrItems);
        }
        skinAttrItems.add(item);
    }

    public void recoverSkin() {

    }

    public void replaceSkin(Context context, int themeResId, String styleableClassPath, String styleableName) {
        context = new ContextThemeWrapper(context, themeResId);
        Class styleableClass = ReflectUtil.getClassByPath(styleableClassPath);
        if (styleableClass == null) {
            return;
        }
        int[] attrs = (int[]) ReflectUtil.getStaticFieldValue(styleableClass, styleableName);
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        Map<Integer, String> filedNameMap = getStyleableAttrMap(styleableClass, styleableName);

        Set<Map.Entry<Integer, String>> entrySet = filedNameMap.entrySet();
        for (Map.Entry<Integer, String> entry : entrySet) {
            Integer key = entry.getKey();
            String value = entry.getValue().substring(styleableName.length() + 1);
            int type = typedArray.getType(key);
            switch (type) {
                case TypedValue.TYPE_INT_COLOR_ARGB8:
                case TypedValue.TYPE_INT_COLOR_RGB8:
                case TypedValue.TYPE_INT_COLOR_ARGB4:
                case TypedValue.TYPE_INT_COLOR_RGB4: {
                    int color = typedArray.getColor(key, 0);
                    List<SkinAttrItem> skinAttrItems = mSkinAttrItemsMap.get(value);
                    if (skinAttrItems != null && !skinAttrItems.isEmpty()) {
                        for (SkinAttrItem skinAttrItem : skinAttrItems) {
                            skinAttrItem.notifySkinChanged(color);
                        }
                    }
                    break;
                }
                case TypedValue.TYPE_STRING: {
                    String string = typedArray.getString(key);
                    if (!TextUtils.isEmpty(string)) {
                        if (string.startsWith("res/mipmap") || string.startsWith("res/drawable")) {
                            Drawable drawable = typedArray.getDrawable(key);
                            List<SkinAttrItem> skinAttrItems = mSkinAttrItemsMap.get(value);
                            if (skinAttrItems != null && !skinAttrItems.isEmpty()) {
                                for (SkinAttrItem skinAttrItem : skinAttrItems) {
                                    skinAttrItem.notifySkinChanged(drawable);
                                }
                            }
                        } else if (string.startsWith("res/color")) {
                            ColorStateList colorStateList = typedArray.getColorStateList(key);
                            List<SkinAttrItem> skinAttrItems = mSkinAttrItemsMap.get(value);
                            if (skinAttrItems != null && !skinAttrItems.isEmpty()) {
                                for (SkinAttrItem skinAttrItem : skinAttrItems) {
                                    skinAttrItem.notifySkinChanged(colorStateList);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        typedArray.recycle();
    }

    private Map<Integer, String> getStyleableAttrMap(Class styleableClass, String styleableName) {
        Map<Integer, String> fieldNameMap = new HashMap<>();
        try {
            Field[] fields = styleableClass.getFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) && field.getName().startsWith(styleableName + "_")) {
                    Object o = field.get(null);
                    fieldNameMap.put((Integer) o, field.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldNameMap;
    }
}
