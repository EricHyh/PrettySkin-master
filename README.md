# PrettySkin-master
Android平台动态换肤框架，无需重启应用即可实现换肤功能，支持原生View、support包中的View、androidx包中的View、及自定义View，功能齐全，代码侵入性极低，接入方式简单，扩展性强。

## 功能介绍
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
- [ ] 暂不支持AsyncLayoutInflater动态布局


## 效果图
<img width="300"  src="https://raw.githubusercontent.com/EricHyh/file-repo/master/PrettySkin/gif/homepage.gif"/>


## 接入框架
### 导入aar包
```
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

### Application中初始化
