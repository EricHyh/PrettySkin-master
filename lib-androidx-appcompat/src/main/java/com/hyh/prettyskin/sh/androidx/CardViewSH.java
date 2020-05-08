package com.hyh.prettyskin.sh.androidx;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.sh.ViewGroupSH;
import com.hyh.prettyskin.utils.AttrValueHelper;
import com.hyh.prettyskin.utils.ViewAttrUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;

/**
 * @author Administrator
 * @description
 * @data 2020/4/16
 */
public class CardViewSH extends ViewGroupSH {

    private Class<?> mStyleableClass;
    private String mStyleableName;
    private int[] mAttrs;

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

    private TypedArray mTypedArray;

    @SuppressLint("PrivateResource")
    public CardViewSH() {
        super(ViewAttrUtil.getStyleAttr("androidx.cardview.R", "cardViewStyle"));
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

        if (mStyleableClass == null) {
            mStyleableClass = Reflect.classForName("androidx.cardview.R$styleable");
            mStyleableName = "CardView";
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }
        mTypedArray = view.getContext().obtainStyledAttributes(
                set,
                mAttrs,
                mDefStyleAttr,
                mDefStyleRes);
    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        if (mSupportAttrNames.contains(attrName)) {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
        } else {
            return super.parse(view, set, attrName);
        }
    }

    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }

    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        if (mSupportAttrNames.contains(attrName)) {
            CardView cardView = (CardView) view;
            switch (attrName) {
                case "cardBackgroundColor": {
                    cardView.setCardBackgroundColor(
                            attrValue.getTypedValue(ColorStateList.class, cardView.getCardBackgroundColor()));
                    break;
                }
                case "cardCornerRadius": {
                    cardView.setRadius(attrValue.getTypedValue(float.class, 0.0f));
                    break;
                }
                case "cardElevation": {
                    cardView.setCardElevation(attrValue.getTypedValue(float.class, 0.0f));
                    break;
                }
                case "cardMaxElevation": {
                    cardView.setMaxCardElevation(attrValue.getTypedValue(float.class, 0.0f));
                    break;
                }
                case "cardUseCompatPadding": {
                    cardView.setUseCompatPadding(attrValue.getTypedValue(boolean.class, false));
                    break;
                }
                case "cardPreventCornerOverlap": {
                    cardView.setPreventCornerOverlap(attrValue.getTypedValue(boolean.class, true));
                    break;
                }
                case "contentPadding": {
                    int contentPadding = attrValue.getTypedValue(int.class, 0);
                    cardView.setContentPadding(contentPadding, contentPadding, contentPadding, contentPadding);
                    break;
                }
                case "contentPaddingLeft": {
                    int contentPaddingLeft = attrValue.getTypedValue(int.class, 0);
                    cardView.setContentPadding(
                            contentPaddingLeft,
                            cardView.getContentPaddingTop(),
                            cardView.getContentPaddingRight(),
                            cardView.getContentPaddingBottom());
                    break;
                }
                case "contentPaddingTop": {
                    int contentPaddingTop = attrValue.getTypedValue(int.class, 0);
                    cardView.setContentPadding(
                            cardView.getContentPaddingLeft(),
                            contentPaddingTop,
                            cardView.getContentPaddingRight(),
                            cardView.getContentPaddingBottom());
                    break;
                }
                case "contentPaddingRight": {
                    int contentPaddingRight = attrValue.getTypedValue(int.class, 0);
                    cardView.setContentPadding(
                            cardView.getContentPaddingLeft(),
                            cardView.getContentPaddingTop(),
                            contentPaddingRight,
                            cardView.getContentPaddingBottom());
                    break;
                }
                case "contentPaddingBottom": {
                    int contentPaddingBottom = attrValue.getTypedValue(int.class, 0);
                    cardView.setContentPadding(
                            cardView.getContentPaddingLeft(),
                            cardView.getContentPaddingTop(),
                            cardView.getContentPaddingRight(),
                            contentPaddingBottom);
                    break;
                }
                case "minWidth": {
                    cardView.setMinimumWidth(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "minHeight": {
                    cardView.setMinimumHeight(attrValue.getTypedValue(int.class, 0));
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}