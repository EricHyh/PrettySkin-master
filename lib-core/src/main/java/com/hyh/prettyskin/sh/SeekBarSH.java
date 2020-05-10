package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;


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