package com.hyh.prettyskin.android;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

/**
 * @author Administrator
 * @description
 * @data 2019/5/6
 */

public class SkinContext extends ContextWrapper {

    private final Resources mResources;
    private final Resources.Theme mTheme;

    public SkinContext(Context base, Resources resources, int skinThemeId) {
        super(base);
        mResources = resources;
        mTheme = mResources.newTheme();
        mTheme.applyStyle(skinThemeId, true);
    }

    @Override
    public Resources getResources() {
        return mResources;
    }

    @Override
    public void setTheme(int resid) {
        mTheme.applyStyle(resid, true);
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme;
    }
}