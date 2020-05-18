package com.hyh.prettyskin;

import android.content.Context;
import android.text.TextUtils;

import com.hyh.prettyskin.utils.PrettySkinThread;
import com.hyh.prettyskin.utils.PrettySkinUtils;
import com.hyh.prettyskin.utils.SkinLogger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @description
 * @data 2020/5/18
 */
public class AssetsApkThemeSkin extends BaseSkin {

    private static Map<String, File> sUsedAssetsSkinMap = new ConcurrentHashMap<>();
    private static Map<String, Boolean> sCheckAssetsSkinMap = new ConcurrentHashMap<>();

    private final Context mContext;
    private final String mAssetsPath;
    private final String mSaveDir;
    private final int mIndex;

    private ApkThemeSkin mApkThemeSkin;

    public AssetsApkThemeSkin(Context context, String assetsPath, int index) {
        this(context, assetsPath, null, index);
    }

    public AssetsApkThemeSkin(Context context, String assetsPath, String saveDir, int index) {
        this.mContext = context.getApplicationContext();
        this.mAssetsPath = assetsPath;
        this.mSaveDir = saveDir == null ?
                new File(context.getFilesDir(), "skin" + File.separator + assetsPath).getAbsolutePath() :
                new File(saveDir, assetsPath).getAbsolutePath();
        this.mIndex = index;
    }

    @Override
    protected AttrValue getInnerAttrValue(String attrKey) {
        return mApkThemeSkin != null ? mApkThemeSkin.getAttrValue(attrKey) : null;
    }

    @Override
    public boolean loadSkinAttrs() {
        if (mApkThemeSkin != null) {
            return true;
        }
        File usedAssetsSkin = sUsedAssetsSkinMap.get(mAssetsPath);
        if (usedAssetsSkin == null) {
            usedAssetsSkin = getUsedAssetsSkin();
        }
        if (usedAssetsSkin == null) {
            return loadAssetsSkin();
        }
        long lastVersionCode = PrettySkinUtils.getLastAppVersionCode(mContext, mAssetsPath);
        long versionCode = PrettySkinUtils.getVersionCode(mContext);
        if (lastVersionCode == versionCode) {
            ApkThemeSkin apkThemeSkin = new ApkThemeSkin(mContext, usedAssetsSkin.getAbsolutePath(), mIndex);
            boolean loadSkinAttrs = apkThemeSkin.loadSkinAttrs();
            if (loadSkinAttrs) {
                mApkThemeSkin = apkThemeSkin;
                sUsedAssetsSkinMap.put(mAssetsPath, usedAssetsSkin);
                checkAssetsSkin(usedAssetsSkin);
                return true;
            } else {
                return loadAssetsSkin();
            }
        } else {
            return loadAssetsSkin();
        }
    }

    private void deleteUnusedAssetsSkin(File usedAssetsSkin) {
        File skinFile0 = getAssetsSkin(0);
        File skinFile1 = getAssetsSkin(1);
        File skinFile2 = getAssetsSkin(2);

        if (!skinFile0.getName().equals(usedAssetsSkin.getName())) {
            deleteFile(skinFile0);
        }
        if (!skinFile1.getName().equals(usedAssetsSkin.getName())) {
            deleteFile(skinFile1);
        }
        if (!skinFile2.getName().equals(usedAssetsSkin.getName())) {
            deleteFile(skinFile2);
        }
    }

