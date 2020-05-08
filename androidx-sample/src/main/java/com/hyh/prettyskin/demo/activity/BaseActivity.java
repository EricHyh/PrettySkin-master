package com.hyh.prettyskin.demo.activity;

import android.content.res.TypedArray;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.SkinChangedListener;
import com.hyh.prettyskin.demo.utils.StatusBarUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Administrator
 * @description
 * @data 2019/4/29
 */

public abstract class BaseActivity extends AppCompatActivity implements SkinChangedListener {

    private int mDefaultStatusBarColor;
    private int mDefaultStatusBarMode;

    protected void initStatusBar() {
        PrettySkin.getInstance().addSkinReplaceListener(this);
        TypedArray typedArray = obtainStyledAttributes(R.styleable.PrettySkin);
        mDefaultStatusBarColor = typedArray.getColor(R.styleable.PrettySkin_status_bar_color, 0xFFDDDDDD);
        mDefaultStatusBarMode = typedArray.getColor(R.styleable.PrettySkin_status_bar_mode, 0);
        typedArray.recycle();
        ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
        if (currentSkin != null) {
            setStatusBar(currentSkin);
        } else {
            StatusBarUtil.setColor(this, mDefaultStatusBarColor, 0);
            switch (mDefaultStatusBarMode) {
                case 0: {
                    StatusBarUtil.setLightMode(this);
                    break;
                }
                case 1: {
                    StatusBarUtil.setDarkMode(this);
                    break;
                }
            }
        }
    }

    @Override
    public void onSkinChanged(ISkin skin) {
        setStatusBar(skin);
    }

    @Override
    public void onSkinAttrChanged(ISkin skin, List<String> changedAttrKeys) {
        if (changedAttrKeys.contains("status_bar_color") || changedAttrKeys.contains("status_bar_mode")) {
            setStatusBar(skin);
        }
    }

    @Override
    public void onSkinRecovered() {
        StatusBarUtil.setColor(this, mDefaultStatusBarColor, 0);
        switch (mDefaultStatusBarMode) {
            case 0: {
                StatusBarUtil.setLightMode(this);
                break;
            }
            case 1: {
                StatusBarUtil.setDarkMode(this);
                break;
            }
        }
    }

    private void setStatusBar(ISkin currentSkin) {
        AttrValue statusBarColor = currentSkin.getAttrValue("status_bar_color");
        if (statusBarColor != null) {
            StatusBarUtil.setColor(this, statusBarColor.getTypedValue(int.class, mDefaultStatusBarColor), 0);
        }

        AttrValue statusBarMode = currentSkin.getAttrValue("status_bar_mode");
        if (statusBarMode != null) {
            switch (statusBarMode.getTypedValue(int.class, 0)) {
                case 0: {
                    StatusBarUtil.setLightMode(this);
                    break;
                }
                case 1: {
                    StatusBarUtil.setDarkMode(this);
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PrettySkin.getInstance().removeSkinReplaceListener(this);
    }
}