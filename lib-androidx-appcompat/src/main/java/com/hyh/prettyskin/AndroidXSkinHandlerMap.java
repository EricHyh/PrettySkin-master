package com.hyh.prettyskin;


import com.google.android.material.internal.FlowLayoutSH;
import com.hyh.prettyskin.sh.androidx.ActionMenuItemViewSH;
import com.hyh.prettyskin.sh.androidx.AppBarLayoutSH;
import com.hyh.prettyskin.sh.androidx.AppCompatButtonSH;
import com.hyh.prettyskin.sh.androidx.AppCompatCheckBoxSH;
import com.hyh.prettyskin.sh.androidx.AppCompatCheckedTextViewSH;
import com.hyh.prettyskin.sh.androidx.AppCompatEditTextSH;
import com.hyh.prettyskin.sh.androidx.AppCompatImageButtonSH;
import com.hyh.prettyskin.sh.androidx.AppCompatImageViewSH;
import com.hyh.prettyskin.sh.androidx.AppCompatRadioButtonSH;
import com.hyh.prettyskin.sh.androidx.AppCompatRatingBarSH;
import com.hyh.prettyskin.sh.androidx.AppCompatSeekBarSH;
import com.hyh.prettyskin.sh.androidx.AppCompatTextViewSH;
import com.hyh.prettyskin.sh.androidx.BottomAppBarSH;
import com.hyh.prettyskin.sh.androidx.BottomNavigationViewSH;
import com.hyh.prettyskin.sh.androidx.CardViewSH;
import com.hyh.prettyskin.sh.androidx.ChipGroupSH;
import com.hyh.prettyskin.sh.androidx.ChipSH;
import com.hyh.prettyskin.sh.androidx.CollapsingToolbarLayoutSH;
import com.hyh.prettyskin.sh.androidx.FloatingActionButtonSH;
import com.hyh.prettyskin.sh.androidx.ForegroundLinearLayoutSH;
import com.hyh.prettyskin.sh.androidx.MaterialButtonSH;
import com.hyh.prettyskin.sh.androidx.MaterialCardViewSH;
import com.hyh.prettyskin.sh.androidx.NavigationViewSH;
import com.hyh.prettyskin.sh.androidx.SwipeRefreshLayoutSH;
import com.hyh.prettyskin.sh.androidx.SwitchCompatSH;
import com.hyh.prettyskin.sh.androidx.TabLayoutSH;
import com.hyh.prettyskin.sh.androidx.TextInputEditTextSH;
import com.hyh.prettyskin.sh.androidx.TextInputLayoutSH;
import com.hyh.prettyskin.sh.androidx.XToolbarSH;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @description
 * @data 2019/4/10
 */

public class AndroidXSkinHandlerMap implements ISkinHandlerMap {

    private final Map<Class, ISkinHandler> mMap = new HashMap<>();

    public ISkinHandler get(Class viewClass) {
        if (viewClass == null) return null;
        ISkinHandler skinHandler = mMap.get(viewClass);
        if (skinHandler != null) return skinHandler;
        switch (viewClass.getName()) {
            case "androidx.appcompat.widget.AppCompatButton": {
                skinHandler = new AppCompatButtonSH();
                break;
            }
            case "androidx.appcompat.view.menu.ActionMenuItemView": {
                skinHandler = new ActionMenuItemViewSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatCheckBox": {
                skinHandler = new AppCompatCheckBoxSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatCheckedTextView": {
                skinHandler = new AppCompatCheckedTextViewSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatEditText": {
                skinHandler = new AppCompatEditTextSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatImageButton": {
                skinHandler = new AppCompatImageButtonSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatImageView": {
                skinHandler = new AppCompatImageViewSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatRadioButton": {
                skinHandler = new AppCompatRadioButtonSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatRatingBar": {
                skinHandler = new AppCompatRatingBarSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatSeekBar": {
                skinHandler = new AppCompatSeekBarSH();
                break;
            }
            case "androidx.appcompat.widget.AppCompatTextView": {
                skinHandler = new AppCompatTextViewSH();
                break;
            }
            case "androidx.appcompat.widget.SwitchCompat": {
                skinHandler = new SwitchCompatSH();
                break;
            }
            case "androidx.appcompat.widget.Toolbar": {
                skinHandler = new XToolbarSH();
                break;
            }
            case "androidx.swiperefreshlayout.widget.SwipeRefreshLayout": {
                skinHandler = new SwipeRefreshLayoutSH();
                break;
            }
            case "androidx.cardview.widget.CardView": {
                skinHandler = new CardViewSH();
                break;
            }
            case "com.google.android.material.appbar.AppBarLayout": {
                skinHandler = new AppBarLayoutSH();
                break;
            }
            case "com.google.android.material.bottomappbar.BottomAppBar": {
                skinHandler = new BottomAppBarSH();
                break;
            }
            case "com.google.android.material.navigation.NavigationView": {
                skinHandler = new NavigationViewSH();
                break;
            }
            case "com.google.android.material.bottomnavigation.BottomNavigationView": {
                skinHandler = new BottomNavigationViewSH();
                break;
            }
            case "com.google.android.material.tabs.TabLayout": {
                skinHandler = new TabLayoutSH();
                break;
            }
            case "com.google.android.material.appbar.CollapsingToolbarLayout": {
                skinHandler = new CollapsingToolbarLayoutSH();
                break;
            }
            case "com.google.android.material.floatingactionbutton.FloatingActionButton": {
                skinHandler = new FloatingActionButtonSH();
                break;
            }
            case "com.google.android.material.textfield.TextInputEditText": {
                skinHandler = new TextInputEditTextSH();
                break;
            }
            case "com.google.android.material.textfield.TextInputLayout": {
                skinHandler = new TextInputLayoutSH();
                break;
            }
            case "com.google.android.material.internal.FlowLayout": {
                skinHandler = new FlowLayoutSH();
                break;
            }
            case "com.google.android.material.chip.ChipGroup": {
                skinHandler = new ChipGroupSH();
                break;
            }
            case "com.google.android.material.chip.Chip": {
                skinHandler = new ChipSH();
                break;
            }
            case "com.google.android.material.internal.ForegroundLinearLayout": {
                skinHandler = new ForegroundLinearLayoutSH();
                break;
            }
            case "com.google.android.material.button.MaterialButton": {
                skinHandler = new MaterialButtonSH();
                break;
            }
            case "com.google.android.material.card.MaterialCardView": {
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