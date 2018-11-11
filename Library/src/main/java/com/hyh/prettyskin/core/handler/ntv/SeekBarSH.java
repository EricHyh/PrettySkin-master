package com.hyh.prettyskin.core.handler.ntv;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class SeekBarSH extends AbsSeekBarSH {

    public SeekBarSH() {
        this(ViewAttrUtil.getDefStyleAttr_internal("seekBarStyle"));//com.android.internal.R.attr.seekBarStyle
    }

    public SeekBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public SeekBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
