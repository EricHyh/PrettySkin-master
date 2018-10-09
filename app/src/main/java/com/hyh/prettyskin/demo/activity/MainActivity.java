package com.hyh.prettyskin.demo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.android.SkinInflateFactory;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //LayoutInflater.from(this).setFactory2();
        getLayoutInflater().setFactory2(new SkinInflateFactory());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }
}
