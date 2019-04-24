package com.hyh.prettyskin.sh;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.sh.CheckedTextViewSH;
import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric_He on 2018/11/11.
 */

public class AppCompatCheckedTextViewSH extends CheckedTextViewSH {

    private static final int sDefStyleAttr = ViewAttrUtil.getAndroidStyleAttr("checkedTextViewStyle");

    private static final int[] TINT_ATTRS = {
            android.R.attr.checkMark
    };

    private List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("checkMark");
    }

    public AppCompatCheckedTextViewSH() {
        this(sDefStyleAttr);
    }

    public AppCompatCheckedTextViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public AppCompatCheckedTextViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return view instanceof AppCompatCheckedTextView && mSupportAttrNames.contains(attrName)
                || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (view instanceof AppCompatCheckedTextView) {
            AppCompatCheckedTextView checkedTextView = (AppCompatCheckedTextView) view;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                Drawable checkMarkDrawable = checkedTextView.getCheckMarkDrawable();
            } else {

            }
        } else {

        }

        if (super.isSupportAttrName(view, attrName)) {
            return super.parse(view, set, attrName);
        } else {
            TypedArray a = view.getContext().obtainStyledAttributes(set,
                    TINT_ATTRS, sDefStyleAttr, 0);
            Drawable drawable = a.getDrawable(0);
            a.recycle();
            return new AttrValue(view.getContext(), ValueType.TYPE_DRAWABLE, drawable);
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}