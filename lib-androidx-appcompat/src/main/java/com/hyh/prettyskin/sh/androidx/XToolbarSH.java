package com.hyh.prettyskin.sh.androidx;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.sh.ViewGroupSH;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;

/**
 * @author Administrator
 * @description
 * @data 2019/5/7
 */

public class XToolbarSH extends ViewGroupSH {

    private Class<?> mStyleableClass;
    private String mStyleableName;
    private int[] mAttrs;

    private final List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("titleTextAppearance");
        mSupportAttrNames.add("subtitleTextAppearance");
        //mSupportAttrNames.add("navigationButtonStyle");
        mSupportAttrNames.add("gravity");
        mSupportAttrNames.add("buttonGravity");

        mSupportAttrNames.add("titleMargin");
        mSupportAttrNames.add("titleMarginStart");
        mSupportAttrNames.add("titleMarginEnd");
        mSupportAttrNames.add("titleMarginTop");
        mSupportAttrNames.add("titleMarginBottom");

        mSupportAttrNames.add("maxButtonHeight");
        mSupportAttrNames.add("contentInsetStart");
        mSupportAttrNames.add("contentInsetEnd");
        mSupportAttrNames.add("contentInsetLeft");
        mSupportAttrNames.add("contentInsetRight");
        mSupportAttrNames.add("contentInsetStartWithNavigation");
        mSupportAttrNames.add("contentInsetEndWithActions");

