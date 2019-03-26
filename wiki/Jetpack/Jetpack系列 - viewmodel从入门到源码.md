
**这篇文章旨在用最简单的方式教会你 ViewModel 如何使用，及它的工作原理。**
### ViewModel介绍
ViewModel是Google推出的JetPack框架组件的一个，**它的主要功能是包装数据，包装后的数据在其宿主(Activity)重建时，数据不会被被重建。**<br/>
通俗的讲，就是：<br/>
在Android系统中，Activity在配置更改后，Activity可能会重启（最常见如屏幕旋转）,此时我们 **修改** 过的Activity的数据(包括在此Activity中网络请求的数据) 就会被销毁然后重新初始化，这些 **修改** 就会丢失。<br/>
例如：如果我们不作处理，在EditText中输入内容，旋转屏幕后，Activity会被销毁重启，我们之前输入的内容也会丢失。这当然不是我们希望看到的。<br/>

![](https://user-gold-cdn.xitu.io/2019/3/5/1694c9eff5bf20d0?w=252&h=252&f=png&s=31606)<br/>
1. 传统方法中，我们可以在Activity销毁前使用 **onSaveInstanceState()** 的Bundle中存储数据，Activity重启时从 **onCreate()** 的Bundle中还原其数据，但此方法仅适用于可以序列化然后反序列化的少量数据，而不适用于潜在的大量数据，如列表数据或位图。<br/>
2. 现在Jetpack组件中的ViewModel能帮助我们在Activity重建时，保护数据不被销毁。 **它也可以用于同一个Activity下的所有Fragment之间的数据共享。** 真正实现将数据和UI分离。 <br/>
**官方推荐ViewModel和LiveData联合使用，但是为了大家更清楚的理解ViewModel的职责和使用，本篇文章将ViewModel单独拿出来使用，关于LiveData的使用，将在下篇文章细说。**<br/>

介绍的最后，大家看下这张 **Activity 和 ViewModel 生命周期对比**(图片摘自Android开发者官网)：<br/>
![ViewModel和Activity生命周期范围](https://user-gold-cdn.xitu.io/2019/3/5/1694ca16248d5f64?w=522&h=543&f=png&s=17900)<br/>
从图中我们可以看出，因为 ViewModel封装了数据，所以数据的生命周期比Activity还长，意味着数据不会再随Activity的生命周期而变化，某种程度上实现了**UI(Activity )和数据(ViewModel)的分离**。<br/>
**说的有点多了，我们进入正题：**

### ViewModel使用
#### 1. 基础使用
1. 创建新项目，在 MainActivity 的布局文件中给 TextView 添加 Id：data_tv。
2. 创建 ModelView 封装的数据类：
```
public class MyBean extends ViewModel {
  private  String dataBean;

  String getDataBean() {
    if (dataBean == null) {
      dataBean = “数据”;  // 初始化数据
    }
    return dataBean;
  }

  void changeValue() {
    dataBean = dataBean + " +1 " ;
  }
}
```
3. 在 MainActivity 中：
```
public class MainActivity extends AppCompatActivity {

  private static final String TAG = “MainActivity”;
  private MyBean mBean;

  @Override
  protected void onCreate( Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView valueTv = findViewById(R.id.data_tv);

    // ViewModel的应用：通过 ViewModelProviders 获取我们自己的 ViewModel(myBean)
    mBean = ViewModelProviders.of(this).get(MyBean.class);
    mBean.getDataBean();

    valueTv.setText(mBean.getDataBean());

    valueTv.setOnClickListener(*new*OnClickListener() {
      @Override
      public void onClick(View v) {
        mBean.changeValue();
        Log.d(TAG, “onClick: 当前 Value 值: “ + mBean.getDataBean());
      }
    });

    Log.d(TAG, “onCreate: “);
  }

  @Override
  protected void onDestroy() {
    Log.d(TAG, “onDestroy: “);
    super.onDestroy();
  }
}
```

OJBK，代码已经写完了~


![](https://user-gold-cdn.xitu.io/2019/3/5/1694e869c3217105?w=240&h=240&f=jpeg&s=53260)<br/>

其实，最近更新代码很简单：<br/>
Activity 初始化的时候初始化我们的 ViewModel（MyBean）并赋值，将ViewModel 的值显示在 TextView 上，然后我们在 TextView 上添加了点击事件，如果点击，触发 changeValue() 修改 ViewModel 的值。

然后我们运行APP，点击三下TextView，然后旋转屏幕，再次点击TextView，同时查看Log：
```
D/MainActivity: onCreate: 
D/MainActivity: onClick: 当前 Value 值: 数据 +1 
D/MainActivity: onClick: 当前 Value 值: 数据 +1  +1 
D/MainActivity: onClick: 当前 Value 值: 数据 +1  +1  +1 
D/MainActivity: onDestroy: 
D/MainActivity: onCreate: 
D/MainActivity: onClick: 当前 Value 值: 数据 +1  +1  +1  +1 
```

Log显示表示：我们ViewModel的数据确实在屏幕旋转后存活下来了。<br/>
如果你想TextView的值随点击事件改变，可以通过 TextView.setText 方法，但LiveData是更优雅简便的实现，下篇分析。

#### 2. 用来在Fragment之间分享数据
**一个 Activity 中的两个或多个 Fragment 需要相互通信是很常见的，但这种情况通常很麻烦：**<br/>
设想：一个 Activity 中同时显示两个Fragment，一个Fragment 用来显示数据的列表，另一个 Fragment 显示所选数据的内容。
这种情况并不简单，因为两个 Fragment 都需要定义一些接口，并且 Activity 必须将这两个 Fragment 绑定在一起。此外，如果有第三个 Fragment ,这两个Fragment 都必须处理另一个 Fragment 的各种场景。

**但是，如果我们采用ViewModel，事情就变简单了：**
```
mBean = ViewModelProviders.of(getActivity()).get(MyBean.class);
```

两个 Fragment 都通过该方式获取 ViewModel 。这样，当 Fragment 各自会收到相同的 ViewModel 实例，该实例的作用域是此 Activity 的作用域。无论有多少个Fragment都可以通过该方式与Activity和所有Fragment共享数据。

好处：
1. 该 Activity 不需要做任何事情，也不需要知道任何有关此通信的信息。
2. 除了ModelView 的之外，Fragment 不需要互相了解。如果其中一个Fragment 销毁了了，另一个仍然像往常一样工作。
3. 每个 Fragment 都有自己的生命周期，并且不受另一个 Fragment 生命周期的影响。如果一个 Fragment 替换另一个 Fragment，那么UI将继续工作而不会出现任何问题。

#### 3. 其他使用

单纯的ViewModel的使用并不能帮我们提高开发效率，下面是几种常见的扩展：
1. **LiveData**: ViewModel 对象和 LiveData（生命周期观察器）联合使用，这也是 Android 的官方推荐。ViewModel 加上了 LiveData 之后，在 Activity 中便可以感知到数据的变化，并在此更新UI。 **真正实现UI和数据的分离，但数据变化时，UI仍然可以随之改变。** 此外，LiveData 还有更多贴心的设计，这个我们下篇再聊。

2. **AndroidviewModel**:如果 ViewModel型需要 Context来做一些操作。这个需求是很常见的。此时我们可以使用或扩展 AndroidViewModel 类，这个比较简单，大家搜索看一下 AndroidViewModel的代码即可，此处不再赘述。
3. **static扩展**:如果 ViewModel 想实现 Activity 之间的数据共享，只需要在 ViewModel 类内具体封装的数据类添加 static 声明，例如：
```
public class MyBean extends ViewModel {
  static private  String dataBean;
...
}
```
这意味着全局只有一份 dataBean 实例，但当我们需要共享登陆用户信息的时候，这个比较有用。
到这里，相信你对ViewModel的使用已经有了自己的理解了<br/>

**恭喜你，ViewModel的精华部分你已经体验过了。**<br/>
如果你对**ViewModel的工作原理**有兴趣，请继续阅读，我会带着你从源码一步一步完全理解 ViewModel 的工作原理。<br/>
如果没有兴趣，可以直接拉到文章末尾看总结~

### ViewModel 的工作原理：
ViewModel 的工作原理核心的问题就是:<br/>
ViewModel 存储在哪里？如何存取的？<br/>
ViewMoedl 如何实现Activity被销毁时而自己不被销毁呢？

#### 1. ViewModelProviders.java
 一步一步来看源码，我们从使用过的这一句入手:
```
mBean = ViewModelProviders.of(this).get(MyBean.class);
```
点击进入 ViewModelProviders.java ,这是它的方法列表:

![ViewModelProviders方法列表](https://user-gold-cdn.xitu.io/2019/3/5/1694ca27be12200d?w=447&h=242&f=png&s=40946)
可以看到，两个check方法为空判断，然后看 of 方法：
```
@NonNull
@MainThread
public static ViewModelProvider of(@NonNull FragmentActivity activity,
        @Nullable Factory factory) {
    Application application = checkApplication(activity);
    if(factory == null) {
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }
    return new ViewModelProvider(ViewModelStores.of(activity), factory);
}
```
这个不是很难，可以看出，ViewProviders 主要职责:
* 通过 Application 获取 AndroidViewModelFactory（这个 AndroidViewModelFactory 是个 **单例模式**）。
* 初始化并返回 ViewModelProvider。
需要传入 ViewModelStore 和 AndroidViewModelFactory。<br/>
**Activity持有ViewModelStroe**（因为 AppCompatActivity  继承 FragmentActivity，而 FragmentActivity 实现了 ViewModelStoreOwner，拥有ViewModelStroe）所以此处使用 ViewModelStores.of(activity)。<br/>
这个 ViewModelStores .of() 我们稍后看。

#### 2.  ViewModelProvider.java
然后我们进入 ViewModelProvider.java:
```
public class ViewModelProvider {

    private static final String DEFAULT_KEY = "android.arch.lifecycle.ViewModelProvider.DefaultKey";

    private final Factory mFactory;
    private final ViewModelStore mViewModelStore;

    public ViewModelProvider(@NonNull ViewModelStore store, @NonNull Factory factory) {
        mFactory = factory;
        this.mViewModelStore = store;
    }

    @NonNull
    @MainThread
    public <T extends ViewModel> T get(@NonNull String key, @NonNull Class <T> modelClass) {
        ViewModel viewModel = mViewModelStore.get(key);

        if (modelClass.isInstance(viewModel)) {
            //noinspection unchecked
            return(T) viewModel;
        } else {
            //noinspection StatementWithEmptyBody
            if(viewModel != null) {
                // TODO: log a warning.
            }
        }

        viewModel = mFactory.create(modelClass);
        mViewModelStore.put(key, viewModel);
        //noinspection unchecked
        return(T) viewModel;
    }

 
    public static class NewInstanceFactory implements Factory {

        @SuppressWarnings("ClassNewInstance")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class <T> modelClass) {
            try{
                return modelClass.newInstance();
            }  catch (InstantiationException*e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
    }

public static class AndroidViewModelFactory extends 
ViewModelProvider.NewInstanceFactory{
        private static AndroidViewModelFactory sInstance;

        @NonNull
        public static AndroidViewModelFactory getInstance(@NonNull Application application) {
            if ( sInstance == null) {
                sInstance = new AndroidViewModelFactory(application);
            }
            return sInstance;
        }

        private Application mApplication;

        public AndroidViewModelFactory(@NonNull Application application) {
            mApplication = application;
        }
···
}

```
这个类主要用来取数据，主要就两个方法：
* 构造方法：传入ViewModelStroe，Factory。<br/>
Factory：AndroidViewModelFactory，静态内部类，**继承NewInstanceFactory,主要用来将 ModelView.Class实例化**。

* **get方法**：检查ViewModelStore 中是否有指定ViewModel的实例，如果已经有，就从ViewModelStore取，如果没有，就通过Factory实例化。

到这里，我们已经明白了第一个问题的答案：<br/>
**真正的数据是存储在 ViewModelStroe的,通过 ViewModelProvider 来存取。**

#### 3.  ViewModelStores.java
然后我们来看 ViewModelStores 的 of 方法 ，在这里我们可以找到Activity 重建时ViewModel 不被破坏的线索:
```
@NonNull
@MainThread
public static ViewModelStore of(@NonNull FragmentActivity activity) {
    if(activity instanceof ViewModelStoreOwner) {
        return((ViewModelStoreOwner) activity).getViewModelStore();
    }
    return holderFragmentFor(activity).getViewModelStore();
}
```
重点在最后一句代码中，传入activity 从 holderFragment中取出 ViewModelStore。

#### 4. HolderFragment.java
我们点进这个HolderFragment.java :
```
public HolderFragment() {
    setRetainInstance(true);
}

```
我们找到了这个方法，这个方法的含义就是：**在Activity因为配置更改而销毁重建时，fragment不会执行 onDestroy() 而被保留**。<br/>
这是 Fragment 的使用方法，我们知道就行，如果还想进入 Fragment一探究竟，这里有个[推荐链接](https://blog.csdn.net/gaugamela/article/details/56280384)。

#### 5. HolderFragmentManager
然后我们在 HolderFragment.java 发现了 HolderFragmentManager 这个静态内部类：
```
static class HolderFragmentManager{
    private Map<Activity, HolderFragment> mNotCommittedActivityHolders = new HashMap<>();
    private Map<Fragment, HolderFragment> mNotCommittedFragmentHolders = new HashMap<>();
...
```
看到这里，我想大家都明白了：HolderFragmentManager并通过键值对的方式存储Activity和holderFragment ，以完成Activity和holderFragment 的映射。<br/>
第二个问题的答案想必大家也明白了：<br/>
**ViewModelStroe 和 holderFragment 建立了联系，holderFragment 保证了Activity重建时 ViewModelStroe 不被销毁。**


### 总结：
**一图胜千言：**

![一图胜千言](https://user-gold-cdn.xitu.io/2019/3/5/1694ca3796437d45?w=2290&h=1040&f=png&s=268906)

ViewModel的实现原理：<br/>
**1. 真正的数据是存储在 ViewModelStroe的,通过 ViewModelProvider  来存取，<br/>
2. ViewModelStroe 和 holderFragment 建立了联系，holderFragment 保证了Activity重建时 ViewModelStroe 不被销毁。**


---------------------------------------   **结束分割线**

##### END

我是雷加,如果您喜欢我的文章，请留下你的赞;如有疑问和建议，请在评论区留言<br/>
**我的[Github](https://github.com/QzwJuHao),欢迎关注~**<br/>

Jetpack 专栏：<br/>
**Lifecycle：**[Jetpack系列 - Lifecycle从入门到源码](https://blog.csdn.net/qizewei123/article/details/88051500)<br/>
**ViewModel：**[Jetpack系列 - ViewModel从入门到源码](https://blog.csdn.net/qizewei123/article/details/88183933)<br/>
**LiveData：**[Jetpack系列 - LiveData从入门到源码](https://blog.csdn.net/qizewei123/article/details/88416990)<br/>

---------------------------------------   **The End**












 
