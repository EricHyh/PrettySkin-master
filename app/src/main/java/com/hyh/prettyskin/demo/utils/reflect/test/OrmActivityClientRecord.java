package com.hyh.prettyskin.demo.utils.reflect.test;

import android.app.Activity;

import com.hyh.prettyskin.demo.utils.reflect.FieldName;
import com.hyh.prettyskin.demo.utils.reflect.TargetTypePath;
import com.hyh.prettyskin.utils.reflect.FieldName;
import com.hyh.prettyskin.utils.reflect.TargetTypePath;

/**
 * @author Administrator
 * @description
 * @data 2018/11/16
 */
@TargetTypePath("android.app.ActivityThread$ActivityClientRecord")
public interface OrmActivityClientRecord {

    @FieldName("activity")
    Activity getActivity();

    @FieldName("paused")
    boolean isPaused();

    @FieldName("stopped")
    boolean isStopped();

}
