package com.hyh.prettyskin.core;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public class SkinAttr {

    private String attrValueKey;

    private AttrValue attrValue;

    public SkinAttr(String attrValueKey, AttrValue attrValue) {
        this.attrValueKey = attrValueKey;
        this.attrValue = attrValue;
    }

    public String getAttrValueKey() {
        return attrValueKey;
    }

    public AttrValue getAttrValue() {
        return attrValue;
    }
}
