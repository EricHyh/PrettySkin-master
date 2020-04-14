package com.hyh.prettyskin.demo.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.hyh.prettyskin.utils.reflect.Reflect;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 * @description
 * @data 2019/1/4
 */

public class PackageUtil {

    private static final String TAG = "PrettySkinUtils";

    private static Application sApplication;

    public static Application getInitialApplication() {
        if (sApplication != null) return sApplication;
        sApplication = Reflect.from("android.app.ActivityThread")
                .method("currentApplication", Application.class)
                .invoke(null);
        return sApplication;
    }

    /**
     * 判断应用中是否有指定开启Activity的intent
     */
    public static boolean isActivityIntentExist(Context context, Intent intent) {
        try {
            return intent != null && context.getPackageManager().resolveActivity(intent, PackageManager.GET_META_DATA) != null;
        } catch (Throwable th) {
            return false;
        }
    }


    /**
     * 判断应用中是否有指定开启Service的intent
     */
    public static boolean isServiceIntentExist(Context context, Intent intent) {
        if (intent == null) {
            return false;
        }
        String packageName = intent.getPackage();
        ComponentName component = intent.getComponent();
        return !(TextUtils.isEmpty(packageName) && component == null) && context.getPackageManager().resolveService(intent, PackageManager.GET_META_DATA) != null;
    }

