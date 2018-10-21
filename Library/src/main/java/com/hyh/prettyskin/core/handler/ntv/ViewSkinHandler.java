package com.hyh.prettyskin.core.handler.ntv;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.AttrUtil;
import com.hyh.prettyskin.utils.ReflectUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */


public class ViewSkinHandler implements ISkinHandler {

    private int defStyleAttr;

    private int defStyleRes;

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
        return true;
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        Class styleableClass = getStyleableClass();
        String styleableName = getStyleableName();
        attrValue = parseAttrValue(view, set, attrName, styleableClass, styleableName);
        return attrValue;
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
                                        type = ValueType.TYPE_COLOR_ID;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (string.matches("^res/[(drawable)|(mipmap)].*/.+$")) {
                                    try {
                                        type = ValueType.TYPE_DRAWABLE_ID;
                                        value = typedArray.getResourceId(index, 0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (string.matches("^res/anim.*/.+\\.xml$")) {
                                    try {
                                        type = ValueType.TYPE_ANIM_ID;
                                        value = typedArray.getResourceId(index, 0);
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
            attrValue = new AttrValue(context.getResources(), type, value);
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
        Resources resources = attrValue.getResources();
        int type = attrValue.getType();
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
                    case ValueType.TYPE_COLOR_ID: {
                        int colorId = 0;
                        if (value != null) {
                            colorId = (int) value;
                        }
                        Drawable background = resources.getDrawable(colorId);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(background);
                        } else {
                            view.setBackgroundDrawable(background);
                        }
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
                    case ValueType.TYPE_DRAWABLE_ID: {
                        int drawableId = 0;
                        if (value != null) {
                            drawableId = (int) value;
                        }
                        Drawable background = null;
                        if (drawableId != 0) {
                            background = resources.getDrawable(drawableId);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(background);
                        } else {
                            view.setBackgroundDrawable(background);
                        }
                        break;
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
                view.setScaleX(scaleY);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                }
                break;
            }
            case "keepScreenOn": {
                type = ValueType.TYPE_INT;
                value = view.getKeepScreenOn();
                break;
            }
            case "filterTouchesWhenObscured": {
                type = ValueType.TYPE_INT;
                value = view.getFilterTouchesWhenObscured();
                break;
            }
            case "nextFocusLeft": {
                type = ValueType.TYPE_INT;
                value = view.getNextFocusLeftId();
                break;
            }
            case "nextFocusRight": {
                type = ValueType.TYPE_INT;
                value = view.getNextFocusRightId();
                break;
            }
            case "nextFocusUp": {
                type = ValueType.TYPE_INT;
                value = view.getNextFocusUpId();
                break;
            }
            case "nextFocusDown": {
                type = ValueType.TYPE_INT;
                value = view.getNextFocusDownId();
                break;
            }
            case "nextFocusForward": {
                type = ValueType.TYPE_INT;
                value = view.getNextFocusForwardId();
                break;
            }
            case "nextClusterForward": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = ValueType.TYPE_INT;
                    value = view.getNextClusterForwardId();
                }
                break;
            }
            case "minWidth": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = view.getMinimumWidth();
                }
                break;
            }
            case "minHeight": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = view.getMinimumHeight();
                }
                break;
            }
            case "overScrollMode": {
                type = ValueType.TYPE_INT;
                value = view.getOverScrollMode();
                break;
            }
            case "verticalScrollbarPosition": {
                type = ValueType.TYPE_INT;
                value = view.getVerticalScrollbarPosition();
                break;
            }
            case "layerType": {
                type = ValueType.TYPE_INT;
                value = view.getLayerType();
                break;
            }
            case "textDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    value = view.getTextDirection();
                }
                break;
            }
            case "textAlignment": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    type = ValueType.TYPE_INT;
                    value = view.getTextAlignment();
                }
                break;
            }
            case "importantForAccessibility": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = view.getImportantForAccessibility();
                }
                break;
            }
            case "accessibilityLiveRegion": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    type = ValueType.TYPE_INT;
                    value = view.getAccessibilityLiveRegion();
                }
                break;
            }
            case "transitionName": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_STRING;
                    value = view.getTransitionName();
                }
                break;
            }
            case "nestedScrollingEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_BOOLEAN;
                    value = view.isNestedScrollingEnabled();
                }
                break;
            }
            case "stateListAnimator": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_OBJECT;
                    value = view.getStateListAnimator();
                }
                break;
            }
            case "backgroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_COLOR_STATE_LIST;
                    value = view.getBackgroundTintList();
                }
                break;
            }
            case "backgroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_OBJECT;
                    value = view.getBackgroundTintMode();
                }
                break;
            }
            case "outlineProvider": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_OBJECT;
                    value = view.getOutlineProvider();
                }
                break;
            }
            case "foreground": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_DRAWABLE;
                    value = view.getForeground();
                }
                break;
            }
            case "foregroundGravity": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_INT;
                    value = view.getForegroundGravity();
                }
                break;
            }
            case "foregroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_OBJECT;
                    value = view.getForegroundTintMode();
                }
                break;
            }
            case "foregroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_COLOR_STATE_LIST;
                    value = view.getForegroundTintList();
                }
                break;
            }
            case "foregroundInsidePadding": {
                //TODO 暂未实现
                break;
            }
            case "scrollIndicators": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_INT;
                    value = view.getScrollIndicators();
                }
                break;
            }
            case "pointerIcon": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    type = ValueType.TYPE_OBJECT;
                    value = view.getPointerIcon();
                }
                break;
            }
            case "forceHasOverlappingRendering": {
                //TODO 暂未实现
                break;
            }
            case "tooltipText": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = ValueType.TYPE_CHARSEQUENCE;
                    value = view.getTooltipText();
                }
                break;
            }
            case "keyboardNavigationCluster": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = ValueType.TYPE_BOOLEAN;
                    value = view.isKeyboardNavigationCluster();
                }
                break;
            }
            case "focusedByDefault": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = ValueType.TYPE_BOOLEAN;
                    value = view.isFocusedByDefault();
                }
                break;
            }
            case "autofillHints": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = ValueType.TYPE_OBJECT;
                    value = view.getAutofillHints();
                }
                break;
            }
            case "importantForAutofill": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    value = view.isImportantForAutofill();
                }
                break;
            }
            case "defaultFocusHighlightEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    value = view.getDefaultFocusHighlightEnabled();
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
