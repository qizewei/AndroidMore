1. startActivity -> startActivityForResult -> execStartActivity -> ActivityManagerNative.getdefult.startActivity。
2. AMS 继承 ActivityManagerNative，ActivityManagerNative 继承 Binder 并实现IActivityManager。

3. AMS 内部间接调用 app.thread.sheduleLaunchActivity。
4. app.thread 继承 IApplicationThread，IApplicationThread 继承 Binder。
5. IApplicationThread 的实现者完成了大量 Activity，Service 启动/停止相关功能。

6. IApplicationThread 的实现类是 ActivityThread 中的 ApplicationThread。
7. ApplicationThread 继承 ApplicationThreadNative,ApplicationThreadNative 继承Binder并实现 IApplicationThread 接口(AIDL)。
8. ApplicationThreadNative 被定义为抽象类，所以 ApplicationThread 即 IApplicationThread 的最终实现者。

9. ApplicationThread -> sheduleLaunchActivity :  发送启动Activity的消息交给主线程Handler：H。
10. Handler H 对 LAUNCHER_ACTIVITY 处理即调用 ActivityThread.HandleLauncherActivity -> performLauncherActivity 最终完成 Activity 对象的创建和启动过程。

11. performLauncherActivity的创建流程：
    1. 从 ActivityClientRecord 中获取启动Activity的组件信息。
    2. 通过 Instrumentation 的 newActivity 方法使用类加载器创建 Activity 对象。
    3. 通过 LoadedAPK 的 makeApplication 方法来尝试创建 Application 对象，如果已经存在就不会重复创建。Application的创建也是通过 Instrumentation 完成，也是通过类加载器来实现。
    4. 创建ContextImp 对象并通过 Activity 的 attach 方法来完成一些重要数据的初始化。ContextImp 是Context的具体实现，Context的大部分逻辑是由 ContextImp 实现的。ContextImp 通过 Activity 的 attach 和 Activity 建立关联。attach 中还完成 Window 的创建并建立联系，所以当Window 收到外部事件就可以传递给Activity了。
    5. 调用 Activity.callActivityOnCreate。mInstrumentation.callActivityOnCreate。