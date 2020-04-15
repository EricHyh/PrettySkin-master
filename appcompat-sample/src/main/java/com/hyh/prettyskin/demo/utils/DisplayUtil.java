package com.hyh.prettyskin.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Eric_He on 2016/10/3.
 */

public class DisplayUtil {

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        size[0] = dm.widthPixels;
        size[1] = dm.heightPixels;
        return size;
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float[] getScreenDpSize(Context context) {
        float[] size = new float[2];
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        size[0] = dm.widthPixels / dm.density;
        size[1] = dm.heightPixels / dm.density;
        return size;
    }

    public static float getScreenDpWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels / dm.density;
    }

    public static float getScreenDpHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels / dm.density;
    }

    public static int getDensityDpi(Context context) {
        Resources resources = context.getResources();
        if (resources != null) {
            DisplayMetrics dm = resources.getDisplayMetrics();
            if (dm != null) {
                return dm.densityDpi;
            }
        }
        return 0;
    }

    public static String getDensityDpiStr(Context context) {
        int densityDpi = getDensityDpi(context);
        if (densityDpi <= 0) return null;
        String densityDpiStr = null;
        if (densityDpi <= 120) {
            densityDpiStr = "ldpi";
        } else if (densityDpi > 120 && densityDpi <= 160) {
            densityDpiStr = "mdpi";
        } else if (densityDpi > 160 && densityDpi <= 240) {
            densityDpiStr = "hdpi";
        } else if (densityDpi > 240 && densityDpi <= 320) {
            densityDpiStr = "xhdpi";
        } else if (densityDpi > 320 && densityDpi <= 480) {
            densityDpiStr = "xxhdpi";
        } else if (densityDpi > 480) {
            densityDpiStr = "xxxhdpi";
        }
        return densityDpiStr;
    }


    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getWindowVisibleHeight(View view) {
        Rect outRect = new Rect();
        view.getWindowVisibleDisplayFrame(outRect);
        return outRect.bottom - outRect.top;
    }

    public boolean hasNavigationBar(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }

        int creenRealHeight = realDisplayMetrics.heightPixels;
        int creenRealWidth = realDisplayMetrics.widthPixels;

        float diagonalPixels = (float) Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
        float screenSize = (diagonalPixels / (160f * density)) * 1f;

        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBarFun = false;
        if (id > 0) {
            hasNavBarFun = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBarFun = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBarFun = true;
            }
        } catch (Exception e) {
            hasNavBarFun = false;
        }
        return hasNavBarFun;
    }

    public static int getScreenOrientation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return context.getResources().getConfiguration().orientation;
        } else {
            int rotation = windowManager.getDefaultDisplay().getRotation();
            int screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            switch (rotation) {
                case Surface.ROTATION_0: {
                    screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                }
                case Surface.ROTATION_90: {
                    screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                }
                case Surface.ROTATION_180: {
                    screenOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                }
                case Surface.ROTATION_270: {
                    screenOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                }
            }
            return screenOrientation;
        }
    }

    public static int[] getScreenSize(Activity activity) {
        int[] size = new int[2];
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getSize(point);
        }
        size[0] = point.x;
        size[1] = point.y;
        return size;
    }

    public static float getScreenDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

    public static boolean isDarkMode(Context context) {
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }
}
