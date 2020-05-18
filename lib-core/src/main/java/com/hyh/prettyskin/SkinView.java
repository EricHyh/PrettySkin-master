package com.hyh.prettyskin;

import android.view.View;

import com.hyh.prettyskin.utils.ViewReferenceUtil;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Set;


public final class SkinView {

    private final Reference<View> mViewReference;

    private final int mHashCode;

    //attrName --> attrKey
    private final Map<String, String> mAttrKeyMap;

    //attrName --> attrValue
    private final Map<String, AttrValue> mDefaultAttrValueMap;

    private BasePrettySkin mPrettySkin;

    public SkinView(View view, Map<String, String> attrKeyMap, Map<String, AttrValue> defaultAttrValueMap) {
        this.mViewReference = ViewReferenceUtil.createViewReference(this, view);
        this.mHashCode = view == null ? 0 : System.identityHashCode(view);
        this.mAttrKeyMap = attrKeyMap;
        this.mDefaultAttrValueMap = defaultAttrValueMap;
    }

    SkinView(View view) {
        this.mViewReference = new WeakReference<>(view);
        this.mHashCode = view == null ? 0 : System.identityHashCode(view);
        this.mAttrKeyMap = null;
        this.mDefaultAttrValueMap = null;
    }

    final void bindPrettySkin(BasePrettySkin prettySkin) {
        this.mPrettySkin = prettySkin;
    }

    final View getView() {
        return mViewReference == null ? null : mViewReference.get();
    }

    final boolean isRecycled() {
        return mViewReference.get() == null;
    }

    final boolean isSupportAttr(String attrKey) {
        return mAttrKeyMap != null && mAttrKeyMap.containsValue(attrKey);
    }

    final void changeSkin(ISkin skin) {
        final BasePrettySkin prettySkin = mPrettySkin;
        if (prettySkin == null) {
            return;
        }
        if (skin == null || mAttrKeyMap == null || mAttrKeyMap.isEmpty()) {
            return;
        }
        View view = mViewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = prettySkin.getSkinHandler(view);
        if (skinHandler == null) {
            return;
        }
        Set<Map.Entry<String, String>> entrySet = mAttrKeyMap.entrySet();
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
        if (skin == null || mAttrKeyMap == null || mAttrKeyMap.isEmpty()) {
            return;
        }
        View view = mViewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = prettySkin.getSkinHandler(view);
        if (skinHandler == null) {
            return;
        }


        Set<Map.Entry<String, String>> entrySet = mAttrKeyMap.entrySet();
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
        if (mDefaultAttrValueMap == null || mDefaultAttrValueMap.isEmpty()) {
            return;
        }
        View view = mViewReference.get();
        if (view == null) {
            return;
        }
        ISkinHandler skinHandler = prettySkin.getSkinHandler(view);
        if (skinHandler != null) {
            Set<Map.Entry<String, AttrValue>> entrySet = mDefaultAttrValueMap.entrySet();
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
        return this.mHashCode;
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