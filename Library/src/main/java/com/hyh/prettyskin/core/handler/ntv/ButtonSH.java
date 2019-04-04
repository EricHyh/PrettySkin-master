package com.hyh.prettyskin.core.handler.ntv;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/10/21.
 */

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
