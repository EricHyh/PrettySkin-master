package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;



public class CheckBoxSH extends CompoundButtonSH {

    public CheckBoxSH() {
        this(ViewAttrUtil.getInternalStyleAttr("checkboxStyle"));//com.android.internal.R.attr.checkboxStyle
    }

    public CheckBoxSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CheckBoxSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}