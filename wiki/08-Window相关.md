##### Window，PhoneWindow，DecorView，WindowManager，WMS简介
1.  Window：它是一个抽象类， 这个类的实例作为一个顶级View添加到Window Manager。每个Activity都包含一个Window对象。它提供了一套标准的UI方法，比如添加背景，标题等等。 它的具体实现是phoneWindow。
2. Window：Android中所有视图都是通过Widow来呈现的，包括Activity，Dialog，Toast。因此Window是View的直接管理者。
3. PhoneWindow：将自己的静态内部类DecorView设置为整个窗口的根View，它是Android中最基本的窗口系统，每个Activity的PhoneWIndow是Activity和整个View系统交互的接口。
4. DecorVIew：它是继承FrameLayout顶层View，将要显示的具体内容呈现在PhoneWindow上，DecorView不会向用户呈现任何东西，它有一下几个功能：
1. Dispatch:ViewRoot分发来的key,touch, trackball等外部事件。
2. DecorView的子View(SystemLayout)，可以设置包含当前UI风格，是否带Title，是否带processBar等(这些属性统称为WIndow decorations)。
3. DecorView只有一个子元素为LinearLayout。代表整个WIndow界面，包含通知栏，标题栏，内容显示区（FrameLayout）。
5.  WindowManager用来创建，删除，更新Wiondow， WindowManager继承ViewManager，ViewManager接口定义了一组规则，也就是add、update、remove的操作View接口。
6.  WindowManager是抽象类，WIndowManagerImpl是实现类，他有内部成员变量WindowManagerGlobal (真正的实现类)。 WindowManager是外界访问Window的入口。
7.  WindowManagerService包含Window的具体操作实现，WindowManager和WindowManagerService的交互是IPC。

##### Window简介：
1. Activity和Window的绑定，点击事件如何传递给Activity：
1. Activity继承了一个特殊接口：WIndow.Callback
2. 在Activity的attach中，mWindow.setCallback(this)。
3. 当WIndow发生布局变化时，便会回调Activity重写的方法。
2.   应用类Window对应一个Activity，子Window不能单独存在(例如Dialog)，系统Window需要声明权限才能创建。
1.  Window是分层的，每种Window都有对应的z-ordered。层级大的会覆盖在层级小的Window上面(应用Window的层级范围1 ~ 99,子Window为1000 ~ 1999,系统Window的层级范围2000 ~ 2999)。
2.  系统Window的层级最高，使用时需要添加权限，否则会报错。
3.  WindowManager提供的功能：addView，updateViewLayout，removeView。足够开发者使用了。
3. WindowManager 的 addView方法（创建WIndow）：
``` mWindowManager.addView(mButton,mLayoutParams) ```
1. WindowManager.Params中的flags和type参数：
1.  Flags表示Windows的属性：FLAG_NOT_FOCUSABLE(Window不需要获取焦点),FLAG_NOT_TOUCH_MODAL(Window区域以外点击事件交给底层Window处理，区域内点击事件自己处理),FLAG_SHOW_WHEN_LOCKED(Window可以显示在锁屏界面上)。
2.  TYPE表示Window的类型：应用Window|子Window|系统Window。

#####  Window的添加过程：
###### Activity的创建：
1.  在Activity的attach方法中，系统会创建Activity所属的phoneWindow对象并为其设置回调(Activity实现Window的Callback接口)，因此当Window接收到状态改变时，就会回调Activity的方法了。
2.  Activity 的Window创建：在setContentView 方法中，获取window，委托给window(phoneWindow)处理：
1. 没有DecorView的话就创建，然后初始化DecorView(通过generateLayout方法，与系统版本和主题有关)。
2.  将指定View添加到DecorView的mContentParent中。
3.  回调Activity的onContentChanged方法通知Activity视图已经改变
4.  在Activity的makeVisible的方法中，DecorView真正的完成了View的添加和现实的过程(windowManager.addView,ViewRootImp发起IPC)。
######  Dialog 的创建：和Activity创建一致。
1.     普通的Dialog必须采用Activity的Context。如果采用Application的Context会报错：是因为没有token导致的。应用token一般只有Activity拥有。除此之外，也可以指定Dialog为系统Window类型(系统Window不需要token)。
######  Toast的创建:
1. Toasta有定时取消的功能 ，所以系统采用了Handler，并且Toast内部有两类IPC：     
1. Toast访问NotificationManagerService。
2. NotifactionManagerService回调Toast的TN接口。
2.   Toast的View(无论是默认样式还是setView设置的自定义View)，都对应Toast的内部成员mNextView。
3.  Toast的显示和隐藏都是通过NMS的IPC实现的。NMS处理Toast的显示和隐藏的时候，会跨进程回调TN中的方法。由于TN是一个Binder类，所以运行在Binder线程池中，所以需要Handler切换到发送Toast所在的线程。
4. Toast的显示过程：
1. 它调用了NMS中的enqueueToast方法。enqueue首先将Toast封装成ToastRecoed并添加到ToastQueue（一个ArrayList，容量为50，防止DOS攻击：无限循环Toast导致其他应用没有机会显示）中，然后NMS通过showNextToastLocked显示当前的Toast。
2. Toast的隐藏也是通过ToastRecord的callback来完成的。
3. TN 中的handleShow中会将Toast的视图添加到Window。

##### Window内部机制
1.  Window是抽象概念。每个Window对应一个View和一个ViewRootImpI。
2. ViewRoot是View和WindowManager的桥梁，是因为在真正操控绘制View的是ViewRootImpl，View通过WindowManager来转接调用ViewRootImpl。
3.  View是Window的实体。WindowManager通过三个基本操作都是针对View的。实际使用中无法直接访问Window，对Window的访问必须通过WindowManager。
4. 1. ViewRootImpl绘制View的时候会先检查当前线程是否是主线程，是才能继续绘制下去(checkTHread())。
5. View与ViewRootImpl的绑定: AttachInfo则是View里面一个静态内部类。
6.  Window的添加：WindowManager是接口，它的实现WindowManagerImp中调用WindowManagerGlobal来处理。
1.  检查参数是否合法，如果是子Window的话需要调整一些布局参数。
2.  创建ViewRootImp并将View添加到表中(Global中的表有mVIews,mRoots,mParams,分别对应所有Window对应的View,ViewRootImp,Params)。
3.  通过ViewRootImp进行绘制并发起IPC来更新界面并完成Window的添加过程(WindowSession通过IPC调用WindowManagerService来实现Widow的添加)。
7.  Window的删除和更新：和Window的添加一样，都是通过ViewRootImp和IPC完成。
1.  Window的删除会做一下垃圾回收和Window更新的操作。
2.  Window的更新主要是通过ViewRootImp替换掉旧的LayoutParams后更新Window的操作。





