package com.hyh.prettyskin.demo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hyh.prettyskin.R;

/**
 * Created by Eric_He on 2018/10/14.
 */

public class SecondActivity extends BaseActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
