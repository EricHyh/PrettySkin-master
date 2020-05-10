package com.hyh.prettyskin.sh;

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

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkinHandler;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;


public class ViewSH implements ISkinHandler {

    protected int mDefStyleAttr;
    protected int mDefStyleRes;

    private final List<String> mSupportAttrNames = new ArrayList<>();

    private Class mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private TypedArray mTypedArray;

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

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "View";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }

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
                        int color = attrValue.getTypedValue(int.class, 0);
                        view.setBackgroundColor(color);
                        break;
                    }
                    case ValueType.TYPE_DRAWABLE:
                    case ValueType.TYPE_REFERENCE:
                    case ValueType.TYPE_LAZY_DRAWABLE: {
                        Drawable background = attrValue.getTypedValue(Drawable.class, null);
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
            /*case "padding": {
                break;
            }*/
            case "paddingLeft": {
                int paddingLeft = attrValue.getTypedValue(int.class, 0);
                int paddingTop = view.getPaddingTop();
                int paddingRight = view.getPaddingRight();
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingTop": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = attrValue.getTypedValue(int.class, 0);
                int paddingRight = view.getPaddingRight();
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingRight": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = attrValue.getTypedValue(int.class, 0);
                int paddingBottom = view.getPaddingBottom();
                view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
                break;
            }
            case "paddingBottom": {
                int paddingLeft = view.getPaddingLeft();
                int paddingTop = view.getPaddingTop();
                int paddingRight = view.getPaddingRight();
                int paddingBottom = attrValue.getTypedValue(int.class, 0);
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
                int scrollX = attrValue.getTypedValue(int.class, 0);
                view.setScrollX(scrollX);
                break;
            }
            case "scrollY": {
                int scrollY = attrValue.getTypedValue(int.class, 0);
                view.setScrollY(scrollY);
                break;
            }
            case "alpha": {
                float alpha = attrValue.getTypedValue(float.class, 1.0f);
                view.setAlpha(alpha);
                break;
            }
            case "transformPivotX": {
                float pivotX = attrValue.getTypedValue(float.class, 0.0f);
                view.setPivotX(pivotX);
                break;
            }
            case "transformPivotY": {
                float pivotY = attrValue.getTypedValue(float.class, 0.0f);
                view.setPivotY(pivotY);
                break;
            }
            case "translationX": {
                float translationX = attrValue.getTypedValue(float.class, 0.0f);
                view.setTranslationX(translationX);
                break;
            }
            case "translationY": {
                float translationY = attrValue.getTypedValue(float.class, 0.0f);
                view.setTranslationY(translationY);
                break;
            }
            case "translationZ": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float translationZ = attrValue.getTypedValue(float.class, 0.0f);
                    view.setTranslationZ(translationZ);
                }
                break;
            }
            case "elevation": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    float elevation = attrValue.getTypedValue(float.class, 0.0f);
                    view.setElevation(elevation);
                }
                break;
            }
            case "rotation": {
                float rotation = attrValue.getTypedValue(float.class, 0.0f);
                view.setRotation(rotation);
                break;
            }
            case "rotationX": {
                float rotationX = attrValue.getTypedValue(float.class, 0.0f);
                view.setRotationX(rotationX);
                break;
            }
            case "rotationY": {
                float rotationY = attrValue.getTypedValue(float.class, 0.0f);
                view.setRotationY(rotationY);
                break;
            }
            case "scaleX": {
                float scaleX = attrValue.getTypedValue(float.class, 0.0f);
                view.setScaleX(scaleX);
                break;
            }
            case "scaleY": {
                float scaleY = attrValue.getTypedValue(float.class, 0.0f);
                view.setScaleY(scaleY);
                break;
            }
            case "fitsSystemWindows": {
                boolean fitsSystemWindows = attrValue.getTypedValue(boolean.class, false);
                view.setFitsSystemWindows(fitsSystemWindows);
                break;
            }
            case "focusable": {
                boolean focusable = attrValue.getTypedValue(boolean.class, false);
                view.setFocusable(focusable);
                break;
            }
            case "focusableInTouchMode": {
                boolean focusableInTouchMode = attrValue.getTypedValue(boolean.class, false);
                view.setFocusableInTouchMode(focusableInTouchMode);
                break;
            }
            case "clickable": {
                boolean clickable = attrValue.getTypedValue(boolean.class, false);
                view.setClickable(clickable);
                break;
            }
            case "longClickable": {
                boolean longClickable = attrValue.getTypedValue(boolean.class, false);
                view.setLongClickable(longClickable);
                break;
            }
            case "contextClickable": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean contextClickable = attrValue.getTypedValue(boolean.class, false);
                    view.setContextClickable(contextClickable);
                }
                break;
            }
            case "saveEnabled": {
                boolean saveEnabled = attrValue.getTypedValue(boolean.class, false);
                view.setSaveEnabled(saveEnabled);
                break;
            }
            case "duplicateParentState": {
                boolean duplicateParentState = attrValue.getTypedValue(boolean.class, false);
                view.setDuplicateParentStateEnabled(duplicateParentState);
                break;
            }
            case "visibility": {
                int visibility = attrValue.getTypedValue(int.class, View.VISIBLE);
                view.setVisibility(visibility);
                break;
            }
            case "layoutDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int layoutDirection = attrValue.getTypedValue(int.class, View.LAYOUT_DIRECTION_LTR);
                    view.setLayoutDirection(layoutDirection);
                }
                break;
            }
            case "drawingCacheQuality": {
                int drawingCacheQuality = attrValue.getTypedValue(int.class, View.DRAWING_CACHE_QUALITY_AUTO);
                view.setDrawingCacheQuality(drawingCacheQuality);
                break;
            }
            case "contentDescription": {
                CharSequence contentDescription = attrValue.getTypedValue(CharSequence.class, null);
                view.setContentDescription(contentDescription);
                break;
            }
            case "accessibilityTraversalBefore": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    int beforeId = attrValue.getTypedValue(int.class, View.NO_ID);
                    view.setAccessibilityTraversalBefore(beforeId);
                }
                break;
            }
            case "accessibilityTraversalAfter": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    int afterId = attrValue.getTypedValue(int.class, View.NO_ID);
                    view.setAccessibilityTraversalAfter(afterId);
                }
                break;
            }
            case "labelFor": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int labelForId = attrValue.getTypedValue(int.class, View.NO_ID);
                    view.setLabelFor(labelForId);
                }
                break;
            }
            case "soundEffectsEnabled": {
                boolean soundEffectsEnabled = attrValue.getTypedValue(boolean.class, false);
                view.setSoundEffectsEnabled(soundEffectsEnabled);
                break;
            }
            case "hapticFeedbackEnabled": {
                boolean hapticFeedbackEnabled = attrValue.getTypedValue(boolean.class, false);
                view.setHapticFeedbackEnabled(hapticFeedbackEnabled);
                break;
            }
            case "scrollbars": {
                final int SCROLLBARS_NONE = 0x00000000;
                final int SCROLLBARS_HORIZONTAL = 0x00000100;
                final int SCROLLBARS_VERTICAL = 0x00000200;
                int scrollbars = attrValue.getTypedValue(int.class, SCROLLBARS_NONE);
                boolean horizontalScrollBarEnabled = (scrollbars & SCROLLBARS_HORIZONTAL) == SCROLLBARS_HORIZONTAL;
                boolean verticalScrollBarEnabled = (scrollbars & SCROLLBARS_VERTICAL) == SCROLLBARS_VERTICAL;
                view.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
                view.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
                break;
            }
            case "fadeScrollbars": {
                boolean fadeScrollbars = attrValue.getTypedValue(boolean.class, false);
                view.setScrollbarFadingEnabled(fadeScrollbars);
                break;
            }
            case "scrollbarFadeDuration": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollBarFadeDuration = attrValue.getTypedValue(int.class, 0);
                    view.setScrollBarFadeDuration(scrollBarFadeDuration);
                }
                break;
            }
            case "scrollbarDefaultDelayBeforeFade": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollBarDefaultDelayBeforeFade = attrValue.getTypedValue(int.class, 0);
                    view.setScrollBarDefaultDelayBeforeFade(scrollBarDefaultDelayBeforeFade);
                }
                break;
            }
            case "scrollbarSize": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int scrollbarSize = attrValue.getTypedValue(int.class, 0);
                    view.setScrollBarSize(scrollbarSize);
                }
                break;
            }
            /*case "scrollbarTrackHorizontal": {
                break;
            }
            case "scrollbarThumbHorizontal": {
                break;
            }
            case "scrollbarAlwaysDrawHorizontalTrack": {
                break;
            }
            case "scrollbarTrackVertical": {
                break;
            }
            case "scrollbarThumbVertical": {
                break;
            }
            case "scrollbarAlwaysDrawVerticalTrack": {
                break;
            }
            case "fadingEdge": {
                break;
            }*/
            case "requiresFadingEdge": {
                final int FADING_EDGE_NONE = 0x00000000;
                final int FADING_EDGE_HORIZONTAL = 0x00001000;
                final int FADING_EDGE_VERTICAL = 0x00002000;
                int requiresFadingEdge = attrValue.getTypedValue(int.class, FADING_EDGE_NONE);
                boolean horizontalFadingEdgeEnabled = (requiresFadingEdge & FADING_EDGE_HORIZONTAL) == FADING_EDGE_HORIZONTAL;
                boolean verticalFadingEdgeEnabled = (requiresFadingEdge & FADING_EDGE_VERTICAL) == FADING_EDGE_VERTICAL;
                view.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled);
                view.setVerticalFadingEdgeEnabled(verticalFadingEdgeEnabled);
                break;
            }
            case "scrollbarStyle": {
                int scrollbarStyle = attrValue.getTypedValue(int.class, View.SCROLLBARS_INSIDE_OVERLAY);
                view.setScrollBarStyle(scrollbarStyle);
                break;
            }
            case "isScrollContainer": {
                boolean isScrollContainer = attrValue.getTypedValue(boolean.class, false);
                view.setScrollContainer(isScrollContainer);
                break;
            }
            case "keepScreenOn": {
                boolean keepScreenOn = attrValue.getTypedValue(boolean.class, false);
                view.setKeepScreenOn(keepScreenOn);
                break;
            }
            case "filterTouchesWhenObscured": {
                boolean enabled = attrValue.getTypedValue(boolean.class, false);
                view.setFilterTouchesWhenObscured(enabled);
                break;
            }
            case "nextFocusLeft": {
                int nextFocusLeftId = attrValue.getTypedValue(int.class, View.NO_ID);
                view.setNextFocusLeftId(nextFocusLeftId);
                break;
            }
            case "nextFocusRight": {
                int nextFocusRightId = attrValue.getTypedValue(int.class, View.NO_ID);
                view.setNextFocusRightId(nextFocusRightId);
                break;
            }
            case "nextFocusUp": {
                int nextFocusUpId = attrValue.getTypedValue(int.class, View.NO_ID);
                view.setNextFocusUpId(nextFocusUpId);
                break;
            }
            case "nextFocusDown": {
                int nextFocusDownId = attrValue.getTypedValue(int.class, View.NO_ID);
                view.setNextFocusDownId(nextFocusDownId);
                break;
            }
            case "nextFocusForward": {
                int nextFocusForwardId = attrValue.getTypedValue(int.class, View.NO_ID);
                view.setNextFocusForwardId(nextFocusForwardId);
                break;
            }
            case "nextClusterForward": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int nextClusterForwardId = attrValue.getTypedValue(int.class, View.NO_ID);
                    view.setNextClusterForwardId(nextClusterForwardId);
                }
                break;
            }
            case "minWidth": {
                int minWidth = attrValue.getTypedValue(int.class, 0);
                view.setMinimumWidth(minWidth);
                break;
            }
            case "minHeight": {
                int minHeight = attrValue.getTypedValue(int.class, 0);
                view.setMinimumHeight(minHeight);
                break;
            }
            case "overScrollMode": {
                int overScrollMode = attrValue.getTypedValue(int.class, View.OVER_SCROLL_IF_CONTENT_SCROLLS);
                view.setOverScrollMode(overScrollMode);
                break;
            }
            case "verticalScrollbarPosition": {
                int position = attrValue.getTypedValue(int.class, View.SCROLLBAR_POSITION_DEFAULT);
                view.setVerticalScrollbarPosition(position);
                break;
            }
            case "layerType": {
                int layerType = attrValue.getTypedValue(int.class, View.LAYER_TYPE_NONE);
                view.setLayerType(layerType, null);
                break;
            }
            case "textDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int textDirection = attrValue.getTypedValue(int.class, View.TEXT_DIRECTION_INHERIT);
                    view.setTextDirection(textDirection);
                }
                break;
            }
            case "textAlignment": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    int textAlignment = attrValue.getTypedValue(int.class, View.TEXT_ALIGNMENT_INHERIT);
                    view.setTextAlignment(textAlignment);
                }
                break;
            }
            case "importantForAccessibility": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    int mode = attrValue.getTypedValue(int.class, View.IMPORTANT_FOR_ACCESSIBILITY_AUTO);
                    view.setImportantForAccessibility(mode);
                }
                break;
            }
            case "accessibilityLiveRegion": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int mode = attrValue.getTypedValue(int.class, View.ACCESSIBILITY_LIVE_REGION_NONE);
                    view.setAccessibilityLiveRegion(mode);
                }
                break;
            }
            case "transitionName": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String transitionName = attrValue.getTypedValue(String.class, null);
                    view.setTransitionName(transitionName);
                }
                break;
            }
            case "nestedScrollingEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    boolean nestedScrollingEnabled = attrValue.getTypedValue(boolean.class, false);
                    view.setNestedScrollingEnabled(nestedScrollingEnabled);
                }
                break;
            }
            case "stateListAnimator": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    StateListAnimator stateListAnimator = attrValue.getTypedValue(StateListAnimator.class, null);
                    view.setStateListAnimator(stateListAnimator);
                }
                break;
            }
            case "backgroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
                    view.setBackgroundTintList(tint);
                }
                break;
            }
            case "backgroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                    view.setBackgroundTintMode(tintMode);
                }
                break;
            }
            case "outlineProvider": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ViewOutlineProvider provider = ViewOutlineProvider.BACKGROUND;
                    int providerInt = attrValue.getTypedValue(int.class, 0);
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
                    view.setOutlineProvider(provider);
                }
                break;
            }
            case "foreground": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Drawable foreground = attrValue.getTypedValue(Drawable.class, null);
                    view.setForeground(foreground);
                }
                break;
            }
            case "foregroundGravity": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int gravity = attrValue.getTypedValue(int.class, Gravity.NO_GRAVITY);
                    view.setForegroundGravity(gravity);
                }
                break;
            }
            case "foregroundTintMode": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PorterDuff.Mode tintMode = attrValue.getTypedValue(PorterDuff.Mode.class, null);
                    view.setForegroundTintMode(tintMode);
                }
                break;
            }
            case "foregroundTint": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ColorStateList tint = attrValue.getTypedValue(ColorStateList.class, null);
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
                    int indicators = attrValue.getTypedValue(int.class, view.getScrollIndicators());
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
                    CharSequence tooltipText = attrValue.getTypedValue(CharSequence.class, null);
                    view.setTooltipText(tooltipText);
                }
                break;
            }
            case "keyboardNavigationCluster": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isCluster = attrValue.getTypedValue(boolean.class, true);
                    view.setKeyboardNavigationCluster(isCluster);
                }
                break;
            }
            case "focusedByDefault": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean isFocusedByDefault = attrValue.getTypedValue(boolean.class, true);
                    view.setFocusedByDefault(isFocusedByDefault);
                }
                break;
            }
            case "autofillHints": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String[] autofillHints = null;
                    if (value != null) {
                        String autofillHintsStr = attrValue.getTypedValue(String.class, null);
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
                    int mode = attrValue.getTypedValue(int.class, View.IMPORTANT_FOR_AUTOFILL_AUTO);
                    view.setImportantForAutofill(mode);
                }
                break;
            }
            case "defaultFocusHighlightEnabled": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean defaultFocusHighlightEnabled = attrValue.getTypedValue(boolean.class, true);
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