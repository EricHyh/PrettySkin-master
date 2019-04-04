package com.hyh.prettyskin.core;

import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public interface ISkin {

    boolean loadSkinAttrs();

    AttrValue getAttrValue(String attrValueKey);

}
