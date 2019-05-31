![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531203809482.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhbndlbnl1YW4wMzA0,size_16,color_FFFFFF,t_70)
## 我们分析的入口：
setContentView(R.layout.activity_main);

## 点击进入setContentView

`public void setContentView(@LayoutRes int layoutResID) {
        getWindow().setContentView(layoutResID);
        initWindowDecorActionBar();
    }`
    
## 点击进入getWindow
(因为调用的是getWindow().setContentView)
`public Window getWindow() {
        return mWindow;
    }`
    
## 点击进入Window查看到

![](https://img-blog.csdnimg.cn/20190531204036453.png)

可以看到这是一个抽象类，从注释中可以看到**PhoneWindow**是Window的实现类。

## 进入PhoneWindow

找到setContentView这个方法

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531204140184.png)
## 查看installDecor()方法
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531204431628.png)
`if (mDecor == null) {
            mDecor = generateDecor(-1);`
generateDecor(-1)方法得到
`new DecorView(context, featureId, this, getAttributes());`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531204732368.png)
## 查看DecorView
`public class DecorView extends FrameLayout implements RootViewSurfaceTaker, WindowCallbacks`
DecorView继承于FrameLayout

## 回到上面的PhoneWindow的installDecor方法
`           
            mContentParent = generateLayout(mDecor);`
 找到该方法点击进入generateLayout方法，这个方法里设置了很多根据主题什么设置了一堆熟悉过
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531210009145.png)           
 核心代码如下
 这块代码是把不同主题布局添加到DecorView
 `mDecor.onResourcesLoaded(mLayoutInflater, layoutResource);`

![主布局ID](https://img-blog.csdnimg.cn/20190531211240493.png)
onResourcesLoaded方法详情
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531210656741.png)
下面这块代码是根据主布局Id来获取主布局
`ViewGroup contentParent = (ViewGroup)findViewById(ID_ANDROID_CONTENT);`
contentParent就是我们要设置我们自己布局的父布局了。
## 代码展示如下 再回到PhoneWindow的setContentView方法
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531214113847.png)

# 综上所述：View是如何添加到屏幕窗口上

 1. 创建顶层布局容器DecorView
 2. 在顶层布局中加载基础布局ViewGroup
 3. 将ContentView添加到基础布局中的FrameLayout中

## 图解如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531213545601.png)
