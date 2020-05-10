package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;



public class RadioButtonSH extends CompoundButtonSH {

    public RadioButtonSH() {
        this(ViewAttrUtil.getInternalStyleAttr("radioButtonStyle"));//com.android.internal.R.attr.radioButtonStyle
    }

    public RadioButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public RadioButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}