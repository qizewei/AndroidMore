### 多进程模式
1. 进程间通信（IPC，Inter-Process Communication），指至少两个进程或线程间传送数据或信号的一些技术或方法。
2. 通过给四大组件指定 android:proccess 属性，只有这一种方法可以开启多进程模式。
3. 通过JNI在native层中fork一个新的进程，是不常用的创建多线程的方法。
4. 入口Activity如果不指定process属性，那么它运行在默认进程中，默认进程的进程名是包名，例如android:process=":"
5. :的含义是指要在当前的进程名前附加上当前的包名。com.xxx.text:remote == :remote
6. 进程名以:开头的属于私有进程。而进程名不以:开头的属于全局进程，其他应用可以通过ShareUID和她跑在统一进程中。
7. Android为每个进程都分配了一个虚拟机，不同的虚拟机访问同一个对象会产生多份副本，导致数据不一致，所以需要IPC。
8. SP不支持两个进程同时写操作，会有一定几率导致数据丢失。

### IPC基础
#### 序列化
1. Serializable 和 Pracelable 接口可以完成对象的序列化
2. Serializable是JAVA提供的序列化接口，只需要在类中声明serialVersionUID，即可自动实现默认的序列化过程。
3. 如果不手动指定serialVersionUID,反序列化时类有所改变，将导致crash。
4. 静态成员变量不属于对象，所以不参与序列化。
5. 用transient关键字编辑的成员变量不参与序列化过程。
6. Serializable 系统默认的序列化过程也是可以改变的，重写指定方法即可。
7. 系统已经为我们提供了许多实现Parcelable接口的类，是可以直接序列化的，Intent，Bundle，Bitmap等。List和Map也可以序列化，前提是他们里面的类都是可序列化的。
8. 使用Parcelable需要重写指定方法。
9.  Serializable是JAVA中的序列化接口，使用简单但开销巨大，序列化个反序列化需要进行大量I/O操作；Parcelable是Android中的序列化方式，使用麻烦但是效率高，Android推荐。
10. Parcelable主要用在内存序列化上。将对象序列化到存储设备或序列化后网络传输通过Parcelable会稍显复杂，这两种情况建议使用Serializable。

#### Binder
1. 从AndroidFrameWork角度来说，Binder是ServiceManager连接各种Manager(ActivityManager,WindowManager,等等)和相应ManagerService的桥梁；
2. 从Android应用层来说，Bindler是客户端和服务端进行通信的媒介。
3. RPC：远程过程调用。
3. 普通Service中的Binder不涉及进程间通信。
5. DESCRIPTOR：Binder的唯一标识，一般以类名表示。用来判断客户端服务端是否是同一进程
5. asInterface：用于将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象(如果客户端和服务端位于同一进程，那么此方法返回的就是服务端的Stub对象本身(Binder类)，否则返回的是系统封装后的Stub.proxy对象)。
6. 跨进程时，方法调用需要走transact，这个逻辑由Stub的内部代理Proxy执行(跨进程)，然后onTransact相应。
7. 当客户端发起远端请求时(transact执行)，当前线程会被挂起直到服务端进程返回数据。所以一个远程方法耗时，那么不能在UI进程中发起远程请求。
8. onTransact方法运行在服务端中的Binder线程池中，客户端发起跨进程请求时，远程请求会通过系统底层封装后交给此方法来处理。如果此方法返回false,那么客户端的请求就会失败(可用作权限验证)。
10. Binder类中提供了配对方法，linkToDeath和unlinkToDeath。通过linkToDeath可以给Binder设置死亡代理，当Binder死亡时可以收到通知并进行下一步操作。
11. Binder的isBinderAlive方法也可以判断Binder是否死亡。
12. IInterface：Binder的基类，Stub和Proxy都分别间接的实现了该接口， 定义新接口时，必须实现IInterface接口，内部只有一个asBinder方法。
13. Stub充当服务端角色时，持有Binder实体(本地对象)：根据客户端出来的数据，根据方法ID调用本地写好的方法，将需要回传的数据写进reply流，传回客户端。
14. Proxy充当客户端角色时，持有Binder引用：生成_data和_reply数据流，向_data中存入客户端的数据，通过transact()方法将他们传递给服务端，并请求调用指定方法。最后通过_reply数据流，获取服务端传回的数据。
![image](3D22D1F6DE5F44A7A725A0CE8F631B07)

#### Android中的IPC方式
##### Bundle
1.  四大组件除了provider外均支持bundle传递信息，前提是传递的信息需要经过序列化。

##### 文件共享
1. 文件共享方式适合在对数据同步要求不高的进程间通信，并且要妥善处理并发读写的问题。
2. 通过文件共享反序列化得到的对象内容上是一样的，但本质上是两个对象。
3. SP也是文件的一种，但系统对它的读写有缓存策略，即内存中会有SP的缓存，因此多进程下SP的读写不可靠。

##### Messageer
1. Messager是轻量级的IPC方案，底层实现是AIDL。
2. Messager一次只处理一个请求，因此不用考虑线程同步的问题。

