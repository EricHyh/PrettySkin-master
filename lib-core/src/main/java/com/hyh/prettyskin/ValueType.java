package com.hyh.prettyskin;

public class ValueType {

    /**
     * 空类型，值为null
     */
    public static final int TYPE_NULL = 0;

    /**
     * 引用类型，例如resourceId
     */
    public static final int TYPE_REFERENCE = 1;

    /**
     * int类型，例如enum、progress等
     */
    public static final int TYPE_INT = 2;

    //public static final int TYPE_LONG = 3;

    /**
     * float类型，例如ratio、px
     */
    public static final int TYPE_FLOAT = 4;

    //public static final int TYPE_DOUBLE = 5;

    /**
     * bool类型
     */
    public static final int TYPE_BOOLEAN = 6;

    //public static final int TYPE_CHARSEQUENCE = 7;

    /**
     * String或者CharSequence类型
     */
    public static final int TYPE_STRING = 8;

    /**
     * int型color
     */
    public static final int TYPE_COLOR_INT = 9;

    /**
     * ColorStateList
     */
    public static final int TYPE_COLOR_STATE_LIST = 10;

    /**
     * ColorStateListFactory，用于创建ColorStateList
     */
    public static final int TYPE_LAZY_COLOR_STATE_LIST = 11;

    //public static final int TYPE_COLOR_ID = 12;

    /**
     * Drawable
     */
    public static final int TYPE_DRAWABLE = 13;

    /**
     * DrawableFactory，用于创建Drawable
     */
    public static final int TYPE_LAZY_DRAWABLE = 14;

    //public static final int TYPE_DRAWABLE_ID = 15;

    //public static final int TYPE_ANIM_ID = 16;

    /**
     * Object类型
     */
    public static final int TYPE_OBJECT = 17;

}