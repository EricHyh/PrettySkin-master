package com.hyh.prettyskin.utils.reflect.test;

import android.app.Application;
import android.os.Handler;
import android.os.IBinder;
import android.util.ArrayMap;

import com.hyh.prettyskin.utils.reflect.FieldName;
import com.hyh.prettyskin.utils.reflect.MethodName;
import com.hyh.prettyskin.utils.reflect.TargetTypePath;
import com.hyh.prettyskin.utils.reflect.Static;

/**
 * @author Administrator
 * @description
 * @data 2018/11/16
 */
@TargetTypePath("android.app.ActivityThread")
public interface OrmActivityThread {

    //static volatile IPackageManager sPackageManager;
    @Static
    @FieldName("sPackageManager")
    Object getPackageManager();

    //ArrayMap<IBinder, ActivityClientRecord> mActivities = new ArrayMap<>();
    @FieldName("mActivities")
    ArrayMap<IBinder, OrmActivityClientRecord> getActivities();

    @Static
    @MethodName("currentActivityThread")
    OrmActivityThread currentActivityThread();

    @Static
    @MethodName("isSystem")
    boolean isSystem();

    @Static
    @MethodName("currentOpPackageName")
    String currentOpPackageName();

    @Static
    @MethodName("currentPackageName")
    String currentPackageName();

    @Static
    @MethodName("currentProcessName")
    String currentProcessName();

    @Static
    @MethodName("currentApplication")
    Application currentApplication();

    @MethodName("getHandler")
    Handler getHandler();
}
