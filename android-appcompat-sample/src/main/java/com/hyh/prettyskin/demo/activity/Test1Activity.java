package com.hyh.prettyskin.demo.activity;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.ISkin;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.ValueType;

import java.util.Arrays;

/**
 * @author Administrator
 * @description
 * @data 2019/5/18
 */

public class Test1Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextTest(this);
        PrettySkin.getInstance().setContextSkinable(contextWrapper);
        setContentView(LayoutInflater.from(contextWrapper).inflate(R.layout.activity_test1, null));
    }

    public void openTest2Activity(View view) {
        startActivity(new Intent(this, Test2Activity.class));
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }

    public void refreshSkin(View view) {
        ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
        currentSkin.setOuterAttrValue("tv_bg", new AttrValue(null, ValueType.TYPE_COLOR_INT, 0xFF000000));
        PrettySkin.getInstance().notifySkinAttrChanged(Arrays.asList("tv_bg"));
    }
}
