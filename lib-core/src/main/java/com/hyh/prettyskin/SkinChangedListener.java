package com.hyh.prettyskin;

import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2019/5/21
 */

public interface SkinChangedListener {

    void onSkinChanged(ISkin skin);

    void onSkinAttrChanged(ISkin skin, List<String> changedAttrKeys);

    void onSkinRecovered();

}