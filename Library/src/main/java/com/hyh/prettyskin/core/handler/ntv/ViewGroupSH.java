package com.hyh.prettyskin.core.handler.ntv;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class ViewGroupSH extends ViewSH {


    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "ViewGroup";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("clipChildren");
        mSupportAttrNames.add("clipToPadding");
        mSupportAttrNames.add("animationCache");
        mSupportAttrNames.add("persistentDrawingCache");
        mSupportAttrNames.add("addStatesFromChildren");
        mSupportAttrNames.add("alwaysDrawnWithCache");
        mSupportAttrNames.add("layoutAnimation");
        mSupportAttrNames.add("descendantFocusability");
        mSupportAttrNames.add("splitMotionEvents");
        mSupportAttrNames.add("animateLayoutChanges");
        mSupportAttrNames.add("layoutMode");
        mSupportAttrNames.add("transitionGroup");
        mSupportAttrNames.add("touchscreenBlocksFocus");
    }

    public ViewGroupSH() {
    }

    public ViewGroupSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ViewGroupSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof ViewGroup && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }


    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parse(view, set, attrName);
        } else {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
        }
    }

    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof ViewGroup) {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Resources resources = null;
            if (context != null) {
                resources = context.getResources();
            }
            ViewGroup viewGroup = (ViewGroup) view;
            Object value = attrValue.getValue();
            switch (attrName) {
                case "clipChildren": {
                    boolean clipChildren = ViewAttrUtil.getBoolean(resources, type, value, true);
                    viewGroup.setClipChildren(clipChildren);
                    break;
                }
                case "clipToPadding": {
                    boolean clipToPadding = ViewAttrUtil.getBoolean(resources, type, value, true);
                    viewGroup.setClipToPadding(clipToPadding);
                    break;
                }
                case "animationCache": {
                    boolean animationCache = ViewAttrUtil.getBoolean(resources, type, true);
                    viewGroup.setAnimationCacheEnabled(animationCache);
                    break;
                }
                case "persistentDrawingCache": {
                    int persistentDrawingCache = ViewAttrUtil.getInt(resources, type, value);
                    viewGroup.setPersistentDrawingCache(persistentDrawingCache);
                    break;
                }
                case "addStatesFromChildren": {
                    boolean addStatesFromChildren = ViewAttrUtil.getBoolean(resources, type, value);
                    viewGroup.setAddStatesFromChildren(addStatesFromChildren);
                    break;
                }
                case "alwaysDrawnWithCache": {
                    boolean alwaysDrawnWithCache = ViewAttrUtil.getBoolean(resources, type, value, true);
                    viewGroup.setAlwaysDrawnWithCacheEnabled(alwaysDrawnWithCache);
                    break;
                }
                case "layoutAnimation": {
                    LayoutAnimationController layoutAnimation = ViewAttrUtil.getLayoutAnimation(context, type, value);
                    viewGroup.setLayoutAnimation(layoutAnimation);
                    break;
                }
                case "descendantFocusability": {
                    int index = ViewAttrUtil.getInt(resources, type, value);
                    int descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS;
                    switch (index) {
                        case 0: {
                            descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS;
                            break;
                        }
                        case 1: {
                            descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS;
                            break;
                        }
                        case 2: {
                            descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS;
                            break;
                        }
                    }
                    viewGroup.setDescendantFocusability(descendantFocusability);
                    break;
                }
                case "splitMotionEvents": {
                    boolean splitMotionEvents = ViewAttrUtil.getBoolean(resources, type, value);
                    viewGroup.setMotionEventSplittingEnabled(splitMotionEvents);
                    break;
                }
                case "animateLayoutChanges": {
                    boolean animateLayoutChanges = ViewAttrUtil.getBoolean(resources, type, value);
                    if (animateLayoutChanges) {
                        viewGroup.setLayoutTransition(new LayoutTransition());
                    } else {
                        viewGroup.setLayoutTransition(null);
                    }
                    break;
                }
                case "layoutMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        int layoutMode = ViewAttrUtil.getInt(resources, type, value, -1);
                        viewGroup.setLayoutMode(layoutMode);
                    }
                    break;
                }
                case "transitionGroup": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        boolean transitionGroup = ViewAttrUtil.getBoolean(resources, type, value);
                        viewGroup.setTransitionGroup(transitionGroup);
                    }
                    break;
                }
                case "touchscreenBlocksFocus": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        boolean touchscreenBlocksFocus = ViewAttrUtil.getBoolean(resources, type, value);
                        viewGroup.setTouchscreenBlocksFocus(touchscreenBlocksFocus);
                    }
                    break;
                }
            }
        }
    }
}
