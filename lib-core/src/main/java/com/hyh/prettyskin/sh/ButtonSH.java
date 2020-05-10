package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;



public class ButtonSH extends TextViewSH {

    public ButtonSH() {
        this(ViewAttrUtil.getInternalStyleAttr("buttonStyle"));//com.android.internal.R.attr.buttonStyle
    }

    public ButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}