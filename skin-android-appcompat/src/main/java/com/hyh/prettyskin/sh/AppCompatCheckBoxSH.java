package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.sh.CheckBoxSH;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatCheckBoxSH extends CheckBoxSH {

    public AppCompatCheckBoxSH() {
        this(android.support.v7.appcompat.R.attr.checkboxStyle);
    }

    public AppCompatCheckBoxSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatCheckBoxSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
