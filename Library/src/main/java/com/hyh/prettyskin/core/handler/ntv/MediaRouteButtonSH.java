package com.hyh.prettyskin.core.handler.ntv;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.core.ValueType;
import com.hyh.prettyskin.utils.ReflectUtil;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class MediaRouteButtonSH extends ViewSH {

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("externalRouteEnabledDrawable");
        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("minHeight");
        mSupportAttrNames.add("mediaRouteTypes");
    }

    public MediaRouteButtonSH() {
        this(ViewAttrUtil.getDefStyleAttr_internal("mediaRouteButtonStyle"));//com.android.internal.R.attr.mediaRouteButtonStyle
    }

    public MediaRouteButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public MediaRouteButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof MediaRouteButton && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        if (super.isSupportAttrName(view, attrName)) {
            return super.parseAttrValue(view, set, attrName);
        } else {
            Class styleableClass = getStyleableClass();
            String styleableName = getStyleableName();
            return parseAttrValue(view, set, attrName, styleableClass, styleableName);
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
        return "MediaRouteButton";
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (super.isSupportAttrName(view, attrName)) {
            super.replace(view, attrName, attrValue);
        } else if (view instanceof MediaRouteButton) {
            MediaRouteButton mediaRouteButton = (MediaRouteButton) view;
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
                case "externalRouteEnabledDrawable": {
                    Drawable drawable = ViewAttrUtil.getDrawable(resources, type, value);
                    //setRemoteIndicatorDrawable(Drawable d)
                    ReflectUtil.invokeMethod(mediaRouteButton,
                            "setRemoteIndicatorDrawable",
                            new Class[]{Drawable.class},
                            drawable);
                    break;
                }
                case "minWidth": {
                    int minWidth = ViewAttrUtil.getInt(resources, type, value);
                    ReflectUtil.setFieldValue(mediaRouteButton, "mMinWidth", minWidth);
                    break;
                }
                case "minHeight": {
                    int minHeight = ViewAttrUtil.getInt(resources, type, value);
                    ReflectUtil.setFieldValue(mediaRouteButton, "mMinHeight", minHeight);
                    break;
                }
                case "mediaRouteTypes": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        int mediaRouteTypes = ViewAttrUtil.getInt(resources, type, value, MediaRouter.ROUTE_TYPE_LIVE_AUDIO);
                        mediaRouteButton.setRouteTypes(mediaRouteTypes);
                    }
                    break;
                }
            }
        }
    }
}
