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

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.AttrUtil;
import com.hyh.prettyskin.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/10/15.
 */

public class NativeAttrParser implements XmlAttrParser {


    private List<XmlAttrParser> mXmlAttrParsers = new ArrayList<>();

    {
        mXmlAttrParsers.add(new ViewAttrParser());
        mXmlAttrParsers.add(new TextViewAttrParser());
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        for (XmlAttrParser xmlAttrParser : mXmlAttrParsers) {
            if (xmlAttrParser.isSupportAttrName(view, attrName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (set == null || TextUtils.isEmpty(attrName)) {
            return null;
        }
        AttrValue attrValue = null;
        for (XmlAttrParser xmlAttrParser : mXmlAttrParsers) {
            if (xmlAttrParser.isSupportAttrName(view, attrName)) {
                attrValue = xmlAttrParser.parse(view, set, attrName);
            }
        }
        if (attrValue != null) {
            return attrValue;
        }
        int type = ValueType.TYPE_NULL;
        Object value = null;
        Context context = view.getContext();
        int attributeCount = set.getAttributeCount();
        if (attributeCount > 0) {
            for (int index = 0; index < attributeCount; index++) {
                String attributeName = set.getAttributeName(index);
                if (TextUtils.equals(attributeName, attrName)) {
                    String attributeValue = set.getAttributeValue(index);
                    if (!TextUtils.isEmpty(attributeName)) {
                        if (attributeValue.startsWith("#")) {
                            value = Color.parseColor(attributeValue);
                        } else if (attributeValue.startsWith("@")) {
                            int attributeResourceValue = set.getAttributeResourceValue(index, 0);
                            if (attributeResourceValue != 0) {
                                String resourceTypeName = context.getResources().getResourceTypeName(attributeResourceValue);
                                if ("color".equalsIgnoreCase(resourceTypeName)) {
                                    value = context.getResources().getDrawable(attributeResourceValue);
                                } else if ("mipmap".equalsIgnoreCase(resourceTypeName) || "drawable".equalsIgnoreCase(resourceTypeName)) {
                                    value = context.getResources().getDrawable(attributeResourceValue);
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
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        value = view.getDefaultFocusHighlightEnabled();
                    }
                    break;
                }
                default: {
                    break;
                }
            }
            if (value != null) {
                attrValue = new AttrValue(type, value);
            }
            return attrValue;
        }
    }

    private static class TextViewAttrParser implements XmlAttrParser {

        private List<String> mSupportAttrNames = new ArrayList<>();

        {
            //mSupportAttrNames.add("editable");
            //mSupportAttrNames.add("inputMethod");
            //mSupportAttrNames.add("numeric");
            //mSupportAttrNames.add("phoneNumber");
            //mSupportAttrNames.add("autoText");
            //mSupportAttrNames.add("capitalize");
            //mSupportAttrNames.add("bufferType");
            //mSupportAttrNames.add("selectAllOnFocus");
            mSupportAttrNames.add("autoLink");
            mSupportAttrNames.add("linksClickable");
            mSupportAttrNames.add("drawableLeft");
            mSupportAttrNames.add("drawableTop");
            mSupportAttrNames.add("drawableRight");
            mSupportAttrNames.add("drawableBottom");
            //mSupportAttrNames.add("drawableStart");
            //mSupportAttrNames.add("drawableEnd");
            mSupportAttrNames.add("drawableTint");
            mSupportAttrNames.add("drawableTintMode");
            mSupportAttrNames.add("maxLines");
            mSupportAttrNames.add("minLines");
            mSupportAttrNames.add("maxHeight");
            mSupportAttrNames.add("minHeight");
            mSupportAttrNames.add("lines");
            mSupportAttrNames.add("height");
            mSupportAttrNames.add("maxEms");
            mSupportAttrNames.add("minEms");
            mSupportAttrNames.add("maxWidth");
            mSupportAttrNames.add("minWidth");
            mSupportAttrNames.add("ems");
            mSupportAttrNames.add("width");
            mSupportAttrNames.add("gravity");
            mSupportAttrNames.add("hint");
            mSupportAttrNames.add("text");
            mSupportAttrNames.add("scrollHorizontally");
            mSupportAttrNames.add("singleLine");
            mSupportAttrNames.add("ellipsize");
            mSupportAttrNames.add("marqueeRepeatLimit");
            mSupportAttrNames.add("includeFontPadding");
            mSupportAttrNames.add("cursorVisible");
            mSupportAttrNames.add("maxLength");
            mSupportAttrNames.add("textScaleX");
            mSupportAttrNames.add("freezesText");
            mSupportAttrNames.add("shadowColor");
            mSupportAttrNames.add("shadowDx");
            mSupportAttrNames.add("shadowDy");
            mSupportAttrNames.add("shadowRadius");
            mSupportAttrNames.add("enabled");
            mSupportAttrNames.add("textColorHighlight");
            mSupportAttrNames.add("textColor");
            mSupportAttrNames.add("textColorHint");
            mSupportAttrNames.add("textColorLink");
            mSupportAttrNames.add("textSize");
            mSupportAttrNames.add("typeface");
            mSupportAttrNames.add("textStyle");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
            mSupportAttrNames.add("digits");
        }

        @Override
        public boolean isSupportAttrName(View view, String attrName) {
            return view instanceof TextView && mSupportAttrNames.contains(attrName);
        }

        @Override
        public AttrValue parse(View view, AttributeSet set, String attrName) {
            Context context = view.getContext();
            if (context == null && !(view instanceof TextView)) {
                return null;
            }
            TextView textView = (TextView) view;
            AttrValue attrValue = null;
            int type = ValueType.TYPE_NULL;
            Object value = null;
            switch (attrName) {
                case "editable": {
                    //TODO 暂不实现
                    break;
                }
                case "inputMethod": {
                    //TODO 暂不实现
                    break;
                }
                case "numeric": {
                    //TODO 暂不实现
                    break;
                }
                case "digits": {
                    //TODO 暂不实现
                    break;
                }
                case "phoneNumber": {
                    //TODO 暂不实现
                    break;
                }
                case "autoText": {
                    //TODO 暂不实现
                    break;
                }
                case "capitalize": {
                    //TODO 暂不实现
                    break;
                }
                case "bufferType": {
                    //TODO 暂不实现
                    break;
                }
                case "selectAllOnFocus": {
                    //TODO 暂不实现
                    break;
                }
                case "autoLink": {
                    type = ValueType.TYPE_INT;
                    value = textView.getAutoLinkMask();
                    break;
                }
                case "linksClickable": {
                    type = ValueType.TYPE_BOOLEAN;
                    value = textView.getLinksClickable();
                    break;
                }
                case "drawableLeft": {
                    type = ValueType.TYPE_DRAWABLE;
                    value = textView.getCompoundDrawables()[0];
                    break;
                }
                case "drawableTop": {
                    type = ValueType.TYPE_DRAWABLE;
                    value = textView.getCompoundDrawables()[1];
                    break;
                }
                case "drawableRight": {
                    type = ValueType.TYPE_DRAWABLE;
                    value = textView.getCompoundDrawables()[2];
                    break;
                }
                case "drawableBottom": {
                    type = ValueType.TYPE_DRAWABLE;
                    value = textView.getCompoundDrawables()[3];
                    break;
                }
                case "drawableStart": {
                    //TODO 暂不实现
                    break;
                }
                case "drawableEnd": {
                    //TODO 暂不实现
                    break;
                }
                case "drawableTint": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        type = ValueType.TYPE_COLOR_STATE_LIST;
                        value = textView.getCompoundDrawableTintList();
                    }
                    break;
                }
                case "drawableTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        type = ValueType.TYPE_OBJECT;
                        value = textView.getCompoundDrawableTintMode();
                    }
                    break;
                }
                case "maxLines": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMaxLines();
                    }
                    break;
                }
                case "minLines": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMinLines();
                    }
                    break;
                }
                case "maxHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMaxHeight();
                    } else {
                        try {
                            type = ValueType.TYPE_INT;
                            value = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                        } catch (Exception e) {
                            value = Integer.MAX_VALUE;
                        }
                    }
                    break;
                }
                case "minHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMinHeight();
                    } else {
                        try {
                            type = ValueType.TYPE_INT;
                            value = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                        } catch (Exception e) {
                            value = 0;
                        }
                    }
                    break;
                }
                case "lines": {
                    int maxLines;
                    int minLines;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxLines = textView.getMaxLines();
                    } else {
                        try {
                            maxLines = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                        } catch (Exception e) {
                            maxLines = Integer.MAX_VALUE;
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minLines = textView.getMinLines();
                    } else {
                        try {
                            minLines = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                        } catch (Exception e) {
                            minLines = 0;
                        }
                    }
                    if (maxLines != -1 && maxLines == minLines) {
                        type = ValueType.TYPE_INT;
                        value = maxLines;
                    }
                    break;
                }
                case "height": {
                    int maxHeight;
                    int minHeight;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxHeight = textView.getMaxHeight();
                    } else {
                        try {
                            maxHeight = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                        } catch (Exception e) {
                            maxHeight = Integer.MAX_VALUE;
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minHeight = textView.getMinHeight();
                    } else {
                        try {
                            minHeight = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                        } catch (Exception e) {
                            minHeight = 0;
                        }
                    }
                    if (maxHeight != -1 && maxHeight == minHeight) {
                        type = ValueType.TYPE_INT;
                        value = maxHeight;
                    }
                    break;
                }
                case "maxEms": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMaxEms();
                    } else {
                        try {
                            type = ValueType.TYPE_INT;
                            value = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            value = Integer.MAX_VALUE;
                        }
                    }
                    break;
                }
                case "minEms": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMinEms();
                    } else {
                        try {
                            type = ValueType.TYPE_INT;
                            value = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            value = 0;
                        }
                    }
                    break;
                }
                case "maxWidth": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMaxWidth();
                    } else {
                        try {
                            type = ValueType.TYPE_INT;
                            value = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            value = Integer.MAX_VALUE;
                        }
                    }
                    break;
                }
                case "minWidth": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        type = ValueType.TYPE_INT;
                        value = textView.getMinWidth();
                    } else {
                        try {
                            type = ValueType.TYPE_INT;
                            value = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            value = 0;
                        }
                    }
                    break;
                }
                case "ems": {
                    int maxEms;
                    int minEms;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxEms = textView.getMaxEms();
                    } else {
                        try {
                            maxEms = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            maxEms = Integer.MAX_VALUE;
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minEms = textView.getMinEms();
                    } else {
                        try {
                            minEms = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            minEms = 0;
                        }
                    }
                    if (maxEms != -1 && maxEms == minEms) {
                        type = ValueType.TYPE_INT;
                        value = maxEms;
                    }
                    break;
                }
                case "width": {
                    int maxWidth;
                    int minWidth;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxWidth = textView.getMaxWidth();
                    } else {
                        try {
                            maxWidth = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            maxWidth = Integer.MAX_VALUE;
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minWidth = textView.getMinWidth();
                    } else {
                        try {
                            minWidth = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            minWidth = 0;
                        }
                    }
                    if (maxWidth != -1 && maxWidth == minWidth) {
                        type = ValueType.TYPE_INT;
                        value = maxWidth;
                    }
                    break;
                }
                case "gravity": {
                    type = ValueType.TYPE_INT;
                    value = textView.getGravity();
                    break;
                }
                case "hint": {
                    type = ValueType.TYPE_CHARSEQUENCE;
                    value = textView.getHint();
                    break;
                }
                case "text": {
                    type = ValueType.TYPE_CHARSEQUENCE;
                    value = textView.getText();
                    break;
                }
                case "scrollHorizontally": {
                    try {
                        type = ValueType.TYPE_BOOLEAN;
                        value = (boolean) ReflectUtil.getFieldValue(textView, "mHorizontallyScrolling");
                    } catch (Exception e) {
                        value = false;
                    }
                    break;
                }
                case "singleLine": {
                    try {
                        type = ValueType.TYPE_BOOLEAN;
                        value = (boolean) ReflectUtil.getFieldValue(textView, "mSingleLine");
                    } catch (Exception e) {
                        value = false;
                    }
                    break;
                }
                case "ellipsize": {
                    type = ValueType.TYPE_OBJECT;
                    value = textView.getEllipsize();
                    break;
                }
                case "marqueeRepeatLimit": {
                    type = ValueType.TYPE_INT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getMarqueeRepeatLimit();
                    } else {
                        try {
                            value = (int) ReflectUtil.getFieldValue(textView, "mMarqueeRepeatLimit");
                        } catch (Exception e) {
                            value = 3;
                        }
                    }
                    break;
                }
                case "includeFontPadding": {
                    type = ValueType.TYPE_BOOLEAN;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getIncludeFontPadding();
                    } else {
                        try {
                            value = (boolean) ReflectUtil.getFieldValue(textView, "mIncludePad");
                        } catch (Exception e) {
                            value = true;
                        }
                    }
                    break;
                }
                case "cursorVisible": {
                    type = ValueType.TYPE_BOOLEAN;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.isCursorVisible();
                    } else {
                        try {
                            Object editor = ReflectUtil.getFieldValue(textView, "mEditor");
                            if (editor != null) {
                                value = (boolean) ReflectUtil.getFieldValue(editor, "mCursorVisible");
                            } else {
                                value = true;
                            }
                        } catch (Exception e) {
                            value = true;
                        }
                    }
                    break;
                }
                case "maxLength": {
                    type = ValueType.TYPE_OBJECT;
                    value = textView.getFilters();
                    break;
                }
                case "textScaleX": {
                    type = ValueType.TYPE_FLOAT;
                    value = textView.getTextScaleX();
                    break;
                }
                case "freezesText": {
                    type = ValueType.TYPE_BOOLEAN;
                    value = textView.getFreezesText();
                    break;
                }
                case "shadowColor": {
                    type = ValueType.TYPE_COLOR_INT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getShadowColor();
                    } else {
                        try {
                            value = (int) ReflectUtil.getFieldValue(textView, "mShadowColor");
                        } catch (Exception e) {
                            value = 0;
                        }
                    }
                    break;
                }
                case "shadowDx": {
                    type = ValueType.TYPE_FLOAT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getShadowDx();
                    } else {
                        try {
                            value = (float) ReflectUtil.getFieldValue(textView, "mShadowDx");
                        } catch (Exception e) {
                            value = 0.0f;
                        }
                    }
                    break;
                }
                case "shadowDy": {
                    type = ValueType.TYPE_FLOAT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getShadowDy();
                    } else {
                        try {
                            value = (float) ReflectUtil.getFieldValue(textView, "mShadowDy");
                        } catch (Exception e) {
                            value = 0.0f;
                        }
                    }
                    break;
                }
                case "shadowRadius": {
                    type = ValueType.TYPE_FLOAT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getShadowRadius();
                    } else {
                        try {
                            value = (float) ReflectUtil.getFieldValue(textView, "mShadowRadius");
                        } catch (Exception e) {
                            value = 0.0f;
                        }
                    }
                    break;
                }
                case "enabled": {
                    type = ValueType.TYPE_BOOLEAN;
                    value = textView.isEnabled();
                    break;
                }
                case "textColorHighlight": {
                    type = ValueType.TYPE_COLOR_INT;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        value = textView.getHighlightColor();
                    } else {
                        try {
                            value = (int) ReflectUtil.getFieldValue(textView, "mHighlightColor");
                        } catch (Exception e) {
                            value = 0x6633B5E5;
                        }
                    }
                    break;
                }
                case "textColor": {
                    type = ValueType.TYPE_COLOR_STATE_LIST;
                    value = textView.getTextColors();
                    break;
                }
                case "textColorHint": {
                    type = ValueType.TYPE_COLOR_STATE_LIST;
                    value = textView.getHintTextColors();
                    break;
                }
                case "textColorLink": {
                    type = ValueType.TYPE_COLOR_STATE_LIST;
                    value = textView.getLinkTextColors();
                    break;
                }
                case "textSize": {
                    type = ValueType.TYPE_FLOAT;
                    value = textView.getTextSize();
                    break;
                }
                case "typeface": {
                    type = ValueType.TYPE_OBJECT;
                    value = textView.getTypeface();
                    break;
                }
                case "textStyle": {
                    AttrUtil.getAttrValueFromAttributeSet(context, set, attrName);
                    break;
                }
                /*case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
                    break;
                }*/
            }
            return attrValue;
        }
    }
}
