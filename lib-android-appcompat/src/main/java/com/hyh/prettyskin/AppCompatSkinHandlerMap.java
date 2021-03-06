package com.hyh.prettyskin;

import android.support.design.internal.FlowLayoutSH;

import com.hyh.prettyskin.sh.support.ActionMenuItemViewSH;
import com.hyh.prettyskin.sh.support.AppBarLayoutSH;
import com.hyh.prettyskin.sh.support.AppCompatButtonSH;
import com.hyh.prettyskin.sh.support.AppCompatCheckBoxSH;
import com.hyh.prettyskin.sh.support.AppCompatCheckedTextViewSH;
import com.hyh.prettyskin.sh.support.AppCompatEditTextSH;
import com.hyh.prettyskin.sh.support.AppCompatImageButtonSH;
import com.hyh.prettyskin.sh.support.AppCompatImageViewSH;
import com.hyh.prettyskin.sh.support.AppCompatRadioButtonSH;
import com.hyh.prettyskin.sh.support.AppCompatRatingBarSH;
import com.hyh.prettyskin.sh.support.AppCompatSeekBarSH;
import com.hyh.prettyskin.sh.support.AppCompatTextViewSH;
import com.hyh.prettyskin.sh.support.BottomAppBarSH;
import com.hyh.prettyskin.sh.support.BottomNavigationViewSH;
import com.hyh.prettyskin.sh.support.CardViewSH;
import com.hyh.prettyskin.sh.support.ChipGroupSH;
import com.hyh.prettyskin.sh.support.ChipSH;
import com.hyh.prettyskin.sh.support.CollapsingToolbarLayoutSH;
import com.hyh.prettyskin.sh.support.FloatingActionButtonSH;
import com.hyh.prettyskin.sh.support.ForegroundLinearLayoutSH;
import com.hyh.prettyskin.sh.support.MaterialButtonSH;
import com.hyh.prettyskin.sh.support.MaterialCardViewSH;
import com.hyh.prettyskin.sh.support.NavigationViewSH;
import com.hyh.prettyskin.sh.support.SwipeRefreshLayoutSH;
import com.hyh.prettyskin.sh.support.SwitchCompatSH;
import com.hyh.prettyskin.sh.support.TabLayoutSH;
import com.hyh.prettyskin.sh.support.TextInputEditTextSH;
import com.hyh.prettyskin.sh.support.TextInputLayoutSH;
import com.hyh.prettyskin.sh.support.V7ToolbarSH;

import java.util.HashMap;
import java.util.Map;


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
            case "android.support.v7.widget.AppCompatEditText": {
                skinHandler = new AppCompatEditTextSH();
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
            case "android.support.v7.widget.CardView": {
                skinHandler = new CardViewSH();
                break;
            }

            case "android.support.design.widget.AppBarLayout": {
                skinHandler = new AppBarLayoutSH();
                break;
            }
            case "android.support.design.bottomappbar.BottomAppBar": {
                skinHandler = new BottomAppBarSH();
                break;
            }
            case "android.support.design.widget.NavigationView": {
                skinHandler = new NavigationViewSH();
                break;
            }
            case "android.support.design.widget.BottomNavigationView": {
                skinHandler = new BottomNavigationViewSH();
                break;
            }
            case "android.support.design.widget.TabLayout": {
                skinHandler = new TabLayoutSH();
                break;
            }
            case "android.support.design.widget.CollapsingToolbarLayout": {
                skinHandler = new CollapsingToolbarLayoutSH();
                break;
            }
            case "android.support.design.widget.FloatingActionButton": {
                skinHandler = new FloatingActionButtonSH();
                break;
            }
            case "android.support.design.widget.TextInputEditText": {
                skinHandler = new TextInputEditTextSH();
                break;
            }
            case "android.support.design.widget.TextInputLayout": {
                skinHandler = new TextInputLayoutSH();
                break;
            }
            case "android.support.design.internal.FlowLayout": {
                skinHandler = new FlowLayoutSH();
                break;
            }
            case "android.support.design.chip.ChipGroup": {
                skinHandler = new ChipGroupSH();
                break;
            }
            case "android.support.design.chip.Chip": {
                skinHandler = new ChipSH();
                break;
            }
            case "android.support.design.internal.ForegroundLinearLayout": {
                skinHandler = new ForegroundLinearLayoutSH();
                break;
            }
            case "android.support.design.button.MaterialButton": {
                skinHandler = new MaterialButtonSH();
                break;
            }
            case "android.support.design.card.MaterialCardView": {
                skinHandler = new MaterialCardViewSH();
                break;
            }
        }
        if (skinHandler != null) {
            mMap.put(viewClass, skinHandler);
        }
        return skinHandler;
    }
}