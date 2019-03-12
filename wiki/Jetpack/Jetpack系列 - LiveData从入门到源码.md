**这篇文章旨在用最简单的方式教会你LiveData使用，及它的工作原理。**

### LiveData介绍
LiveData 是 Google推出的 JetPack框架组件的一个，主要用来**给数据设置监听**，在数据变化时可以通知UI修改或者做其他操作。

### LiveData使用
Google推荐 LiveData 和 ViewModel 配合食用，[上篇文章](https://note.youdao.com/)我们已经了解过ViewModel，这里我们就使用LiveData + ViewModel，**读者注意留意两者的职责。**

1. 创建新的项目，给布局文件添加id:
    ```
    <android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
     <TextView
      android:id="@+id/data_tv"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="8dp"
      android:layout_marginBottom="8dp"
      android:layout_marginStart="8dp"
      android:layout_marginEnd="8dp"
      android:layout_marginLeft="8dp"
      android:layout_marginRight="8dp"
      android:gravity="center"
      android:text="Hello World!"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintEnd_toEndOf="parent"
      app:layout_constraintStart_toStartOf="parent"
      app:layout_constraintTop_toTopOf="parent"/>
    </android.support.constraint.ConstraintLayout>
    ```
    
2. 创建 MyBean 类,声明 MutableLiveData数据对象 (LiveData的扩展类，实现了setValue方法):
    ```
    public class MyBean extends ViewModel {
         private static MutableLiveData<String> dataBean;

         LiveData<String> getDataBean() {
           if (dataBean == null) {
             dataBean = new MutableLiveData<>();
              loadData();
            }
          return dataBean;
         }

        private void loadData() {
           dataBean.setValue("数据: ");
        }

        void changeValue() {
          dataBean.setValue(dataBean.getValue() + " +1 ");
        }
    }
    ```
    
3. 在 MainActivity中使用：
    ``` 
    public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView valueTv = findViewById(R.id.data_tv);

        // ViewModel的应用：通过 ViewModelProviders 获取我们自己的 ViewModel(myBean)
        mBean = ViewModelProviders.of(this).get(MyBean.class);
        mBean.getDataBean();
        // 打印我们当前 ViewModel(myBean) 的值
        valueTv.setText(mBean.getDataBean().getValue());

        // LiveData的应用：给数据设置监听，当数据改变时候，做一些操作(通常用来更新UI，实现UI和Data的自动同步)
        mBean.getDataBean().observe(this, new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            valueTv.setText(s);
        }
    });

        // 给 TextView 设置点击事件：打印我们当前 ViewModel(myBean) 的值
        valueTv.setOnClickListener(new OnClickListener() {
         @Override
        public void onClick(View v) {
            mBean.changeValue();
         Log.d(TAG, "onClick: 当前 Value 值: " + mBean.getDataBean().getValue());
         }
        });
        Log.d(TAG, "onCreate: ");
    }


    @Override
     protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
     super.onDestroy();
    }
    }
    ```

4. 运行app，控制台打印Log如下：
    ```
    /MainActivity: onClick: 当前 Value 值: 数据:  +1 
    /MainActivity: onClick: 当前 Value 值: 数据:  +1  +1 
    /MainActivity: onClick: 当前 Value 值: 数据:  +1  +1  +1 
    ```
    并且我们 Activity 页面的上 TextView的显示也发生了变化。
    
    以上代码很简单，我们使用LiveData为数据设置了监听，当数据变化时更新TextView的值。
    当我们通过点击事件真的修改数据的值得时候，TextView确实响应修改了。<br/>
    **恭喜你，已经学会使用LiveData了。**<br/>
    如果你对LiveData 的工作原理有兴趣，请继续阅读，我会带着你从源码一步一步完全理解 ViewModel 的工作原理。<br/>
    如果没有兴趣，可以直接拉到文章末尾看总结~<br/>
    
### LiveData的工作原理

1. **惯例思路，我们从使用到的 LiveData 方法作为入口**，在上述代码中，我们用到了 observer 方法：
    ```
    // LiveData的应用：给数据设置监听，当数据改变时候，做一些操作(通常用来更新UI，实现UI和Data的自动同步)
    mBean.getDataBean().observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        valueTv.setText(s);
      }
    });
    ```

2. 根据方法点进来之后，我们进入到了 LiveData的类，抛去无用的代码，我们看到方法**核心代码是下面这一句**：
    ```
    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
       ...
        LifecycleBoundObserver wrapper = new LifecycleBoundObserver(owner, observer);  //1
       ...
        owner.getLifecycle().addObserver(wrapper);
    }
    ```
    很明显，代码的意思是：**给 Lifecycle的宿主 添加了 观察者**，在 [Jetpack系列 - Lifecycle从入门到源码](https://blog.csdn.net/qizewei123/article/details/88051500) 我们知道，Lifecycle的宿主 正是我们的组件( Activity和Fragment )。

    就这样，我们把我们的 Observer 和我们的组件( Activity 和 Fragment )绑定到了一起。
    
    但是我们的组件生命周期变化时，我们的 Observer 会如何变化？在上面代码中，我们发现了 **系统用 LifecycleBoundObserver 把我们的 observer 进行了一次封装**才使用。

3. 我们点进 **LifecycleBoundObserver**
    ```
    class LifecycleBoundObserver extends ObserverWrapper implements GenericLifecycleObserver {
        @NonNull final LifecycleOwner mOwner;
        ···
        
        @Override
        boolean shouldBeActive() {
            return mOwner.getLifecycle().getCurrentState().isAtLeast(STARTED);
        }
        
        @Override
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (mOwner.getLifecycle().getCurrentState() == DESTROYED) {
                removeObserver(mObserver);
                return;
            }
            activeStateChanged(shouldBeActive());  //1
        }
        
        ···
    }
    ```
    **当 Lifecycle宿主 生命周期发生变化时,如果宿主没有被销毁，会执行到 activeStateChanged 方法。**

4. 在activeStateChanged 方法中，调用了 dispatchingValue。
    ```
   void activeStateChanged(boolean newActive) {
     ···
            if (mActive) {
                dispatchingValue(this);
            }
        }
    ```

5. **dispatchingValue 这个方法比较重要**。 

    ```
   private void dispatchingValue(@Nullable ObserverWrapper initiator) {
     ···
        do {
            mDispatchInvalidated = false;
            if (initiator != null) {
                considerNotify(initiator);
                initiator = null;
            } else {
            
                for (Iterator<Map.Entry<Observer<T>, ObserverWrapper>> iterator =
                        mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
                    considerNotify(iterator.next().getValue());
                    if (mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } 
        ···
    }
    ```
    **在 dispatchingValue 方法中，通过 considerNotify 通知 Observer 更新。**
    
    如果 观察者(ObserverWrapper)不止一个，则通过遍历 逐个通知更新。**这意味者我们可以为一个LiveData添加多个Observer。**
    
6. 在 considerNotify 中，我们看到了 onChanged，这个就是我们在**使用 LiveData 时重写的 方法**。但是大家注意看一下调用时机，当 mLastVersion >= mVersion 才会调用 onChanged 更新数据。
    ```
    private void considerNotify(ObserverWrapper observer) {
     ···
      if (observer.mLastVersion >= mVersion) {
            return;
        }
        observer.mLastVersion = mVersion;
        //noinspection unchecked
        observer.mObserver.onChanged((T) mData);
    }
    ```

7. 使用过LiveData后我们知道，**数据的改变才会触发Observer**，所以我们来到了 **setValue方法**。找到了 mVersion被调用的地方。
    ```
    @MainThread
    protected void setValue(T value) {
        assertMainThread("setValue");
        mVersion++;
        mData = value;
        dispatchingValue(null);
    }
    ```
    setValue方法，想必大家也看出来，**mVersion 是用来记录数据变化的。**
    
    联系上一步，我们知道，**considerNotify 方法中通过 mVersion 会检查数据是否更新，如果更新就调用 我们重写的 onChange 方法。**
    
    **在 setValue 方法中，我们也看到了 dispatchingValue 方法。证明，当数据变化时，也会通知 Observer 进行数据更新(considerNotify).**


### 总结
再回顾下我们看到的源码，就可以知道 

**LiveData的工作原理 和 接口回调类似，只不过LiveData 内部通过Lifecycle帮我们处理了生命周期变化时的种种情况**：

1. LiveData 把我们 重写的 Observer 进行了包装，然后**和当前组件(Activity或者Fragment)的 Lifecycle 进行了绑定**，以处理宿主生命周期变化的情况，比如当 Lifecycle 生命周期为Destroy时则不再通知。

2. 当 LiveData 数据变化(SetValue方法被调用)时 ，就会通过 dispatchingValue 方法，通知所有的 Observer 执行其重写的 OnChange 方法。

##### END

我是雷加,如果您喜欢我的文章，请留下你的赞;如有疑问和建议，请在评论区留言<br/>
**我的[Github](https://github.com/QzwJuHao),欢迎关注~**<br/>

Jetpack 专栏：<br/>
**Lifecycle：**[Jetpack系列 - Lifecycle从入门到源码](https://blog.csdn.net/qizewei123/article/details/88051500)<br/>
**ViewModel：**[Jetpack系列 - ViewModel从入门到源码](https://blog.csdn.net/qizewei123/article/details/88183933)<br/>
**LiveData：**[Jetpack系列 - LiveData从入门到源码](https://blog.csdn.net/qizewei123/article/details/88416990)<br/>

---------------------------------------   **The End**