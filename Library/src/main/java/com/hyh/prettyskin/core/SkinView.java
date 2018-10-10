package com.hyh.prettyskin.core;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public class SkinView {

    private WeakReference<View> mViewReference;

    private String attrName;

    private String attrValueKey;

/*    private String defaultAttrValue;

    private String currentAttrValue;*/

    public SkinView(View view, String attrName, String attrValueKey) {
        this.mViewReference = new WeakReference<>(view);
        this.attrName = attrName;
        this.attrValueKey = attrValueKey;
    }

    public String getAttrValueKey() {
        return attrValueKey;
    }

    public void notifySkinChanged(int valueType, Object attrValue) {
        View view = mViewReference.get();
        if (view == null) {
            return;
        }
        switch (attrName) {
            case "textColor": {
                TextView textView = (TextView) view;
                textView.setTextColor((Integer) attrValue);
                break;
            }
            case "background": {
                view.setBackgroundColor((Integer) attrValue);
                break;
            }
            case "src": {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable((Drawable) attrValue);
                break;
            }
        }
    }
}
