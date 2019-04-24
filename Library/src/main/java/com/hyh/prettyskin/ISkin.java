package com.hyh.prettyskin;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public interface ISkin {

    boolean loadSkinAttrs();

    AttrValue getAttrValue(String attrValueKey);

}
