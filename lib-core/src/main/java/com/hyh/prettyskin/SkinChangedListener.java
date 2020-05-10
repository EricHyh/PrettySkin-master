package com.hyh.prettyskin;

import java.util.List;



public interface SkinChangedListener {

    void onSkinChanged(ISkin skin);

    void onSkinAttrChanged(ISkin skin, List<String> changedAttrKeys);

    void onSkinRecovered();

}