package com.hyh.prettyskin.demo.activity;

import android.app.Activity;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.hyh.prettyskin.R;

/**
 * @author Administrator
 * @description
 * @data 2019/5/18
 */

public class Test2Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextTest(this);
        setContentView(LayoutInflater.from(contextWrapper).inflate(R.layout.activity_test2, null));
    }
}
