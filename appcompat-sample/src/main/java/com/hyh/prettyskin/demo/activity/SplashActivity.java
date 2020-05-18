package com.hyh.prettyskin.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.hyh.prettyskin.AssetsApkThemeSkin;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.SkinReplaceListener;
import com.hyh.prettyskin.ThemeSkin;
import com.hyh.prettyskin.demo.base.SkinStyle;
import com.hyh.prettyskin.demo.utils.PreferenceUtil;

/**
 * @author Administrator
 * @description
 * @data 2020/5/14
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ISkin skin = null;
        int skinStyle = PreferenceUtil.getInt(this, "skin_style", -1);
        switch (skinStyle) {
            case SkinStyle.WHITE: {
                skin = new ThemeSkin(this, R.style.PrettySkin_white, R.styleable.class, "PrettySkin");
                break;
            }
            case SkinStyle.BLACK: {
                skin = new ThemeSkin(this, R.style.PrettySkin_black, R.styleable.class, "PrettySkin");
                break;
            }
            case SkinStyle.PURPLE: {
                skin = new AssetsApkThemeSkin(this, "skin-package-first", 0);
                break;
            }
            case SkinStyle.ORANGE: {
                skin = new AssetsApkThemeSkin(this, "skin-package-first", 1);
                break;
            }
        }
        if (skin != null) {
            PrettySkin.getInstance().replaceSkinAsync(skin, new SkinReplaceListener() {
                @Override
                public void onSuccess() {
                    MainActivity.start(getApplicationContext());
                    finish();
                }

                @Override
                public void onFailure(int replaceCode) {
                    MainActivity.start(getApplicationContext());
                    finish();
                }
            });
        } else {
            MainActivity.start(getApplicationContext());
            finish();
        }
    }
}