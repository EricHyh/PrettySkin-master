package com.hyh.prettyskin.demo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }

    public void changeSkin(View view) {
        PrettySkin.getInstance().replaceSkin(this, R.style.PrettySkin_1, R.styleable.class.getName(), "PrettySkin");
    }
}
