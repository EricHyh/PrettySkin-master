package com.hyh.prettyskin.sh;

import com.hyh.prettyskin.utils.ViewAttrUtil;

/**
 * Created by Eric_He on 2018/10/21.
 */

public class EditTextSH extends TextViewSH {

    public EditTextSH() {
        this(ViewAttrUtil.getInternalStyleAttr("editTextStyle"));//com.android.internal.R.attr.editTextStyle
    }

    public EditTextSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public EditTextSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

}
