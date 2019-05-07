package com.hyh.prettyskin.demo.base;

import android.app.Application;

import com.hyh.prettyskin.ApkThemeSkin;
import com.hyh.prettyskin.AppCompatSkinHandlerMap;
import com.hyh.prettyskin.PrettySkin;
import com.hyh.prettyskin.demo.utils.StreamUtil;
import com.hyh.prettyskin.demo.utils.ThreadManager;
import com.hyh.prettyskin.demo.widget.CustomView;
import com.hyh.prettyskin.demo.widget.CustomViewSH;

import java.io.File;
import java.io.InputStream;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrettySkin.getInstance().init(this);
        PrettySkin.getInstance().addSkinHandler(CustomView.class, new CustomViewSH());
        PrettySkin.getInstance().addSkinHandler(new AppCompatSkinHandlerMap());

        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getAssets().open("skin-package-first-debug.apk");

                    File filesDir = getFilesDir();
                    File file = new File(filesDir, "skin-package-first-debug.apk");
                    StreamUtil.copyFileToTargetPath(inputStream, file.getAbsolutePath());
                    PrettySkin.getInstance().replaceSkinSync(new ApkThemeSkin(getApplicationContext(), file.getAbsolutePath(), 0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}