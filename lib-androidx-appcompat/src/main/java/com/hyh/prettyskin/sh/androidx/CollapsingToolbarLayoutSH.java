package com.hyh.prettyskin.sh.androidx;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.sh.ViewGroupSH;

import androidx.core.view.GravityCompat;

/**
 * @author Administrator
 * @description
 * @data 2020/4/20
 */
public class CollapsingToolbarLayoutSH extends ViewGroupSH {

    private TypedArray mTypedArray;

    public CollapsingToolbarLayoutSH() {
    }

    public CollapsingToolbarLayoutSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CollapsingToolbarLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof CollapsingToolbarLayout) &&
                (
                        TextUtils.equals(attrName, "expandedTitleGravity")
                                || TextUtils.equals(attrName, "collapsedTitleGravity")
                                || TextUtils.equals(attrName, "expandedTitleMargin")
                                || TextUtils.equals(attrName, "expandedTitleMarginStart")
                                || TextUtils.equals(attrName, "expandedTitleMarginEnd")
                                || TextUtils.equals(attrName, "expandedTitleMarginTop")
                                || TextUtils.equals(attrName, "expandedTitleMarginBottom")
                                || TextUtils.equals(attrName, "titleEnabled")
                                || TextUtils.equals(attrName, "title")
                                //|| TextUtils.equals(attrName, "expandedTitleTextAppearance")
                                //|| TextUtils.equals(attrName, "collapsedTitleTextAppearance")
                                || TextUtils.equals(attrName, "scrimVisibleHeightTrigger")
                                || TextUtils.equals(attrName, "scrimAnimationDuration")
                                || TextUtils.equals(attrName, "contentScrim")
                                || TextUtils.equals(attrName, "statusBarScrim")
                ))
                || super.isSupportAttrName(view, attrName);
    }


    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mTypedArray = view.getContext().obtainStyledAttributes(
                set,
                com.google.android.material.R.styleable.CollapsingToolbarLayout,
                mDefStyleAttr,
                com.google.android.material.R.style.Widget_Design_CollapsingToolbar);
    }

    @SuppressLint("PrivateResource")
    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view;
        switch (attrName) {
            case "expandedTitleGravity": {
                int expandedTitleGravity = collapsingToolbarLayout.getExpandedTitleGravity();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, expandedTitleGravity);
            }
            case "collapsedTitleGravity": {
                int collapsedTitleGravity = collapsingToolbarLayout.getCollapsedTitleGravity();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, collapsedTitleGravity);
            }
            case "expandedTitleMargin": {
                int expandedTitleMargin = 0;
                if (mTypedArray != null) {
                    expandedTitleMargin = mTypedArray.getInt(com.google.android.material.R.styleable.CollapsingToolbarLayout_expandedTitleMargin,
                            GravityCompat.START | Gravity.BOTTOM);
                }
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, expandedTitleMargin);
            }
            case "expandedTitleMarginStart": {
                int expandedTitleMarginStart = collapsingToolbarLayout.getExpandedTitleMarginStart();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, expandedTitleMarginStart);
            }
            case "expandedTitleMarginEnd": {
                int expandedTitleMarginEnd = collapsingToolbarLayout.getExpandedTitleMarginEnd();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, expandedTitleMarginEnd);
            }
            case "expandedTitleMarginTop": {
                int expandedTitleMarginTop = collapsingToolbarLayout.getExpandedTitleMarginTop();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, expandedTitleMarginTop);
            }
            case "expandedTitleMarginBottom": {
                int expandedTitleMarginBottom = collapsingToolbarLayout.getExpandedTitleMarginBottom();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, expandedTitleMarginBottom);
            }
            case "titleEnabled": {
                boolean titleEnabled = collapsingToolbarLayout.isTitleEnabled();
                return new AttrValue(view.getContext(), ValueType.TYPE_BOOLEAN, titleEnabled);
            }
            case "title": {
                CharSequence title = collapsingToolbarLayout.getTitle();
                return new AttrValue(view.getContext(), ValueType.TYPE_STRING, title);
            }
            case "scrimVisibleHeightTrigger": {
                int scrimVisibleHeightTrigger = collapsingToolbarLayout.getScrimVisibleHeightTrigger();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, scrimVisibleHeightTrigger);
            }
            case "scrimAnimationDuration": {
                int scrimVisibleHeightTrigger = (int) collapsingToolbarLayout.getScrimAnimationDuration();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, scrimVisibleHeightTrigger);
            }
            case "contentScrim": {
                Drawable contentScrim = collapsingToolbarLayout.getContentScrim();
                return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, contentScrim);
            }
            case "statusBarScrim": {
                Drawable statusBarScrim = collapsingToolbarLayout.getStatusBarScrim();
                return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, statusBarScrim);
            }
            default: {
                return super.parse(view, set, attrName);
            }
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
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view;
        switch (attrName) {
            case "expandedTitleGravity": {
                collapsingToolbarLayout.setExpandedTitleGravity(
                        attrValue.getTypedValue(int.class, GravityCompat.START | Gravity.BOTTOM));
                break;
            }
            case "collapsedTitleGravity": {
                collapsingToolbarLayout.setCollapsedTitleGravity(
                        attrValue.getTypedValue(int.class, GravityCompat.START | Gravity.BOTTOM));
                break;
            }
            case "expandedTitleMargin": {
                int margin = attrValue.getTypedValue(int.class, 0);
                collapsingToolbarLayout.setExpandedTitleMargin(margin, margin, margin, margin);
                break;
            }
            case "expandedTitleMarginStart": {
                int margin = attrValue.getTypedValue(int.class, 0);
                collapsingToolbarLayout.setExpandedTitleMarginStart(margin);
                break;
            }
            case "expandedTitleMarginEnd": {
                int margin = attrValue.getTypedValue(int.class, 0);
                collapsingToolbarLayout.setExpandedTitleMarginEnd(margin);
                break;
            }
            case "expandedTitleMarginTop": {
                int margin = attrValue.getTypedValue(int.class, 0);
                collapsingToolbarLayout.setExpandedTitleMarginTop(margin);
                break;
            }
            case "expandedTitleMarginBottom": {
                int margin = attrValue.getTypedValue(int.class, 0);
                collapsingToolbarLayout.setExpandedTitleMarginBottom(margin);
                break;
            }
            case "titleEnabled": {
                collapsingToolbarLayout.setTitleEnabled(attrValue.getTypedValue(boolean.class, true));
                break;
            }
            case "title": {
                collapsingToolbarLayout.setTitle(attrValue.getTypedValue(CharSequence.class, null));
                break;
            }
            case "scrimVisibleHeightTrigger": {
                collapsingToolbarLayout.setScrimVisibleHeightTrigger(attrValue.getTypedValue(int.class, -1));
                break;
            }
            case "scrimAnimationDuration": {
                collapsingToolbarLayout.setScrimAnimationDuration(attrValue.getTypedValue(int.class, 600));
                break;
            }
            case "contentScrim": {
                collapsingToolbarLayout.setContentScrim(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            case "statusBarScrim": {
                collapsingToolbarLayout.setStatusBarScrim(attrValue.getTypedValue(Drawable.class, null));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }

    }
}