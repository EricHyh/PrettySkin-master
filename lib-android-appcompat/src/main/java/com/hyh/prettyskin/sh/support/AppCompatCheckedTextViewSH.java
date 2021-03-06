package com.hyh.prettyskin.sh.support;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatTextSH;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.sh.CheckedTextViewSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;



public class AppCompatCheckedTextViewSH extends CheckedTextViewSH {

    private static final int sDefStyleAttr = ViewAttrUtil.getAndroidStyleAttr("checkedTextViewStyle");

    private static final int[] TINT_ATTRS = {
            android.R.attr.checkMark
    };

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("checkMark");
    }

    private final AppCompatTextSH mTextSH;

    public AppCompatCheckedTextViewSH() {
        this(sDefStyleAttr);
    }

    public AppCompatCheckedTextViewSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public AppCompatCheckedTextViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
        mTextSH = new AppCompatTextSH(defStyleAttr);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof AppCompatCheckedTextView &&
                (mSupportAttrNames.contains(attrName)
                        || mTextSH.isSupportAttrName(view, attrName)
                        || super.isSupportAttrName(view, attrName));
    }

    @Override
    public AttrValue parse(final View view, final AttributeSet set, String attrName) {
        if (TextUtils.equals(attrName, "checkMark")) {
            TypedArray a = view.getContext().obtainStyledAttributes(set,
                    TINT_ATTRS, sDefStyleAttr, 0);
            Drawable drawable = a.getDrawable(0);
            a.recycle();
            return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawable);
        } else if (mTextSH.isSupportAttrName(view, attrName)) {
            return mTextSH.parse(view, set, attrName);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (TextUtils.equals(attrName, "checkMark")) {
            AppCompatCheckedTextView textView = (AppCompatCheckedTextView) view;
            textView.setCheckMarkDrawable(attrValue.getTypedValue(Drawable.class, null));
        } else if (mTextSH.isSupportAttrName(view, attrName)) {
            mTextSH.replace(view, attrName, attrValue);
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}