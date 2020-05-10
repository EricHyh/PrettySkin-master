package com.hyh.prettyskin.sh;

import android.app.MediaRouteButton;
import android.content.Context;
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



public class MediaRouteButtonSH extends ViewSH {

    private Class<?> mStyleableClass;

    private String mStyleableName;

    private int[] mAttrs;

    private final List<String> mSupportAttrNames = new ArrayList<>();

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

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            mStyleableName = "MediaRouteButton";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }


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
            switch (attrName) {
                case "externalRouteEnabledDrawable": {
                    Drawable drawable = attrValue.getTypedValue(Drawable.class, null);
                    //setRemoteIndicatorDrawable(Drawable d)
                    Reflect.from(mediaRouteButton.getClass())
                            .method("setRemoteIndicatorDrawable")
                            .param(Drawable.class, drawable)
                            .invoke(mediaRouteButton);
                    break;
                }
                case "minWidth": {
                    int minWidth = attrValue.getTypedValue(int.class, 0);
                    Reflect.from(mediaRouteButton.getClass())
                            .filed("mMinWidth", int.class)
                            .set(mediaRouteButton, minWidth);
                    mediaRouteButton.requestLayout();
                    break;
                }
                case "minHeight": {
                    int minHeight = attrValue.getTypedValue(int.class, 0);
                    Reflect.from(mediaRouteButton.getClass())
                            .filed("mMinHeight", int.class)
                            .set(mediaRouteButton, minHeight);
                    mediaRouteButton.requestLayout();
                    break;
                }
                case "mediaRouteTypes": {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        int mediaRouteTypes = attrValue.getTypedValue(int.class, MediaRouter.ROUTE_TYPE_LIVE_AUDIO);
                        mediaRouteButton.setRouteTypes(mediaRouteTypes);
                    }
                    break;
                }
            }
        }
    }
}
