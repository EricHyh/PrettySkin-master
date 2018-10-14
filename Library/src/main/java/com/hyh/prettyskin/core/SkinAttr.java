package com.hyh.prettyskin.core;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public class SkinAttr {

    public static final int TYPE_NULL = 0;
    public static final int TYPE_REFERENCE = 1;
    public static final int TYPE_COLOR = 2;
    public static final int TYPE_COLOR_STATE_LIST = 3;
    public static final int TYPE_DRAWABLE = 4;

    private String attrName;

    private int valueType;

    private Object attrValue;

    public SkinAttr(String attrName, int valueType, Object attrValue) {
        this.attrName = attrName;
        this.valueType = valueType;
        this.attrValue = attrValue;
    }

    public String getAttrName() {
        return attrName;
    }

    public int getValueType() {
        return valueType;
    }

    public Object getAttrValue() {
        return attrValue;
    }
}
