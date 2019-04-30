package com.hyh.prettyskin;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */
public class SkinAttr {

    private String attrKey;

    private AttrValue attrValue;

    public SkinAttr(String attrKey, AttrValue attrValue) {
        this.attrKey = attrKey;
        this.attrValue = attrValue;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public AttrValue getAttrValue() {
        return attrValue;
    }
}
