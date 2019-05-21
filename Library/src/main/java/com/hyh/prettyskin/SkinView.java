package com.hyh.prettyskin;

import android.view.View;

import com.hyh.prettyskin.utils.ViewReferenceUtil;

import java.lang.ref.Reference;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public class SkinView {

    private Reference<View> viewReference;

    //attrName --> attrKey
    private Map<String, String> attrKeyMap;

    //attrName --> attrValue
    private Map<String, AttrValue> defaultAttrValueMap;

    public SkinView(View view, Map<String, String> attrKeyMap, Map<String, AttrValue> defaultAttrValueMap) {
        this.viewReference = ViewReferenceUtil.createViewReference(this, view);
        this.attrKeyMap = attrKeyMap;
        this.defaultAttrValueMap = defaultAttrValueMap;
    }

    public View getView() {
        return viewReference == null ? null : viewReference.get();
    }

    public boolean isRecycled() {
        return viewReference.get() == null;
    }

    public boolean isSupportAttr(String attrKey) {
        return attrKeyMap != null && attrKeyMap.containsValue(attrKey);
    }

    public void changeSkin(ISkin skin) {
        if (skin == null || attrKeyMap == null || attrKeyMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler == null) {
            return;
        }
        Set<Map.Entry<String, String>> entrySet = attrKeyMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String attrName = entry.getKey();
            String attrKey = entry.getValue();
            if (skinHandler.isSupportAttrName(view, attrName)) {
                AttrValue attrValue = skin.getAttrValue(attrKey);
                if (attrValue != null) {
                    skinHandler.replace(view, attrName, attrValue);
                }
            }
        }
    }


    public void changeSkin(ISkin skin, List<String> changedAttrKeys) {
        if (skin == null || attrKeyMap == null || attrKeyMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler == null) {
            return;
        }
        Set<Map.Entry<String, String>> entrySet = attrKeyMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            String attrName = entry.getKey();
            String attrKey = entry.getValue();
            if (!changedAttrKeys.contains(attrKey)) {
                continue;
            }
            if (skinHandler.isSupportAttrName(view, attrName)) {
                AttrValue attrValue = skin.getAttrValue(attrKey);
                if (attrValue != null) {
                    skinHandler.replace(view, attrName, attrValue);
                }
            }
        }
    }

    public void recoverSkin() {
        if (defaultAttrValueMap == null || defaultAttrValueMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(view);
        if (skinHandler != null) {
            Set<Map.Entry<String, AttrValue>> entrySet = defaultAttrValueMap.entrySet();
            for (Map.Entry<String, AttrValue> entry : entrySet) {
                String attrName = entry.getKey();
                AttrValue defaultAttrValue = entry.getValue();
                if (skinHandler.isSupportAttrName(view, attrName)) {
                    skinHandler.replace(view, attrName, defaultAttrValue);
                }
            }
        }
    }
}