##### AIDL
1. Messager是串行方式处理客户端信息，如果需要并行，可以使用AIDL。而且Messager无法实现跨进程调用服务端的方法，AIDL可以。
2. AIDL文件中，即使在一个包内的类使用时也需要导入声明。
3. AIDL的使用：
    1. 服务端创建Service监听用户连接，然后创建AIDL文件，将暴露给客户端的接口在这个AIDL文件中声明，然后让Service实现这个接口。
    2. 后创建AIDL文件，生成Service，绑定该Service，将绑定成功返回的Binder对象转成AIDL所属的类型，就可以调用了。
4. 在服务端中，可以使用CopyInWriteArrayList，它支持并发读写。因为AIDL的服务端是在线程池中执行，所以需要处理线程同步问题。类似的类还有ConcurrentHashMap。
5. RemoteCallbackList是系统专门提供用于删除跨进程listener的接口。工作原理：内部有一个Map专门保存所有AIDL的回调。Map的key是IBinder类型，value是Callback类型。
6. 多次跨进程传输客户端的同一个对象会在服务端产生不同的对象，但是这些新生成的对象底层的Bindler对象是同一个。
7. RemoteCallbackList的另一个功能，当客户端进程终止后，它能够自动移除客户端所注册的listencer。
8. RemoteCallbackList遍历需要按照指定的方式进行，其中的beginBroadCast和finishBroadCast必须配对使用。
9. 由于客户端发起远程调用后会被挂起，所以避免在UI中发起远程调用。
10. 由于服务端方法本身就运行在服务端的Binder线程池中，所以方法本身就可以执行大量耗时操作。
11. Binder是可能意外死亡的，一般是因为服务端进程意外停止，这时候我们需要重连，一般有两种方法：
    1. 给Binder设置死亡监听(在客户端的Binder线程中执行，不能访问UI)。
    2. 在onServiceDisabled中重连远程服务(在客户端UI线程中被回调)。
12. Binder 中，举例两种权限验证的方法：
    1. 在onBind中验证，不通过返回null，无法绑定服务。验证方式可以使用自定义Permission。
    2. 可以在服务端的onTransact方法中进行权限验证，验证失败返回false。验证方式可以通过getCallingUid和getCallingPid拿到客户端的UID和PID。通过这两个参数，可以验证Permission也可以验证包名。
    

##### ContentProvider
1. ContentProvider底层实现也是Binder。
2. ContentProvider进程中，除了onCreat由系统回调并运行在主线程里，其他五个方法均有外界回调发生在Binder线程池中。
3. 虽然ContentProvider的底层数据看起来像SQLLite数据库，其实它对底层数据的数据没有任何要求。
4. ContentProvider还支持文件数据，比如图片视频表格等，处理这类数据时可以在ContentProvider返回文件的句柄给外界从而让文件来访问ContentProvider中的文件信息。
5. Android系统所提供的MediaStore功能就是文件类型的ContentProvider。
6. ContentProvider的使用：
    1. 声明时，android:authorities 是唯一标识，建议命名时加上包名前缀。而且可以通过控制permission来控制外界访问的权限。
    2.  ContentProvider的三次query方法可能运行在三个不同的线程中，一个Binder线程池中。
    3.  除了query方法，update，delete，insert方法会引起数据源的改变，可以通过注册registerContentResever来注册观察者，unregister解除观察者。通过ContentResever的notifyChange通知外界数据已经改变。
    4.  增删改查是存在多线程并发的，因此方法内部需要做好线程同步。比如只使用一个SQLite对象连接，因为SQLiteDatabase内部对数据库的操作是有同步处理的。
    5.  ContentProvider除了支持对数据源的增删改查之外，还支持自定义调用，通过ContentResever和ContentProvider的Call方法来完成。
    

##### Socket
1. Socket也称套接字，是网络通信中的概念。
2. 分为流式套接字和用户数据报套接字，对应网络传输控制层中的TCP和UDP协议。
3. TCP面向连接的协议，提供稳定的双向通信功能。经过三次握手才能完成，自身提供超时重传机制，稳定性强。
4. UDP是无连接的，提供不稳定的单向通讯，当然也可以实现双向。UDP有较好的性能，缺点是稳定性不强。
5. Socket的使用：
    1. 在配置文件中声明网络访问的权限（不能在主线程中访问网络）。
    2. 远程Service建立一个TCP服务，然后在主界面中连接TCP服务。
    3. 重连机制：每次连接失败后尝试重新建立连接，为了降低重试机制的开销，加入休眠机制，即线程休眠1000毫秒。
    4. Activity退出时(OnDestory)，需要关闭Socket。
    5. Socket可以实现进程间通讯，也可以实现设备间通信，前提是设备之间的IP地址互相可见。

##### Binder连接池
1. 多个不同的业务模块需要使用AIDL进行进程间通讯，为了减少Service的数量，将所有的AIDL放在同一个Service中进行管理。
2. 各个模块像服务端提供自己的唯一标识和与其对应的Binder对象；服务端至于要一个Service，然后提供一个queryBinder接口，这个接口根据相应的模块来返回对应的Binder。

##### 对比
1. Bundle 只能传输指定类型数据，主要用于四大组件。
2. 文件共享，Messager 不适合高并发的场景,不支持RPC。
3. ContentProvider，主要提供一对多数据源的增删改查。
4. Socket 实现细节稍微繁琐，不支持直接的RPC，主要用在网络。