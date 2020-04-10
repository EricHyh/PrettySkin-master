package com.hyh.prettyskin.demo.base;

import android.app.Application;

/**
 * @author Administrator
 * @description
 * @data 2018/9/30
 */

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //PrettySkin.getInstance().init(this);
        //PrettySkin.getInstance().addSkinHandler(CustomView.class, new CustomViewSH());
        //PrettySkin.getInstance().addSkinHandler(new AppCompatSkinHandlerMap());

       /* ThreadManager.execute(new Runnable() {
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
        });*/


    }
}