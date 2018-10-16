package com.hyh.prettyskin.core;

/**
 * @author Administrator
 * @description
 * @data 2018/10/16
 */

public class ViewAttr {

    private String attrName;

    private int minSdkVersion;

    private String attrValueKey;

    private Object defaultAttrValue;

    private Object currentAttrValue;

    public ViewAttr(String attrName, String attrValueKey, Object defaultAttrValue) {
        this.attrName = attrName;
        this.attrValueKey = attrValueKey;
        this.defaultAttrValue = defaultAttrValue;
    }

    public ViewAttr(String attrName, int minSdkVersion, String attrValueKey, Object defaultAttrValue) {
        this.attrName = attrName;
        this.minSdkVersion = minSdkVersion;
        this.attrValueKey = attrValueKey;
        this.defaultAttrValue = defaultAttrValue;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(int minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public String getAttrValueKey() {
        return attrValueKey;
    }

    public void setAttrValueKey(String attrValueKey) {
        this.attrValueKey = attrValueKey;
    }

    public Object getDefaultAttrValue() {
        return defaultAttrValue;
    }

    public void setDefaultAttrValue(Object defaultAttrValue) {
        this.defaultAttrValue = defaultAttrValue;
    }

    public Object getCurrentAttrValue() {
        return currentAttrValue;
    }

    public void setCurrentAttrValue(Object currentAttrValue) {
        this.currentAttrValue = currentAttrValue;
    }
}
