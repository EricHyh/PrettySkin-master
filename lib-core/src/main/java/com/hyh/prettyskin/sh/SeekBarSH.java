package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/11/6
 */

public class SeekBarSH extends AbsSeekBarSH {

    public SeekBarSH() {
        this(ViewAttrUtil.getInternalStyleAttr("seekBarStyle"));//com.android.internal.R.attr.seekBarStyle
    }

    public SeekBarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public SeekBarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}