package com.hyh.prettyskin.demo.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * @author Administrator
 * @description
 * @data 2019/8/9
 */

public class PhoneStateUtil {

    public static void listenCallState(Context context, final PhoneStateListener listener) {
        try {
            final TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final int events = PhoneStateListener.LISTEN_CALL_STATE;
            if (manager != null) {
                if (ThreadUtil.isMain()) {
                    manager.listen(listener, events);
                } else {
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            manager.listen(listener, events);
                        }
                    });
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static boolean isIdleState(Context context) {
        return getCallState(context) == TelephonyManager.CALL_STATE_IDLE;
    }

    public static int getCallState(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                return manager.getCallState();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return TelephonyManager.CALL_STATE_IDLE;
    }

    public static boolean isDeviceSecureCompat(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return isDeviceSecure(context);
        } else {
            return isKeyguardSecure(context);
        }
    }


    /**
     * 判断设备是否设置了密码锁（PIN, pattern or password or a SIM card）
     *
     * @param context
     * @return
     */
    private static boolean isKeyguardSecure(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            return keyguardManager != null && keyguardManager.isKeyguardSecure();
        } else {
            return isSecure(context);
        }
    }

    private static boolean isSecure(Context context) {
        boolean isSecured;
        String classPath = "com.android.internal.widget.LockPatternUtils";
        try {
            Class<?> lockPatternClass = Class.forName(classPath);
            Object lockPatternObject = lockPatternClass.getConstructor(Context.class).newInstance(context);
            Method method = lockPatternClass.getMethod("isSecure");
            isSecured = (boolean) method.invoke(lockPatternObject);
        } catch (Throwable e) {
            isSecured = false;
        }
        return isSecured;
    }

    /**
     * 判断设备是否设置了密码锁（PIN, pattern or password）
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static boolean isDeviceSecure(Context context) {
        try {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            return keyguardManager != null && keyguardManager.isDeviceSecure();
        } catch (Throwable e) {
            return false;
        }
    }


    /**
     * 判断当前是否处于锁屏状态
     *
     * @param context
     * @return
     */
    public static boolean isDeviceLocked(Context context) {
        try {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                return keyguardManager.isDeviceLocked();
            } else {
                return keyguardManager.isKeyguardLocked();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean isDeviceLockedByFingerprint(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDeviceLocked(context)) {
                FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
                if (fingerprintManager == null) {
                    return false;
                }
                if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
                    return true;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断当前是否处于锁屏状态
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isKeyguardLocked(Context context) {
        try {
            KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            return mKeyguardManager != null && mKeyguardManager.isKeyguardLocked();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 判断当前是否处于锁屏界面
     *
     * @param context
     * @return
     */
    public static boolean inKeyguardRestrictedInputMode(Context context) {
        try {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            return keyguardManager != null && keyguardManager.inKeyguardRestrictedInputMode();
        } catch (Throwable e) {
            return false;
        }
    }


    /**
     * 屏幕是否处于亮屏状态
     *
     * @param context
     * @return
     */
    public static boolean isScreenOn(Context context) {
        try {
            PowerManager powerManger = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (powerManger == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                return powerManger.isInteractive();
            } else {
                return powerManger.isScreenOn();
            }
        } catch (Throwable e) {
            return false;
        }
    }


    /**
     * 屏幕是否处于黑屏状态
     *
     * @param context
     * @return
     */
    public static boolean isScreenOff(Context context) {
        return !isScreenOn(context);
    }

}
