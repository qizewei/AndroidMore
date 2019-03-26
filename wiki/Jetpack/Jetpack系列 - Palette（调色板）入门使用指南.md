**这篇文章旨在用最简单的代码教会你 Palette的使用方式。**

### Palette 的介绍
**先看一下官方的介绍**

 > The palette library is a support library that extracts prominent colors from images to help you create visually engaging apps.
 
 翻译过来大概就是：Palette 是一个支持库，**可从图像中提取突出的颜色**，以帮助您创建引人入胜的应用程序。
 
 Palette的主要功能就是从图片中提取颜色，**目前只支持Bitmap**,其他格式的图片需要转成Bitmap的格式才能使用。Palette能帮我们提取图片中**六种主要的颜色**，以供我们选择。
 
 开发者可以使用 Palette 为图片，专辑封面的页面设置背景色，也可以**根据UI背景的颜色调整ToolBar和状态栏的颜色**。
 
 
 ### Palette 的使用
 为了更快的告诉读者调色板的使用，这里只做了简单使用，效果如下：
 


![稍等片刻，加载中...](https://user-gold-cdn.xitu.io/2019/3/18/1698ed1c0dc5691b?w=350&h=750&f=gif&s=4499577)

 实例中，我们点击按钮，根据图片的颜色来设置按钮和背景的颜色。
 
 代码很简单，如下：
 1. 导入依赖：
    ```
        dependencies {
            ···
            implementation 'com.android.support:palette-v7:28.0.0'
        }
    ```
2. 给布局文件添加 Button 和 ImageView，并调整到合适位置。
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <android.support.constraint.ConstraintLayout
    android:id="@+id/background"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    
    <Button
        android:id="@+id/change_bt"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text="Change !"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintHorizontal_bias="0.498"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/show_imageView"/>
    <ImageView
        android:id="@+id/show_imageView"
        android:layout_width="match_parent"
        android:layout_height="278dp"
        android:scaleType="fitXY"
        tools:layout_editor_absoluteX="0dp"
        tools:layout_editor_absoluteY="0dp"
        tools:srcCompat="@tools:sample/backgrounds/scenic"/>

    </android.support.constraint.ConstraintLayout>
    ```
3. 然后就是MainActivity：
    ```
    public class MainActivity extends AppCompatActivity {

    private Bitmap mBitmap;
    private boolean isFirst = true;
    private ImageView mShowImage;
    private ConstraintLayout mBackgroundLayout;
    private Button mChangeBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        // 日常FindByID ，找到对应组件。
        mBackgroundLayout = findViewById(R.id.background);
        mChangeBt = findViewById(R.id.change_bt);
        mShowImage = findViewById(R.id.show_imageView);

        // 设置初始的图片和背景色，按钮色
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img01);
        changeColor(mBitmap);

        mChangeBt.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFirst) {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img02);
            changeColor(mBitmap);
            } else {
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img03);
            changeColor(mBitmap);
            }
            isFirst = !isFirst;
        }
        });
    }

    // 主要的代码在这里~~~
    void changeColor(Bitmap bitmap) {
        mShowImage.setImageBitmap(bitmap);
        Palette.from(mBitmap).generate(new Palette.PaletteAsyncListener() {
        @Override
        public void onGenerated(Palette palette) {
            mBackgroundLayout.setBackgroundColor(
                palette.getMutedColor(getResources().getColor(R.color.colorPrimary)));
            mChangeBt.setBackgroundColor(
                palette.getLightMutedColor(getResources().getColor(R.color.colorPrimary)));
            }
        });
    }
    }
    ```

**代码到这里就写完了，然后运行，就能看到效果了。**

我们可以看到，Palette的调用主要就在这里：
    
```
        Palette.from(mBitmap).generate(new Palette.PaletteAsyncListener() {
        @Override
        public void onGenerated(Palette palette) {

            mBackgroundLayout.setBackgroundColor(
                palette.getMutedColor(getResources().getColor(R.color.colorPrimary)));
            mChangeBt.setBackgroundColor(
            palette.getLightMutedColor(getResources().getColor(R.color.colorPrimary)));
        }
        });
```
    
在 onGenerated 的回调方法中，我们可以通过出入的 palette 获得 上文提到的六种颜色：

```
 // 获取到柔和的深色的颜色（可传默认值）
 palette.getDarkMutedColor(Color.BLUE);
 // 获取到活跃的深色的颜色（可传默认值）
palette.getDarkVibrantColor(Color.BLUE);
// 获取到柔和的明亮的颜色（可传默认值）
palette.getLightMutedColor(Color.BLUE);
// 获取到活跃的明亮的颜色（可传默认值）
palette.getLightVibrantColor(Color.BLUE);
// 获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
palette.getVibrantColor(Color.BLUE);
// 获取图片中一个最柔和的颜色（可传默认值）
palette.getMutedColor(Color.BLUE);
```

具体的颜色的样子，官方网站给了一张实例图，大家自己在代码中运行一下，就能体会到了。

![image](https://user-gold-cdn.xitu.io/2019/3/18/1698ec24cced69d0?w=1248&h=536&f=png&s=295117)

注意：
1. palttte**偶尔可能捕获不到颜色**，所以需要传入默认值。
2. 如果你需要不断地生成图像或对象的排序列表调色板，防止慢UI性能。不应该在主线程上创建调色板。如果**异步生成调色板**，请使用该onGenerated() 回调方法在创建调色板后立即访问该调色板。
官方示例代码：
    ```
    // Generate palette synchronously and return it
    public Palette createPaletteSync(Bitmap bitmap) {
    Palette p = Palette.from(bitmap).generate();
    return p;
    }

    // Generate palette asynchronously and use it on a different
    // thread using onGenerated()
    public void createPaletteAsync(Bitmap bitmap) {
    Palette.from(bitmap).generate(new PaletteAsyncListener() {
        public void onGenerated(Palette p) {
        // Use generated instance
        }
    });
    ```


### END

我是雷加,如果您喜欢我的文章，请留下你的赞;如有疑问和建议，请在评论区留言<br/>
**我的[Github](https://github.com/QzwJuHao),欢迎关注~**<br/>

Jetpack 专栏：<br/>
**Lifecycle：**[Jetpack系列 - Lifecycle从入门到源码](https://blog.csdn.net/qizewei123/article/details/88051500)<br/>
**ViewModel：**[Jetpack系列 - ViewModel从入门到源码](https://blog.csdn.net/qizewei123/article/details/88183933)<br/>
**LiveData：**[Jetpack系列 - LiveData从入门到源码](https://blog.csdn.net/qizewei123/article/details/88416990)<br/>
**Palette：**[Jetpack系列 - Palette 入门指南](https://blog.csdn.net/qizewei123/article/details/88635617)<br/>

---------------------------------------   **The End**
