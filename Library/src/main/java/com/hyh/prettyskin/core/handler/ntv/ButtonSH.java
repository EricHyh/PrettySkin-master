package com.hyh.prettyskin.core.handler.ntv;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.core.AttrValue;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/10/21.
 */

public class ButtonSH extends TextViewSH {

    public ButtonSH() {
        this(ViewAttrUtil.getDefStyleAttr("buttonStyle"));//com.android.internal.R.attr.buttonStyle
    }

    public ButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public AttrValue parseAttrValue(View view, AttributeSet set, String attrName) {
        return super.parseAttrValue(view, set, attrName);
    }
}
