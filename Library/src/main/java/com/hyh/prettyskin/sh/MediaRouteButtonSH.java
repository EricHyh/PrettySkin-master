package com.hyh.prettyskin.sh;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class MediaRouteButtonSH extends ViewSH {

    private final Class mStyleableClass;

    private final String mStyleableName;

    private final int[] mAttrs;

    {
        mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
        mStyleableName = "MediaRouteButton";
        mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
    }

    private List<String> mSupportAttrNames = new ArrayList<>();

    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("externalRouteEnabledDrawable");
        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("minHeight");
        mSupportAttrNames.add("mediaRouteTypes");
    }

    public MediaRouteButtonSH() {
        this(ViewAttrUtil.getInternalStyleAttr("mediaRouteButtonStyle"));//com.android.internal.R.attr.mediaRouteButtonStyle
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
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        Context context = view.getContext();
        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);
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
                    Reflect.from(mediaRouteButton.getClass())
                            .method("setRemoteIndicatorDrawable")
                            .param(Drawable.class, drawable)
                            .invoke(mediaRouteButton);
                    break;
                }
                case "minWidth": {
                    int minWidth = ViewAttrUtil.getInt(resources, type, value);
                    Reflect.from(mediaRouteButton.getClass())
                            .filed("mMinWidth", int.class)
                            .set(mediaRouteButton, minWidth);
                    mediaRouteButton.requestLayout();
                    break;
                }
                case "minHeight": {
                    int minHeight = ViewAttrUtil.getInt(resources, type, value);
                    Reflect.from(mediaRouteButton.getClass())
                            .filed("mMinHeight", int.class)
                            .set(mediaRouteButton, minHeight);
                    mediaRouteButton.requestLayout();
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
