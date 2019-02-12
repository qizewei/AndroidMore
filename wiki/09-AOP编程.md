1. AOP技术利用一种称为”横切”的技术，剖解开封装的对象内部，并将那些影响了多个类的公共行为封装到一个可重用模块，并将其命名为”Aspect”，即切面。所谓”切面”，简单说就是那些与业务无关，却为业务模块所共同调用的逻辑或责任封装起来，便于减少系统的重复代码，降低模块之间的耦合度，并有利于未来的可操作性和可维护性。
2. AspectJ是一个面向切面编程的一个框架，它扩展了java语言，并定义了实现AOP的语法。在将.java文件编译为.class文件时默认使用javac编译工具，而AspectJ会有一套符合java字节码编码规范的编译工具来替代javac，在将.java文件编译为.class文件时，会动态的插入一些代码来做到对某一类指定东西的统一处理。
3. AspectJ使用：
    1. 引入jar包，配置文件和环境；
    2. 定义一个注解（例如LoginFilter,LogFilter）,用来注解方法，对这些方法做统一的切面标注；
    3. 编写一个单例来统一处理所有切面，单例的初始化越早越好；
    4. 编写Java文件来同一处理所有标注的切面，示例代码
```
//@Aspect, @Pointcut,@Around等都是AspectJ定义的API
@Aspect
public class LoginFilterAspect {
    private static final String TAG = "LoginFilterAspect";
    
    //Pointcut(切入点)的excution指定处理切面的注解
    @Pointcut("execution(@com.xsm.loginarchitecture.lib_login.annotation.LoginFilter * *(..))")
    public void loginFilter() {}

    //Around表示被注解的方法执行前后都可以做切面处理，类似的注解还有@Before，@After
    @Around("loginFilter()")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //逻辑处理1
        ILogin iLogin = LoginAssistant.getInstance().getiLogin();
        if (iLogin == null) {
            throw new NoInitException("LoginSDK 没有初始化！");
        }

        //逻辑处理2
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new AnnotationException("LoginFilter 注解只能用于方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        LoginFilter loginFilter = methodSignature.getMethod().getAnnotation(LoginFilter.class);
        if (loginFilter == null) {
            return;
        }

        Context param = LoginAssistant.getInstance().getApplicationContext();
        //逻辑处理3
        if (iLogin.isLogin(param)) {
            joinPoint.proceed();
        } else {
            iLogin.login(param, loginFilter.userDefine());
        }
    }
}
```
4. Build项目之后，项目下\build\intermediates\classes\debug文件夹会生成经过AspectJ编译器编译后的.class文件。此时的文件就已经被处理过了，AspectJ会在运行期将目标类加载后，为接口生成动态代理，将切面织入到代理类中，从而实行对方法的统一处理。

##### 笔记来源：[XSM的文章](https://juejin.im/post/5b75244e6fb9a009c927b7c1)
