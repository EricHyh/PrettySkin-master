package com.hyh.prettyskin.sh.support;

/**
 * @author Administrator
 * @description
 * @data 2020/4/20
 */
public class TextInputEditTextSH extends AppCompatEditTextSH {

    public TextInputEditTextSH() {
        this(android.support.design.R.attr.editTextStyle);
    }

    public TextInputEditTextSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public TextInputEditTextSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }
}