package com.hyh.prettyskin.core.handler.ntv;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class ImageButtonSH extends ImageViewSH {

    public ImageButtonSH() {
        this(ViewAttrUtil.getDefStyleAttr("imageButtonStyle"));//com.android.internal.R.attr.imageButtonStyle
    }

    public ImageButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ImageButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}