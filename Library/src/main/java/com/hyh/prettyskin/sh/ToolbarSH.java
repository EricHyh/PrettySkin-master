package com.hyh.prettyskin.sh;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.Toolbar;

/**
 * @author Administrator
 * @description
 * @data 2019/5/7
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ToolbarSH extends ViewGroupSH {

    public ToolbarSH() {
        Toolbar toolbar = new Toolbar(null);
    }

    public ToolbarSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public ToolbarSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}
