package com.hyh.prettyskin.core.parser.ntv;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.core.parser.XmlAttrParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/19
 */
public class ViewAttrParser implements XmlAttrParser {

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

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        AttrValue attrValue = null;
        int type = ValueType.TYPE_NULL;
        Object value = null;
        switch (attrName) {
            case "background": {
                Drawable drawable = view.getBackground();
                if (drawable != null && drawable instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable) drawable;
                    type = ValueType.TYPE_COLOR_INT;
                    value = colorDrawable.getColor();
                } else {
                    type = ValueType.TYPE_DRAWABLE;
                    value = drawable;
                }
                break;
            }
            case "padding": {
                //TODO 暂不实现
                break;
            }
            case "paddingLeft": {
                type = ValueType.TYPE_INT;
                value = view.getPaddingLeft();
                break;
            }
            case "paddingTop": {
                type = ValueType.TYPE_INT;
                value = view.getPaddingTop();
                break;
            }
            case "paddingRight": {
                type = ValueType.TYPE_INT;
                value = view.getPaddingRight();
                break;
            }
            case "paddingBottom": {
                type = ValueType.TYPE_INT;
                value = view.getPaddingBottom();
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
                type = ValueType.TYPE_FLOAT;
                value = view.getScrollX();
                break;
            }
            case "scrollY": {
                type = ValueType.TYPE_FLOAT;
                value = view.getScaleY();
                break;
            }
            case "alpha": {
                type = ValueType.TYPE_FLOAT;
                value = view.getAlpha();
                break;
            }
            case "transformPivotX": {
                type = ValueType.TYPE_FLOAT;
                value = view.getPivotX();
                break;
            }
            case "transformPivotY": {
                type = ValueType.TYPE_FLOAT;
                value = view.getPivotY();
                break;
            }
            case "translationX": {
                type = ValueType.TYPE_FLOAT;
                value = view.getTranslationX();
                break;
            }
            case "translationY": {
                type = ValueType.TYPE_FLOAT;
                value = view.getTranslationY();
                break;
            }
            case "translationZ": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_FLOAT;
                    value = view.getTranslationZ();
                }
                break;
            }
            case "elevation": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    type = ValueType.TYPE_FLOAT;
                    value = view.getElevation();
                }
                break;
            }
            case "rotation": {
                type = ValueType.TYPE_FLOAT;
                value = view.getRotation();
                break;
            }
            case "rotationX": {
                type = ValueType.TYPE_FLOAT;
                value = view.getRotationX();
                break;
            }
            case "rotationY": {
                type = ValueType.TYPE_FLOAT;
                value = view.getRotationY();
                break;
            }
            case "scaleX": {
                type = ValueType.TYPE_FLOAT;
                value = view.getScaleX();
                break;
            }
            case "scaleY": {
                type = ValueType.TYPE_FLOAT;
                value = view.getScaleX();
                break;
            }
            case "fitsSystemWindows": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_BOOLEAN;
                    value = view.getFitsSystemWindows();
                } else {
                    type = ValueType.TYPE_BOOLEAN;
                    value = false;
                }
                break;
            }
            case "focusable": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isFocusable();
                break;
            }
            case "focusableInTouchMode": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isFocusableInTouchMode();
                break;
            }
            case "clickable": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isClickable();
                break;
            }
            case "longClickable": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isLongClickable();
                break;
            }
            case "contextClickable": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    type = ValueType.TYPE_BOOLEAN;
                    value = view.isContextClickable();
                }
                break;
            }
            case "saveEnabled": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isSaveEnabled();
                break;
            }
            case "duplicateParentState": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isDuplicateParentStateEnabled();
                break;
            }
            case "visibility": {
                type = ValueType.TYPE_INT;
                value = view.getVisibility();
                break;
            }
            case "layoutDirection": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    type = ValueType.TYPE_INT;
                    value = view.getLayoutDirection();
                }
                break;
            }
            case "drawingCacheQuality": {
                type = ValueType.TYPE_INT;
                value = view.getDrawingCacheQuality();
                break;
            }
            case "contentDescription": {
                type = ValueType.TYPE_CHARSEQUENCE;
                value = view.getContentDescription();
                break;
            }
            case "accessibilityTraversalBefore": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    type = ValueType.TYPE_INT;
                    value = view.getAccessibilityTraversalBefore();
                }
                break;
            }
            case "accessibilityTraversalAfter": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    type = ValueType.TYPE_INT;
                    value = view.getAccessibilityTraversalAfter();
                }
                break;
            }
            case "labelFor": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    type = ValueType.TYPE_INT;
                    value = view.getLabelFor();
                }
                break;
            }
            case "soundEffectsEnabled": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isSoundEffectsEnabled();
                break;
            }
            case "hapticFeedbackEnabled": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isHapticFeedbackEnabled();
                break;
            }
            case "scrollbars": {
                boolean horizontalScrollBarEnabled = view.isHorizontalScrollBarEnabled();
                boolean verticalScrollBarEnabled = view.isVerticalScrollBarEnabled();
                final int SCROLLBARS_NONE = 0x00000000;
                final int SCROLLBARS_HORIZONTAL = 0x00000100;
                final int SCROLLBARS_VERTICAL = 0x00000200;
                int scrollbars = SCROLLBARS_NONE;
                if (horizontalScrollBarEnabled) {
                    scrollbars |= SCROLLBARS_HORIZONTAL;
                }
                if (verticalScrollBarEnabled) {
                    scrollbars |= SCROLLBARS_VERTICAL;
                }
                type = ValueType.TYPE_INT;
                value = scrollbars;
                break;
            }
            case "fadeScrollbars": {
                type = ValueType.TYPE_BOOLEAN;
                value = view.isScrollbarFadingEnabled();
                break;
            }
            case "scrollbarFadeDuration": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = view.getScrollBarFadeDuration();
                }
                break;
            }
            case "scrollbarDefaultDelayBeforeFade": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = view.getScrollBarDefaultDelayBeforeFade();
                }
                break;
            }
            case "scrollbarSize": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_INT;
                    value = view.getScrollBarSize();
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
                boolean horizontalFadingEdgeEnabled = view.isHorizontalFadingEdgeEnabled();
                boolean verticalFadingEdgeEnabled = view.isVerticalFadingEdgeEnabled();
                final int FADING_EDGE_NONE = 0x00000000;
                final int FADING_EDGE_HORIZONTAL = 0x00001000;
                final int FADING_EDGE_VERTICAL = 0x00002000;
                int requiresFadingEdge = FADING_EDGE_NONE;
                if (horizontalFadingEdgeEnabled) {
                    requiresFadingEdge |= FADING_EDGE_HORIZONTAL;
                }
                if (verticalFadingEdgeEnabled) {
                    requiresFadingEdge |= FADING_EDGE_VERTICAL;
                }
                type = ValueType.TYPE_INT;
                value = requiresFadingEdge;
                break;
            }
            case "scrollbarStyle": {
                type = ValueType.TYPE_INT;
                value = view.getScrollBarStyle();
                break;
            }
            case "isScrollContainer": {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    type = ValueType.TYPE_BOOLEAN;
                    value = view.isScrollContainer();
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
        if (value != null) {
            //attrValue = new AttrValue(type, value);
        }
        return attrValue;
    }
}
