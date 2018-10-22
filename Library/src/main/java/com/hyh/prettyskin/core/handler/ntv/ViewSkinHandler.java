package com.hyh.prettyskin.core.handler.ntv;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.AttrUtil;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */


public class ViewSkinHandler implements ISkinHandler {

    private int defStyleAttr;

    private int defStyleRes;

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("background");
        //mSupportAttrNames.add("padding");
        mSupportAttrNames.add("paddingLeft");
        mSupportAttrNames.add("paddingTop");
        mSupportAttrNames.add("paddingRight");
        mSupportAttrNames.add("paddingBottom");
        //mSupportAttrNames.add("paddingStart");
        //mSupportAttrNames.add("paddingEnd");
        //mSupportAttrNames.add("paddingHorizontal");
        //mSupportAttrNames.add("paddingVertical");
        mSupportAttrNames.add("scrollX");
        mSupportAttrNames.add("scrollY");
        mSupportAttrNames.add("alpha");
        mSupportAttrNames.add("transformPivotX");
        mSupportAttrNames.add("transformPivotY");
        mSupportAttrNames.add("translationX");
        mSupportAttrNames.add("translationY");
        mSupportAttrNames.add("translationZ");
        mSupportAttrNames.add("elevation");
        mSupportAttrNames.add("rotation");
        mSupportAttrNames.add("rotationX");
        mSupportAttrNames.add("rotationY");
        mSupportAttrNames.add("scaleX");
        mSupportAttrNames.add("scaleY");
        mSupportAttrNames.add("fitsSystemWindows");
        mSupportAttrNames.add("focusable");
        mSupportAttrNames.add("focusableInTouchMode");
        mSupportAttrNames.add("clickable");
        mSupportAttrNames.add("longClickable");
        mSupportAttrNames.add("contextClickable");
        mSupportAttrNames.add("saveEnabled");
        mSupportAttrNames.add("duplicateParentState");
        mSupportAttrNames.add("visibility");
        mSupportAttrNames.add("layoutDirection");
        mSupportAttrNames.add("drawingCacheQuality");
        mSupportAttrNames.add("contentDescription");
        mSupportAttrNames.add("accessibilityTraversalBefore");
        mSupportAttrNames.add("accessibilityTraversalAfter");
        mSupportAttrNames.add("labelFor");
        mSupportAttrNames.add("soundEffectsEnabled");
        mSupportAttrNames.add("hapticFeedbackEnabled");
        mSupportAttrNames.add("scrollbars");
        mSupportAttrNames.add("fadeScrollbars");
        mSupportAttrNames.add("scrollbarFadeDuration");
        mSupportAttrNames.add("scrollbarDefaultDelayBeforeFade");
        mSupportAttrNames.add("scrollbarSize");
        //mSupportAttrNames.add("scrollbarTrackHorizontal");
        //mSupportAttrNames.add("scrollbarThumbHorizontal");
        //mSupportAttrNames.add("scrollbarAlwaysDrawHorizontalTrack");
        //mSupportAttrNames.add("scrollbarTrackVertical");
        //mSupportAttrNames.add("scrollbarThumbVertical");
        //mSupportAttrNames.add("scrollbarAlwaysDrawVerticalTrack");
        //mSupportAttrNames.add("fadingEdge");
        mSupportAttrNames.add("requiresFadingEdge");
        mSupportAttrNames.add("scrollbarStyle");
        mSupportAttrNames.add("isScrollContainer");
        mSupportAttrNames.add("keepScreenOn");
        mSupportAttrNames.add("filterTouchesWhenObscured");
        mSupportAttrNames.add("nextFocusLeft");
        mSupportAttrNames.add("nextFocusRight");
        mSupportAttrNames.add("nextFocusUp");
        mSupportAttrNames.add("nextFocusDown");
        mSupportAttrNames.add("nextFocusForward");
        mSupportAttrNames.add("nextClusterForward");
        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("minHeight");
        mSupportAttrNames.add("overScrollMode");
        mSupportAttrNames.add("verticalScrollbarPosition");
        mSupportAttrNames.add("layerType");
        mSupportAttrNames.add("textDirection");
        mSupportAttrNames.add("textAlignment");
        mSupportAttrNames.add("importantForAccessibility");
        mSupportAttrNames.add("accessibilityLiveRegion");
        mSupportAttrNames.add("transitionName");
        mSupportAttrNames.add("nestedScrollingEnabled");
        mSupportAttrNames.add("stateListAnimator");
        mSupportAttrNames.add("backgroundTint");
        mSupportAttrNames.add("backgroundTintMode");
        mSupportAttrNames.add("outlineProvider");
        mSupportAttrNames.add("foreground");
        mSupportAttrNames.add("foregroundGravity");
        mSupportAttrNames.add("foregroundTintMode");
        //mSupportAttrNames.add("foregroundInsidePadding");
        mSupportAttrNames.add("scrollIndicators");
        mSupportAttrNames.add("pointerIcon");
        //mSupportAttrNames.add("forceHasOverlappingRendering");
        mSupportAttrNames.add("tooltipText");
        mSupportAttrNames.add("keyboardNavigationCluster");
        mSupportAttrNames.add("focusedByDefault");
        mSupportAttrNames.add("autofillHints");
        mSupportAttrNames.add("importantForAutofill");
        mSupportAttrNames.add("defaultFocusHighlightEnabled");
    }

    public ViewSkinHandler() {
    }

    public ViewSkinHandler(int defStyleAttr) {
        this.defStyleAttr = defStyleAttr;
    }

    public ViewSkinHandler(int defStyleAttr, int defStyleRes) {
        this.defStyleAttr = defStyleAttr;
        this.defStyleRes = defStyleRes;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        Class styleableClass = getStyleableClass();
        String styleableName = getStyleableName();
        return parseAttrValue(view, set, attrName, styleableClass, styleableName);
    }

    protected AttrValue parseAttrValue(View view, AttributeSet set, String attrName, Class styleableClass, String styleableName) {
        AttrValue attrValue = null;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        Context context = view.getContext();
        int[] attrs = AttrUtil.getAttrs(styleableClass, styleableName);
        if (attrs != null) {
            int styleableIndex = AttrUtil.getStyleableIndex(styleableClass, styleableName, attrName);
            final int defStyleAttr = this.defStyleAttr;
            final int defStyleRes = this.defStyleRes;
            TypedArray typedArray = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes);
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArray.getIndex(i);
                if (styleableIndex == index) {
                    int indexType = getTypedValue(typedArray, index);
                    switch (indexType) {
                        case TypedValue.TYPE_INT_COLOR_ARGB8:
                        case TypedValue.TYPE_INT_COLOR_RGB8:
                        case TypedValue.TYPE_INT_COLOR_ARGB4:
                        case TypedValue.TYPE_INT_COLOR_RGB4: {
                            int color = typedArray.getColor(index, 0);
                            type = ValueType.TYPE_COLOR_INT;
                            value = color;
                            break;
                        }
                        case TypedValue.TYPE_INT_DEC:
                        case TypedValue.TYPE_INT_HEX: {
                            type = ValueType.TYPE_INT;
                            value = typedArray.getInt(index, 0);
                            break;
                        }
                        case TypedValue.TYPE_FLOAT: {
                            type = ValueType.TYPE_FLOAT;
                            value = typedArray.getFloat(index, 0.0f);
                            break;
                        }
                        case TypedValue.TYPE_FRACTION: {
                            type = ValueType.TYPE_FLOAT;
                            value = typedArray.getFraction(index, 1, 1, 0.0f);
                            break;
                        }
                        case TypedValue.TYPE_INT_BOOLEAN: {
                            type = ValueType.TYPE_BOOLEAN;
                            value = typedArray.getBoolean(index, false);
                            break;
                        }
                        case TypedValue.TYPE_DIMENSION: {
                            type = ValueType.TYPE_FLOAT;
                            value = typedArray.getDimension(index, 0.0f);
                            break;
                        }
                        case TypedValue.TYPE_STRING: {
                            String string = typedArray.getString(index);//res/drawable-anydpi-v21/ic_launcher_background.xml
                            if (!TextUtils.isEmpty(string)) {
                                if (string.matches("^res/color.*/.+\\.xml$")) {
                                    try {
                                        value = typedArray.getResourceId(index, 0);
                                        type = ValueType.TYPE_REFERENCE;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (string.matches("^res/[(drawable)|(mipmap)].*/.+$")) {
                                    try {
                                        value = typedArray.getResourceId(index, 0);
                                        type = ValueType.TYPE_REFERENCE;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (string.matches("^res/anim.*/.+\\.xml$")) {
                                    try {
                                        value = typedArray.getResourceId(index, 0);
                                        type = ValueType.TYPE_REFERENCE;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (value == null) {
                                    type = ValueType.TYPE_STRING;
                                    value = string;
                                }
                            }
                            break;
                        }
                        case TypedValue.TYPE_REFERENCE: {
                            type = ValueType.TYPE_REFERENCE;
                            value = typedArray.getResourceId(index, 0);
                            break;
                        }
                        case TypedValue.TYPE_ATTRIBUTE: {
                            break;
                        }
                    }
                    break;
                }
            }
            typedArray.recycle();
        }
        if (value != null) {
            attrValue = new AttrValue(context, type, value);
        }
        return attrValue;
    }

    private int getTypedValue(TypedArray typedArray, int index) {
        int type = TypedValue.TYPE_NULL;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            type = typedArray.getType(index);
        } else {
            /**
             *
             * index *= AssetManager.STYLE_NUM_ENTRIES;
             * return mData[index + AssetManager.STYLE_TYPE];
             */
            int STYLE_NUM_ENTRIES = 6;
            Object STYLE_NUM_ENTRIES_OBJ = ReflectUtil.getStaticFieldValue(AssetManager.class, "STYLE_NUM_ENTRIES");
            if (STYLE_NUM_ENTRIES_OBJ != null && STYLE_NUM_ENTRIES_OBJ instanceof Integer) {
                STYLE_NUM_ENTRIES = (int) STYLE_NUM_ENTRIES_OBJ;
            }
            int STYLE_TYPE = 0;
            Object STYLE_TYPE_OBJ = ReflectUtil.getStaticFieldValue(AssetManager.class, "STYLE_NUM_ENTRIES");
            if (STYLE_TYPE_OBJ != null && STYLE_TYPE_OBJ instanceof Integer) {
                STYLE_TYPE = (int) STYLE_TYPE_OBJ;
            }
            int[] mData = (int[]) ReflectUtil.getFieldValue(typedArray, "mData");
            index *= STYLE_NUM_ENTRIES;
            index += STYLE_TYPE;
            if (mData != null && mData.length > index) {
                type = mData[index];
            }
        }
        return type;
    }


    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        Context context = attrValue.getThemeContext();
        int type = attrValue.getType();
        if (context == null && type == ValueType.TYPE_REFERENCE) {
            return;
        }
        Resources resources = null;
        if (context != null) {
            resources = context.getResources();
        }
        Object value = attrValue.getValue();
        switch (attrName) {
            case "background": {
                switch (type) {
                    case ValueType.TYPE_COLOR_INT: {
                        int color = 0;
                        if (value != null) {
                            color = (int) value;
                        }
                        view.setBackgroundColor(color);
                        break;
                    }
                    case ValueType.TYPE_DRAWABLE: {
                        Drawable background = null;
                        if (value != null) {
                            background = (Drawable) value;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(background);
                        } else {
                            view.setBackgroundDrawable(background);
                        }
                        break;
                    }
                    case ValueType.TYPE_REFERENCE: {
                        int drawableId = 0;
                        Drawable background = null;
                        if (value != null) {
                            background = resources.getDrawable(drawableId);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(background);
                        } else {
                            view.setBackgroundDrawable(background);
                        }
                    }
                }
                break;
            }
            case "padding": {
                //TODO 暂不实现
                break;
            }
            case "paddingLeft": {
                int paddingLeft = 0;
                int paddingTop = view.getPaddingTop();
                int paddingRight = view.getPaddingRight();
                int paddingBottom = view.getPaddingBottom();
                if (value != null) {
                    paddingLeft = (int) value;
                }
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingTop": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = 0;
                int paddingRight = view.getPaddingRight();
                int paddingBottom = view.getPaddingBottom();
                if (value != null) {
                    paddingTop = (int) value;
                }
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingRight": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = 0;
                int paddingBottom = view.getPaddingBottom();
                if (value != null) {
                    paddingRight = (int) value;
                }
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingBottom": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = view.getPaddingRight();
                int paddingBottom = 0;
                if (value != null) {
                    paddingBottom = (int) value;
                }
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingStart": {
                //TODO 暂不实现
                break;
            }
            case "paddingEnd": {
                //TODO 暂不实现
                break;
            }
            case "paddingHorizontal": {
                //TODO 暂不实现
                break;
            }
            case "paddingVertical": {
                //TODO 暂不实现
                break;
            }
            case "scrollX": {
                int scrollX = 0;
                if (value != null) {
                    scrollX = (int) value;
                }
                view.setScrollX(scrollX);
                break;
            }
            case "scrollY": {
                int scrollY = 0;
                if (value != null) {
                    scrollY = (int) value;
                }
                view.setScrollY(scrollY);
                break;
            }
            case "alpha": {
                float alpha = 1.0f;
                if (value != null) {
                    alpha = (float) value;
                }
                view.setAlpha(alpha);
                break;
            }
            case "transformPivotX": {
                float pivotX = 0.0f;
                if (value != null) {
                    pivotX = (float) value;
                }
                view.setPivotX(pivotX);
                break;
            }
            case "transformPivotY": {
                float pivotY = 0.0f;
                if (value != null) {
                    pivotY = (float) value;
                }
                view.setPivotY(pivotY);
                break;
            }
            case "translationX": {
                float translationX = 0.0f;
                if (value != null) {
                    translationX = (float) value;
                }
                view.setTranslationX(translationX);
                break;
            }
            case "translationY": {
                float translationY = 0.0f;
                if (value != null) {
                    translationY = (float) value;
                }
                view.setTranslationY(translationY);
                break;
            }
            case "translationZ": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float translationZ = 0.0f;
                    if (value != null) {
                        translationZ = (float) value;
                    }
                    view.setTranslationZ(translationZ);
                }
                break;
            }
            case "elevation": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float elevation = 0.0f;
                    if (value != null) {
                        elevation = (float) value;
                    }
                    view.setElevation(elevation);
                }
                break;
            }
            case "rotation": {
                float rotation = 0.0f;
                if (value != null) {
                    rotation = (float) value;
                }
                view.setRotation(rotation);
                break;
            }
            case "rotationX": {
                float rotationX = 0.0f;
                if (value != null) {
                    rotationX = (float) value;
                }
                view.setRotationX(rotationX);
                break;
            }
            case "rotationY": {
                float rotationY = 0.0f;
                if (value != null) {
                    rotationY = (float) value;
                }
                view.setRotationY(rotationY);
                break;
            }
            case "scaleX": {
                float scaleX = 0.0f;
                if (value != null) {
                    scaleX = (float) value;
                }
                view.setScaleX(scaleX);
                break;
            }
            case "scaleY": {
                float scaleY = 0.0f;
                if (value != null) {
                    scaleY = (float) value;
                }
                view.setScaleY(scaleY);
                break;
            }
            case "fitsSystemWindows": {
                boolean fitsSystemWindows = false;
                if (value != null) {
                    fitsSystemWindows = (boolean) value;
                }
                view.setFitsSystemWindows(fitsSystemWindows);
                break;
            }
            case "focusable": {
                boolean focusable = false;
                if (value != null) {
                    focusable = (boolean) value;
                }
                view.setFocusable(focusable);
                break;
            }
            case "focusableInTouchMode": {
                boolean focusableInTouchMode = false;
                if (value != null) {
                    focusableInTouchMode = (boolean) value;
                }
                view.setFocusableInTouchMode(focusableInTouchMode);
                break;
            }
            case "clickable": {
                boolean clickable = false;
                if (value != null) {
                    clickable = (boolean) value;
                }
                view.setClickable(clickable);
                break;
            }
            case "longClickable": {
                boolean longClickable = false;
                if (value != null) {
                    longClickable = (boolean) value;
                }
                view.setLongClickable(longClickable);
                break;
            }
            case "contextClickable": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean contextClickable = false;
                    if (value != null) {
                        contextClickable = (boolean) value;
                    }
                    view.setContextClickable(contextClickable);
                }
                break;
            }
            case "saveEnabled": {
                boolean saveEnabled = false;
                if (value != null) {
                    saveEnabled = (boolean) value;
                }
                view.setSaveEnabled(saveEnabled);
                break;
            }
            case "duplicateParentState": {
                boolean duplicateParentState = false;
                if (value != null) {
                    duplicateParentState = (boolean) value;
                }
                view.setDuplicateParentStateEnabled(duplicateParentState);
                break;
            }
            case "visibility": {
                int visibility = View.VISIBLE;
                if (value != null) {
                    visibility = (int) value;
                }
                view.setVisibility(visibility);
                break;
            }
            case "layoutDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int layoutDirection = View.LAYOUT_DIRECTION_LTR;
                    if (value != null) {
                        layoutDirection = (int) value;
                    }
                    view.setLayoutDirection(layoutDirection);
                }
                break;
            }
            case "drawingCacheQuality": {
                int drawingCacheQuality = View.DRAWING_CACHE_QUALITY_AUTO;
                if (value != null) {
                    drawingCacheQuality = (int) value;
                }
                view.setDrawingCacheQuality(drawingCacheQuality);
                break;
            }
            case "contentDescription": {
                CharSequence contentDescription = null;
                if (value != null) {
                    contentDescription = (CharSequence) value;
                }
                view.setContentDescription(contentDescription);
                break;
            }
            case "accessibilityTraversalBefore": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    int beforeId = View.NO_ID;
                    if (value != null) {
                        beforeId = (int) value;
                    }
                    view.setAccessibilityTraversalBefore(beforeId);
                }
                break;
            }
            case "accessibilityTraversalAfter": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    int afterId = View.NO_ID;
                    if (value != null) {
                        afterId = (int) value;
                    }
                    view.setAccessibilityTraversalAfter(afterId);
                }
                break;
            }
            case "labelFor": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int labelForId = View.NO_ID;
                    if (value != null) {
                        labelForId = (int) value;
                    }
                    view.setLabelFor(labelForId);
                }
                break;
            }
            case "soundEffectsEnabled": {
                boolean soundEffectsEnabled = false;
                if (value != null) {
                    soundEffectsEnabled = (boolean) value;
                }
                view.setSoundEffectsEnabled(soundEffectsEnabled);
                break;
            }
            case "hapticFeedbackEnabled": {
                boolean hapticFeedbackEnabled = false;
                if (value != null) {
                    hapticFeedbackEnabled = (boolean) value;
                }
                view.setHapticFeedbackEnabled(hapticFeedbackEnabled);
                break;
            }
            case "scrollbars": {
                final int SCROLLBARS_NONE = 0x00000000;
                final int SCROLLBARS_HORIZONTAL = 0x00000100;
                final int SCROLLBARS_VERTICAL = 0x00000200;
                int scrollbars = SCROLLBARS_NONE;
                if (value != null) {
                    scrollbars = (int) value;
                }
                boolean horizontalScrollBarEnabled = (scrollbars & SCROLLBARS_HORIZONTAL) == SCROLLBARS_HORIZONTAL;
                boolean verticalScrollBarEnabled = (scrollbars & SCROLLBARS_VERTICAL) == SCROLLBARS_VERTICAL;
                view.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
                view.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
                break;
            }
            case "fadeScrollbars": {
                boolean fadeScrollbars = false;
                if (value != null) {
                    fadeScrollbars = (boolean) value;
                }
                view.setScrollbarFadingEnabled(fadeScrollbars);
                break;
            }
            case "scrollbarFadeDuration": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollBarFadeDuration = 0;
                    if (value != null) {
                        scrollBarFadeDuration = (int) value;
                    }
                    view.setScrollBarFadeDuration(scrollBarFadeDuration);
                }
                break;
            }
            case "scrollbarDefaultDelayBeforeFade": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollBarDefaultDelayBeforeFade = 0;
                    if (value != null) {
                        scrollBarDefaultDelayBeforeFade = (int) value;
                    }
                    view.setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade);
                }
                break;
            }
            case "scrollbarSize": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollbarSize = 0;
                    if (value != null) {
                        scrollbarSize = (int) value;
                    }
                    view.setScrollBarSize(scrollbarSize);
                }
                break;
            }
            case "scrollbarTrackHorizontal": {
                //TODO 暂不实现
                break;
            }
            case "scrollbarThumbHorizontal": {
                //TODO 暂不实现
                break;
            }
            case "scrollbarAlwaysDrawHorizontalTrack": {
                //TODO 暂不实现
                break;
            }
            case "scrollbarTrackVertical": {
                //TODO 暂不实现
                break;
            }
            case "scrollbarThumbVertical": {
                //TODO 暂不实现
                break;
            }
            case "scrollbarAlwaysDrawVerticalTrack": {
                //TODO 暂不实现
                break;
            }
            case "fadingEdge": {
                //TODO 暂不实现
                break;
            }
            case "requiresFadingEdge": {
                final int FADING_EDGE_NONE = 0x00000000;
                final int FADING_EDGE_HORIZONTAL = 0x00001000;
                final int FADING_EDGE_VERTICAL = 0x00002000;
                int requiresFadingEdge = FADING_EDGE_NONE;
                if (value != null) {
                    requiresFadingEdge = (int) value;
                }
                boolean horizontalFadingEdgeEnabled = (requiresFadingEdge & FADING_EDGE_HORIZONTAL) == FADING_EDGE_HORIZONTAL;
                boolean verticalFadingEdgeEnabled = (requiresFadingEdge & FADING_EDGE_VERTICAL) == FADING_EDGE_VERTICAL;
                view.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled);
                view.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled);
                break;
            }
            case "scrollbarStyle": {
                int scrollbarStyle = View.SCROLLBARS_INSIDE_OVERLAY;
                if (value != null) {
                    scrollbarStyle = (int) value;
                }
                view.setScrollBarStyle(scrollbarStyle);
                break;
            }
            case "isScrollContainer": {
                boolean isScrollContainer = false;
                if (value != null) {
                    isScrollContainer = (boolean) value;
                }
                view.setScrollContainer(isScrollContainer);
                break;
            }
            case "keepScreenOn": {
                boolean keepScreenOn = false;
                if (value != null) {
                    keepScreenOn = (boolean) value;
                }
                view.setKeepScreenOn(keepScreenOn);
                break;
            }
            case "filterTouchesWhenObscured": {
                boolean enabled = false;
                if (value != null) {
                    enabled = (boolean) value;
                }
                view.setFilterTouchesWhenObscured(enabled);
                break;
            }
            case "nextFocusLeft": {
                int nextFocusLeftId = View.NO_ID;
                if (value != null) {
                    nextFocusLeftId = (int) value;
                }
                view.setNextFocusLeftId(nextFocusLeftId);
                break;
            }
            case "nextFocusRight": {
                int nextFocusRightId = View.NO_ID;
                if (value != null) {
                    nextFocusRightId = (int) value;
                }
                view.setNextFocusRightId(nextFocusRightId);
                break;
            }
            case "nextFocusUp": {
                int nextFocusUpId = View.NO_ID;
                if (value != null) {
                    nextFocusUpId = (int) value;
                }
                view.setNextFocusUpId(nextFocusUpId);
                break;
            }
            case "nextFocusDown": {
                int nextFocusDownId = View.NO_ID;
                if (value != null) {
                    nextFocusDownId = (int) value;
                }
                view.setNextFocusDownId(nextFocusDownId);
                break;
            }
            case "nextFocusForward": {
                int nextFocusForwardId = View.NO_ID;
                if (value != null) {
                    nextFocusForwardId = (int) value;
                }
                view.setNextFocusForwardId(nextFocusForwardId);
                break;
            }
            case "nextClusterForward": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int nextClusterForwardId = View.NO_ID;
                    if (value != null) {
                        nextClusterForwardId = (int) value;
                    }
                    view.setNextClusterForwardId(nextClusterForwardId);
                }
                break;
            }
            case "minWidth": {
                int minWidth = 0;
                if (value != null) {
                    minWidth = (int) value;
                }
                view.setMinimumWidth(minWidth);
                break;
            }
            case "minHeight": {
                int minHeight = 0;
                if (value != null) {
                    minHeight = (int) value;
                }
                view.setMinimumHeight(minHeight);
                break;
            }
            case "overScrollMode": {
                int overScrollMode = View.OVER_SCROLL_IF_CONTENT_SCROLLS;
                if (value != null) {
                    overScrollMode = (int) value;
                }
                view.setOverScrollMode(overScrollMode);
                break;
            }
            case "verticalScrollbarPosition": {
                int position = View.SCROLLBAR_POSITION_DEFAULT;
                if (value != null) {
                    position = (int) value;
                }
                view.setVerticalScrollbarPosition(position);
                break;
            }
            case "layerType": {
                int layerType = View.LAYER_TYPE_NONE;
                if (value != null) {
                    layerType = (int) value;
                }
                view.setLayerType(layerType, null);
                break;
            }
            case "textDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int textDirection = View.TEXT_DIRECTION_INHERIT;
                    if (value != null) {
                        textDirection = (int) value;
                    }
                    view.setTextDirection(textDirection);
                }

                break;
            }
            case "textAlignment": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int textAlignment = View.TEXT_ALIGNMENT_INHERIT;
                    if (value != null) {
                        textAlignment = (int) value;
                    }
                    view.setTextAlignment(textAlignment);
                }
                break;
            }
            case "importantForAccessibility": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int mode = View.IMPORTANT_FOR_ACCESSIBILITY_AUTO;
                    if (value != null) {
                        mode = (int) value;
                    }
                    view.setImportantForAccessibility(mode);
                }
                break;
            }
            case "accessibilityLiveRegion": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int mode = View.ACCESSIBILITY_LIVE_REGION_NONE;
                    if (value != null) {
                        mode = (int) value;
                    }
                    view.setAccessibilityLiveRegion(mode);
                }
                break;
            }
            case "transitionName": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String transitionName = null;
                    if (value != null) {
                        transitionName = (String) value;
                    }
                    view.setTransitionName(transitionName);
                }
                break;
            }
            case "nestedScrollingEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    boolean nestedScrollingEnabled = false;
                    if (value != null) {
                        nestedScrollingEnabled = (boolean) value;
                    }
                    view.setNestedScrollingEnabled(nestedScrollingEnabled);
                }
                break;
            }
            case "stateListAnimator": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (type == ValueType.TYPE_REFERENCE) {
                        StateListAnimator stateListAnimator = null;
                        int stateListAnimatorId;
                        if (value != null) {
                            stateListAnimatorId = (int) value;
                            stateListAnimator = AnimatorInflater.loadStateListAnimator(context, stateListAnimatorId);
                        }
                        view.setStateListAnimator(stateListAnimator);
                    }
                }
                break;
            }
            case "backgroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorStateList tint = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_COLOR_INT: {
                                tint = ColorStateList.valueOf((int) value);
                                break;
                            }
                            case ValueType.TYPE_COLOR_STATE_LIST: {
                                tint = (ColorStateList) value;
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                tint = resources.getColorStateList((int) value);
                                break;
                            }
                        }
                    }
                    view.setBackgroundTintList(tint);
                }
                break;
            }
            case "backgroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    PorterDuff.Mode tintMode = null;
                    if (value != null) {
                        int backgroundTintModeIndex = (int) value;
                        tintMode = (PorterDuff.Mode) ReflectUtil.invokeStaticMethod(Drawable.class,
                                "parseTintMode",
                                new Class[]{int.class, PorterDuff.Mode.class},
                                backgroundTintModeIndex, null);
                    }
                    view.setBackgroundTintMode(tintMode);
                }
                break;
            }
            case "outlineProvider": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ViewOutlineProvider provider = ViewOutlineProvider.BACKGROUND;
                    if (value != null) {
                        int providerInt = (int) value;
                        switch (providerInt) {
                            case 0: {
                                provider = ViewOutlineProvider.BACKGROUND;
                                break;
                            }
                            case 1: {
                                provider = null;
                                break;
                            }
                            case 2: {
                                provider = ViewOutlineProvider.BOUNDS;
                                break;
                            }
                            case 3: {
                                provider = ViewOutlineProvider.PADDED_BOUNDS;
                                break;
                            }
                        }
                    }
                    view.setOutlineProvider(provider);
                }
                break;
            }
            case "foreground": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Drawable foreground = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_DRAWABLE: {
                                foreground = (Drawable) value;
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                foreground = resources.getDrawable((int) value);
                                break;
                            }
                        }
                    }
                    view.setForeground(foreground);
                }
                break;
            }
            case "foregroundGravity": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int gravity = Gravity.NO_GRAVITY;
                    if (value != null) {
                        gravity = (int) value;
                    }
                    view.setForegroundGravity(gravity);
                }
                break;
            }
            case "foregroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PorterDuff.Mode tintMode = null;
                    if (value != null) {
                        int foregroundTintModeIndex = (int) value;
                        tintMode = (PorterDuff.Mode) ReflectUtil.invokeStaticMethod(Drawable.class,
                                "parseTintMode",
                                new Class[]{int.class, PorterDuff.Mode.class},
                                foregroundTintModeIndex, null);
                    }
                    view.setForegroundTintMode(tintMode);
                }
                break;
            }
            case "foregroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ColorStateList tint = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_COLOR_INT: {
                                tint = ColorStateList.valueOf((int) value);
                                break;
                            }
                            case ValueType.TYPE_COLOR_STATE_LIST: {
                                tint = (ColorStateList) value;
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                tint = resources.getColorStateList((int) value);
                                break;
                            }
                        }
                    }
                    view.setForegroundTintList(tint);
                }
                break;
            }
            case "foregroundInsidePadding": {
                //TODO 暂未实现
                break;
            }
            case "scrollIndicators": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int indicators = 0;
                    if (value != null) {
                        indicators = (int) value;
                    }
                    view.setScrollIndicators(indicators);
                }
                break;
            }
            case "pointerIcon": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    PointerIcon pointerIcon = null;
                    if (value != null) {
                        switch (type) {
                            case ValueType.TYPE_INT: {
                                if (context == null) {
                                    return;
                                }
                                int pointerType = (int) value;
                                pointerIcon = PointerIcon.getSystemIcon(context, pointerType);
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                int pointerIconId = (int) value;
                                pointerIcon = PointerIcon.load(resources, pointerIconId);
                                break;
                            }
                        }
                    }
                    view.setPointerIcon(pointerIcon);
                }
                break;
            }
            case "forceHasOverlappingRendering": {
                //TODO 暂未实现
                break;
            }
            case "tooltipText": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence tooltipText = null;
                    if (value != null) {
                        tooltipText = (CharSequence) value;
                    }
                    view.setTooltipText(tooltipText);
                }
                break;
            }
            case "keyboardNavigationCluster": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isCluster = true;
                    if (value != null) {
                        isCluster = (boolean) value;
                    }
                    view.setKeyboardNavigationCluster(isCluster);
                }
                break;
            }
            case "focusedByDefault": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isFocusedByDefault = true;
                    if (value != null) {
                        isFocusedByDefault = (boolean) value;
                    }
                    view.setFocusedByDefault(isFocusedByDefault);
                }
                break;
            }
            case "autofillHints": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String[] autofillHints = null;
                    if (value != null) {
                        String autofillHintsStr = null;
                        switch (type) {
                            case ValueType.TYPE_STRING: {
                                autofillHintsStr = (String) value;
                                break;
                            }
                            case ValueType.TYPE_REFERENCE: {
                                autofillHintsStr = resources.getString((int) value);
                                break;
                            }
                        }
                        if (!TextUtils.isEmpty(autofillHintsStr)) {
                            String[] split = autofillHintsStr.split(",");
                            autofillHints = new String[split.length];
                            int numHints = split.length;
                            for (int rawHintNum = 0; rawHintNum < numHints; rawHintNum++) {
                                autofillHints[rawHintNum] = split[rawHintNum].trim();
                            }
                        }
                    }
                    view.setAutofillHints(autofillHints);
                }
                break;
            }
            case "importantForAutofill": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int mode = View.IMPORTANT_FOR_AUTOFILL_AUTO;
                    if (value != null) {
                        mode = (int) value;
                    }
                    view.setImportantForAutofill(mode);
                }
                break;
            }
            case "defaultFocusHighlightEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean defaultFocusHighlightEnabled = true;
                    if (value != null) {
                        defaultFocusHighlightEnabled = (boolean) value;
                    }
                    view.setDefaultFocusHighlightEnabled(defaultFocusHighlightEnabled);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private Class getStyleableClass() {
        try {
            return Class.forName("com.android.internal.R$styleable");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private String getStyleableName() {
        return "View";
    }

}
