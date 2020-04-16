package com.hyh.prettyskin.sh;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2020/4/16
 */
public class CardViewSH extends ViewGroupSH {


    private final Class mStyableClass = android.support.v7.cardview.R.styleable.class;

    private final String mStyableName = "CardView";


    private final List<String> mSupportAttrNames = new ArrayList<>();

    {
        mSupportAttrNames.add("cardBackgroundColor");
        mSupportAttrNames.add("cardCornerRadius");
        mSupportAttrNames.add("cardElevation");
        mSupportAttrNames.add("cardMaxElevation");
        mSupportAttrNames.add("cardUseCompatPadding");
        mSupportAttrNames.add("cardPreventCornerOverlap");

        mSupportAttrNames.add("contentPadding");
        mSupportAttrNames.add("contentPaddingLeft");
        mSupportAttrNames.add("contentPaddingTop");
        mSupportAttrNames.add("contentPaddingRight");
        mSupportAttrNames.add("contentPaddingBottom");

        mSupportAttrNames.add("minWidth");
        mSupportAttrNames.add("minHeight");
    }


    @SuppressLint("PrivateResource")
    public CardViewSH() {
        super(android.support.v7.cardview.R.attr.cardViewStyle);
        Class<CardView> cardViewClass = CardView.class;
    }

    public CardViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public CardViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        return super.parse(view, set, attrName);
    }

    @Override
    public void finishParse() {
        super.finishParse();
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        super.replace(view, attrName, attrValue);
    }
}
