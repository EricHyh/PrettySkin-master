package com.hyh.prettyskin.core.handler.ntv;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/11/8.
 */

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
