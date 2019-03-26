1. JVM 加载的是Class 文件，DVM 和 ART(Android 4.4之后) 加载的是dex 文件。
2. Android中主要包括：自定义类加载器 和 系统类加载器：BootClassLoader，PathClassLoader 和 DexClassLoader。

#### BootClassLoader
1. 系统启动时，会使用 BootClassLoader 来预加载常用类。
2. BootClassLoader 是一个单例类，访问修饰符是默认的(一个包内才能访问)，所以应用程序中无法调用。

#### DexClassLoader
1. DexClassLoader 用来加载 Dex 文件，和包含 Dex 文件的压缩文件(apk,jar)。
2. DexClassLoader 继承自 BaseDexClassLoader，方法都放在其中实现。

#### PathClassLoader
1. 系统使用 PathClassLoader 来加载系统类 和应用程序的类。
2. PathClassLoader 默认路径为 data/dalvik-cache,所以PathClassLoader通常用来加载已经安装的Apk的dex文件(安装的dex文件会存储在/data/dalvik-cache中)



* ClassLoader 是抽象类，定义了主要功能。BootClassLoader是 ClassLoader 的内部类。

* SourceClassLoader 并不是 ClassLoader 的实现类，它扩展了ClassLoader，增加了权限方面的功能，加强了安全性。
* URLClassLoader 继承自 SourceClassLoader，通过URL路径从jar文件中加载类和资源。

* BaseDexClassLoader 继承自 ClassLoader，是ClassLoader的具体实现类。PathClassLoader，DexClassLoader，InMemoryDexClassLoader都继承自它。
* InMemoryDexClassLoader 是Android8.0 增加的类加载器，继承自 BaseDexClassLoader,用于加载内存中的 dex 文件。


ClassLoader的加载过程：
双亲委托：
1. 先判断出入的类是否已经加载，如果已经加载就返回；
2. 如果没有加载，就叫给父类加载器检查；
3. 直到顶层父类加载器也没有加载，就开始从父类向下查找该类，找到类就 加载后返回。

双亲模式的好处：
1. 避免重复加载
2. 更加安全。因为如果不使用双亲委托模式，就可以自定义String类来替换系统的String类。所以系统的String类在系统启动时候就被加载过，就不会被替换掉了。


BootClassLoader的创建：
是在 Zygeto 进程的入口方法中被创建的，用于加载 preloaded-classes 文件中存有的预加载类(拿空间换时间)。

PathClassLoader 是在 SystemServer 进程中采用工厂模式创建(在 Zygeto fork 自身创建 SystemServer进程之后)。
