package com.hyh.prettyskin.core.handler.ntv;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.utils.ReflectUtil;

/**
 * Created by Eric_He on 2018/10/21.
 */

public class ButtonSkinHandler extends TextViewSkinHandler {

    public ButtonSkinHandler() {
        this((int) ReflectUtil.getStaticFieldValue("com.android.internal.R$attr", "buttonStyle"));//com.android.internal.R.attr.buttonStyle
    }

    public ButtonSkinHandler(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ButtonSkinHandler(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        return super.parseAttrValue(view, set, attrName);
    }
}
