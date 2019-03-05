### RxJAVA
1. Push-Based的数据处理方式的大家也都把它称为观察者模式。Push模型的理念就是：上游（Observable）几乎需要处理所有繁杂，耗时的工作，而下游（Observer）只需要对结果数据进行响应。
2. Observable接口会通过subscribe()方法拿到Observer的引用，然后当有数据的时候通知Observer(数据流向：数据从Observable流向Observer)。
3. 操作符的类的基本结构，也就是说，每个操作符都实现了一整个基于Push的接口体系，同时实现了Observable和Observer两个接口，想要实现整个调用链的数据流转，那么每个操作符只需要给上游操作符提供Observer，并给下游提供一个Observable。
4. 实现异步：每个操作符内部都会接收到上游操作符传入的数据，那么线程操作符会将上游传入的数据缓存起来，然后开启一个线程，在线程中传递出去，.observeOn(Schedulers.io())来表示我们需要切换到io线程，内部实现：
```
ObservableObserveOn(Observable的子类)
@Override
protected void subscribeActual(Observer observer) {
// 先获得一个线程操作类，Work封装了具体的线程
Scheduler.Worker worker=schedulers.createWorker();
// 创建一个内部类
ObserveronInner inner=new ObserveronInner(observer,worker);
// 将这个内部类传递给上游
// 上游的Observable获得内部类的引用，
// 当上游有数据时，会将数据传递给这个内部类
observable.subscribe(inner);
```
5. Schedulers.io()会获取到IoScheduler对象，但是我们发现Schedulers内的IoScheduler对象是一个全局静态的对象,而且它是在静态代码块中被初始化的，IO内部维护了一个线程池，通过createWorker()方法，可以获取到一个CachedWorkerPool对象，转化为work()返回。:
```
public final class Schedulers {
@NonNull
static final Scheduler SINGLE;

@NonNull
static final Scheduler COMPUTATION;
//
@NonNull
static final Scheduler IO;

@NonNull
static final Scheduler TRAMPOLINE;

@NonNull
static final Scheduler NEW_THREAD;
}
```

6. Push-Based的数据处理方式,上下游是不会充分沟通的,当上游出现大量数据，下游处理速度很慢时，就会出现问题。
7. 背压：在Rxjava2.x中，除了Observable/Observer这套核心类之外，还有一套Flowable/Subscriber核心类，这其中，Flowable/Subscriber就实现了Reactive Stream的API接口，全面支持上下游的速度的协调，也就是支持背压。
9. 响应式的思想，它希望有某种方式能够传递变化，构建关系，而不是执行某种赋值命令。对于命令式编程和响应式编程有一个比较有意思的比喻:
1. 响应式： 上厕所之后应该洗手才能吃饭。
2. 命令式：上厕所，洗手，吃饭。

### Retrofit
1. Retrofit的Create中使用动态代理模式实现我们定义的网络请求接口，在重写invoke方法的时候构建了一个ServiceMethod对象，在构建这个对象的过程中进行了方法的注解解析得到网络请求方式httpMethod，以及参数的注解分析，拼接成一个省略域名的URL。

2. 当一切都准备好之后会把数据添加到Retrofit的RequestBuilder中。等待发起网络调用。

3. 当我们调用Retrofit的网络请求方式的时候，就会调用okhttp的网络请求（toCall方法，根据获得的参数），okhttp的配置包括请求方式，URL等在Retrofit的RequestBuilder的build()方法中实现，然后发起真正的网络请求。

