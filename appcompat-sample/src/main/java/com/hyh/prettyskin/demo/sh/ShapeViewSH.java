package com.hyh.prettyskin.demo.sh;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.hyh.prettyskin.AttrValue;
import com.hyh.prettyskin.R;
import com.hyh.prettyskin.ValueType;
import com.hyh.prettyskin.demo.widget.ShapeView;
import com.hyh.prettyskin.sh.ViewSH;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description 处理ShapeView的自定义属性；
 * 注意：
 * 1.这里为什么继承的是{@link ViewSH}，而不是直接去实现{@link com.hyh.prettyskin.ISkinHandler}；
 * 是因为继承ViewSH，可以复用ViewSH中对View属性的处理，例如View的background属性；
 * 这里继承的原则就是，你自定义View是继承至哪个View，那么实现ISkinHandler时就继承父类对应的ISkinHandler；
 * 比如你的自定义的父类是Button，那么实现ISkinHandler时就继承ButtonSH；
 * <p>
 * 2.如过你有ISkinHandler需要实现，可以参照这里的实现；
 * @data 2020/4/14
 */
public class ShapeViewSH extends ViewSH {

    /**
     * 支持的属性
     */
    private final List<String> mSupportAttrNames = new ArrayList<>();
    private TypedArray mTypedArray;

    {
        mSupportAttrNames.add("shape");
        mSupportAttrNames.add("border_color");
        mSupportAttrNames.add("border_width");
    }

    public ShapeViewSH() {
    }

    public ShapeViewSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    /**
     * @param defStyleAttr 默认主题属性，对应context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)中的第三个参数
     * @param defStyleRes  默认主题ID，对应context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)中的第四个参数
     */
    public ShapeViewSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    /**
     * 判断某个属性是否支持处理
     *
     * @param view     要处理的View
     * @param attrName 要处理的属性
     * @return 是否支持处理
     */
    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return mSupportAttrNames.contains(attrName) || super.isSupportAttrName(view, attrName);
    }

    /**
     * 准备解析布局中的皮肤属性
     *
     * @param view 要处理的View
     * @param set  布局属性集合
     */
    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);
        mTypedArray = view.getContext().obtainStyledAttributes(set, R.styleable.ShapeView);
    }

    /**
     * 解析皮肤属性
     *
     * @param view     要处理的View
     * @param set      布局属性集合
     * @param attrName 属性名称
     * @return 解析后的值
     */
    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        //如果是ShapeView的自定义属性，那么由这里处理
        if (mSupportAttrNames.contains(attrName)) {
            AttrValue attrValue = null;
            switch (attrName) {
                case "shape": {
                    int shape = mTypedArray.getInt(R.styleable.ShapeView_shape, 0);
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_INT, shape);
                    break;
                }
                case "border_color": {
                    int borderColor = mTypedArray.getColor(R.styleable.ShapeView_border_color, 0);
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_COLOR_INT, borderColor);
                    break;
                }
                case "border_width": {
                    float borderWidth = mTypedArray.getDimension(R.styleable.ShapeView_border_width, 0);
                    attrValue = new AttrValue(view.getContext(), ValueType.TYPE_FLOAT, borderWidth);
                    break;
                }
            }

            //觉得switch每个属性处理太麻烦？可以试试用框架提供的工具类，方便的解析属性.
           /* Class styleableClass = com.hyh.prettyskin.R.styleable.class;//com.hyh.prettyskin.R.styleable.ShapeView所在的styleable类的Class对象
            String styleableName = "ShapeView";//样式名称，在attrs.xml中定义的 <declare-styleable name="ShapeView">
            int styleableIndex = AttrValueHelper.getStyleableIndex(styleableClass, styleableName, attrName);
            attrValue = AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);*/

            return attrValue;
        } else { //否则由父类处理
            return super.parse(view, set, attrName);
        }
    }

    /**
     * 结束解析布局中的皮肤属性，在这里做释放内存的操作，{@link #prepareParse}与{@link #finishParse}总是成对出现
     */
    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }


    /**
     * 这里实现对属性的更新
     *
     * @param view      要处理的View
     * @param attrName  属性名称
     * @param attrValue 新的属性值
     */
    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        //如果是ShapeView的自定义属性，那么由这里处理
        if (mSupportAttrNames.contains(attrName)) {
            ShapeView shapeView = (ShapeView) view;
            switch (attrName) {
                case "shape": {
                    shapeView.setShape(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "border_color": {
                    shapeView.setBorderColor(attrValue.getTypedValue(int.class, 0));
                    break;
                }
                case "border_width": {
                    shapeView.setBorderWidth(attrValue.getTypedValue(float.class, 0.0f));
                    break;
                }
            }
        } else {
            super.replace(view, attrName, attrValue);
        }
    }
}