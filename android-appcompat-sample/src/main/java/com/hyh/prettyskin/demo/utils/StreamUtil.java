package com.hyh.prettyskin.demo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator
 * @description
 * @data 2018/3/26
 */

public class StreamUtil {


    public static String stream2String(InputStream inputStream) throws IOException {
        String result = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        try {
            if (inputStream != null) {
                bis = new BufferedInputStream(inputStream);
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = bis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                result = baos.toString("utf-8");
            }
        } finally {
            close(baos, bis);
        }
        return result;
    }

    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length <= 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean copyFileToTargetPath(InputStream inputStream, String targetPath) {
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

    private static boolean copyFileToTargetPath(String filePath, String targetPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(filePath));
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
            close(bos, bis);
        }
        return false;
    }
}
