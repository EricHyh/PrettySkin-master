package com.hyh.prettyskin.sh.support;

import android.support.design.card.MaterialCardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ValueType;

public class MaterialCardViewSH extends CardViewSH {

    public MaterialCardViewSH() {
        this(android.support.design.R.attr.materialCardViewStyle);
    }

    public MaterialCardViewSH(int defStyleAttr) {
        this(defStyleAttr, 0);
    }

    public MaterialCardViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return ((view instanceof MaterialCardView) &&
                (
                        TextUtils.equals(attrName, "strokeColor")
                                || TextUtils.equals(attrName, "strokeWidth")
                )) || super.isSupportAttrName(view, attrName);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        MaterialCardView cardView = (MaterialCardView) view;
        switch (attrName) {
            case "strokeColor": {
                int strokeColor = cardView.getStrokeColor();
                return new AttrValue(view.getContext(), ValueType.TYPE_COLOR_INT, strokeColor);
            }
            case "strokeWidth": {
                int strokeWidth = cardView.getStrokeWidth();
                return new AttrValue(view.getContext(), ValueType.TYPE_INT, strokeWidth);
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        MaterialCardView cardView = (MaterialCardView) view;
        switch (attrName) {
            case "strokeColor": {
                cardView.setStrokeColor(attrValue.getTypedValue(int.class, -1));
                break;
            }
            case "strokeWidth": {
                cardView.setStrokeWidth(attrValue.getTypedValue(int.class, 0));
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}
