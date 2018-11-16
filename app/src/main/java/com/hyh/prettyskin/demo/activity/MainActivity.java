package com.hyh.prettyskin.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.core.ThemeSkin;
import com.hyh.prettyskin.utils.NumberUtil;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

//状态栏：https://juejin.im/entry/591ec14a570c3500698e848e
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        return (int) Reflect.from("com.android.internal.R$dimen")
                .filed("status_bar_height")
                .defaultValue(0)
                .get(null);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }

    public void changeSkin(View view) {
        //R.styleable.PrettySkin
        ThemeSkin themeSkin = new ThemeSkin(this, R.style.PrettySkin_1, R.styleable.class, "PrettySkin");
        PrettySkin.getInstance().replaceSkinSync(themeSkin);
    }

    public void recoverSkin(View view) {
        PrettySkin.getInstance().recoverDefaultSkin();
        boolean hexNumber = NumberUtil.isHexNumber("0x1");
        Log.d(TAG, "recoverSkin: ");
    }

    public void startSecondAct(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
