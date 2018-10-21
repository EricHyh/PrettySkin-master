package com.hyh.prettyskin.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.core.ThemeSkin;
import com.hyh.prettyskin.utils.NumberUtil;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
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
