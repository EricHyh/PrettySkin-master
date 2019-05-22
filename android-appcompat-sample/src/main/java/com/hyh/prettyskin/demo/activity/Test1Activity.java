package com.hyh.prettyskin.demo.activity;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.ThemeSkin;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.drawable.DynamicDrawable;

/**
 * @author Administrator
 * @description
 * @data 2019/5/18
 */

public class Test1Activity extends Activity {

    private static final String TAG = "Test1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextWrapper contextWrapper = new ContextTest(this);
        PrettySkin.getInstance().setContextSkinable(contextWrapper);
        setContentView(LayoutInflater.from(contextWrapper).inflate(R.layout.activity_test1, null));

        View view = findViewById(R.id.view_test_bg);

        view.setBackgroundDrawable(new DynamicDrawable("view_bg2", new ColorDrawable(Color.RED)));


        AttrValue attrValue1 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.dimen.test_dimen_1);
        AttrValue attrValue2 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.fraction.test_fraction_1);
        AttrValue attrValue3 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.bool.test_bool_1);
        AttrValue attrValue4 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.integer.test_integer_1);
        AttrValue attrValue5 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.drawable.custom_drawable);
        AttrValue attrValue6 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.color.black);
        AttrValue attrValue7 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.color.custom_color);
        AttrValue attrValue8 = new AttrValue(this, ValueType.TYPE_REFERENCE, R.anim.layout);


        Log.d(TAG, "onCreate: ");


    }


    public void openTest2Activity(View view) {
        startActivity(new Intent(this, Test2Activity.class));
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }

    public void refreshSkin(View view) {
        ThemeSkin prettySkin = new ThemeSkin(this, R.style.PrettySkin_1, R.styleable.class, "PrettySkin");
        prettySkin.setOuterAttrValue("tv_bg", new AttrValue(null, ValueType.TYPE_COLOR_INT, 0xFF000000));
        PrettySkin.getInstance().replaceSkinSync(prettySkin);
    }
}