    public static void backToHome(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "PrettySkinUtils backToHome failed: ", e);
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            return packageInfo != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static Intent getAppLauncherIntent(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            return context.getPackageManager().getLaunchIntentForPackage(packageName);
        } catch (Exception e) {
            Log.d(TAG, "get [ " + packageName + " ] launch intent failed: ", e);
        }
        return null;
    }

    public static PackageInfo getPackageArchiveInfo(Context context, String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA);
        } catch (Exception e) {
            Log.d(TAG, "get package archive info failed: ", e);
        }
        return null;
    }

    public static boolean isAppFile(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        boolean isPackage = false;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(filePath, PackageManager.GET_META_DATA);
            if (packageInfo != null) {
                isPackage = true;
            }
        } catch (Exception e) {
            Log.d(TAG, "get package archive info failed: ", e);
        }
        return isPackage;
    }

    public static int getTargetSdkVersion(Context context) {
        Application application = getInitialApplication();
        if (application != null) {
            return application.getApplicationInfo().targetSdkVersion;
        }
        int targetSdkVersion = Build.VERSION.SDK_INT;
        try {
            String packageName = context.getPackageName();
            Log.d(TAG, "getTargetSdkVersion: packageName = " + packageName);
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            targetSdkVersion = applicationInfo.targetSdkVersion;
        } catch (Exception e) {
            Log.d(TAG, "get target sdk version failed: ", e);
        }
        Log.d(TAG, "getTargetSdkVersion: version = " + targetSdkVersion);
        return targetSdkVersion;
    }

    public static String getStringMetaData(Context context, String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        try {
            ApplicationInfo appInfo =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                if (appInfo.metaData == null) {
                    return "";
                } else {
                    return appInfo.metaData.getString(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取安卓系统属性的指定字段
     *
     * @param key 属性名称
     * @return 属性对应的内容
     */
    public static String getSystemProperties(String key) {
        try {
            @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getDeclaredMethod("get", String.class, String.class);
            String value = (String) method.invoke(null, key, "");
            return TextUtils.isEmpty(value) ? "" : value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVersionCode(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return "";
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            if (packageInfo != null) {
                return String.valueOf(packageInfo.versionCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getVersionName(Context context, String packageName) {
        if (packageName == null) {
            return "";
        }
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            if (packinfo != null) {
                return packinfo.versionName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAppName(Context context, String packageName) {
        if (packageName == null) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            if (info != null) {
                return info.loadLabel(pm).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * 获取程序 图标
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        if (packageName == null) {
            return null;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            if (info != null) {
                return info.loadIcon(pm);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 通过deepling解析系统中是否存在一个这样的activity
     */
    public static ResolveInfo queryActivityByDeepLink(Context context, String deepLink) {
        try {
            Intent intent = getIntent2startActivityByDeepLink(deepLink);
            PackageManager packageManager = context.getPackageManager();
            return packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过deepling解析系统中是否存在一个这样的activity
     */
    public static ResolveInfo queryActivityByDeepLink(Context context, String packageName, String deepLink) {
        try {
            Intent intent = getIntent2startActivityByDeepLink(deepLink);
            if (intent != null && !TextUtils.isEmpty(packageName)) {
                intent.setPackage(packageName);
            }
            PackageManager packageManager = context.getPackageManager();
            return packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过deeplink  获取应用指定的开启activity的Intent
     */
    public static Intent getIntent2startActivityByDeepLink(String deepLink) {
        if (TextUtils.isEmpty(deepLink)) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deepLink));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static void selectFirstActivityIfNecessary(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (!TextUtils.isEmpty(intent.getPackage())) {
            return;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfo != null) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo != null) {
                    String packageName = activityInfo.packageName;
                    if (!TextUtils.isEmpty(packageName) && !TextUtils.equals("android", packageName)) {
                        intent.setPackage(packageName);
                        return;
                    }
                }
            }
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
            if (resolveInfoList == null || resolveInfoList.size() <= 0) {
                return;
            }
            resolveInfo = resolveInfoList.get(0);
            String packageName = resolveInfo.activityInfo.packageName;
            if (!TextUtils.isEmpty(packageName)) {
                intent.setPackage(packageName);
            }
        } catch (Exception e) {
            //
        }
    }

    public static ResolveInfo queryIntentActivity(Context context, Intent intent) {
        if (intent == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfo != null) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo != null) {
                    String packageName = activityInfo.packageName;
                    if (!TextUtils.isEmpty(packageName) && !TextUtils.equals("android", packageName)) {
                        return resolveInfo;
                    }
                }
            }
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
            if (resolveInfoList == null || resolveInfoList.isEmpty()) {
                return null;
            }
            return resolveInfoList.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public static ResolveInfo queryIntentService(Context context, Intent intent) {
        if (intent == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ResolveInfo resolveInfo = packageManager.resolveService(intent, 0);
            if (resolveInfo != null) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                if (serviceInfo != null) {
                    String packageName = serviceInfo.packageName;
                    if (!TextUtils.isEmpty(packageName) && !TextUtils.equals("android", packageName)) {
                        return resolveInfo;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isLauncherPackage(Context context, String packageName) {
        List<String> launcherPackageNames = getLauncherPackageNames(context);
        return launcherPackageNames.contains(packageName);

    }

    public static List<String> getLauncherPackageNames(Context context) {
        List<String> packageNames = new ArrayList<>();
        try {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, 0);
            for (ResolveInfo ri : resolveInfo) {
                packageNames.add(ri.activityInfo.packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageNames;
    }

    public static ComponentName getTopActivity(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTasks = null;
            if (am != null) {
                runningTasks = am.getRunningTasks(1);
            }
            if (runningTasks != null && !runningTasks.isEmpty()) {
                return runningTasks.get(0).topActivity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取应用的签名
     *
     * @return
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static String getAppSignatureFingerprint(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            //返回包括在包中的签名信息
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            //签名信息
            Signature[] signatures = packageInfo.signatures;
            if (signatures == null || signatures.length <= 0) {
                return null;
            }
            byte[] cert = signatures[0].toByteArray();
            //将签名转换为字节数组流
            InputStream input = new ByteArrayInputStream(cert);

            //证书工厂类，这个类实现了出厂合格证算法的功能
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            //X509 证书，X.509 是一种非常通用的证书格式
            X509Certificate c = (X509Certificate) cf.generateCertificate(input);

            //加密算法的类，这里的参数可以使 MD4,MD5 等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            return byte2HexFormatted(publicKey);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    //这里是将获取到得编码进行16 进制转换
    private static String byte2HexFormatted(byte[] bytes) {
        StringBuilder str = new StringBuilder(bytes.length * 2);
        for (int index = 0; index < bytes.length; index++) {
            String h = Integer.toHexString(bytes[index]);
            int l = h.length();
            if (l == 1) {
                h = "0" + h;
            }
            if (l > 2) {
                h = h.substring(l - 2, l);
            }
            str.append(h.toUpperCase());
            if (index < (bytes.length - 1)) {
                str.append(':');
            }
        }
        return str.toString();
    }

    public static String getSettingPackageName(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
            return resolveInfo.activityInfo.packageName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}