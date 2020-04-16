package com.hyh.prettyskin;

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
import com.hyh.prettyskin.sh.CardViewSH;
import com.hyh.prettyskin.sh.SwipeRefreshLayoutSH;
import com.hyh.prettyskin.sh.SwitchCompatSH;
import com.hyh.prettyskin.sh.TabLayoutSH;
import com.hyh.prettyskin.sh.V7ToolbarSH;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class AppCompatSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class, ISkinHandler> mMap = new HashMap<>();

    public ISkinHandler get(Class viewClass) {
        if (viewClass == null) return null;
        ISkinHandler skinHandler = mMap.get(viewClass);
        if (skinHandler != null) return skinHandler;
        switch (viewClass.getName()) {
            case "android.support.v7.widget.AppCompatButton": {
                skinHandler = new AppCompatButtonSH();
                break;
            }
            case "android.support.v7.view.menu.ActionMenuItemView": {
                skinHandler = new ActionMenuItemViewSH();
                break;
            }
            case "android.support.v7.widget.AppCompatCheckBox": {
                skinHandler = new AppCompatCheckBoxSH();
                break;
            }
            case "android.support.v7.widget.AppCompatCheckedTextView": {
                skinHandler = new AppCompatCheckedTextViewSH();
                break;
            }
            case "android.support.v7.widget.AppCompatImageButton": {
                skinHandler = new AppCompatImageButtonSH();
                break;
            }
            case "android.support.v7.widget.AppCompatImageView": {
                skinHandler = new AppCompatImageViewSH();
                break;
            }
            case "android.support.v7.widget.AppCompatRadioButton": {
                skinHandler = new AppCompatRadioButtonSH();
                break;
            }
            case "android.support.v7.widget.AppCompatRatingBar": {
                skinHandler = new AppCompatRatingBarSH();
                break;
            }
            case "android.support.v7.widget.AppCompatSeekBar": {
                skinHandler = new AppCompatSeekBarSH();
                break;
            }
            case "android.support.v7.widget.AppCompatTextView": {
                skinHandler = new AppCompatTextViewSH();
                break;
            }
            case "android.support.v7.widget.SwitchCompat": {
                skinHandler = new SwitchCompatSH();
                break;
            }
            case "android.support.v7.widget.Toolbar": {
                skinHandler = new V7ToolbarSH();
                break;
            }
            case "android.support.v4.widget.SwipeRefreshLayout": {
                skinHandler = new SwipeRefreshLayoutSH();
                break;
            }
            case "android.support.design.widget.TabLayout": {
                skinHandler = new TabLayoutSH();
                break;
            }
            case "android.support.v7.widget.CardView": {
                skinHandler = new CardViewSH();
                break;
            }
        }
        if (skinHandler != null) {
            mMap.put(viewClass, skinHandler);
        }
        return skinHandler;
    }
}