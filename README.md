# BaseJava
java底层封装（本着能使开发android项目更简单、方便原则进行封装）[老版本(已停止维护)](https://github.com/BenYanYi/javalib)

 [推荐kotlin项目使用kotlin项目底层](https://github.com/BenYanYi/BaseKotlin)

<br/>

## 使用方法
#### module 下添加

     implementation 'com.yanyi.benyanyi:androidjava:1.1.7'
     
#### 感谢

[glide](https://github.com/bumptech/glide),[gson](https://github.com/google/gson)
感谢以上开源库提供使用

#### 介绍

* 普通的activity继承BaseActivity
* 普通的fragment继承BaseFragment
* 提供网络请求方法[OkHttpUtil](https://github.com/BenYanYi/OkHttp)
* 里面提供了sqlite的封装类，使用时只需要调用DBUtil.getInstance(Context,tabName,dbName,fieldMap,version)，
      调用其中的增删查改方法，其中fieldMap是你要创建的表的表字段,其中的增删查改时出传入的map的key均应填写要均
      表示表字段，mMap的value表示插入的值，oMap的value表示条件的值,sMap的value表示更改后的值
* 里面封装了LOG类，使用前需要在Application中初始化，JLog.init(true)，默认为false，不显示log,
      该封装类提供了json显示和xml显示，并能显示类、方法、行号
* 提供了轻量级存储封装方法SharedUtil，保存方法为save方法，取出方法为get方法，支持基本数据类型，Set<String>和list
* 2017/11/15更新 添加时间工具类DateUtil,设备工具类AndroidUtil,数组工具类ArrayUtil,装换工具类DpAndPxUtil,
                     图片工具类ImageUtil,连续点击判断OnClickTime,拼音工具类PinYinUtil,文件大小工具类FileSizeUtil
                     1.0.8版本之前数据判断方法为StringUtil,1.0.8版本之后更改为FormatUtil
* 2017/11/24更新 添加权限activity父类
* 2018/03/07更新 添加[时间选择器](https://github.com/BenYanYi/DateSelect),在父类activity中添加Toolbar
* 2018/03/07更新 添加颜色资源
* 2018/04/16更新 优化viewpager中使用带下拉刷新上拉加载的fragment是否在显示当前页面时请求数据，优化RecyclerView的请求动画，
                  在下拉基类中可以通过设置animationID来设置RecyclerView的动画，添加动画工具类RecyclerViewAnimation，使用时
                  不设置animationID则会使用默认的RecyclerView动画。
* 2018/04/27更新 优化一些细节
* 2018/04/28更新 添加activity是否需要双击退出判断,更改设置布局的方式，添加页面布局中需要设置为layoutID，添加状态栏工具类StatusBarUtil
* 2018/05/08更新 正式提交开源
* 2018/06/01更新 优化一些细节
* 2018/06/10更新 变更开源库地址
* 2018/07/04更新 添加自定义单选框(CheckBox)SmoothCheckBox
* 2018/08/23更新 1.0.5去除时间选择器，将框架轻量化，添加轻量级存储工具类存储文件单独命名
* 2018/08/24更新 升级网络请求工具方法
* 2018/09/12更新 1.1.0优化在线下载方法
* 2018/09/14更新 1.1.1优化下载文件方法
* 2018/09/28更新 1.1.2优化网络请求方法，详情看[OkHttp](https://github.com/BenYanYi/OkHttp)
* 2018/10/10更新 1.1.3优化网络请求方法，详情看[OkHttp](https://github.com/BenYanYi/OkHttp)
* 2018/10/22更新 1.1.4优化BaseActivity方法，添加退出当前应用时操作方法
* 2018/12/27更新 1.1.5优化网络请求方法，详情看[OkHttp](https://github.com/BenYanYi/OkHttp)，更改注解框架[BindView](https://github.com/BenYanYi/BindViewLib)
* 2019/01/25更新 1.1.6更改注释框架，摒弃自定义控件，添加崩溃日志收集
* 2019/01/31更新 1.1.7完善注释框架
* 2019/03/22更新 1.1.8修改log版本，修改网络请求版本
* ........

<br/>
若在使用过程中出现什么问题，可以联系作者<br/>
作者：演绎<br/>
QQ：1541612424<br/>
email： work@yanyi.red<br/>
微信公众号：benyanyi(演绎未来)&nbsp;&nbsp;&nbsp;将会不定期的更新关于android的一些文章