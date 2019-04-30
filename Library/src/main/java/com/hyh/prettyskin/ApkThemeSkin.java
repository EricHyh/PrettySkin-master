package com.hyh.prettyskin;

import android.content.Context;

/**
 * @author Administrator
 * @description
 * @data 2019/4/30
 */

public class ApkThemeSkin implements ISkin {

    private final Context mApplicationContext;
    private final String mApkPath;

    public ApkThemeSkin(Context context, String apkPath) {
        this.mApplicationContext = context.getApplicationContext();
        this.mApkPath = apkPath;
    }

    @Override
    public boolean loadSkinAttrs() {
        //mApplicationContext.getApplicationInfo().metaData.
        return false;
    }

    @Override
    public AttrValue getAttrValue(String attrKey) {
        return null;
    }
}