package com.hyh.prettyskin.core;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public class SkinAttr {

    private String attrValueKey;

    private int valueType;

    private Object attrValue;

    public SkinAttr(String attrValueKey, int valueType, Object attrValue) {
        this.attrValueKey = attrValueKey;
        this.valueType = valueType;
        this.attrValue = attrValue;
    }

    public String getAttrValueKey() {
        return attrValueKey;
    }

    public int getValueType() {
        return valueType;
    }

    public Object getAttrValue() {
        return attrValue;
    }
}