    private void checkAssetsSkin(File usedAssetsSkin) {
        Boolean check = sCheckAssetsSkinMap.get(mAssetsPath);
        if (check != null && check) {
            return;
        }
        PrettySkinThread.execute(() -> {
            try {
                InputStream inputStream = mContext.getAssets().open(mAssetsPath);
                String assetsMD5 = streamToMD5(inputStream);
                String usedAssetsSkinMD5 = fileToMD5(usedAssetsSkin);
                if (TextUtils.equals(assetsMD5, usedAssetsSkinMD5)) {
                    sCheckAssetsSkinMap.put(mAssetsPath, true);
                    return;
                }
                File nextAssetsSkin = getNextAssetsSkin();
                boolean unZip = unZipAssetsFile(nextAssetsSkin);
                if (!unZip) {
                    deleteFile(nextAssetsSkin);
                    sCheckAssetsSkinMap.put(mAssetsPath, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private boolean loadAssetsSkin() {
        File nextAssetsSkin = getNextAssetsSkin();
        boolean unZip = unZipAssetsFile(nextAssetsSkin);
        if (unZip) {
            ApkThemeSkin apkThemeSkin = new ApkThemeSkin(mContext, nextAssetsSkin.getAbsolutePath(), mIndex);
            boolean loadSkinAttrs = apkThemeSkin.loadSkinAttrs();
            if (loadSkinAttrs) {
                mApkThemeSkin = apkThemeSkin;
                PrettySkinUtils.saveLastAppVersionCode(mContext, mAssetsPath);
                sUsedAssetsSkinMap.put(mAssetsPath, nextAssetsSkin);
                deleteUnusedAssetsSkin(nextAssetsSkin);
            } else {
                deleteFile(nextAssetsSkin);
            }
            return loadSkinAttrs;
        }
        return false;
    }

    private File getUsedAssetsSkin() {
        File skinFile0 = getAssetsSkin(0);
        File skinFile1 = getAssetsSkin(1);
        File skinFile2 = getAssetsSkin(2);
        return getUsedSkin(skinFile0, skinFile1, skinFile2);
    }

    private File getNextAssetsSkin() {
        File skinFile0 = getAssetsSkin(0);
        File skinFile1 = getAssetsSkin(1);
        File skinFile2 = getAssetsSkin(2);
        return getNextSkin(skinFile0, skinFile1, skinFile2);
    }

    private File getAssetsSkin(int index) {
        return new File(ensureCreated(new File(mSaveDir)), index + ".dex");
    }

    private File getUsedSkin(File skinFile0, File skinFile1, File skinFile2) {
        int flag = 0;
        flag += (skinFile0.exists() && skinFile0.length() > 0) ? 0b001 : 0;
        flag += (skinFile1.exists() && skinFile1.length() > 0) ? 0b010 : 0;
        flag += (skinFile2.exists() && skinFile2.length() > 0) ? 0b100 : 0;
        switch (flag) {
            case 1:
            case 5: {
                return skinFile0;
            }
            case 2:
            case 3: {
                return skinFile1;
            }
            case 4:
            case 6:
            case 7: {
                return skinFile2;
            }
        }
        return null;
    }

    private File getNextSkin(File skinFile0, File skinFile1, File skinFile2) {
        File file;
        int flag = 0;
        flag += (skinFile0.exists() && skinFile0.length() > 0) ? 0b001 : 0;
        flag += (skinFile1.exists() && skinFile1.length() > 0) ? 0b010 : 0;
        flag += (skinFile2.exists() && skinFile2.length() > 0) ? 0b100 : 0;
        switch (flag) {
            case 0:
            case 4:
            case 6:
            case 7:
            default: {
                file = skinFile0;
                break;
            }
            case 1:
            case 5: {
                file = skinFile1;
                break;
            }
            case 2:
            case 3: {
                file = skinFile2;
                break;
            }
        }
        if (flag == 7) {
            deleteFile(skinFile0);
            deleteFile(skinFile1);
        }
        return file;
    }


    private boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.exists()) {
                boolean delete = file.delete();
                SkinLogger.d("deleteFile " + file.getAbsolutePath() + " is delete:" + delete);
                return delete;
            }
        } catch (Exception e) {
            SkinLogger.d("deleteFile failed, filePath:" + file.getAbsolutePath());
        }
        return false;
    }

    private boolean unZipAssetsFile(File targetFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            InputStream inputStream = mContext.getAssets().open(mAssetsPath);
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(new FileOutputStream(targetFile, false));
            byte[] buffer = new byte[16 * 1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return true;
        } catch (Exception e) {
            SkinLogger.d("unZipAssetsFile failed: ", e);
        } finally {
            close(bos, bis);
        }
        return false;
    }

    private void close(Closeable... closeables) {
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
                SkinLogger.d(closeable + " close failed");
            }
        }
    }

    private File ensureCreated(File folder) {
        if (!folder.exists() && !folder.mkdirs()) {
            SkinLogger.w("Unable to create the directory:" + folder.getPath());
        }
        return folder;
    }


    private String fileToMD5(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return "";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte[] buffer = new byte[16 * 1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            SkinLogger.d("file convert to md5 failed, filePath:" + file.getAbsolutePath());
        } finally {
            close(in);
        }
        if (digest != null) {
            return bytesToHexString(digest.digest());
        }
        return "";
    }

    private String fileToMD5(String filePath) {
        return fileToMD5(new File(filePath));
    }

    private String streamToMD5(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        MessageDigest digest = null;
        BufferedInputStream in = null;
        byte[] buffer = new byte[16 * 1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new BufferedInputStream(inputStream);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            SkinLogger.d("stream convert to md5 failed");
        } finally {
            close(in);
        }
        if (digest != null) {
            return bytesToHexString(digest.digest());
        }
        return "";
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }
}