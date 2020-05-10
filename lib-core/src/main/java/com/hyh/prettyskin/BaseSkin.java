package com.hyh.prettyskin;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;


public abstract class BaseSkin implements ISkin {

    private Map<String, AttrValue> mOuterSkinAttrMap;

    @Override
    public AttrValue getAttrValue(String attrKey) {
        AttrValue attrValue = null;
        if (mOuterSkinAttrMap != null) {
            attrValue = mOuterSkinAttrMap.get(attrKey);
        }
        if (attrValue != null) {
            return attrValue;
        }
        return getInnerAttrValue(attrKey);
    }

    protected abstract AttrValue getInnerAttrValue(String attrKey);

    @Override
    public void setOuterAttrValue(String attrKey, AttrValue attrValue) {
        if (TextUtils.isEmpty(attrKey)) return;
        if (mOuterSkinAttrMap == null) {
            mOuterSkinAttrMap = new HashMap<>();
        }
        mOuterSkinAttrMap.put(attrKey, attrValue);
    }
}