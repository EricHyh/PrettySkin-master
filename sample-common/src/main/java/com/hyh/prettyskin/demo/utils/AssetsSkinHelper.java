package com.hyh.prettyskin.demo.utils;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author Administrator
 * @description
 * @data 2020/5/7
 */
public class AssetsSkinHelper {

    public static boolean isUnziped(Context context) {
        File filesDir = context.getFilesDir();
        File skinDir = new File(filesDir, "skin");
        skinDir.mkdirs();
        return new File(skinDir, "assets.skin-package-first").exists();
    }

    public static boolean unzipAssetsSkin(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("skin-package-first");
            File filesDir = context.getFilesDir();
            File skinDir = new File(filesDir, "skin");
            skinDir.mkdirs();
            File file = new File(skinDir, "assets.skin-package-first");
            return copyFileToTargetPath(inputStream, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean copyFileToTargetPath(InputStream inputStream, String targetPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(new FileOutputStream(targetPath, false));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(bos, bis);
        }
        return false;
    }

    public static String getSkinPath(Context context) {
        File filesDir = context.getFilesDir();
        File skinDir = new File(filesDir, "skin");
        return new File(skinDir, "assets.skin-package-first").getAbsolutePath();
    }
}