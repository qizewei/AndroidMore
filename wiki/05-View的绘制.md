1. View的三大流程(measure，layout，draw)都是通过ViewRoot来完成的，在ActivityThread中，当Activity对象被创建完毕后，会将DoctorView添加到Window中，同时创建ViewRootImp对象将和DecorView建立关联。
2. ViewRoot对应于ViewRootImp,它是连接 WindowManager和 DecorView的纽带。
3. View的绘制流程是从ViewRoot的performTraversals方法开始的。

![image](https://note.youdao.com/yws/res/7784/A4F344D719B840818079E6BD2D49B498)
4. performTraversals 会依次调用performMeasure，performLayout，performDraw三个方法完成顶级View的绘制流程。
5. measure过程：决定了View的宽高：通过getMeasureWidth/Height获取测量后的宽高。
6. Layout过程：决定了View的四个顶点位置和实际View的宽高：通过getTop/bottom/right/left拿到四个顶点的位置。
7. Draw过程：决定了View的显示，Draw方法完成以后 View的内容才呈现在屏幕上。
8. ViewGroup的Measure过程中，一般是先测量子View的宽高之后，再确定自身的宽高。layout则是现在Layout()中确定自己的布局，然后在onLayout中布局子View。

1. MeasureSpec是一个32位int值，高2位代表SpecMode(测量模式)，低30位代表SpecSize(指定模式下规定的大小)。
2. MeasureSpec一旦确定后，onMeasure就可以确定View的测量宽高。
3. SpecMode(测量模式)的三个值：
1. UNSPECIFIED:父容器对View没有任何限制，要多大给多大。
2. EXACTLY:父容器已经检测出View的精确大小，SpecSize就是View的大小。它对应LayoutParams中的matchParent和具体数值两种模式。
3. AT_MOST:View的最终大小不能不能大于SpecSize所制定的值，对应于LayoutParams中的wrap_content。
4. onMeasure中调用setMeasuredDimension()方法设定View宽高。
4. 可以给View设置LayoutParams，在View测量的时候，系统会将LayoutParams在父容器的约束下转换成对应的MeasureSpec，然后根据MeasureSpec来确定View测量的宽高。
5. ViewGroup的measureChildWithMargins会对子元素进行measure，在调用子元素的measure之前会先通过getChildMeasureSpec来得到子元素的Measure(子元素的measure的创建与父容器的MeasureSpec和子元素本身的LayoutParams有关，还有View的padding,marging有关)。

measure：
1.  ViewGroup除了完成自己的测量之外，还会去遍历调用子元素的measure方法。
2.  measure方法是一个final方法，意味着子类不能重写。
3.  直接继承View的自定义控件，需要重写onMeasure方法并设置wrap_content时的自身大小，否则在布局中使用wrapcontent就相当于使用matchparent。
4.  因为自定义View如果使用wrapcontent,那么他的specMode是AT_MOST，那么他的宽高等于specSize，但此时的View的specSize是parentSize是父容器剩余的大小。
5.  处理方法：重写onMeasure方法，给View指定一个默认宽高，并在wrapcontent时设置此宽高(setMeasuredDimension)，对于非wrapcontent的情况，沿用系统设置即可。
6.  因为不同的ViewGroup对子类有不同的布局特性，所以ViewGroup是一个抽象类，他是没有定义其测量的具体过程，他的测量过程的onMeasure需要各个子类(LinearLayout等)去实现。
7.  某些极端情况，系统可能多次measure才能确定最终宽高，这种情况通过getMeasureWidth获得的宽高是不准确的，一个比较好的习惯是在onLayout中去获取View的宽高。
8.  View的Measure过程和Activity的生命周期不是同步的，所以在Activy某个生命周期方法中获取View的宽高可能为0(没有绘制完成)。
9.  四种解决方法：
1.  onWidndowFocusChanged：这个方法的含义是：View已经初始化完成了，宽高都准备好了。onWidndowFocusChanged会被调用多次(得到焦点和失去焦点时)。
2.  view.post(runable):通过post将runable投到信息队列尾部，然后等Looper调用到的时候，View也已经初始化好了。
3.  ViewTreeObserver:该Observer的众多回调可以完成这个功能。
4.  view.measure(int,int)：通过手动对View进行measure来得到View的宽高，这种方法比较复杂，需要根据View的LayoutParams来区别对待。
10. ViewGroup一般重写onMeasure方法，遍历测量子View，否则就会执行View相同的逻辑。

layout：
1.  ViewGroup的位置被确定后，在onLayout中会遍历所有的子元素并调用layout方法。
2.  layout方法确定View本身的位置，onLayout则确定所有子元素的位置。
3.  View的布局主要通过确定上下左右四个点来确定的。
4.  onLayout方法：View不实现，ViewGroup必须实现来对子View布局。
5.  layout()通过setFrame()来确定自身的布局。

draw：
1.  onDraw方法：是个空方法，所以View和ViewGroup都需要实现。
1.  View的绘制传递是通过dispatchDraw来实现的，dispatchDraw会遍历所有子元素的Draw方法。
2.  View的setWellNotDraw方法：如果一个View或者ViewGroup不需要绘制任何内容时，可以开启这个标志位，系统会进行相应的优化。
3.  Draw的绘制步骤：
1.  绘制背景 background.draw(canvas)
2.  绘制自己 onDraw
3.  绘制Child dispatchDraw(非ViewGroup不实现)
4.  绘制装饰 onDrawScrollBars

自定义View
1.  四种分类：
1.  继承View重写onDraw方法。
2.  继承ViewGroup派生特殊的Layout。
3.  继承特定的View(如TextView)。
4.  继承特定的ViewGroup(如LinearLayout)。
2.  自定义View注意点：
1.  让View支持wrap_content;
2.  如有必要，让View支持padding；
3.  不要在View中使用Handler，因为View内部提供了post系列的方法；
4.  View中如果有线程或者动画，需要及时停止，View的onDetachedFromWindow会在Activity退出或者Remove的时候调用，对应的方法是onAttachedWindow。当View不可见时也需要停止线程和动画。
5.  View带滑动嵌套情形时，需要处理好滑动冲突。
