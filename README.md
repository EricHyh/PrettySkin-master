# PrettySkin-master
Android平台动态换肤框架，无需重启应用即可实现换肤功能，支持原生View、support包中的View、androidx包中的View、及自定义View，功能齐全，代码侵入性极低，接入方式简单，扩展性强。  
只要你会使用android的theme，那么你已经掌握了该框架的核心使用方法，如果你还不会使用android的theme，那么通过该框架也可以学会如何使用theme。


<!-- TOC -->

- [PrettySkin-master](#prettyskin-master)
    - [1. 功能介绍](#1-功能介绍)
    - [2. 效果图](#2-效果图)
    - [3. 接入框架](#3-接入框架)
        - [3.1 导入aar包](#31-导入aar包)
        - [3.2 请在Application的onCreate中进行初始化](#32-请在application的oncreate中进行初始化)
        - [3.3 定义皮肤](#33-定义皮肤)
            - [3.3.1 应用内主题皮肤](#331-应用内主题皮肤)
            - [3.3.2 外部APK主题皮肤（可在一个APK文件中定义多套皮肤）](#332-外部apk主题皮肤可在一个apk文件中定义多套皮肤)
        - [3.4 使用皮肤](#34-使用皮肤)
            - [3.4.1 在xml中使用](#341-在xml中使用)
            - [3.4.2 在代码中使用](#342-在代码中使用)
    - [5. 恢复默认皮肤](#5-恢复默认皮肤)
    - [6. 更多用法](#6-更多用法)
    - [7. 代码混淆配置](#7-代码混淆配置)
    - [8. DEMO下载](#8-demo下载)
    - [9. 其他问题](#9-其他问题)
        - [9.1 外部APK皮肤包后缀问题](#91-外部apk皮肤包后缀问题)
        - [9.2 某个布局换肤失效](#92-某个布局换肤失效)
        - [9.3 使用了AsyncLayoutInflater](#93-使用了asynclayoutinflater)
        - [9.4 状态栏颜色跟随皮肤动态变化](#94-状态栏颜色跟随皮肤动态变化)
        - [9.5 想查看框架中实现了View的哪些属性](#95-想查看框架中实现了view的哪些属性)
    - [TODO](#todo)
    - [特别鸣谢](#特别鸣谢)
    - [联系我](#联系我)
    - [License](#license)

<!-- /TOC -->


## 1. 功能介绍
- [x] 支持使用应用内的主题换肤
- [x] 支持使用外部APK文件中的主题换肤
- [x] 支持动态替换或新增皮肤包中的属性
- [x] 支持自定义皮肤包实现方式
- [x] 支持原生View中的大部分属性
- [x] 支持support包中的View中的大部分属性，可选择是否接入该模块
- [x] 支持androidx包中的View中的大部分属性，可选择是否接入该模块
- [x] 支持给某个View扩展皮肤属性
- [x] 支持动态drawable，让drawable也跟随皮肤动态变化
- [x] 支持监听皮肤变化事件，可用于实现状态栏，导航栏跟随皮肤动态变化
- [x] 支持AndResGuard资源混淆


## 2. 效果图
<img width="300"  src="https://raw.githubusercontent.com/EricHyh/file-repo/master/PrettySkin/gif/homepage.gif"/>


## 3. 接入框架
### 3.1 导入aar包
```
allprojects {
    repositories {
        jcenter()
    }
}
dependencies {

    //框架核心库，没有引用其他库
	implementation 'com.hyh.prettyskin:prettyskin-core:1.0.0'        

    //如果用到了support包下某View的自定义属性，可引入该模块，其中包含v4、v7、design包下的View
    implementation ('com.hyh.prettyskin:skinhandler-support:1.0.0') {

        transitive = false  //去除依赖传递

    } 

    //如果用到了androidx包下某View的自定义属性，可引入该模块，其中包含androidx、material包下的View
    implementation ('com.hyh.prettyskin:skinhandler-androidx:1.0.0') {

        transitive = false  //去除依赖传递

    }   
}    
```

### 3.2 请在Application的onCreate中进行初始化
```

//初始化
PrettySkin.getInstance().init(this);

//开启解析xml中View的默认属性，该参数必须在执行更换皮肤函数之前设置
//如果你需要在某个时刻恢复应用默认皮肤，那么需要将该参数设置为true，否则你不需要设置该参数
//PrettySkin.getInstance().setParseDefaultAttrValueEnabled(true);

//添加ShapeView自定义属性处理器
//PrettySkin.getInstance().addSkinHandler(ShapeView.class, new ShapeViewSH());


//如果使用了support包，可添加support包中所有View的自定义属性处理器
//PrettySkin.getInstance().addSkinHandler(new AppCompatSkinHandlerMap());


//如果使用了androidx包，可添加androidx包中所有View的自定义属性处理器
//PrettySkin.getInstance().addSkinHandler(new AndroidXSkinHandlerMap());




//设置皮肤的操作，也可以放在应用Splash页面中，可参考DEMO中SplashActivity的做法
Context context      = this;
int style            = R.style.your_theme;  //当前使用的皮肤主题ID
Class clzz           = R.styleable.class;   //皮肤样式表所在的styleable类
String styleableName = "YourName";          //皮肤样式表名

//创建主题皮肤
ISkin skin = new ThemeSkin(context, style, clzz, styleableName);

//执行换肤函数
PrettySkin.getInstance().replaceSkinAsync(skin, null);

```


### 3.3 定义皮肤
#### 3.3.1 应用内主题皮肤
1. 在res/values的xml中定义皮肤主题样式表，例如：
```
<!--定义皮肤属性-->
<declare-styleable name="YourName">

    <attr name="status_bar_color" format="color" />
    <attr name="status_bar_mode" format="enum" />

    <attr name="toolbar_bg" format="color|reference" />
    <attr name="toolbar_title_color" format="color|reference" />

    <attr name="tablayout_bg" format="color|reference" />

    <attr name="content_bg_color" format="color" />
    <attr name="list_bg_color" format="color" />
    <attr name="item_bg_color" format="color" />

    <attr name="main_toolbar_navigation" format="reference" />
    <attr name="web_toolbar_navigation" format="reference" />

    <attr name="web_progress_drawable" format="reference" />

    <!--your more attrs-->

</declare-styleable>
```

> DEMO参考位置： sample-common/src/main/res/values/styles.xml 

2. 在res/values/styles.xml中定义你的皮肤主题，例如：
```
<!--white skin-->
<style name="PrettySkin_white" parent="AppTheme">

    <item name="status_bar_color">#DDDDDD</item>
    <item name="status_bar_mode">@integer/status_bar_mode_light</item>

    <item name="toolbar_bg">@color/white</item>
    <item name="toolbar_title_color">#212121</item>

    <item name="tablayout_bg">@drawable/tablayout_bg_white_style</item>

    <item name="content_bg_color">@color/white</item>
    <item name="list_bg_color">#F4F4F4</item>
    <item name="item_bg_color">@color/white</item>

    <item name="main_toolbar_navigation">@mipmap/main_toolbar_navigation_white_style</item>
    <item name="web_toolbar_navigation">@mipmap/web_toolbar_navigation_white_style</item>

    <item name="web_progress_drawable">@drawable/web_progress_white_style</item>

    <!--your more attrs-->

</style>

<!--black skin-->
<style name="PrettySkin_black" parent="AppTheme">

    <item name="status_bar_color">#373737</item>
    <item name="status_bar_mode">@integer/status_bar_mode_dark</item>

    <item name="toolbar_bg">#373737</item>
    <item name="toolbar_title_color">#8F8F8F</item>

    <item name="tablayout_bg">@drawable/tablayout_bg_black_style</item>

    <item name="content_bg_color">#373737</item>
    <item name="list_bg_color">#2B2B2B</item>
    <item name="item_bg_color">#373737</item>

    <item name="main_toolbar_navigation">@mipmap/main_toolbar_navigation_black_style</item>
    <item name="web_toolbar_navigation">@mipmap/web_toolbar_navigation_black_style</item>

    <item name="web_progress_drawable">@drawable/web_progress_black_style</item>

    <!--your more attrs-->

</style>

<!--your more skins-->

```
> DEMO参考位置： appcompat-sample/src/main/res/values/styles.xml 

3. 创建皮肤对象
```
Context context      = applicationContext;
int style            = R.style.your_theme;    //当前使用的皮肤主题ID
Class clzz           = R.styleable.class;     //皮肤属性表所在的styleable类
String styleableName = "YourName";            //皮肤属性表名

//创建主题皮肤
ISkin skin = new ThemeSkin(context, style, clzz, styleableName);

//执行换肤函数
PrettySkin.getInstance().replaceSkinAsync(skin, null);
```

#### 3.3.2 外部APK主题皮肤（可在一个APK文件中定义多套皮肤）
由于需要支持在一个APK文件中定义多套皮肤，导致这里的步骤稍显繁杂，请严格按照下列步骤操作，特别要注意在manifest文件中注册皮肤包那个步骤。

1. 创建一个Application类型的模块或者工程，该工程无需写任何代码，只是用于放置资源文件，在该模块的build.gradle中做如下配置，避免出现dex分包而导致找不到R文件
```
android {
    defaultConfig {
        applicationId "your package name"
        versionCode 100
        versionName "1.0.0"

        multiDexEnabled false
    }
    lintOptions {
        abortOnError false
    }
    dexOptions {
        preDexLibraries = false
    }
}
```

2. 在res/values的xml中定义皮肤主题样式，例如：
```
<!--皮肤属性表-->
<declare-styleable name="YourSkinName">

    <attr name="status_bar_color" format="color" />
    <attr name="status_bar_mode" format="enum" />

    <attr name="toolbar_bg" format="color|reference" />
    <attr name="toolbar_title_color" format="color|reference" />

    <attr name="tablayout_bg" format="color|reference" />

    <attr name="content_bg_color" format="color" />
    <attr name="list_bg_color" format="color" />
    <attr name="item_bg_color" format="color" />

    <!--your more attrs-->

</declare-styleable>
```

> DEMO参考位置： skin-package-first/src/main/res/values/styles.xml 

3. 在res/values/styles.xml中定义你的皮肤主题，例如：
```
<!--white skin-->
<style name="PrettySkin_white" parent="AppTheme">

    <item name="status_bar_color">#DDDDDD</item>
    <item name="status_bar_mode">@integer/status_bar_mode_light</item>

    <item name="toolbar_bg">@color/white</item>
    <item name="toolbar_title_color">#212121</item>

    <item name="tablayout_bg">@drawable/tablayout_bg_white_style</item>

    <item name="content_bg_color">@color/white</item>
    <item name="list_bg_color">#F4F4F4</item>
    <item name="item_bg_color">@color/white</item>

    <item name="main_toolbar_navigation">@mipmap/main_toolbar_navigation_white_style</item>
    <item name="web_toolbar_navigation">@mipmap/web_toolbar_navigation_white_style</item>

    <item name="web_progress_drawable">@drawable/web_progress_white_style</item>

    <!--your more attrs-->

</style>

<!--black skin-->
<style name="PrettySkin_black" parent="AppTheme">

    <item name="status_bar_color">#373737</item>
    <item name="status_bar_mode">@integer/status_bar_mode_dark</item>

    <item name="toolbar_bg">#373737</item>
    <item name="toolbar_title_color">#8F8F8F</item>

    <item name="tablayout_bg">@drawable/tablayout_bg_black_style</item>

    <item name="content_bg_color">#373737</item>
    <item name="list_bg_color">#2B2B2B</item>
    <item name="item_bg_color">#373737</item>

    <item name="main_toolbar_navigation">@mipmap/main_toolbar_navigation_black_style</item>
    <item name="web_toolbar_navigation">@mipmap/web_toolbar_navigation_black_style</item>

    <item name="web_progress_drawable">@drawable/web_progress_black_style</item>

    <!--your more attrs-->

</style>

<!--your more skins-->

```

> DEMO参考位置： skin-package-first/src/main/res/values/styles.xml 

4. 在res/values/styles.xml中记录定义主题皮肤名称列表，例如：
```
<string-array name="skin_list">
    <item>PrettySkin_white</item>
    <item>PrettySkin_black</item>
    <!--your more skins-->
</string-array>
```

> DEMO参考位置： skin-package-first/src/main/res/values/styles.xml 

5. 在manifest文件中注册皮肤包，例如：
```
<application tools:ignore="all">

    <!--R文件路径：name固定，value为该皮肤包APK的R文件路径-->
    <meta-data
        android:name="skin_r_class_path"
        android:value="com.hyh.prettyskin.R" />

    <!--皮肤属性表名称：name固定，value为在第2步中定义的皮肤属性表名称-->
    <meta-data
        android:name="skin_declare_styleable"
        android:value="YourSkinName" />

    <!--皮肤名称列表：name固定，resource为在第4步定义的主题皮肤名称列表-->
    <meta-data
        android:name="skin_theme_list"
        android:resource="@array/skin_list" />

    <!--皮肤主题名称对应的主题ID--------------Start-------------->

    <!--皮肤主题名称对应的主题ID：name=主题名称，resource=主题ID-->
    <meta-data
        android:name="PrettySkin_white"
        android:resource="@style/PrettySkin_white" />

    <meta-data
        android:name="PrettySkin_black"
        android:resource="@style/PrettySkin_black" />
    
    <!--your more skins-->

    <!--皮肤名称对应的主题ID--------------END-------------->

</application>
```

> DEMO参考位置： skin-package-first/src/main/AndroidManifest.xml

6. 创建皮肤对象
```
Context context  = applicationContext;
String apkPath   = "your skin package path";    //皮肤包路径
int index        = your theme index;            //该皮肤包中的第几个皮肤，从0开始，对应在第4步中定义的顺序

//创建主题皮肤
ISkin skin = new ApkThemeSkin(context, apkPath, index);

//执行换肤函数
PrettySkin.getInstance().replaceSkinAsync(skin, null);
```

### 3.4 使用皮肤
#### 3.4.1 在xml中使用
1. 在需要使用皮肤属性的layout.xml的根标签中加入指定的命名空间<xmlns:skin="http://schemas.android.com/android/skin">，目前不支持自定义
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:skin="http://schemas.android.com/android/skin">

</LinearLayout>
```

1. 在View标签下指定需要使用的皮肤属性<skin:skin_attrs="view_attr_name1=skin_attr_name1|view_attr_name2=skin_attr_name2|...">，请按照该格式输入，分割符"|"前后可键入换行或空格


```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:skin="http://schemas.android.com/android/skin"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">


    <android.support.v7.widget.Toolbar
        android:id="@+id/tool_bar"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"
        android:background="?attr/toolbar_bg"
        android:elevation="2dp"
        app:navigationIcon="?attr/web_toolbar_navigation"
        app:titleTextAppearance="@style/WebToolbarTitleAppearance"
        app:titleTextColor="?attr/toolbar_title_color"
        skin:skin_attrs="background=toolbar_bg
            |navigationIcon=web_toolbar_navigation
            |titleTextColor=toolbar_title_color" />


    <ProgressBar
        android:id="@+id/web_progressbar"
        style="@style/WebProgressBarStyle"
        android:layout_width="match_parent"
        android:layout_height="3dp"
        android:visibility="gone"
        skin:skin_attrs="progressDrawable=web_progress_drawable" />


    <!--your more views-->

</LinearLayout>
```

> DEMO参考位置： appcompat-sample/src/main/res/layout/*.xml 


#### 3.4.2 在代码中使用
有些View可能是通过代码创建的，这类View如何让它的某些属性也更随皮肤动态变化？参考代码如下：
```
TextView textView = new TextView(context);

int textBg;
int textColor;
float textSize;

//从应用默认的主题中，将属性的初始值解析出来
{
    TypedArray typedArray = context.obtainStyledAttributes(R.styleable.PrettySkin);
    textBg = typedArray.getColor(R.styleable.PrettySkin_new_text_view_bg, 0);
    textColor = typedArray.getColor(R.styleable.PrettySkin_new_text_view_text_color, 0);
    textSize = typedArray.getDimension(R.styleable.PrettySkin_new_text_view_text_size, 0.0f);
    typedArray.recycle();
}


//View属性对应的皮肤属性
Map<String, String> attrKeyMap = new HashMap<>();
attrKeyMap.put("background", "new_text_view_bg");
attrKeyMap.put("textColor", "new_text_view_text_color");
attrKeyMap.put("textSize", "new_text_view_text_size");

//View的默认属性值，默认属性值指的是没有设置皮肤时View本身的属性；如果项目中没有恢复默认皮肤的需求，可以省略该步骤
Map<String, AttrValue> defaultAttrValueMap = new HashMap<>();
{
    defaultAttrValueMap.put("new_text_view_bg", new AttrValue(context, ValueType.TYPE_COLOR_INT, textBg));
    defaultAttrValueMap.put("new_text_view_text_color", new AttrValue(context, ValueType.TYPE_COLOR_INT, textColor));
    defaultAttrValueMap.put("new_text_view_text_size", new AttrValue(context, ValueType.TYPE_FLOAT, textSize));
}

//创建皮肤View
SkinView skinView = new SkinView(textView, attrKeyMap, defaultAttrValueMap);

//添加到皮肤管理中
PrettySkin.getInstance().addSkinView(skinView);
```
<span style="color:red">如非必要，强烈建议使用xml的方式书写布局</span>


## 5. 恢复默认皮肤
如果使用了某个皮肤后，想恢复到没有设置任何皮肤的状态，可以执行以下函数：
```
PrettySkin.getInstance().recoverDefaultSkin();
```

<span style="color:red">使用该函数前请注意：</span>
1. 请确保在初始化时，开启了解析xml中View的默认属性的开关；
2. 针对在代码中创建的View，请确保设置了默认属性；


## 6. 更多用法
传送门：
1. [扩展View的皮肤属性](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#1)
2. [动态修改或添加皮肤包属性](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#2)
3. [监听皮肤包变化事件](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#3)
4. [动态Drawable的使用](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#4)
5. [扩展皮肤包实现方式](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#5)
6. [动态禁用layout布局中某些View使用皮肤](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#6)


## 7. 代码混淆配置
```
# 保留自定义控件(继承自View)不被混淆
-keep public class * extends android.view.View {
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持R类静态成员不被混淆
-keepclassmembers class **.R$* {
    public static <fields>;
}

```

## 8. DEMO下载
[https://raw.githubusercontent.com/EricHyh/file-repo/master/PrettySkin/apk/PrettySkin.apk](https://raw.githubusercontent.com/EricHyh/file-repo/master/PrettySkin/apk/PrettySkin.apk)

## 9. 其他问题
### 9.1 外部APK皮肤包后缀问题
在android低版本中，APK文件的后缀目前已知必须为.apk、.dex或者.jar，否则会导致DexClassLoader创建失败而无法读取APK中的R文件，所以为了兼容低版本，请将皮肤包的后缀设置为这三个中的一种；具体从android哪个版本开始我也没细测，实测android4.4会出现该问题。


### 9.2 某个布局换肤失效
情况一：在代码中使用了一个新建的ContextThemeWrapper去创建布局：
```
//问题示例：
ContextThemeWrapper context = new ContextThemeWrapper(Context base, int themeResId);
View view =  LayoutInflater.from(context).inflate(R.layout.your_layout,null);

//解决方案：
ContextThemeWrapper context = new ContextThemeWrapper(Context base, int themeResId);
PrettySkin.getInstance().setContextSkinable(context);
View view =  LayoutInflater.from(context).inflate(R.layout.your_layout,null);
```

情况二：Dialog使用了layout_id的方式去设置布局（AlertDialog也有类似的问题）：
```
//问题示例：
Dialog dialog = new Dialog(context);
dialog.setContentView(R.layout.your_layout);
dialog.show();

//解决方案一：
View dialogView = LayoutInflater.from(context).inflate(R.layout.your_layout, null);
Dialog dialog = new Dialog(context);
dialog.setContentView(dialogView);
dialog.show();

//解决方案二：
Dialog dialog = new Dialog(context);
PrettySkin.getInstance().setContextSkinable(dialog.getContext());
dialog.setContentView(R.layout.your_layout);
dialog.show();
```

情况二本质上有情况一是一样的，因为Dialog内部会创建一个ContextThemeWrapper，Dialog代码片段：
```
Dialog(@NonNull Context context, @StyleRes int themeResId, boolean createContextThemeWrapper) {
    if (createContextThemeWrapper) {
        if (themeResId == ResourceId.ID_NULL) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, outValue, true);
            themeResId = outValue.resourceId;
        }
        mContext = new ContextThemeWrapper(context, themeResId);//此处创建了一个ContextThemeWrapper
    } else {
        mContext = context;
    }

    ...
}
```

### 9.3 使用了AsyncLayoutInflater
如果在项目中使用了AsyncLayoutInflater也会导致布局换肤失效，原因是因为AsyncLayoutInflater内部会重新创建一个LayoutInflater去加载布局，目前可以通过以下方式解决该问题
```
 @Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //设置Window背景，否则会出现背景色与皮肤不匹配的情况
    ISkin currentSkin = PrettySkin.getInstance().getCurrentSkin();
    if(currentSkin!=null){
        AttrValue attrValue = currentSkin.getAttrValue("content_bg_color");
        Drawable typedValue = attrValue.getTypedValue(Drawable.class, null);
        getWindow().setBackgroundDrawable(typedValue);
    }


    //创建AsyncLayoutInflater对象
    AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(this);
    //反射获取AsyncLayoutInflater中创建的LayoutInflater对象，注意代码混淆问题
    LayoutInflater inflater = Reflect.from(AsyncLayoutInflater.class).filed("mInflater",LayoutInflater.class) .get(asyncLayoutInflater);
    //让该LayoutInflater支持换肤
    PrettySkin.getInstance().setLayoutInflaterSkinable(inflater);

    asyncLayoutInflater.inflate(R.layout.activity_main, null, (view, i, viewGroup) -> {
        setContentView(view);
        initStatusBar();
        initToolBar();
        initDrawerLayout();
        initFragmentTabHost();
        initLeftDrawer();
    });
}
```




### 9.4 状态栏颜色跟随皮肤动态变化
可参考Demo中的**com.hyh.prettyskin.demo.activity.BaseActivity**实现，相关接口详细说明[传送门](https://github.com/EricHyh/PrettySkin-master/blob/master/README_MORE.md/#3)


### 9.5 想查看框架中实现了View的哪些属性
例如你想查看**android.view.View**实现了哪些属性，你可以在源码中查找**ViewSH**类，在该类中搜索你想查看的属性，如果代码中有该属性的具体实现，那么表示支持该属性；  
有些**View**本身是没有自定义属性或者它的些自定义属性没有被实现为皮肤属性，此时可以查看该View父类的支持情况；例如**LinearLayout**，它本身的属性有**orientation、gravity**等，但是框架中没有实现这些属性的换肤功能，所以它没有对应的**LinearLayoutSH**，查看它的属性支持情况时可以查看它的父类**ViewGroup**对应的**ViewGroupSH**，而**ViewGroupSH**继承自**ViewSH**，所以**LinearLayout**的属性支持情况=**ViewGroupSH**+**ViewSH**。



## TODO
- [ ] 支持的动态布局，实现类似AsyncLayoutInflater的效果
- [ ] 支持布局中，自定义设置皮肤属性的格式

## 特别鸣谢
* 首页数据来源，[WanAndroid Api](https://www.wanandroid.com/)
* 状态栏工具类，[StatusBarUtil](https://github.com/laobie/StatusBarUtil)

## 联系我
QQ交流群：1079835495


## License
```
Copyright 2020 EricHyh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
