package com.hyh.prettyskin.demo.base;

import android.app.Application;

import com.hyh.prettyskin.AppCompatSkinHandlerMap;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.demo.lifecycle.ActivityLifecycleHelper;
import com.hyh.prettyskin.demo.sh.ShapeViewSH;
import com.hyh.prettyskin.demo.widget.ShapeView;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycleHelper.getInstance().init(this);

        PrettySkin.getInstance().init(this);
        PrettySkin.getInstance().addSkinHandler(ShapeView.class, new ShapeViewSH());//添加ShapeView自定义属性处理器
        PrettySkin.getInstance().addSkinHandler(new AppCompatSkinHandlerMap());//添加appcompat包中所有View的自定义属性处理器
        /*ThemeSkin themeSkin = new ThemeSkin(this, R.style.PrettySkin_black, R.styleable.class, "PrettySkin");
        PrettySkin.getInstance().replaceSkinAsync(themeSkin, new SkinReplaceListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int replaceCode) {

            }
        });*/

    }
}