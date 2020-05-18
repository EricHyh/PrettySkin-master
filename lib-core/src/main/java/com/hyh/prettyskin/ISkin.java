package com.hyh.prettyskin;


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
     * 设置外部属性，设置后会优先使用该属性，若先删除设置的属性，可将attrValue设置为null
     */
    void setOuterAttrValue(String attrKey, AttrValue attrValue);

}