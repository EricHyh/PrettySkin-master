package com.hyh.prettyskin.sh;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
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

    private Class<?> mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private final List<String> mSupportAttrNames = new ArrayList<>();

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

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "ViewGroup";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }

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
            ViewGroup viewGroup = (ViewGroup) view;
            switch (attrName) {
                case "clipChildren": {
                    boolean clipChildren = attrValue.getTypedValue(boolean.class, true);
                    viewGroup.setClipChildren(clipChildren);
                    break;
                }
                case "clipToPadding": {
                    boolean clipToPadding = attrValue.getTypedValue(boolean.class, true);
                    viewGroup.setClipToPadding(clipToPadding);
                    break;
                }
                case "animationCache": {
                    boolean animationCache = attrValue.getTypedValue(boolean.class, true);
                    viewGroup.setAnimationCacheEnabled(animationCache);
                    break;
                }
                case "persistentDrawingCache": {
                    int persistentDrawingCache = attrValue.getTypedValue(int.class, 0);
                    viewGroup.setPersistentDrawingCache(persistentDrawingCache);
                    break;
                }
                case "addStatesFromChildren": {
                    boolean addStatesFromChildren = attrValue.getTypedValue(boolean.class, false);
                    viewGroup.setAddStatesFromChildren(addStatesFromChildren);
                    break;
                }
                case "alwaysDrawnWithCache": {
                    boolean alwaysDrawnWithCache = attrValue.getTypedValue(boolean.class, false);
                    viewGroup.setAlwaysDrawnWithCacheEnabled(alwaysDrawnWithCache);
                    break;
                }
                case "layoutAnimation": {
                    LayoutAnimationController layoutAnimation = attrValue.getTypedValue(LayoutAnimationController.class, null);
                    viewGroup.setLayoutAnimation(layoutAnimation);
                    break;
                }
                case "descendantFocusability": {
                    int index = attrValue.getTypedValue(int.class, 0);
                    int descendantFocusability = ViewAttrUtil.getDescendantFocusability(index);
                    viewGroup.setDescendantFocusability(descendantFocusability);
                    break;
                }
                case "splitMotionEvents": {
                    boolean splitMotionEvents = attrValue.getTypedValue(boolean.class, false);
                    viewGroup.setMotionEventSplittingEnabled(splitMotionEvents);
                    break;
                }
                case "animateLayoutChanges": {
                    boolean animateLayoutChanges = attrValue.getTypedValue(boolean.class, false);
                    if (animateLayoutChanges) {
                        viewGroup.setLayoutTransition(new LayoutTransition());
                    } else {
                        viewGroup.setLayoutTransition(null);
                    }
                    break;
                }
                case "layoutMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        int layoutMode = attrValue.getTypedValue(int.class, -1);
                        viewGroup.setLayoutMode(layoutMode);
                    }
                    break;
                }
                case "transitionGroup": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        boolean transitionGroup = attrValue.getTypedValue(boolean.class, false);
                        viewGroup.setTransitionGroup(transitionGroup);
                    }
                    break;
                }
                case "touchscreenBlocksFocus": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        boolean touchscreenBlocksFocus = attrValue.getTypedValue(boolean.class, false);
                        viewGroup.setTouchscreenBlocksFocus(touchscreenBlocksFocus);
                    }
                    break;
                }
            }
        }
    }
}