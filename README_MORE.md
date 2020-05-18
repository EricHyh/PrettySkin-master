

<!-- TOC -->

- [1. 扩展View的皮肤属性](#1-扩展view的皮肤属性)
    - [1.1 为已有的View扩展皮肤属性](#11-为已有的view扩展皮肤属性)
    - [1.2 为自定义View扩展皮肤属性](#12-为自定义view扩展皮肤属性)
- [2. 动态修改或添加皮肤包属性](#2-动态修改或添加皮肤包属性)
- [3. 监听皮肤包变化事件](#3-监听皮肤包变化事件)
- [4. 动态Drawable的使用](#4-动态drawable的使用)
    - [4.1 XML中定义的Drawable](#41-xml中定义的drawable)
    - [4.2 代码中创建的自定义的Drawable](#42-代码中创建的自定义的drawable)
- [5. 扩展皮肤包实现方式](#5-扩展皮肤包实现方式)
- [6. 动态禁用layout布局中某些View使用皮肤](#6-动态禁用layout布局中某些view使用皮肤)

<!-- /TOC -->


<span id="1"></span>
## 1. 扩展View的皮肤属性
### 1.1 为已有的View扩展皮肤属性
框架中几乎已经将native-view、support-view、androidx-view中大部分属性都已经实现换肤功能，如果你想让某个View的某些属性支持换肤功能，可以通过扩展**com.hyh.prettyskin.ISkinHandler**实现，例如我们将**LinearLayout**的**orientation、gravity**属性支持换肤能力；

1. 找到源码中**LinearLayout**中当前匹配的**com.hyh.prettyskin.ISkinHandler**实现类，可通过以下测试代码拿到实现类的路径：
```
Class viewClass = your find view class;
ISkinHandler skinHandler = PrettySkin.getInstance().getSkinHandler(viewClass);
Log.d(TAG, "skinHandler = " + skinHandler.getClass().getName());
```
这里找到**LinearLayout**的对应实现为**ViewGroupSH**；


2. 新建一个类继承自找到的实现
```
public class LinearLayoutSH extends ViewGroupSH {

    public LinearLayoutSH() {
    }

    public LinearLayoutSH(int defStyleAttr) {
        super(defStyleAttr);
    }

    public LinearLayoutSH(int defStyleAttr, int defStyleRes) {
        super(defStyleAttr, defStyleRes);
    }

    /**
     * 在该函数中，添加新增的属性
     *
     * @param view
     * @param attrName
     * @return
     */
    @Override
    public boolean isSupportAttrName(View view, String attrName) {
        return (
                TextUtils.equals(attrName, "orientation")
                        || TextUtils.equals(attrName, "baselineAligned"))
                || super.isSupportAttrName(view, attrName);
    }


    //如果你的项目中没有，恢复应用默认皮肤的需求，那么可以不需要重写这三个函数
    //解析解析XML中的默认属性-------------START-------------

    private Class<?> mStyleableClass;
    private String mStyleableName;
    private int[] mAttrs;

    private TypedArray mTypedArray;

    /**
     * 准备解析XML中的默认属性
     *
     * @param view 这里就是LinearLayout
     * @param set  XML中使用的属性集合
     */
    @Override
    public void prepareParse(View view, AttributeSet set) {
        super.prepareParse(view, set);

        Context context = view.getContext();

        if (mStyleableClass == null) {
            //LinearLayout中使用的styleable类的路径
            mStyleableClass = Reflect.classForName("com.android.internal.R$styleable");
            //LinearLayout中使用的styleable的名称
            mStyleableName = "LinearLayout";
            //反射获取LinearLayout的属性数组
            mAttrs = Reflect.from(mStyleableClass).filed(mStyleableName, int[].class).get(null);
        }

        mTypedArray = context.obtainStyledAttributes(set, mAttrs, mDefStyleAttr, mDefStyleRes);

    }

    @Override
    public AttrValue parse(View view, AttributeSet set, String attrName) {
        //如果是一个自定义View这里其实很好实现，就跟自定义View中构造函数的解析方式一样就行了；
        //但是这里是系统类的LinearLayout，它构造函数中的用到的资源都是隐藏的，无法直接使用，
        //这里提供两种实现方式供大家参考（自定义View也可以用以下方式实现）：

        //方式一，适合你有很多属性需要支持时使用：
        if (TextUtils.equals(attrName, "orientation")
                || TextUtils.equals(attrName, "gravity")) {
            int styleableIndex = AttrValueHelper.getStyleableIndex(mStyleableClass, mStyleableName, attrName);
            return AttrValueHelper.getAttrValue(view, mTypedArray, styleableIndex);
        } else {
            return super.parse(view, set, attrName);
        }

        //方式二，适合你有少量属性需要支持，且View提供了相应的get函数：
        LinearLayout linearLayout = (LinearLayout) view;
        switch (attrName) {
            case "orientation": {
                int orientation = linearLayout.getOrientation();
                return new AttrValue(linearLayout.getContext(), ValueType.TYPE_INT, orientation);
            }
            case "baselineAligned": {
                boolean baselineAligned = linearLayout.isBaselineAligned();
                return new AttrValue(linearLayout.getContext(), ValueType.TYPE_BOOLEAN, baselineAligned);
            }
            default: {
                return super.parse(view, set, attrName);
            }
        }
    }

    /**
     * 结束解析，释放内存
     */
    @Override
    public void finishParse() {
        super.finishParse();
        if (mTypedArray != null) {
            mTypedArray.recycle();
            mTypedArray = null;
        }
    }
    //解析解析XML中的默认属性-------------END-------------

    /**
     * 实现换肤功能
     *
     * @param view      这里就是LinearLayout
     * @param attrName  需要替换的属性
     * @param attrValue 新的皮肤包中属性对应的值
     */
    @Override
    public void replace(View view, String attrName, AttrValue attrValue) {
        LinearLayout linearLayout = (LinearLayout) view;
        switch (attrName) {
            case "orientation": {
                int orientation = attrValue.getTypedValue(int.class, LinearLayout.HORIZONTAL);
                linearLayout.setOrientation(orientation);
                break;
            }
            case "baselineAligned": {
                boolean baselineAligned = attrValue.getTypedValue(boolean.class, true);
                linearLayout.setBaselineAligned(baselineAligned);
                break;
            }
            default: {
                super.replace(view, attrName, attrValue);
                break;
            }
        }
    }
}
```

3. 在初始化时，添加自定义的**LinearLayoutSH**
```
PrettySkin.getInstance().addSkinHandler(LinearLayout.class, new LinearLayoutSH());
```

### 1.2 为自定义View扩展皮肤属性
扩展方式同上，可参考DEMO项目中的**com.hyh.prettyskin.demo.sh.ShapeViewSH**


<span id="2"></span>
## 2. 动态修改或添加皮肤包属性
```
ISkin skin = your skin obj;

String attrKey = attr name declared in styleable;//定义在declare-styleable中属性名称
AttrValue attrValue = new AttrValue(themeContext, type, value);//属性对应的值

skin.setOuterAttrValue(attrKey, attrValue);

List<String> changedAttrKeys = your changed attr keys;
PrettySkin.getInstance().notifySkinAttrChanged(changedAttrKeys);
```
AttrValue说明：
```
public class AttrValue {

    /**
     * @param themeContext 对应主题的上下文，例如当value是一个resourceId时，可通过该context获取到对应的资源
     * @param type         属性值类型，可查看{@link ValueType}
     * @param value        属性值
     */
    public AttrValue(Context themeContext, int type, Object value);

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
     * @param defaultValue 如果转换失败则返回该默认值
     */
    public <T> T getTypedValue(Class<T> valueClass, T defaultValue) {
        return ViewAttrUtil.getTypedValue(this, valueClass, defaultValue);
    }
}
```

属性值类型：
```
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

    /**
     * float类型，例如ratio、px
     */
    public static final int TYPE_FLOAT = 4;

    /**
     * bool类型
     */
    public static final int TYPE_BOOLEAN = 6;

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

    /**
     * Drawable
     */
    public static final int TYPE_DRAWABLE = 13;

    /**
     * DrawableFactory，用于创建Drawable
     */
    public static final int TYPE_LAZY_DRAWABLE = 14;

    /**
     * Object类型
     */
    public static final int TYPE_OBJECT = 17;

}
```


<span id="3"></span>
## 3. 监听皮肤包变化事件
```
PrettySkin.getInstance().addSkinReplaceListener(new SkinChangedListener() {

    /**
     * 当使用的皮肤发生变化时的回调
     * @param skin 当前使用的皮肤
     */
    @Override
    public void onSkinChanged(ISkin skin) {
    }

    /**
     * 当皮肤包的属性发生变化时的回调
     * @param skin  当前使用的皮肤
     * @param changedAttrKeys 发生变化的属性
     */
    @Override
    public void onSkinAttrChanged(ISkin skin, List<String> changedAttrKeys) {
    }

    /**
     * 当恢复应用默认皮肤时的回调
     */
    @Override
    public void onSkinRecovered() {
    }
});
```

<span id="4"></span>
## 4. 动态Drawable的使用
### 4.1 XML中定义的Drawable

```
String attrKey = attr name declared in styleable;;  //定义在declare-styleable中属性名称
Drawable defaultDrawable = 默认Drawable;            //不能为null，当没有设置皮肤时，使用该值

Drawable drawable = new DynamicDrawable(attrKey, defaultDrawable)
```

### 4.2 代码中创建的自定义的Drawable
```
String attrKey = "add_drawable_color";
int defaultColor = context.getResources().getColor(R.color.primary_red);
DynamicDrawable dynamicDrawable = new DynamicDrawable(attrKey, new AddDrawable(context, defaultColor)) {

    /**
     * 重写该函数，实现根据指定的皮肤属性创建自定义Drawable
     */
    @Override
    protected Drawable convertAttrValueToDrawable(AttrValue attrValue) {
        return new YoureDrawable(attrValue);
    }
};
```

<span id="5"></span>
## 5. 扩展皮肤包实现方式


<span id="6"></span>
## 6. 动态禁用layout布局中某些View使用皮肤