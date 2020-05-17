package com.hyh.prettyskin;

import android.content.Context;

import com.hyh.prettyskin.utils.ViewAttrUtil;

import java.lang.ref.WeakReference;


public class AttrValue {

    private WeakReference<Context> themeContextRef;

    private int type;

    private Object value;

    /**
     * @param themeContext 对应主题的上下文，例如当value是一个resourceId时，可通过该context获取到对应的资源
     * @param type         属性值类型，可查看{@link ValueType}
     * @param value        属性值
     */
    public AttrValue(Context themeContext, int type, Object value) {
        this.themeContextRef = new WeakReference<>(themeContext);
        this.type = type;
        this.value = value;
    }

    public Context getThemeContext() {
        return themeContextRef.get();
    }

    public int getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    /**
     * 获取指定类型的属性值，目前支持以下类型：
     * 1.int：enum类型，例如gravity          （根据attrName可知是否为enum类型）
     * 2.int：数值类型，例如progress         （根据attrName可知是否为数值类型）
     * 3.int：color类型                      （根据attrName可知是否为color类型）
     * 4.int：resourceId类型，例如style id   （根据attrName可知是否为resourceId类型）
     * 5.float
     * 6.boolean
     * 7.String
     * 8.CharSequence
     * 9.Drawable
     * 10.ColorStateList
     * 11.ImageView.ScaleType
     * 12.PorterDuff.Mode
     * 13.Typeface
     * 14.TextUtils.TruncateAt
     * 15.Animation
     * 16.LayoutAnimationController
     * 17.Interpolator
     * 18.StateListAnimator
     * 19.Object：需要外部自己根据attrName转换成对应的类型
     *
     * @param valueClass   属性值的类型
     * @param defaultValue 如果转行失败则返回该默认值
     */
    public <T> T getTypedValue(Class<T> valueClass, T defaultValue) {
        return ViewAttrUtil.getTypedValue(this, valueClass, defaultValue);
    }
}