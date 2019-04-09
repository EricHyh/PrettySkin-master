package com.hyh.prettyskin.core.handler.ntv;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.core.handler.ISkinHandler;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */


public class ViewSH implements ISkinHandler {

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "View";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    protected int mDefStyleAttr;

    protected int mDefStyleRes;


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

    public ViewSH() {
    }

    public ViewSH(int defStyleAttr) {
        this.mDefStyleAttr = defStyleAttr;
    }

    public ViewSH(int defStyleAttr, int defStyleRes) {
        this.mDefStyleAttr = defStyleAttr;
        this.mDefStyleRes = defStyleRes;
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
        return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
    }

    @Override
    public void finishParse() {
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
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
                int paddingLeft = ViewAttrUtil.getInt(resources, type, value);
                int paddingTop = view.getPaddingTop();
                int paddingRight = view.getPaddingRight();
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingTop": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = ViewAttrUtil.getInt(resources, type, value);
                int paddingRight = view.getPaddingRight();
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingRight": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = ViewAttrUtil.getInt(resources, type, value);
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingBottom": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = view.getPaddingRight();
                int paddingBottom = ViewAttrUtil.getInt(resources, type, value);
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
                int scrollX = ViewAttrUtil.getInt(resources, type, value);
                view.setScrollX(scrollX);
                break;
            }
            case "scrollY": {
                int scrollY = ViewAttrUtil.getInt(resources, type, value);
                view.setScrollY(scrollY);
                break;
            }
            case "alpha": {
                float alpha = ViewAttrUtil.getFloat(resources, type, value, 1.0f);
                if (value != null) {
                    alpha = (float) value;
                }
                view.setAlpha(alpha);
                break;
            }
            case "transformPivotX": {
                float pivotX = ViewAttrUtil.getFloat(resources, type, value);
                view.setPivotX(pivotX);
                break;
            }
            case "transformPivotY": {
                float pivotY = ViewAttrUtil.getFloat(resources, type, value);
                view.setPivotY(pivotY);
                break;
            }
            case "translationX": {
                float translationX = ViewAttrUtil.getFloat(resources, type, value);
                view.setTranslationX(translationX);
                break;
            }
            case "translationY": {
                float translationY = ViewAttrUtil.getFloat(resources, type, value);
                view.setTranslationY(translationY);
                break;
            }
            case "translationZ": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float translationZ = ViewAttrUtil.getFloat(resources, type, value);
                    view.setTranslationZ(translationZ);
                }
                break;
            }
            case "elevation": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float elevation = ViewAttrUtil.getFloat(resources, type, value);
                    view.setElevation(elevation);
                }
                break;
            }
            case "rotation": {
                float rotation = ViewAttrUtil.getFloat(resources, type, value);
                view.setRotation(rotation);
                break;
            }
            case "rotationX": {
                float rotationX = ViewAttrUtil.getFloat(resources, type, value);
                view.setRotationX(rotationX);
                break;
            }
            case "rotationY": {
                float rotationY = ViewAttrUtil.getFloat(resources, type, value);
                view.setRotationY(rotationY);
                break;
            }
            case "scaleX": {
                float scaleX = ViewAttrUtil.getFloat(resources, type, value);
                view.setScaleX(scaleX);
                break;
            }
            case "scaleY": {
                float scaleY = ViewAttrUtil.getFloat(resources, type, value);
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
                    int labelForId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                    view.setLabelFor(labelForId);
                }
                break;
            }
            case "soundEffectsEnabled": {
                boolean soundEffectsEnabled = ViewAttrUtil.getBoolean(resources, type, value);
                view.setSoundEffectsEnabled(soundEffectsEnabled);
                break;
            }
            case "hapticFeedbackEnabled": {
                boolean hapticFeedbackEnabled = ViewAttrUtil.getBoolean(resources, type, value);
                view.setHapticFeedbackEnabled(hapticFeedbackEnabled);
                break;
            }
            case "scrollbars": {
                final int SCROLLBARS_NONE = 0x00000000;
                final int SCROLLBARS_HORIZONTAL = 0x00000100;
                final int SCROLLBARS_VERTICAL = 0x00000200;
                int scrollbars = ViewAttrUtil.getInt(resources, type, value, SCROLLBARS_NONE);
                boolean horizontalScrollBarEnabled = (scrollbars & SCROLLBARS_HORIZONTAL) == SCROLLBARS_HORIZONTAL;
                boolean verticalScrollBarEnabled = (scrollbars & SCROLLBARS_VERTICAL) == SCROLLBARS_VERTICAL;
                view.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
                view.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
                break;
            }
            case "fadeScrollbars": {
                boolean fadeScrollbars = ViewAttrUtil.getBoolean(resources, type, value);
                view.setScrollbarFadingEnabled(fadeScrollbars);
                break;
            }
            case "scrollbarFadeDuration": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollBarFadeDuration = ViewAttrUtil.getInt(resources, type, value);
                    view.setScrollBarFadeDuration(scrollBarFadeDuration);
                }
                break;
            }
            case "scrollbarDefaultDelayBeforeFade": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollBarDefaultDelayBeforeFade = ViewAttrUtil.getInt(resources, type, value);
                    view.setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade);
                }
                break;
            }
            case "scrollbarSize": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollbarSize = ViewAttrUtil.getInt(resources, type, value);
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
                int requiresFadingEdge = ViewAttrUtil.getInt(resources, type, value, FADING_EDGE_NONE);
                boolean horizontalFadingEdgeEnabled = (requiresFadingEdge & FADING_EDGE_HORIZONTAL) == FADING_EDGE_HORIZONTAL;
                boolean verticalFadingEdgeEnabled = (requiresFadingEdge & FADING_EDGE_VERTICAL) == FADING_EDGE_VERTICAL;
                view.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled);
                view.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled);
                break;
            }
            case "scrollbarStyle": {
                int scrollbarStyle = ViewAttrUtil.getInt(resources, type, value, View.SCROLLBARS_INSIDE_OVERLAY);
                view.setScrollBarStyle(scrollbarStyle);
                break;
            }
            case "isScrollContainer": {
                boolean isScrollContainer = ViewAttrUtil.getBoolean(resources, type, value);
                view.setScrollContainer(isScrollContainer);
                break;
            }
            case "keepScreenOn": {
                boolean keepScreenOn = ViewAttrUtil.getBoolean(resources, type, value);
                view.setKeepScreenOn(keepScreenOn);
                break;
            }
            case "filterTouchesWhenObscured": {
                boolean enabled = ViewAttrUtil.getBoolean(resources, type, value);
                view.setFilterTouchesWhenObscured(enabled);
                break;
            }
            case "nextFocusLeft": {
                int nextFocusLeftId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                view.setNextFocusLeftId(nextFocusLeftId);
                break;
            }
            case "nextFocusRight": {
                int nextFocusRightId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                view.setNextFocusRightId(nextFocusRightId);
                break;
            }
            case "nextFocusUp": {
                int nextFocusUpId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                view.setNextFocusUpId(nextFocusUpId);
                break;
            }
            case "nextFocusDown": {
                int nextFocusDownId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                view.setNextFocusDownId(nextFocusDownId);
                break;
            }
            case "nextFocusForward": {
                int nextFocusForwardId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                view.setNextFocusForwardId(nextFocusForwardId);
                break;
            }
            case "nextClusterForward": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int nextClusterForwardId = ViewAttrUtil.getInt(resources, type, value, View.NO_ID);
                    view.setNextClusterForwardId(nextClusterForwardId);
                }
                break;
            }
            case "minWidth": {
                int minWidth = ViewAttrUtil.getInt(resources, type, value);
                view.setMinimumWidth(minWidth);
                break;
            }
            case "minHeight": {
                int minHeight = ViewAttrUtil.getInt(resources, type, value);
                view.setMinimumHeight(minHeight);
                break;
            }
            case "overScrollMode": {
                int overScrollMode = ViewAttrUtil.getInt(resources, type, value, View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                view.setOverScrollMode(overScrollMode);
                break;
            }
            case "verticalScrollbarPosition": {
                int position = ViewAttrUtil.getInt(resources, type, value, View.SCROLLBAR_POSITION_DEFAULT);
                view.setVerticalScrollbarPosition(position);
                break;
            }
            case "layerType": {
                int layerType = ViewAttrUtil.getInt(resources, type, value, View.LAYER_TYPE_NONE);
                view.setLayerType(layerType, null);
                break;
            }
            case "textDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int textDirection = ViewAttrUtil.getInt(resources, type, value, View.TEXT_DIRECTION_INHERIT);
                    view.setTextDirection(textDirection);
                }
                break;
            }
            case "textAlignment": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int textAlignment = ViewAttrUtil.getInt(resources, type, value, View.TEXT_ALIGNMENT_INHERIT);
                    view.setTextAlignment(textAlignment);
                }
                break;
            }
            case "importantForAccessibility": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int mode = ViewAttrUtil.getInt(resources, type, value, View.IMPORTANT_FOR_ACCESSIBILITY_AUTO);
                    view.setImportantForAccessibility(mode);
                }
                break;
            }
            case "accessibilityLiveRegion": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int mode = ViewAttrUtil.getInt(resources, type, value, View.ACCESSIBILITY_LIVE_REGION_NONE);
                    view.setAccessibilityLiveRegion(mode);
                }
                break;
            }
            case "transitionName": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String transitionName = ViewAttrUtil.getString(resources, type, value);
                    view.setTransitionName(transitionName);
                }
                break;
            }
            case "nestedScrollingEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    boolean nestedScrollingEnabled = ViewAttrUtil.getBoolean(resources, type, value);
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
                    ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
                    view.setBackgroundTintList(tint);
                }
                break;
            }
            case "backgroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
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
                    Drawable foreground = ViewAttrUtil.getDrawable(resources, type, value);
                    view.setForeground(foreground);
                }
                break;
            }
            case "foregroundGravity": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int gravity = ViewAttrUtil.getInt(resources, type, value, Gravity.NO_GRAVITY);
                    view.setForegroundGravity(gravity);
                }
                break;
            }
            case "foregroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PorterDuff.Mode tintMode = ViewAttrUtil.getTintMode(type, value);
                    view.setForegroundTintMode(tintMode);
                }
                break;
            }
            case "foregroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ColorStateList tint = ViewAttrUtil.getColorStateList(resources, type, value);
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
                    int indicators = ViewAttrUtil.getInt(resources, type, value);
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
                    CharSequence tooltipText = ViewAttrUtil.getCharSequence(resources, type, value);
                    view.setTooltipText(tooltipText);
                }
                break;
            }
            case "keyboardNavigationCluster": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isCluster = ViewAttrUtil.getBoolean(resources, type, value, true);
                    view.setKeyboardNavigationCluster(isCluster);
                }
                break;
            }
            case "focusedByDefault": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isFocusedByDefault = ViewAttrUtil.getBoolean(resources, type, value, true);
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
                    int mode = ViewAttrUtil.getInt(resources, type, value, View.IMPORTANT_FOR_AUTOFILL_AUTO);
                    view.setImportantForAutofill(mode);
                }
                break;
            }
            case "defaultFocusHighlightEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean defaultFocusHighlightEnabled = ViewAttrUtil.getBoolean(resources, type, value, true);
                    view.setDefaultFocusHighlightEnabled(defaultFocusHighlightEnabled);
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}