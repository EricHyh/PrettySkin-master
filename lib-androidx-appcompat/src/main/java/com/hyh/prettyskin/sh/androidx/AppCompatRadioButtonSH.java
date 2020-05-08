package com.hyh.prettyskin.sh.androidx;

import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.RadioButtonSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import androidx.appcompat.widget.AppCompatCompoundButtonSH;
import androidx.appcompat.widget.AppCompatTextSH;

/**
 * Created by Eric_He on 2018/11/8.
 */

public class AppCompatRadioButtonSH extends RadioButtonSH {

    private final AppCompatCompoundButtonSH mButtonSH;
    private final AppCompatTextSH mTextSH;


    public AppCompatRadioButtonSH() {
        this(ViewAttrUtil.getStyleAttr("androidx.appcompat.R", "radioButtonStyle"));
    }

    public AppCompatRadioButtonSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatRadioButtonSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
        mButtonSH = new AppCompatCompoundButtonSH(defStyleAttr);
        mTextSH = new AppCompatTextSH(defStyleAttr);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mButtonSH.isSupportAttrName(view, attrName)
                || mTextSH.isSupportAttrName(view, attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mButtonSH.isSupportAttrName(view, attrName)) {
            return mButtonSH.parse(view, set, attrName);
        } else if (mTextSH.isSupportAttrName(view, attrName)) {
            return mTextSH.parse(view, set, attrName);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (mButtonSH.isSupportAttrName(view, attrName)) {
            mButtonSH.replace(view, attrName, attrValue);
        } else if (mTextSH.isSupportAttrName(view, attrName)) {
            mTextSH.replace(view, attrName, attrValue);
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}
