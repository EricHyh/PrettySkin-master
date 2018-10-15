package com.hyh.prettyskin.core;

import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public interface ISkin {

    List<SkinAttr> getSkinAttrs();

    int getValueType(String attrValueKey);

    Object getAttrValue(String attrValueKey);

    boolean equals(ISkin skin);
}
