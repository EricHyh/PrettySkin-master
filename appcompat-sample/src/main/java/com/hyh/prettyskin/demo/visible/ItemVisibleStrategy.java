package com.hyh.prettyskin.demo.visible;

/**
 * @author Administrator
 * @description
 * @data 2019/6/4
 */

public class ItemVisibleStrategy {

    public static final int VISIBLE_TYPE_ALL = 0;
    public static final int VISIBLE_TYPE_HORIZONTAL = 1;
    public static final int VISIBLE_TYPE_VERTICAL = 2;

    public int requiredVisibleType = VISIBLE_TYPE_ALL;
    public float requiredVisibleScale;
    public long requiredVisibleTimeMillis;

    public ItemVisibleStrategy() {
    }

    public ItemVisibleStrategy(float requiredVisibleScale, long requiredVisibleTimeMillis) {
        this.requiredVisibleScale = requiredVisibleScale;
        this.requiredVisibleTimeMillis = requiredVisibleTimeMillis;
    }

    public ItemVisibleStrategy(int requiredVisibleType, float requiredVisibleScale, long requiredVisibleTimeMillis) {
        this.requiredVisibleType = requiredVisibleType;
        this.requiredVisibleScale = requiredVisibleScale;
        this.requiredVisibleTimeMillis = requiredVisibleTimeMillis;
    }
}