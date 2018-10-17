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
    public Object parse(View view, AttributeSet set, String attrName) {
        if (set == null || TextUtils.isEmpty(attrName)) {
            return null;
        }
        Object attrValue = null;
        for (XmlAttrParser xmlAttrParser : mXmlAttrParsers) {
            if (xmlAttrParser.isSupportAttrName(view, attrName)) {
                attrValue = xmlAttrParser.parse(view, set, attrName);
            }
        }
        if (attrValue != null) {
            return attrValue;
        }
        Context context = view.getContext();
        int attributeCount = set.getAttributeCount();
        if (attributeCount > 0) {
            for (int index = 0; index < attributeCount; index++) {
                String attributeName = set.getAttributeName(index);
                if (TextUtils.equals(attributeName, attrName)) {
                    String attributeValue = set.getAttributeValue(index);
                    if (!TextUtils.isEmpty(attributeName)) {
                        if (attributeValue.startsWith("#")) {
                            attrValue = Color.parseColor(attributeValue);
                        } else if (attributeValue.startsWith("@")) {
                            int attributeResourceValue = set.getAttributeResourceValue(index, 0);
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
        public Object parse(View view, AttributeSet set, String attrName) {
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
                    attrValue = scrollbars;
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
                    attrValue = requiresFadingEdge;
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
        public Object parse(View view, AttributeSet set, String attrName) {
            Context context = view.getContext();
            if (context == null && !(view instanceof TextView)) {
                return null;
            }
            TextView textView = (TextView) view;
            Object attrValue = null;
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
                    attrValue = textView.getAutoLinkMask();
                    break;
                }
                case "linksClickable": {
                    attrValue = textView.getLinksClickable();
                    break;
                }
                case "drawableLeft": {
                    attrValue = textView.getCompoundDrawables()[0];
                    break;
                }
                case "drawableTop": {
                    attrValue = textView.getCompoundDrawables()[1];
                    break;
                }
                case "drawableRight": {
                    attrValue = textView.getCompoundDrawables()[2];
                    break;
                }
                case "drawableBottom": {
                    attrValue = textView.getCompoundDrawables()[3];
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
                        attrValue = textView.getCompoundDrawableTintList();
                    }
                    break;
                }
                case "drawableTintMode": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        attrValue = textView.getCompoundDrawableTintMode();
                    }
                    break;
                }
                case "maxLines": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMaxLines();
                    }
                    break;
                }
                case "minLines": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMinLines();
                    }
                    break;
                }
                case "maxHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMaxHeight();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "minHeight": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMinHeight();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "lines": {
                    int maxLines = Integer.MAX_VALUE;
                    int minLines = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxLines = textView.getMaxLines();
                    } else {
                        try {
                            maxLines = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minLines = textView.getMinLines();
                    } else {
                        try {
                            minLines = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (maxLines != -1 && maxLines == minLines) {
                        attrValue = maxLines;
                    }
                    break;
                }
                case "height": {
                    int maxHeight = Integer.MAX_VALUE;
                    int minHeight = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxHeight = textView.getMaxHeight();
                    } else {
                        try {
                            maxHeight = (int) ReflectUtil.getFieldValue(textView, "mMaximum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minHeight = textView.getMinHeight();
                    } else {
                        try {
                            minHeight = (int) ReflectUtil.getFieldValue(textView, "mMinimum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (maxHeight != -1 && maxHeight == minHeight) {
                        attrValue = maxHeight;
                    }
                    break;
                }
                case "maxEms": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMaxEms();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "minEms": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMinEms();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "maxWidth": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMaxWidth();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "minWidth": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMinWidth();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "ems": {
                    int maxEms = Integer.MAX_VALUE;
                    int minEms = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxEms = textView.getMaxEms();
                    } else {
                        try {
                            maxEms = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minEms = textView.getMinEms();
                    } else {
                        try {
                            minEms = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (maxEms != -1 && maxEms == minEms) {
                        attrValue = maxEms;
                    }
                    break;
                }
                case "width": {
                    int maxWidth = Integer.MAX_VALUE;
                    int minWidth = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        maxWidth = textView.getMaxWidth();
                    } else {
                        try {
                            maxWidth = (int) ReflectUtil.getFieldValue(textView, "mMaxWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        minWidth = textView.getMinWidth();
                    } else {
                        try {
                            minWidth = (int) ReflectUtil.getFieldValue(textView, "mMinWidth");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (maxWidth != -1 && maxWidth == minWidth) {
                        attrValue = maxWidth;
                    }
                    break;
                }
                case "gravity": {
                    attrValue = textView.getGravity();
                    break;
                }
                case "hint": {
                    attrValue = textView.getHint();
                    break;
                }
                case "text": {
                    attrValue = textView.getText();
                    break;
                }
                case "scrollHorizontally": {
                    try {
                        attrValue = (boolean) ReflectUtil.getFieldValue(textView, "mHorizontallyScrolling");
                    } catch (Exception e) {
                        attrValue = false;
                    }
                    break;
                }
                case "singleLine": {
                    try {
                        attrValue = (boolean) ReflectUtil.getFieldValue(textView, "mSingleLine");
                    } catch (Exception e) {
                        attrValue = false;
                    }
                    break;
                }
                case "ellipsize": {
                    attrValue = textView.getEllipsize();
                    break;
                }
                case "marqueeRepeatLimit": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getMarqueeRepeatLimit();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mMarqueeRepeatLimit");
                        } catch (Exception e) {
                            attrValue = 3;
                        }
                    }
                    break;
                }
                case "includeFontPadding": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getIncludeFontPadding();
                    } else {
                        try {
                            attrValue = (boolean) ReflectUtil.getFieldValue(textView, "mIncludePad");
                        } catch (Exception e) {
                            attrValue = true;
                        }
                    }
                    break;
                }
                case "cursorVisible": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.isCursorVisible();
                    } else {
                        try {
                            Object editor = ReflectUtil.getFieldValue(textView, "mEditor");
                            if (editor != null) {
                                attrValue = (boolean) ReflectUtil.getFieldValue(editor, "mCursorVisible");
                            }
                        } catch (Exception e) {
                            attrValue = true;
                        }
                    }
                    break;
                }
                case "maxLength": {
                    attrValue = textView.getFilters();
                    break;
                }
                case "textScaleX": {
                    attrValue = textView.getTextScaleX();
                    break;
                }
                case "freezesText": {
                    attrValue = textView.getFreezesText();
                    break;
                }
                case "shadowColor": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getShadowColor();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mShadowColor");
                        } catch (Exception e) {
                            attrValue = 0;
                        }
                    }
                    break;
                }
                case "shadowDx": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getShadowDx();
                    } else {
                        try {
                            attrValue = (float) ReflectUtil.getFieldValue(textView, "mShadowDx");
                        } catch (Exception e) {
                            attrValue = 0.0f;
                        }
                    }
                    break;
                }
                case "shadowDy": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getShadowDy();
                    } else {
                        try {
                            attrValue = (float) ReflectUtil.getFieldValue(textView, "mShadowDy");
                        } catch (Exception e) {
                            attrValue = 0.0f;
                        }
                    }
                    break;
                }
                case "shadowRadius": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getShadowRadius();
                    } else {
                        try {
                            attrValue = (float) ReflectUtil.getFieldValue(textView, "mShadowRadius");
                        } catch (Exception e) {
                            attrValue = 0.0f;
                        }
                    }
                    break;
                }
                case "enabled": {
                    attrValue = textView.isEnabled();
                    break;
                }
                case "textColorHighlight": {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        attrValue = textView.getHighlightColor();
                    } else {
                        try {
                            attrValue = (int) ReflectUtil.getFieldValue(textView, "mHighlightColor");
                        } catch (Exception e) {
                            attrValue = 0x6633B5E5;
                        }
                    }
                    break;
                }
                case "textColor": {
                    attrValue = textView.getTextColors();
                    break;
                }
                case "textColorHint": {
                    attrValue = textView.getHintTextColors();
                    break;
                }
                case "textColorLink": {
                    attrValue = textView.getLinkTextColors();
                    break;
                }
                case "textSize": {
                    attrValue = textView.getTextSize();
                    break;
                }
                case "typeface": {
                    attrValue = textView.getTypeface();
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
