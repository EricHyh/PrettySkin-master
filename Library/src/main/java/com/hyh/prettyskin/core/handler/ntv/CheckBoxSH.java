package com.hyh.prettyskin.core.handler.ntv;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class CheckBoxSH extends CompoundButtonSH {

    public CheckBoxSH() {
        this(ViewAttrUtil.getDefStyleAttr_internal("checkboxStyle"));//com.android.internal.R.attr.checkboxStyle
    }

    public CheckBoxSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CheckBoxSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
