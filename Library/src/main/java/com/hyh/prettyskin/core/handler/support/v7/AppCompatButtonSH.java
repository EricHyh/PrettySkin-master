package com.hyh.prettyskin.core.handler.support.v7;

import com.hyh.prettyskin.core.handler.ntv.ButtonSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/7
 */

public class AppCompatButtonSH extends ButtonSH {

    public AppCompatButtonSH() {
        this(ViewAttrUtil.getDefStyleAttr_V7("buttonStyle"));//R.attr.buttonStyle
    }

    public AppCompatButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
