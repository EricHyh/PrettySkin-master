package com.hyh.prettyskin.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hyh.prettyskin.R;
import com.hyh.prettyskin.utils.reflect.Reflect;

/**
 * @author Administrator
 * @description
 * @data 2018/11/17
 */

public class ReflectTestActivity extends Activity {

    private static final String TAG = "ReflectTestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect_test);
    }

    public void testReflect(View view) {
        //1.找不到类
        /*Character getChar = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity.Test1")
                .method("getChar", char.class)
                .defaultValue('a')
                .printException()
                .invoke(null);
        Log.d(TAG, "testReflect: 1-getChar=" + getChar);*/

        //2.找不到Filed
        /*Boolean aBoolean = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .filed("Boolean", boolean.class)
                .printException()
                .get(null);
        Log.d(TAG, "testReflect: 2-aBoolean=" + aBoolean);*/


        //3.获取Filed值失败
        /*String str = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .filed("str", String.class)
                .printException()
                .defaultValue("hhh")
                .get(null);
        Log.d(TAG, "testReflect: 3-str=" + str);*/

        //4.找不到方法Method
        /*Character getchar = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .method("getchar", char.class)
                .printException()
                .invoke(null);
        Log.d(TAG, "testReflect: 4-getchar=" + getchar);*/

        //5.执行Method失败（参数不对）
        /*Integer add = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .method("add", int.class)
                .params(new Class[]{int.class, int.class}, new Object[]{1})
                .printException()
                .invoke(null);
        Log.d(TAG, "testReflect: 5-add=" + add);*/

        //6.执行Method失败（方法内部错误）
        /*Character charAt = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .method("charAt", char.class)
                .params(new Class[]{String.class, int.class}, new Object[]{null, 5})
                .printException()
                .invoke(null);
        Log.d(TAG, "testReflect: 6-charAt=" + charAt);*/

        //7.找不到Constructor

        /*byte b = 1;
        short s = 2;
        Object o = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .constructor()
                .param(byte.class, b)
                .param(short.class, s)
                .printException()
                .newInstance();
        Log.d(TAG, "testReflect: 7-o=" + o);*/

        //8.执行Constructor失败（参数不对）

        //9.执行RefConstructor失败（方法内部错误）
        /*Object o = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .constructor()
                .param(String.class, "")
                .param(int.class, 5)
                .printException()
                .newInstance();
        Log.d(TAG, "testReflect: 9-o=" + o);*/

        //10.filedType指定错误
        /*Character getChar = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .filed("sBoolean", char.class)
                .defaultValue('a')
                .printException()
                .get(null);
        Log.d(TAG, "testReflect: 9-o=");*/

        //11.returnType指定错误
        /*Long add = Reflect.from("com.hyh.prettyskin.demo.activity.ReflectTestActivity$Test1")
                .method("add", long.class)
                .params(new Class[]{int.class, int.class}, new Object[]{1, 2})
                .printException()
                .invoke(null);
        Log.d(TAG, "testReflect: add = " + add);*/

        //12.cls为null
        Class c = null;
        Long add = Reflect.from(c)
                .method("add", long.class)
                .params(new Class[]{int.class, int.class}, new Object[]{1, 2})
                .printException()
                .invoke(null);
        Log.d(TAG, "testReflect: add = " + add);
    }


    public static class Test1 {

        public static Boolean sBoolean = true;

        private byte b;

        private short s;

        private int i;

        private long l;

        private boolean boo;

        private float f;

        private double d;

        private String str;

        private int index;

        public Test1(byte b, short s) {
            this.b = b;
            this.s = s;
        }

        public Test1(String str, int index) {
            this.str = str;
            this.index = index;
            char aChar = getChar();
        }


        public static int add(int a, int b) {
            return a + b;
        }

        public static char charAt(String str, int index) {
            return str.charAt(index);
        }


        public char getChar() {
            return str.charAt(index);
        }

        public byte getB() {
            return b;
        }

        public short getS() {
            return s;
        }

        public int getI() {
            return i;
        }

        public long getL() {
            return l;
        }

        public boolean isBoo() {
            return boo;
        }

        public float getF() {
            return f;
        }

        public double getD() {
            return d;
        }
    }

    public static class Test2 {

        public static String[] getPath(Context context) {
            return context.getPackageName().split("\\.");
        }
    }
}
