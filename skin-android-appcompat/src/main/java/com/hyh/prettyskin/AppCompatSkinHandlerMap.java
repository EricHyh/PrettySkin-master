package com.hyh.prettyskin;

import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.hyh.prettyskin.sh.ActionMenuItemViewSH;
import com.hyh.prettyskin.sh.AppCompatButtonSH;
import com.hyh.prettyskin.sh.AppCompatCheckBoxSH;
import com.hyh.prettyskin.sh.AppCompatCheckedTextViewSH;
import com.hyh.prettyskin.sh.AppCompatImageButtonSH;
import com.hyh.prettyskin.sh.AppCompatImageViewSH;
import com.hyh.prettyskin.sh.AppCompatRadioButtonSH;
import com.hyh.prettyskin.sh.AppCompatRatingBarSH;
import com.hyh.prettyskin.sh.AppCompatSeekBarSH;
import com.hyh.prettyskin.sh.AppCompatTextViewSH;
import com.hyh.prettyskin.sh.SwitchCompatSH;
import com.hyh.prettyskin.sh.V7ToolbarSH;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class AppCompatSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class<? extends View>, ISkinHandler> mSkinHandlerMap = new ConcurrentHashMap<>();

    {
        mSkinHandlerMap.put(AppCompatButton.class, new AppCompatButtonSH());
        mSkinHandlerMap.put(ActionMenuItemView.class, new ActionMenuItemViewSH());
        mSkinHandlerMap.put(AppCompatCheckBox.class, new AppCompatCheckBoxSH());
        mSkinHandlerMap.put(AppCompatCheckedTextView.class, new AppCompatCheckedTextViewSH());
        mSkinHandlerMap.put(AppCompatImageButton.class, new AppCompatImageButtonSH());
        mSkinHandlerMap.put(AppCompatImageView.class, new AppCompatImageViewSH());
        mSkinHandlerMap.put(AppCompatRadioButton.class, new AppCompatRadioButtonSH());
        mSkinHandlerMap.put(AppCompatRatingBar.class, new AppCompatRatingBarSH());
        mSkinHandlerMap.put(AppCompatSeekBar.class, new AppCompatSeekBarSH());
        mSkinHandlerMap.put(AppCompatTextView.class, new AppCompatTextViewSH());
        mSkinHandlerMap.put(SwitchCompat.class, new SwitchCompatSH());
        mSkinHandlerMap.put(android.support.v7.widget.Toolbar.class, new V7ToolbarSH());
    }

    @Override
    public Map<Class<? extends View>, ISkinHandler> get() {
        return mSkinHandlerMap;
    }
}