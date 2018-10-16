package com.hyh.prettyskin.core.parser;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/15.
 */

public class NativeAttrParser implements XmlAttrParser {


    private List<XmlAttrParser> mXmlAttrParsers = new ArrayList<>();

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return false;
    }

    @Override
    public Object parse(View view, AttributeSet attrs, String attrName) {
        if (attrs == null || TextUtils.isEmpty(attrName)) {
            return null;
        }
        Context context = view.getContext();
        Object attrValue = null;
        boolean isAttrParsed = false;
        if (TextUtils.equals(attrName, "background")) {
            Drawable drawable = view.getBackground();
            if (drawable != null && drawable instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) drawable;
                attrValue = colorDrawable.getColor();
            } else {
                attrValue = drawable;
            }
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

    private static class ViewAttrParser implements XmlAttrParser {

        @Override
        public boolean isSupportAttrName(View view, String attrName) {
            return false;
        }

        @Override
        public Object parse(View view, AttributeSet attrs, String attrName) {
            Object attrValue = null;
            switch (attrName) {
                case "background": {
                    Drawable drawable = view.getBackground();
                    if (drawable != null && drawable instanceof ColorDrawable) {
                        ColorDrawable colorDrawable = (ColorDrawable) drawable;
                        attrValue = colorDrawable.getColor();
                    } else {
                        attrValue = drawable;
                    }
                    break;
                }
                case "padding": {
                    //TODO 暂不实现
                    break;
                }
                case "paddingLeft": {
                    attrValue = view.getPaddingLeft();
                    break;
                }
                case "paddingTop": {
                    attrValue = view.getPaddingTop();
                    break;
                }
                case "paddingRight": {
                    attrValue = view.getPaddingRight();
                    break;
                }
                case "paddingBottom": {
                    attrValue = view.getPaddingBottom();
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
                    attrValue = view.getScrollX();
                    break;
                }
                case "scrollY": {
                    attrValue = view.getScaleY();
                    break;
                }
                case "alpha": {
                    attrValue = view.getAlpha();
                    break;
                }
                case "transformPivotX": {
                    attrValue = view.getPivotX();
                    break;
                }
                case "transformPivotY": {
                    attrValue = view.getPivotY();
                    break;
                }
                case "translationX": {
                    attrValue = view.getTranslationX();
                    break;
                }
                case "translationY": {
                    attrValue = view.getTranslationY();
                    break;
                }
                case "translationZ": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getTranslationZ();
                    }
                    break;
                }
                case "elevation": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getElevation();
                    }
                    break;
                }
                case "rotation": {
                    attrValue = view.getRotation();
                    break;
                }
                case "rotationX": {
                    attrValue = view.getRotationX();
                    break;
                }
                case "rotationY": {
                    attrValue = view.getRotationY();
                    break;
                }
                case "scaleX": {
                    attrValue = view.getScaleX();
                    break;
                }
                case "scaleY": {
                    attrValue = view.getScaleX();
                    break;
                }
                case "fitsSystemWindows": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getFitsSystemWindows();
                    } else {
                        attrValue = false;
                    }
                    break;
                }
                case "focusable": {
                    attrValue = view.isFocusable();
                    break;
                }
                case "focusableInTouchMode": {
                    attrValue = view.isFocusableInTouchMode();
                    break;
                }
                case "clickable": {
                    attrValue = view.isClickable();
                    break;
                }
                case "longClickable": {
                    attrValue = view.isLongClickable();
                    break;
                }
                case "contextClickable": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = view.isContextClickable();
                    }
                    break;
                }
                case "saveEnabled": {
                    attrValue = view.isSaveEnabled();
                    break;
                }
                case "duplicateParentState": {
                    attrValue = view.isDuplicateParentStateEnabled();
                    break;
                }
                case "visibility": {
                    attrValue = view.getVisibility();
                    break;
                }
                case "layoutDirection": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        attrValue = view.getLayoutDirection();
                    }
                    break;
                }
                case "drawingCacheQuality": {
                    attrValue = view.getDrawingCacheQuality();
                    break;
                }
                case "contentDescription": {
                    attrValue = view.getContentDescription();
                    break;
                }
                case "accessibilityTraversalBefore": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        attrValue = view.getAccessibilityTraversalBefore();
                    }
                    break;
                }
                case "accessibilityTraversalAfter": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        attrValue = view.getAccessibilityTraversalAfter();
                    }
                    break;
                }
                case "labelFor": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        attrValue = view.getLabelFor();
                    }
                    break;
                }
                case "soundEffectsEnabled": {
                    attrValue = view.isSoundEffectsEnabled();
                    break;
                }
                case "hapticFeedbackEnabled": {
                    attrValue = view.isHapticFeedbackEnabled();
                    break;
                }
                case "scrollbars": {
                    boolean horizontalScrollBarEnabled = view.isHorizontalScrollBarEnabled();
                    boolean verticalScrollBarEnabled = view.isVerticalScrollBarEnabled();
                    break;
                }
                case "fadeScrollbars": {
                    attrValue = view.isScrollbarFadingEnabled();
                    break;
                }
                case "scrollbarFadeDuration": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getScrollBarFadeDuration();
                    }
                    break;
                }
                case "scrollbarDefaultDelayBeforeFade": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getScrollBarDefaultDelayBeforeFade();
                    }
                    break;
                }
                case "scrollbarSize": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getScrollBarSize();
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
                    break;
                }
                case "scrollbarStyle": {
                    attrValue = view.getScrollBarStyle();
                    break;
                }
                case "isScrollContainer": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.isScrollContainer();
                    }
                    break;
                }
                case "keepScreenOn": {
                    attrValue = view.getKeepScreenOn();
                    break;
                }
                case "filterTouchesWhenObscured": {
                    attrValue = view.getFilterTouchesWhenObscured();
                    break;
                }
                case "nextFocusLeft": {
                    attrValue = view.getNextFocusLeftId();
                    break;
                }
                case "nextFocusRight": {
                    attrValue = view.getNextFocusRightId();
                    break;
                }
                case "nextFocusUp": {
                    attrValue = view.getNextFocusUpId();
                    break;
                }
                case "nextFocusDown": {
                    attrValue = view.getNextFocusDownId();
                    break;
                }
                case "nextFocusForward": {
                    attrValue = view.getNextFocusForwardId();
                    break;
                }
                case "nextClusterForward": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attrValue = view.getNextClusterForwardId();
                    }
                    break;
                }
                case "minWidth": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getMinimumWidth();
                    }
                    break;
                }
                case "minHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getMinimumHeight();
                    }
                    break;
                }
                case "overScrollMode": {
                    attrValue = view.getOverScrollMode();
                    break;
                }
                case "verticalScrollbarPosition": {
                    attrValue = view.getVerticalScrollbarPosition();
                    break;
                }
                case "layerType": {
                    attrValue = view.getLayerType();
                    break;
                }
                case "textDirection": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        attrValue = view.getTextDirection();
                    }
                    break;
                }
                case "textAlignment": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        attrValue = view.getTextAlignment();
                    }
                    break;
                }
                case "importantForAccessibility": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = view.getImportantForAccessibility();
                    }
                    break;
                }
                case "accessibilityLiveRegion": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        attrValue = view.getAccessibilityLiveRegion();
                    }
                    break;
                }
                case "transitionName": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getTransitionName();
                    }
                    break;
                }
                case "nestedScrollingEnabled": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.isNestedScrollingEnabled();
                    }
                    break;
                }
                case "stateListAnimator": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getStateListAnimator();
                    }
                    break;
                }
                case "backgroundTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getBackgroundTintList();
                    }
                    break;
                }
                case "backgroundTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getBackgroundTintMode();
                    }
                    break;
                }
                case "outlineProvider": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        attrValue = view.getOutlineProvider();
                    }
                    break;
                }
                case "foreground": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = view.getForeground();
                    }
                    break;
                }
                case "foregroundGravity": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = view.getForegroundGravity();
                    }
                    break;
                }
                case "foregroundTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = view.getForegroundTintMode();
                    }
                    break;
                }
                case "foregroundTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = view.getForegroundTintList();
                    }
                    break;
                }
                case "foregroundInsidePadding": {
                    //TODO 暂未实现
                    break;
                }
                case "scrollIndicators": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = view.getScrollIndicators();
                    }
                    break;
                }
                case "pointerIcon": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        attrValue = view.getPointerIcon();
                    }
                    break;
                }
                case "forceHasOverlappingRendering": {
                    //TODO 暂未实现
                    break;
                }
                case "tooltipText": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attrValue = view.getTooltipText();
                    }
                    break;
                }
                case "keyboardNavigationCluster": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attrValue = view.isKeyboardNavigationCluster();
                    }
                    break;
                }
                case "focusedByDefault": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attrValue = view.isFocusedByDefault();
                    }
                    break;
                }
                case "autofillHints": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attrValue = view.getAutofillHints();
                    }
                    break;
                }
                case "importantForAutofill": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        attrValue = view.isImportantForAutofill();
                    }
                    break;
                }
                case "defaultFocusHighlightEnabled": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        attrValue = view.getDefaultFocusHighlightEnabled();
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            return attrValue;
        }
    }
}