        mSupportAttrNames.add("collapseIcon");
        mSupportAttrNames.add("collapseContentDescription");
        mSupportAttrNames.add("title");
        mSupportAttrNames.add("subtitle");
        mSupportAttrNames.add("popupTheme");
        mSupportAttrNames.add("navigationIcon");
        mSupportAttrNames.add("navigationContentDescription");
        mSupportAttrNames.add("logo");
        mSupportAttrNames.add("logoDescription");
        mSupportAttrNames.add("titleTextColor");
        mSupportAttrNames.add("subtitleTextColor");
    }


    public XToolbarSH() {
        this(ViewAttrUtil.getStyleAttr("androidx.appcompat.R", "toolbarStyle"));
    }

    public XToolbarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public XToolbarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }


    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof Toolbar && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("androidx.appcompat.R$styleable");
            mStyleableName = "Toolbar";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }

        mTypedArray = context.obtainStyledAttributes(
                set,
                mAttrs,
                mDefStyleAttr,
                mDefStyleRes);
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
        } else {
            Context context = attrValue.getThemeContext();
            int type = attrValue.getType();
            if (context == null && type == ValueType.TYPE_REFERENCE) {
                return;
            }
            Toolbar toolbar = (Toolbar) view;
            switch (attrName) {
                case "titleTextAppearance": {
                    int titleTextAppearance = attrValue.getTypedValue(int.class, 0);
                    if (titleTextAppearance != 0) {
                        TextView titleTextView = Reflect.from(Toolbar.class).filed("mTitleTextView", TextView.class).get(toolbar);
                        if (titleTextView == null) {
                            titleTextView = new TextView(toolbar.getContext());
                            titleTextView.setSingleLine();
                            titleTextView.setEllipsize(TextUtils.TruncateAt.END);
                            titleTextView.setTextAppearance(context, titleTextAppearance);
                            int titleTextColor = Reflect.from(Toolbar.class).filed("mTitleTextColor", int.class).get(toolbar);
                            if (titleTextColor != 0) {
                                titleTextView.setTextColor(titleTextColor);
                            }
                            Reflect.from(Toolbar.class).filed("mTitleTextView", TextView.class).set(toolbar, titleTextView);
                        } else {
                            titleTextView.setTextAppearance(context, titleTextAppearance);
                        }
                    }
                    break;
                }
                case "subtitleTextAppearance": {
                    int titleTextAppearance = attrValue.getTypedValue(int.class, 0);
                    if (titleTextAppearance != 0) {
                        TextView subTitleTextView = Reflect.from(Toolbar.class).filed("mSubtitleTextView", TextView.class).get(toolbar);
                        if (subTitleTextView == null) {
                            subTitleTextView = new TextView(toolbar.getContext());
                            subTitleTextView.setSingleLine();
                            subTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                            subTitleTextView.setTextAppearance(context, titleTextAppearance);
                            int subTitleTextColor = Reflect.from(Toolbar.class).filed("mSubTitleTextColor", int.class).get(toolbar);
                            if (subTitleTextColor != 0) {
                                subTitleTextView.setTextColor(subTitleTextColor);
                            }
                            Reflect.from(Toolbar.class).filed("mSubtitleTextView", TextView.class).set(toolbar, subTitleTextView);
                        } else {
                            subTitleTextView.setTextAppearance(context, titleTextAppearance);
                        }
                    }
                    break;
                }
                case "gravity": {
                    int gravity = attrValue.getTypedValue(int.class, Gravity.START | Gravity.CENTER_VERTICAL);
                    Reflect.from(Toolbar.class).filed("mGravity", int.class).set(toolbar, gravity);
                    toolbar.requestLayout();
                    break;
                }
                case "buttonGravity": {
                    int gravity = attrValue.getTypedValue(int.class, Gravity.TOP);
                    Reflect.from(Toolbar.class).filed("mButtonGravity", int.class).set(toolbar, gravity);
                    toolbar.requestLayout();
                    break;
                }
                case "titleMargin": {
                    int titleMargin = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setTitleMargin(titleMargin, titleMargin, titleMargin, titleMargin);
                    } else {
                        Reflect.from(Toolbar.class).filed("mTitleMarginStart", int.class).set(toolbar, titleMargin);
                        Reflect.from(Toolbar.class).filed("mTitleMarginTop", int.class).set(toolbar, titleMargin);
                        Reflect.from(Toolbar.class).filed("mTitleMarginEnd", int.class).set(toolbar, titleMargin);
                        Reflect.from(Toolbar.class).filed("mTitleMarginBottom", int.class).set(toolbar, titleMargin);
                        toolbar.requestLayout();
                    }
                    break;
                }
                case "titleMarginStart": {
                    int titleMarginStart = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setTitleMarginStart(titleMarginStart);
                    } else {
                        Reflect.from(Toolbar.class).filed("mTitleMarginStart", int.class).set(toolbar, titleMarginStart);
                        toolbar.requestLayout();
                    }
                    break;
                }
                case "titleMarginEnd": {
                    int titleMarginEnd = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setTitleMarginEnd(titleMarginEnd);
                    } else {
                        Reflect.from(Toolbar.class).filed("mTitleMarginEnd", int.class).set(toolbar, titleMarginEnd);
                        toolbar.requestLayout();
                    }
                    break;
                }
                case "titleMarginTop": {
                    int titleMarginTop = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setTitleMarginTop(titleMarginTop);
                    } else {
                        Reflect.from(Toolbar.class).filed("mTitleMarginTop", int.class).set(toolbar, titleMarginTop);
                        toolbar.requestLayout();
                    }
                    break;
                }
                case "titleMarginBottom": {
                    int titleMarginBottom = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setTitleMarginBottom(titleMarginBottom);
                    } else {
                        Reflect.from(Toolbar.class).filed("mTitleMarginBottom", int.class).set(toolbar, titleMarginBottom);
                        toolbar.requestLayout();
                    }
                    break;
                }
                case "maxButtonHeight": {
                    int maxButtonHeight = attrValue.getTypedValue(int.class, 0);
                    Reflect.from(Toolbar.class).filed("mMaxButtonHeight", int.class).set(toolbar, maxButtonHeight);
                    toolbar.requestLayout();
                    break;
                }
                case "contentInsetStart": {
                    int contentInsetStart = attrValue.getTypedValue(int.class, 0);
                    toolbar.setContentInsetsRelative(contentInsetStart, toolbar.getContentInsetEnd());
                    break;
                }
                case "contentInsetEnd": {
                    int contentInsetEnd = attrValue.getTypedValue(int.class, 0);
                    toolbar.setContentInsetsRelative(toolbar.getContentInsetStart(), contentInsetEnd);
                    break;
                }
                case "contentInsetLeft": {
                    int contentInsetLeft = attrValue.getTypedValue(int.class, 0);
                    toolbar.setContentInsetsAbsolute(contentInsetLeft, toolbar.getContentInsetRight());
                    break;
                }
                case "contentInsetRight": {
                    int contentInsetRight = attrValue.getTypedValue(int.class, 0);
                    toolbar.setContentInsetsAbsolute(toolbar.getContentInsetLeft(), contentInsetRight);
                    break;
                }
                case "contentInsetStartWithNavigation": {
                    int contentInsetStartWithNavigation = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setContentInsetStartWithNavigation(contentInsetStartWithNavigation);
                    } else {
                        Reflect.from(Toolbar.class).filed("mContentInsetStartWithNavigation", int.class).set(toolbar, contentInsetStartWithNavigation);
                        if (toolbar.getNavigationIcon() != null) {
                            toolbar.requestLayout();
                        }
                    }
                    break;
                }
                case "contentInsetEndWithActions": {
                    int contentInsetEndWithActions = attrValue.getTypedValue(int.class, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toolbar.setContentInsetEndWithActions(contentInsetEndWithActions);
                    } else {
                        Reflect.from(Toolbar.class).filed("mContentInsetEndWithActions", int.class).set(toolbar, contentInsetEndWithActions);
                        if (toolbar.getNavigationIcon() != null) {
                            toolbar.requestLayout();
                        }
                    }
                    break;
                }
                case "collapseIcon": {
                    Drawable collapseIcon = attrValue.getTypedValue(Drawable.class, null);
                    Reflect.from(Toolbar.class).filed("mCollapseIcon", Drawable.class).set(toolbar, collapseIcon);
                    ImageButton collapseButtonView = Reflect.from(Toolbar.class).filed("mCollapseButtonView", ImageButton.class).get(toolbar);
                    if (collapseButtonView != null) {
                        collapseButtonView.setImageDrawable(collapseIcon);
                    }
                    break;
                }
                case "collapseContentDescription": {
                    CharSequence description = attrValue.getTypedValue(CharSequence.class, null);
                    Reflect.from(Toolbar.class).filed("mCollapseDescription", CharSequence.class).set(toolbar, description);
                    ImageButton collapseButtonView = Reflect.from(Toolbar.class).filed("mCollapseButtonView", ImageButton.class).get(toolbar);
                    if (collapseButtonView != null) {
                        collapseButtonView.setContentDescription(description);
                    }
                    break;
                }
                case "title": {
                    CharSequence title = attrValue.getTypedValue(CharSequence.class, null);
                    toolbar.setTitle(title);
                    break;
                }
                case "subtitle": {
                    CharSequence subTitle = attrValue.getTypedValue(CharSequence.class, null);
                    toolbar.setSubtitle(subTitle);
                    break;
                }
                case "navigationIcon": {
                    Drawable navigationIcon = attrValue.getTypedValue(Drawable.class, null);
                    toolbar.setNavigationIcon(navigationIcon);
                    break;
                }
                case "navigationContentDescription": {
                    CharSequence navDesc = attrValue.getTypedValue(CharSequence.class, null);
                    toolbar.setNavigationContentDescription(navDesc);
                    break;
                }
                case "logo": {
                    Drawable logo = attrValue.getTypedValue(Drawable.class, null);
                    toolbar.setLogo(logo);
                    break;
                }
                case "logoDescription": {
                    CharSequence logoDescription = attrValue.getTypedValue(CharSequence.class, null);
                    toolbar.setLogoDescription(logoDescription);
                    break;
                }
                case "titleTextColor": {
                    int titleTextColor = attrValue.getTypedValue(int.class, 0);
                    toolbar.setTitleTextColor(titleTextColor);
                    break;
                }
                case "subtitleTextColor": {
                    int subtitleTextColor = attrValue.getTypedValue(int.class, 0);
                    toolbar.setSubtitleTextColor(subtitleTextColor);
                    break;
                }
            }
        }
    }
}