
**这篇文章旨在用最简单的方式教会你Lifecycle如何使用，及它的工作原理。**
### Lifecycle介绍  
Lifecycle是Google推出的JetPack框架组件的一个，**主要是用来感知Activity和Fragment的生命周期**，可以帮你写出更简洁，通用的代码。

### Lifecycle使用
**总共两步**
1. 第一步，编写自己的Observer类文件，并实现对应的监听方法，通过**注解**实现对响应生命周期方法的监听，代码如下：
```
public class MyLifeObserver implements LifecycleObserver {

  private static final String TAG = "MyLifeObserver";

  // OnLifecycleEvent()内的注解Lifecycle.Event.XXX 对应不同的生命周期方法，你可以根据需要监听不同的生命周期方法。
  // 方法名可以随意，这里为了方便理解定义为onResumeListener()。
  @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
  public void onResumeListener() {
    Log.d(TAG, "onResume: ");
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
  public void onPauseListener() {
    Log.d(TAG, "onPause: ");
  }

  @OnLifecycleEvent(Event.ON_DESTROY)
  public void onDestoryListener() {
    Log.d(TAG, "onDestory: ");
  }
```
2. 第二步在被监听的Activity/Fragment内注册：
```
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getLifecycle().addObserver(new MyLifeObserver());
  }
}
```

**不管你信不信，代码已经写完了！**</br>

