package com.hyh.prettyskin;

/**
 * @author Administrator
 * @description
 * @data 2018/10/10
 */

public interface ISkin {

    /**
     * 加载皮肤属性
     *
     * @return 是否加载成功
     */
    boolean loadSkinAttrs();

    /**
     * 根据属性的Key获取属性的Value
     *
     * @param attrKey 属性的Key
     * @return
     */
    AttrValue getAttrValue(String attrKey);

    /**
     * 设置皮肤属性，如果该KEY已存在则替换属性
     */
    void setOuterAttrValue(String attrKey, AttrValue attrValue);

}