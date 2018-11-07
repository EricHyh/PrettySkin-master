package com.hyh.prettyskin.core.handler.support.v7;

import com.hyh.prettyskin.core.handler.ntv.CheckBoxSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatCheckBoxSH extends CheckBoxSH {

    public AppCompatCheckBoxSH() {
        this(ViewAttrUtil.getDefStyleAttr_V7("checkboxStyle"));//R.attr.checkboxStyle
    }

    public AppCompatCheckBoxSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatCheckBoxSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