![](https://user-gold-cdn.xitu.io/2019/2/28/169346bb2fdbb318?w=240&h=240&f=jpeg&s=38415)</br>
我们跑一下代码：
```
2019-02-28 12:00:40.068 com.example.lifecycledemo D/MyLifeObserver: onResume: 
2019-02-28 12:00:46.091 com.example.lifecycledemo D/MyLifeObserver: onPause: 
2019-02-28 12:00:46.416 com.example.lifecycledemo D/MyLifeObserver: onDestory: 
```

Log 显示我们写的 Observer 内的方法的确在 Activity 的指定周期内执行了。**恭喜你，已经能够使用 Lifecycle 了**！</br>
但是注意几点：
1. 通常我们会 把MyLifeObserver 作为**内部类**写在另一个 Activity/Fragment 中，以方便在监控到生命周期方法后做一些业务操作，以上代码为了快速看到效果而做了省略。
2. 注意上面 Activity 的代码中，我们的 Activity 继承了AppCompatActivity，AppCompatActivity 和 Fragment 都实现了LifecycleOwner 接口（Support Library 26.1.0之后的版本），所以可以直接拿来使用。如果你被监控的 Actiivty 是自定义的 Activity，需要手动继 承LifecycleOwner，具体操作如下：
```
1. 实现LifecycleOwner接口，并重写getLifecycle方法。
2. 手动在每个生命周期方法中做标记。
```
具体代码如下：
```
public class MyActivity extends Activity implements LifecycleOwner {
  private LifecycleRegistry mLifecycleRegistry;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);
    //创建Lifecycle对象
    mLifecycleRegistry = *new*LifecycleRegistry(this);
    //做标记
    mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    //添加观察者
    getLifecycle().addObserver(new MyLifeObserver());
  }

  @NonNull
  @Override
  public Lifecycle getLifecycle() {
    return mLifecycleRegistry;
  }

  @Override
  public void onStart() {
    super.onStart();
    //做标记
    mLifecycleRegistry.markState(Lifecycle.State.STARTED);
  }

  @Override
  protected void onResume() {
    super.onResume();
    //做标记
    mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //做标记
    mLifecycleRegistry.markState(Lifecycle.State.DESTROYED); 
  }
}
```

至此，Lifecycle 的使用方法已经介绍完了，因为 AppCompatActivity 已经帮我们实现了 LifecycleOwner 接口，所以同学们注意**活学活用**。</br>
比如：在封装网络请求工具时候，传入当前 Activity 对象，当 Activity 对象生命周期为 Destory 时，撤销请求网络的操作。
Lifecycle 用武之地还有很多，这里只介绍最基础的，你现在可以思考如何通过 Lifecycle 来优化你的项目了。</br>
如果你对**Lifecyle的工作原理**有兴趣，请继续阅读，我会带着你从源码一步一步完全理解 Lifecycle 的工作原理。
如果没有兴趣，可以直接拉到文章末尾看总结~

### Lifecycle的工作原理
我们就以使用到的几个类为切入口，分析 Lifecycle 的工作原理。
我们主要点进这几个类看(Crtl+单击类名)：**LifecycleOwner.java，Lifecycle.java，LifecycleRegistry.java**。
#### LifecycleOwner.java
这个很简单，LifecycleOwner定义了一个getLifecycle()方法，通过@NonNull强制我们返回Lifecycle对象。代码如下：
```
public interface LifecycleOwner {
    @NonNull
    Lifecycle getLifecycle();
}

```

#### Lifecycle.java
代码如下：
```
public abstract class Lifecycle {
 
    @MainThread
    public abstract void addObserver(@NonNull LifecycleObserver observer);

    @MainThread
    public abstract void removeObserver(@NonNull LifecycleObserver observer);

    @MainThread
    @NonNull
    public abstract State getCurrentState();

    @SuppressWarnings("WeakerAccess")
    public enum Event {
    ON_CREATE,ON_START,ON_RESUME,ON_PAUSE,ON_STOP,
    ON_DESTROY,ON_ANY
    }

    @SuppressWarnings("WeakerAccess")
    public enum State {

        DESTROYED,INITIALIZED,CREATED,STARTED, RESUMED;

        public boolean isAtLeast(@NonNull State state) {
            return compareTo(state) >= 0;
        }
    }
}
```

Lifecycle的代码也并不多，注意它是一个抽象类。Lifecycle内部通过**两个枚举**分别定义了组件的生命周期的**状态**和**事件**。
下面这张图摘自Android开发者官网,介绍了**State**和**Event**的关系：<br/>

![摘自Android开发者官网](https://user-gold-cdn.xitu.io/2019/2/28/169346af1c3466be?w=700&h=381&f=png&s=54515)<br/>

简单说下这个图怎么看：**看箭头**，比如 ON_CREATE 箭头表示：State 从 INITIALIZED 变为 CREATED，会触发 ON_CREATE 的 Event。依次类推。

#### LifecycleRegistry.java
这个类的代码比较多，是 Lifecycle 的子类，也是监听组件声明周期的**核心类**。我们就从使用到的方法一个一个来看：
<br/>

**A**.构造方法，主要初始化了 mLifecycleOwner 和当前组件的状态(mState),没什么说的。
```
publicLifecycleRegistry(@NonNull LifecycleOwner provider) {
  mLifecycleOwner = new WeakReference<>(provider);
  mState = INITIALIZED;
}
```
**B**. 我们在 Lifecycle 的使用部分，继承了 LifecycleOwner，并在组件的生命周期内调用了 markState()方法。点进去如下：
```
public void markState(@NonNull State state) {
  moveToState(state);
}
```
**C**. 省去了一些复杂逻辑的代码，我们找到了这个核心的方法 sync():
```
private void moveToState(State next) {
 ...
  mHandlingEvent = true;
  sync();
  mHandlingEvent = false;
}

```
**D**. 同样在 sync()方法中，忽略掉逻辑代码，我们发现了正真执行的两个方法：backwardPass(),forwardPass()

```
private void sync() {
    LifecycleOwner lifecycleOwner = mLifecycleOwner.get();
    while(!isSynced()) {
        mNewEventOccurred = false;
        if(mState.compareTo(mObserverMap.eldest().getValue().mState) < 0) {
            backwardPass(lifecycleOwner);
        }
        Entry<LifecycleObserver, ObserverWithState > newest = mObserverMap.newest();
        if(!mNewEventOccurred && newest != null && mState.compareTo(newest.getValue().mState) > 0) {
            forwardPass(lifecycleOwner);
        }
    }
    mNewEventOccurred = false;
}

```
**E**. 在 backwardPass()和 forwardPass()，调用了 observer.dispatchEvent(),看名字想必就是发送事件的方法了：
```
private void forwardPass(LifecycleOwner lifecycleOwner) {
    ...
            observer.dispatchEvent(lifecycleOwner, upEvent(observer.mState));
    ...
}

private void backwardPass(LifecycleOwner lifecycleOwner) {
 ...
            observer.dispatchEvent(lifecycleOwner, event);
            popParentState();
 ...
}
```
**F**. 我们点击 dispatchEvent()方法，进入了下面这个静态内部类中，这个类中，就可以确定了：Observer(下面代码中的 mLifecycleObserver)触监听定生命周期的方法在这里被触发了。
    但是我们如何确定这个方法的 mLifecycleObserver 就是我们实现的 Observer 呢？这个静态类还有一个方法，我们点击一下看它在哪里被调用了：
```
static class ObserverWithState {
    State mState;
    GenericLifecycleObserver mLifecycleObserver;

    ObserverWithState(LifecycleObserver observer, State initialState) {
        mLifecycleObserver = Lifecycling.getCallback(observer);
        mState = initialState;
    }

    void dispatchEvent(LifecycleOwner owner, Event event) {
        State newState = getStateAfter(event);
        mState = min(mState, newState);
        mLifecycleObserver.onStateChanged(owner, event);
        mState = newState;
    }
}
```
**G**. 很高兴，我们来到了 addObserver()方法，你一定还记得我们在讲解 Lifecycle 使用部分是，绑定 Observer 就是使用这个方法。
```
@Override
public void addObserver(@NonNull LifecycleObserver observer) {
    State initialState = mState == DESTROYED ? DESTROYED : INITIALIZED;
    ObserverWithState*statefulObserver = new ObserverWithState(observer, initialState);
    ObserverWithState*previous = mObserverMap.putIfAbsent(observer, statefulObserver);
...
}
```
**H**. 最后我们看一眼 LifecycleObserver接口(观察者)，发现它是空的。其实我们看到注解应该猜到，系统是通过 apt-processor(注释处理器)将我们的 Observer 转换成 Lifecycle 真正需要类型 的Observer，下面是 OnLifecycleEvent 注解的声明：
```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnLifecycleEvent {
    Lifecycle.Event value();
}
```

#### 总结
看到这里，相信大家对 Lifecycle 的工作原理已经理解的差不多了吧，跟接口回调类似，**我们创建了 Obersver 的对象，然后将 Obersver 的引用交给 Lifecycle，在组件生命周期方法执行时，调用 Observer 的相应的方法。**

![](https://user-gold-cdn.xitu.io/2019/2/28/16934769544bf12c?w=982&h=453&f=png&s=72411)
当然，我们自己也可以写一套接口回调来监控组件的生命周期，但是我们不得不考虑在哪里设置监听器，生命周期的问题。
**其实我们使用 Lifecycle 真正的理由是，Lifecycle(LifecycleRegistry)类帮我们处理了大量的与宿主组件生命周期相关的问题，使我们的代码更简洁。专心处理业务逻辑即可。**

##### END

我是雷加,如果您喜欢我的文章，请留下你的赞;如有疑问和建议，请在评论区留言<br/>
**我的[Github](https://github.com/QzwJuHao),欢迎关注~**<br/>

Jetpack 专栏：<br/>
**Lifecycle：**[Jetpack系列 - Lifecycle从入门到源码](https://blog.csdn.net/qizewei123/article/details/88051500)<br/>
**ViewModel：**[Jetpack系列 - ViewModel从入门到源码](https://blog.csdn.net/qizewei123/article/details/88183933)<br/>
**LiveData：**[Jetpack系列 - LiveData从入门到源码](https://blog.csdn.net/qizewei123/article/details/88416990)<br/>

---------------------------------------   **The End**









