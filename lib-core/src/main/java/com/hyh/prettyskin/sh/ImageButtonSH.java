package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class ImageButtonSH extends ImageViewSH {

    public ImageButtonSH() {
        this(ViewAttrUtil.getInternalStyleAttr("imageButtonStyle"));//com.android.internal.R.attr.imageButtonStyle
    }

    public ImageButtonSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ImageButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
