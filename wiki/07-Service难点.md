##### Service onStartCommand的返回值。
1.  START_NOT_STICKY：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，即使系统内存足够可用，系统也不会尝试重新创建此Service。除非程序中Client明确再次调用startService(...)启动此Service。
2.  START_STICKY(默认返回)：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，当系统内存足够可用的情况下，系统将会尝试重新创建此Service，一旦创建成功后将回调onStartCommand(...)方法，但其中的Intent将是null，pendingintent除外。
3.  START_REDELIVER_INTENT：与START_STICKY唯一不同的是，回调onStartCommand(...)方法时，其中的Intent将是非空，将是最后一次调用startService(...)中的intent。
4.  START_STICKY_COMPATIBILITY：。此值一般不会使用，所以注意前面三种情形就好。

##### bindService后，ServiceConnection里面的回调方法运行在哪个线程？他们的调用实际分别是什么？Service的onCreate运行在哪个线程？
1.  Service的onCreate，onStartCommand，onDestory等全部生命周期方法都运行在UI线程，ServiceConnection里面的回调方法也是运行在UI线程。
2.  Service确实是运行在主线程里的，也就是说如果你在Service里编写了非常耗时的代码，程序必定会出现ANR的。
3.  Activity很难对Thread进行控制，当Activity被销毁之后，就没有任何其它的办法可以再重新获取到之前创建的子线程的实例，Service可以。

##### 先start再bind，如何停止一个Service？
1.  startService开启服务：
1.  开启服务时：调用startService: onCreate() ==> onStartCommand()。
2.  多次startService，onCreate只有第一次会被执行，而onStartCommand会执行多次。
3.  结束服务时，调用stopService: onDestroy()。
4.  多次调用stopService时，onDestroy只有第一次会被执行。

2.  onBind开启服务：
1.  onBind返回值是null:
1.  bindService开启服务：onCreate() ==> onBind();
2.  调用多次bindService，onCreate和onBind也只在第一次会被执行。
3.  unbindService结束服务：onDestroy()。
4.  unbindService方法只能调用一次，多次调用应用会抛出异常。调用unbindService要确保服务已经开启，否则应用会抛出异常。
2.  onBind返回值不为null:
1.  onBind方法的返回类型IBinder:返回的对象不要直接实现这个接口，应该继承Binder这个类。
2.  onCreate() ==> onBind() ==> onServiceConnected();
3.  调用多次bindService，onCreate和onBind都只在第一次会被执行，onServiceConnected会执行多次,onServiceConnected返回的值是onBind传过来的。
4.  unbindService结束服务和上面相同，unbindService只能调用一次，onDestroy也只执行一次，多次调用会抛出异常。
3.  startService和bindService开启服务时，他们与activity之间的关系：
1.  startService开启服务以后，与activity就没有关联，不受影响，独立运行。
2.  bindService开启服务以后，与activity存在关联，退出activity时必须调用unbindService方法，否则会报ServiceConnection泄漏的错误。
3.  同一个服务可以用两种方式一同开启，没有先后顺序的要求，Service的onCreate只会执行一次。
4.  **关闭服务需要stopService和unbindService都被调用，也没有先后顺序的影响**，Service的onDestroy也只执行一次。**但是如果只用一种方式关闭服务，不论是哪种关闭方式，onDestroy都不会被执行，服务也不会被关闭。这一点需要注意。**
