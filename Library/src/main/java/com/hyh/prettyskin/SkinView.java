package com.hyh.prettyskin;

import android.view.View;

import com.hyh.prettyskin.utils.ViewReferenceUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 * @description
 * @data 2018/10/9
 */

public final class SkinView {

    private final Reference<View> viewReference;

    private final int hashCode;

    //attrName --> attrKey
    private final Map<String, String> attrKeyMap;

    //attrName --> attrValue
    private final Map<String, AttrValue> defaultAttrValueMap;

    private BasePrettySkin mPrettySkin;

    public SkinView(View view, Map<String, String> attrKeyMap, Map<String, AttrValue> defaultAttrValueMap) {
        this.viewReference = ViewReferenceUtil.createViewReference(this, view);
        this.hashCode = view == null ? 0 : System.identityHashCode(view);
        this.attrKeyMap = attrKeyMap;
        this.defaultAttrValueMap = defaultAttrValueMap;
    }

    SkinView(View view) {
        this.viewReference = new WeakReference<>(view);
        this.hashCode = view == null ? 0 : System.identityHashCode(view);
        this.attrKeyMap = null;
        this.defaultAttrValueMap = null;
    }

    final void bindPrettySkin(BasePrettySkin prettySkin) {
        this.mPrettySkin = prettySkin;
    }

    final View getView() {
        return viewReference == null ? null : viewReference.get();
    }

    final boolean isRecycled() {
        return viewReference.get() == null;
    }

    final boolean isSupportAttr(String attrKey) {
        return attrKeyMap != null && attrKeyMap.containsValue(attrKey);
    }

    final void changeSkin(ISkin skin) {
        final BasePrettySkin prettySkin = mPrettySkin;
        if (prettySkin == null) {
            return;
        }
        if (skin == null || attrKeyMap == null || attrKeyMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = prettySkin.getSkinHandler(view);
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


    final void changeSkin(ISkin skin, List<String> changedAttrKeys) {
        final BasePrettySkin prettySkin = mPrettySkin;
        if (prettySkin == null) {
            return;
        }
        if (skin == null || attrKeyMap == null || attrKeyMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = prettySkin.getSkinHandler(view);
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

    final void recoverSkin() {
        final BasePrettySkin prettySkin = mPrettySkin;
        if (prettySkin == null) {
            return;
        }
        if (defaultAttrValueMap == null || defaultAttrValueMap.isEmpty()) {
            return;
        }
        View view = viewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = prettySkin.getSkinHandler(view);
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

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        SkinView that = (SkinView) obj;
        View thisView = this.getView();
        View thatView = that.getView();
        return thisView == thatView;
    }

    public void destroy() {
        final BasePrettySkin prettySkin = mPrettySkin;
        if (prettySkin == null) {
            return;
        }
        prettySkin.removeSkinView(this);
    }
}