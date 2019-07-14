# _DesignPattern
设计模式C++/java描述



### UML类图

类之间的关系：依赖、泛化（继承）、实现、关联、聚合与组合。

* 依赖关系

  * 类中使用到了方法
  * 如果是类的成员属性
  * 如果是方法的返回类型
  * 是方法接受的参数类型
  * 方法中使用到

* 泛化关系：是依赖关系的一种特例，就是继承关系   A继承B ->A和B存在泛化关系

* 实现关系：是依赖关系的特例，A类实现B类

* 关联关系：单向一对一关系、双向一对一关系。类与类之间的关系

  * ```java
    //单向一对一
    public class Person{
        private IDCard card;
    }
    public class IDCard{}
    ```

  * ```java
    //双向一对一
    class Person{
        private IDCard card;
    }
    public class IDCard{
        private Person person;
    }
    ```

* 聚合关系：表示的是整体和部分的关系，整体与部分可以分开，聚合关系是关联关系的特例。使用空心菱形的实现表示

  一台电脑由键盘、显示器，鼠标组成；*组成电脑的各个部分可以从电脑上分离出来*。注意：如果不能分离出来则为组合关系。

* 组合关系：如上所述

  ```java
  public class Computer{
      private Mouse mouse=new Mouse();//鼠标和computer不能分离，共存亡
      private Monitor monitor; //显示器和电脑可以分离
      
      //setter...
  }
  ```



## 设计模式？

设计模式代表了一种**最佳的实践**，是众多软件开发人员经过想当长的一段时间的实验和总结出来的！是针对某些问题的一种通用的解决方案。

* 提高软件的维护性、通用性和扩展性，并降低软件的复杂度



### 单例模式

---

一、单例模式分析：

* 饿汉式（静态常量）、饿汉式（静态代码块）
* 懒汉式（线程不安全、线程安全同步方法、线程安全同步代码块）
* 双重检查法
* 静态内部类
* 枚举

饿汉式（静态常量）应用实例

1、构造器私有化（防止new）

2、类的内部创建对象

3、向外暴露一个静态的公共方法  getInstance

4、代码实现

* 饿汉式1 使用静态变量

```java
package singleton;

/**
 * @Author: sam
 * @Description:
 **/
public class SingletonTest01 {
    public static void main(String[] args) {
        //测试
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2);//true
        System.out.println("instance2 hashcode：" + instance2.hashCode());
        System.out.println("instance hashcode:" + instance.hashCode());
    }
}

//饿汉式（静态变量）

/**
 * 1、优点：比较简单，在类加载时完成实例化，避免线程同步问题
 * 2、缺点：类加载时就完成实例化，没有达到lazy loading效果，如果从始至终没有实例化这个实例，会造成内存浪费
 * 3、这种方式基于classloader机制避免了多线程的同步问题。不过，instance在类装载时就实例化了，在单例模式
 * 中大多数都是调用getInstance方法，但是导致类装载的原因有很多，因此不能确定有其它方式（或者其它的静态方法）
 * 导致类装载，这时候初始化instance就没有达到lazy loading的效果。
 * 4、这种单例模式可能造成内存浪费
 */
class Singleton {
    //1、构造器私有化,外部不能new
    private Singleton() {

    }

    //2、本类内部创建对象实例  类加载时创建对象
    private final static Singleton instance = new Singleton();


    //3、提供一个共有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return instance;
    }
}

```

* 饿汉式2：静态代码块
  优点与缺点与懒汉式1相同，简单但可能造成内存浪费

```java
package singleton.singleton2;

/**
 * @Author: sam
 * @Description:
 **/
public class SingletonTest02 {
    public static void main(String[] args) {
        //测试
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2);//true
        System.out.println("instance2 hashcode：" + instance2.hashCode());
        System.out.println("instance hashcode:" + instance.hashCode());
    }
}

//饿汉式（静态代码块）
class Singleton {
    //1、构造器私有化,外部不能new
    private Singleton() {

    }

    //2、本类内部创建对象实例  类加载时创建对象
    private static Singleton instance;

    static { //在静态代码块中，创建单例对象
        instance = new Singleton();
    }

    //3、提供一个共有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return instance;
    }
}

```

* 懒汉式：线程不安全
  * 起到了lazy loading的效果，但是只能在单线程下使用
  * 如果多线程，一个线程进入了if(singleton==null)，还未来得及往下执行，另一个线程也通过了这个判断语句，这时就会生成多个实例，所以在多线程环境下不可使用。

```java
package singleton.singleton3;

/**
 * @Author: sam
 * @Description:
 **/
public class SingletonTest03 {
    public static void main(String[] args) {
        //测试
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2);//true
        System.out.println("instance2 hashcode：" + instance2.hashCode());
        System.out.println("instance hashcode:" + instance.hashCode());
    }
}

class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    //提供一个静态的公有方法，当使用该方法时，才去创建instance  懒汉式  线程不安全
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}

```

懒汉式2 ：解决线程安全问题，但是效率太低了，每个线程在想获得类的实例的时候，执行getInstance()方法都要进行同步，而其实这个方法只执行一次实例化代码就够了，后面想获得该类实例直接return，方法进行同步效率太低。

```java
 //加入同步处理的代码，解决线程安全问题
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
```

懒汉式3：线程安全，同步代码块

但是这种同步并不能起到线程同步的作用，实际开发中不使用

```java
class Singleton{
    private static Singleton singleton;
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        if(singleton==null){
            sychronized(Singleton.class){
                singleton=new Singleton();
            }
        }
        return singleton;
    }
}
```

双重检查法（解决线程安全和效率问题）

是多线程开发中经常使用到的，实例化代码只用执行一次，后面再次访问时，判断if(singleton==null),直接return实例化对象，也避免的反复进行方法同步

线程安全；延迟加载；效率较高

实际开发中，使用这种单例设计模式

```java
class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
    }

    //加入双重检查法，解决线程安全问题，同时解决懒加载问题
    //同时保证了效率，推荐使用
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
```

*静态内部类*

* 这种方式采用了类装载的机制来保证初始化实例时只有一个线程。
* 静态内部类方式在Singleton类被加载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成Singleton的实例化
* 类的静态属性只会在第一次加载类的时候初始化，所以JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
* 避免了线程不安全，利用静态内部类特点实现延迟加载，效率高。

```java
//使用静态内部类完成单例模式,推荐使用
class Singleton {
    private static volatile Singleton instance;

    //构造器私有化
    private Singleton() {
    }

    //写一个静态内部类，该类中有一个静态属性Singleton
    private static class SingletonInstance {
        private static final Singleton INSTANCE = new Singleton();
    }

    //提供一个静态的共有方法，直接返回SingletonInstance.INSTANCE
    public static synchronized Singleton getInstance() {
        return SingletonInstance.INSTANCE;
    }
}

```

枚举方式

借助JDK1.5中添加的枚举来实现单例模式，不仅能避免多线程同步问题，而且还能**防止反序列化重新创建新的对象**，推荐使用。

```java
package singleton.singleton6;

/**
 * @Author: sam
 * @Description:
 **/
public class SingletonTest06 {
    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;
        Singleton instance2 = Singleton.INSTANCE;

        System.out.println(instance == instance2);
        System.out.println(instance.hashCode());
        System.out.println(instance2.hashCode());

        instance.sayOK();
    }
}

//使用枚举可以实现单例
enum Singleton {
    INSTANCE;  //属性

    public void sayOK() {
        System.out.println("ok~");
    }
}
```

二、单例模式的案例

1、JDK  java.lang.Runtime就是经典的单例模式。

```java	
public class Runtime {
    private static Runtime currentRuntime = new Runtime();

    public static Runtime getRuntime() {
        return currentRuntime;
    }
    private Runtime() {}
	
    //...
}
```

三、单例模式使用的细节和注意事项：

1、单例模式保证了系统中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高系统性能。

2、当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用new

3、单例模式使用的场景：需要频繁的进行创建和销毁的对象、创建对象时消耗过多或耗费资源过多（即：重量级对象），但又经常用到的对象、工具类对象、频繁访问数据库或文件的对象（比如数据源、session工厂等）



### 简单工厂模式

---

一、一个具体的需求

制作pizza的过程，要求要便于披萨种类的扩展，便于维护

* 披萨的种类有很多（比如GreekPizz、ChesssePizz等）
* 披萨的制作有prepare、bake、cut、box
* 完成披萨店订购的功能。



1、先看传统的方式实现：

代码如下：首先有一个抽象类

```java	
package simplefactory.pizzastore.pizza;

/**
 * @Author: sam
 * @Description:
 **/
public abstract class Pizza { //抽象类
    protected String name;  //pizza名字

    //准备原材料，不同的pizza不一样，因此为抽象方法
    public abstract void prepare();

    //烘烤
    public void bake() {
        System.out.println(name + " baking;");
    }

    //切割
    public void cut() {
        System.out.println(name + " cutting;");
    }

    //打包
    public void box() {
        System.out.println(name + " boxing;");
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

2、不同披萨实现自己不同的准备方法

奶酪披萨

```java	
public class CheessPizza extends Pizza{
    @Override
    public void prepare() {
        System.out.println("给制作奶酪披萨，准备原材料");
    }
}
```

希腊披萨

```java
public class GreekPizza extends Pizza{
    @Override
    public void prepare() {
        System.out.println("给希腊披萨 准备原材料");
    }
}

```

3、订购披萨的订单类

```java	
package simplefactory.pizzastore.order;

import simplefactory.pizzastore.pizza.CheessPizza;
import simplefactory.pizzastore.pizza.GreekPizza;
import simplefactory.pizzastore.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: sam
 * @Description:
 **/
public class OrderPizza {

    //构造器
    public OrderPizza() {
        Pizza pizza = null;
        String orderType; //订购披萨的类型
        do {
            orderType = getType();
            if (orderType.equals("greek")) {
                pizza = new GreekPizza();
                pizza.setName("希腊披萨");
            } else if (orderType.equals("cheese")) {
                pizza = new CheessPizza();
                pizza.setName("奶酪披萨");
            } else {
                break;
            }
            //输出pizza制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        } while (true);
    }

    //获取客户希望订购的披萨种类
    private String getType() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String str = strin.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
```

4、测试

```java	
//相当于一个客户端，发出订购任务
public class PizzaStore {
    public static void main(String[] args) {
        new OrderPizza();
    }
}
```

5、总结

优点：比较好理解，简单容易操作

缺点：违反了ocp(开闭原则)，即对外扩展开放，对修改关闭。当我们给类增加新功能的时候，尽量不修改代码，或则尽可能少修改代码。比如我们还需要增加一个胡椒披萨，则需要修改订单类。



二、改进思路

1、分析：修改代码可以接受，但是如果我们在其他地方也有创建pizza的代码，就意味着我们也要修改那段代码，而创建pizza的代码往往有很多处。

2、思路： 把创建pizza对象封装到一个类中，这样有新的pizza类时，只需要修改该类就可以了，其他有创建到pizza对象的代码就不需要修改了-> 简单工厂模式

3、简单工厂模式：

* 属于创建型模式，是工厂模式的一种，由一个工厂对象决定创建出哪一个产品类的实例。是工厂模式家族中最简单实用的模式。
* 定义了一个创建对象的类，由这个类来封装实例化对象的行为。
* 使用场景：当我们会用到大量的创建某种、某类或者某批对象时，就会使用到工厂模式。

```java
 //定义一个简单工厂对象
    SimpleFactory simpleFactory;
    Pizza pizza = null;

    //构造器
    public OrderPizza(SimpleFactory simpleFactory){
        setSimpleFactory(simpleFactory);
    }

    public void setSimpleFactory(SimpleFactory simpleFactroy) {
        String orderType = "";
        this.simpleFactory = simpleFactroy; //设置简单工厂对象

        do {
            orderType = getType();
            pizza = this.simpleFactory.createPizza(orderType);
            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println(" 订购披萨失败");
                break;
            }
        } while (true);
    }
```



```java
//简单工厂类  返回对应的pizza类
public class SimpleFactory {
    public Pizza createPizza(String orderType) {
        Pizza pizza = null;

        System.out.println("使用简单工厂模式");
        if (orderType.equals("greek")) {
            pizza = new GreekPizza();
            pizza.setName("希腊披萨");
        } else if (orderType.equals("cheese")) {
            pizza = new CheessPizza();
            pizza.setName("奶酪披萨");
        }

        return pizza;
    }
}
```



```java
//相当于一个客户端，发出订购任务
public class PizzaStore {
    public static void main(String[] args) {
        //使用简单工厂模式
        new OrderPizza(new SimpleFactory());
        System.out.println("退出程序");
    }
}
```

静态工厂

```java
//简单工厂模式 也叫静态工厂模式
    public static  Pizza createPizza2(String orderType) {
        Pizza pizza = null;

        System.out.println("使用简单工厂模式");
        if (orderType.equals("greek")) {
            pizza = new GreekPizza();
            pizza.setName("希腊披萨");
        } else if (orderType.equals("cheese")) {
            pizza = new CheessPizza();
            pizza.setName("奶酪披萨");
        }

        return pizza;
    }
```

```java	
public class OrderPizza{
    Pizza pizza=null;
    String orderType="";
    
    //构造器
    public orderPizza(){
        do{
            orderType=getType();
            pizza=SimpleFactory.createPizza2(orderType);
            
            if(pizza!=null){ //订购成功
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }else{
                System.out.printlin(" 订购披萨失败");
            }
        }while(true);
    }
}
```



### 工厂方法模式

---

一、新需求

客户在点披萨的时候，可以点不同口味的披萨，不如北京的奶酪pizza，北京的胡椒pizza，或者伦敦的奶酪pizza，伦敦的呼叫pizza。

思路1：使用简单工厂模式，创建不同的简单工厂类，如BJPizzaSimpleFactory、LDPizzaSimpleFactory，但是软件的可维护性、可扩展性并不是很好。

思路2：使用工厂方法模式



二、工厂方法模式介绍：是在简单工厂模式上对方法的抽象

工厂方法模式设计方案：将披萨项目的实例化功能抽象成抽象方法，在不同的口味点餐子类中具体实现。

定义了一个创建对象的抽象方法，由子类决定要实例化的类。工厂方法模式将**对象的实例化推迟到子类**。

1、创建四种不同地区和口味的披萨

```java
/*BJCheesePizza*/
public class BJCheesePizza extends Pizza {

    @Override
    public void prepare() {

        setName("北京的奶酪pizza");

        System.out.println("给北京奶酪披萨，准备原材料");
    }
}

/*BJPepperPizza*/
public class BJPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("北京的胡椒披萨");
        System.out.println("给北京胡椒披萨 准备原材料");
    }
}

/*LDCheessPizza*/
public class LDCheessPizza extends Pizza{

    @Override
    public void prepare() {

        setName("伦敦的奶酪pizza");

        System.out.println("给伦敦奶酪披萨，准备原材料");
    }
}
/*LDPepperPizza*/
public class LDPepperPizza extends Pizza {

    @Override
    public void prepare() {
        setName("伦敦的胡椒披萨");
        System.out.println("给伦敦的胡椒披萨，准备原材料");
    }
}
```

2、分别创建伦敦、北京工厂

```java
/*BJOrderPizza*/
public class BJOrderPizza extends OrderPizza {


    @Override
    Pizza createPizza(String orderType) {
        Pizza pizza = null;

        if(orderType.equals("cheese")){
            pizza=new BJCheesePizza();
        }else if(orderType.equals("pepper")){
            pizza=new BJPepperPizza();
        }
        return pizza;
    }
}

/*LDOrderPizza*/
public class LDOrderPizza extends OrderPizza {


    @Override
    Pizza createPizza(String orderType) {
        Pizza pizza = null;

        if(orderType.equals("cheese")){
            pizza=new LDCheessPizza();
        }else if(orderType.equals("pepper")){
            pizza=new LDPepperPizza();
        }
        return pizza;
    }
}

```

3、创建抽象工厂

```java
public abstract class OrderPizza {

    //构造器
    public OrderPizza() {
        Pizza pizza = null;
        String orderType; //订购披萨的类型
        do {
            orderType = getType();
            pizza = createPizza(orderType);//抽象方法，由工厂子类完成
            if (pizza != null) {
                //输出pizza制作过程
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }else{
                System.out.println("pizza不存在");
            }
        } while (true);
    }

    //定义一个抽象方法，createPizza，让各个工厂子类自己实现
    abstract Pizza createPizza(String orderType);


    //获取客户希望订购的披萨种类
    private String getType() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String str = strin.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

```

4、测试

```java
   public static void main(String[] args) {
        //创建北京口味的各种pizza
        new BJOrderPizza();
    }
```



### 抽象工厂模式

---

一、介绍

* 抽象工厂模式：定义了一个interface用于创建相关或有依赖关系的对象簇，而无需指明具体的类。
* 抽象工厂模式可以将**简单工厂**和**工厂方法**模式进行整合。
* 从设计层面上看，抽象工厂模式就是对简单工厂模式的改进（进一步抽象）
* 抽象工厂抽象成两层，AbsFactory（抽象工厂）和具体实现的工厂子类。程序员还可以根据创建对象类型使用对应的工厂子类。这样将单个的简单工厂变成了工厂簇。更利用代码的维护和扩展。

1、抽象层

```java
//一个抽象工厂模式的抽象层（接口）
public interface AbsFactory {
    Pizza createPizza(String orderType);
}

```

2、使用抽象的工厂模式

```java
/*BJFactory*/
public class BJFactory implements AbsFactory {

    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("使用抽象的工厂模式");
        Pizza pizza = null;

        if (orderType.equals("cheese")) {
            pizza = new BJCheesePizza();
        } else if (orderType.equals("pepper")) {
            pizza = new BJPepperPizza();
        }
        return pizza;
    }
}

/*LDFactory*/
public class LDFactory implements AbsFactory{

    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("使用抽象的工厂模式");
        Pizza pizza = null;

        if (orderType.equals("cheese")) {
            pizza = new LDCheessPizza();
        } else if (orderType.equals("pepper")) {
            pizza = new LDPepperPizza();
        }
        return pizza;
    }
}

```

3、订单类

```java
package factory.abstactfactory.order;

import factory.abstactfactory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: sam
 * @Description:
 **/
public class OrderPizza {

    AbsFactory factory;

    public OrderPizza(AbsFactory absFactory) {
        setAbsFactory(absFactory);
    }

    public void setAbsFactory(AbsFactory factory) {
        Pizza pizza = null;
        String orderType = "";//用户输入
        this.factory = factory;

        do {
            orderType = getType();
            pizza = factory.createPizza(orderType);//factory 可能是北京的工厂子类，也可能是伦敦的工厂子类

            if (pizza != null) {
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println("订购失败");
                break;
            }
        } while (true);
    }

    private String getType() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String str = strin.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

```

4、测试

```java
public class PizzaStore {
    public static void main(String[] args) {
        new OrderPizza(new BJFactory());
    }
}
```



二、工厂模式JDK源码中的应用(Calendar类)

```java	
public class Factory {
    public static void main(String[] args) {
        //getInstance是Calendar的一个静态方法
        Calendar cal = Calendar.getInstance();

        System.out.println("年：" + cal.get(Calendar.YEAR));
        System.out.println("月：" + (cal.get(Calendar.MARCH) + 1));
        System.out.println("日：" + cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("时：" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("分：" + cal.get(Calendar.MINUTE));
        System.out.println("秒：" + cal.get(Calendar.SECOND));
    }
}
```

源码追踪：

```java
 public static Calendar getInstance()
 {
       	return createCalendar(TimeZone.getDefault(),
                              Locale.getDefault(Locale.Category.FORMAT));
 }

 public static TimeZone getDefault() {
        return (TimeZone) getDefaultRef().clone();
 }

private static Calendar createCalendar(TimeZone zone,
                                           Locale aLocale)
    {
        CalendarProvider provider =
            LocaleProviderAdapter.getAdapter(CalendarProvider.class, aLocale)
                                 .getCalendarProvider();
        if (provider != null) {
            try {
                return provider.getInstance(zone, aLocale);
            } catch (IllegalArgumentException iae) {
                // fall back to the default instantiation
            }
        }

        Calendar cal = null;

        if (aLocale.hasExtensions()) {
            String caltype = aLocale.getUnicodeLocaleType("ca");
            if (caltype != null) {
                switch (caltype) {
                case "buddhist":  //根据不同情况 返回不同Calendar 
                cal = new BuddhistCalendar(zone, aLocale);
                    break;
                case "japanese":
                    cal = new JapaneseImperialCalendar(zone, aLocale);
                    break;
                case "gregory":
                    cal = new GregorianCalendar(zone, aLocale);
                    break;
                }
            }
        }
        if (cal == null) {
            // If no known calendar type is explicitly specified,
            // perform the traditional way to create a Calendar:
            // create a BuddhistCalendar for th_TH locale,
            // a JapaneseImperialCalendar for ja_JP_JP locale, or
            // a GregorianCalendar for any other locales.
            // NOTE: The language, country and variant strings are interned.
            if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
                cal = new BuddhistCalendar(zone, aLocale);
            } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja"
                       && aLocale.getCountry() == "JP") {
                cal = new JapaneseImperialCalendar(zone, aLocale);
            } else {
                cal = new GregorianCalendar(zone, aLocale);
            }
        }
        return cal;
    }

```

三、小结

* 工厂模式的意义：将实例化对象的代码抽取出来，放到一个类中统一管理和维护，达到和主项目的依赖关系的解耦。从而提高项目的扩展和维护性。
* 三种工厂模式（简单工厂模式、工厂方法模式、抽象工厂模式）
* 设计模式的**依赖抽象**原则

创建对象实例时，不要直接new一个类，而是把这个类的动作放在一个工厂的方法中，并返回。（变量不要直接持有具体类的引用）

不要让类继承具体类，而是继承抽象类或者是实现interface（接口）

不要覆盖基类中已经实现的方法



---

### 原型模式

------

一、问题引入

现有一只羊，姓名为tom，年龄为1，颜色为白色。请编写程序创建和tom属性完全一样的10只羊。

1、传统方法

```java
package prototype.traditional;

/**
 * @Author: sam
 * @Description:
 **/
public class Sheep {
    private String name;
    private int age;
    private String color;

    public Sheep(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

```

```java	
package prototype.traditional;

/**
 * @Author: sam
 * @Description:
 **/
public class Sheep {
    private String name;
    private int age;
    private String color;

    public Sheep(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

```

客户端

```java
public class Client {
    public static void main(String[] args) {
        Sheep sheep = new Sheep("tom", 1, "白色");

        Sheep sheep1 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep sheep2 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep sheep3 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep sheep4 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        Sheep sheep5 = new Sheep(sheep.getName(), sheep.getAge(), sheep.getColor());
        //省略...
    }
}
```

传统方法的优点是比较好理解，操作简单

但是在创建新的对象时，总是需要重新获取原始对象的属性，如果创建的对象比较复杂，效率就会比较低。

总是需要重新初始化对象，而不是动态地获取对象运行时状态，不够灵活



改进的思路：Java类中的Object类是所有类的根类，Object类提供了一个clone()方法，该方法可以将一个java对象复制一份，但是需要实现clone的java类必须要实现一个接口Cloneable，该接口表示该类能够复制并且具有复制的能力。->原型模式



二、原型模式介绍

- 原型模式（prototype模式）是指：用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象。
- 原型模式是一种创建型设计模式，允许一个对象再创建另一个可定制的对象，无需知道如何创建的细节。
- 工作原理是：通过将一个原型对象传给那个要发动创建的对象，这个要发动创建的对象通过请求原型对象拷贝它们自己来实施创建，即对象.clone()
- 形象理解：孙悟空拔出猴毛变出其它大圣

![1563107863894](C:/Users/missb/Desktop/Develop/%E7%AE%97%E6%B3%95/_DesignPattern/image/%E5%8E%9F%E5%9E%8B%E6%A8%A1%E5%BC%8F1.png)

- Prototype：原型类，声明一个克隆自己的接口
- ConcretePrototype：具体的原型类，实现一个克隆自己的操作
- Client：让一个原型对象克隆自己，从而创建一个新的对象（属性一样）

```java
public class Sheep implements Cloneable {
    private String name;
    private int age;
    private String color;

    @Override
    protected Object clone() {
        //克隆该实例，使用默认的clone方法来完成
        Sheep sheep = null;
        try {
            sheep = (Sheep) super.clone();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sheep;
    }
}
```

```java
public class Client {
    public static void main(String[] args) {
        Sheep sheep=new Sheep("tom",1,"白色");
        Sheep sheep2= (Sheep) sheep.clone();//克隆
        Sheep sheep3= (Sheep) sheep.clone();
        Sheep sheep4= (Sheep) sheep.clone();
        Sheep sheep5= (Sheep) sheep.clone();
        Sheep sheep6= (Sheep) sheep.clone();
        Sheep sheep7= (Sheep) sheep.clone();
        Sheep sheep8= (Sheep) sheep.clone();
        Sheep sheep9= (Sheep) sheep.clone();
        Sheep sheep10= (Sheep) sheep.clone();

        System.out.println(sheep10);//与sheep一样
    }
}
```



三、原型模式在spring框架中的源码分析

- Spring中原型bean的创建、就是原型模式的应用

```xml
beans.xml
<bean id="id007" class="com.spring.bean" scope="prototype" /> 
<!--使用了原型模式来创建-->
```

Test.java

```java
ApplicationContext applicationContext=new ClassPathXmlApplicationContext("beans.xml");
Object bean=applicationContext.getBean("id007");
Object bean2=applicationContext.getBean("id007");
System.out.println(bean==bean2); //false 因为使用了prototype 只是属性相同
```



**四、深拷贝和浅拷贝**

1、浅拷贝

- 对于数据类型是基本数据类型的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。
- 对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组、某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。因为实际上两个对象的该成员变量都指向同一个实例。在这种情况下，在一个对象中修改该成员变量会影响到另一对象的该成员变量值。
- 前面的克隆羊例子就是浅拷贝
- 浅拷贝是使用默认的clone()方法来实现，  sheep=(Sheep)super.clone();

2、深拷贝

- 复制对象的所有基本数据类型的成员变量值

- 为所有引用数据类型的成员变量申请存储空间，并复制每一个引用数据类型成员变量所引用的对象，知道该对象可达的所有对象。也就是说，对象进行深拷贝要对整个对象进行拷贝。

- 

- 实现方式：

  - 深拷贝实现方式1：重写clone方法来实现深拷贝
  - 深拷贝实现方式2：通过对象序列化实现深拷贝

  实现：DeepCloneableTarget.java

  ```java
  public class DeepCloneableTarget implements Serializable,Cloneable {
  
      private static final long serialVersionUID=1L;
      private String cloneName;
      private String cloneClass;
  
      public DeepCloneableTarget(String cloneName, String cloneClass) {
          this.cloneName = cloneName;
          this.cloneClass = cloneClass;
      }
  
      //因为该类的属性都是string，因此我们用默认的clone方法即可
      @Override
      protected Object clone() throws CloneNotSupportedException {
          return super.clone();
      }
  }
  
  ```

  

  DeepProtoType.java

  ```java
  public class DeepProtoType implements Serializable, Cloneable {
  
      public String name;//string属性
      public DeepCloneableTarget deepCloneableTarget;//引用类型
  
      public DeepProtoType() {
          super();
      }
  
      @Override
      protected Object clone() throws CloneNotSupportedException {
          Object deep = null;
          //这里完成对基本数据类型和string的克隆
          deep = super.clone();
          //对引用类型的属性，进行单独处理
          DeepProtoType deepProtoType = (DeepProtoType) deep;
          deepProtoType.deepCloneableTarget = (DeepCloneableTarget) deepCloneableTarget.clone();
          return deepProtoType;
      }
  }
  
  ```

- 深拷贝2：通过对象的序列化实现（推荐）

  ```java
   public Object deepClone() {
          //创建流对象
          ByteArrayOutputStream bos = null;
          ObjectOutputStream oos = null;
          ByteArrayInputStream bis = null;
          ObjectInputStream ois = null;
  
          try {
              //序列化
              bos = new ByteArrayOutputStream();
              oos = new ObjectOutputStream(bos);
              oos.writeObject(this); //当前这个对象以对象流的方式输出，序列化
  
              //反序列化
              bis = new ByteArrayInputStream(bos.toByteArray());
              ois = new ObjectInputStream(bis);//读到了原先序列化的对象
              DeepProtoType copyObj = (DeepProtoType) ois.readObject();
              return copyObj;
          } catch (Exception e) {
              e.printStackTrace();
              return null;
          }finally {
              //关闭流
              try {
                  bos.close();
                  oos.close();
                  bis.close();
                  ois.close();
              }catch (Exception e2){
                  e2.printStackTrace();
              }
          }
      }
  ```



五、原型模式的注意事项和细节

- 创建新的对象比较复杂时，可以利用原型模式简化对象的创建过程，同时也能够提高效率
- 不用重新初始化对象，而是动态地获得对象运行时的状态
- 如果原始对象发生变化（增加或者减少属性），其它克隆对象也会发生相应的变化，而无需修改代码
- 在实现深克隆的时候可能需要比较复杂的代码
- 缺点：需要为每一类配备一个克隆方法，这对全新的类来说不是很难，但对已有的类进行改造时，违背了ocp原则。



### 建造者模式

------

一、问题引入

盖房子的需求

- 需要建造房子：这一过程为打桩、砌墙、封顶
- 房子有各种各样的，比如普通房、高楼、别墅，各种房子的过程虽然一样，但是要求不要相同的

1、传统方式解决：

```java
package builder;

/**
 * @Author: sam
 * @Description:
 **/
public abstract class AbstractHouse {

    //打地基
    public abstract void buildBasic();

    //砌墙
    public abstract void buildWalls();

    //封顶
    public abstract void roofed();

    public void build() {
        buildBasic();
        buildWalls();
        roofed();
    }
}

```

普通房子类

```java
package builder;

/**
 * @Author: sam
 * @Description:
 **/
public class CommonHouse extends AbstractHouse {

    @Override
    public void buildBasic() {
        System.out.println(" 普通房子打地基");
    }

    @Override
    public void buildWalls() {
        System.out.println(" 普通房子砌墙");
    }

    @Override
    public void roofed() {
        System.out.println(" 普通房子封顶");
    }
}

```

客户端测试

```java
public class Client {

    public static void main(String[] args) {
        CommonHouse commonHouse = new CommonHouse();
        commonHouse.build();
    }
}
```

传统方式的优缺点：

- 优点是好理解，简单易操作
- 设计的程序结构，过于简单，没有设计缓存层对象，程序的扩展和维护不好，也就是说，这种设计方案，把产品（房子）和创建产品的过程（建房子流程）封装到一起，耦合性增强
- 解决方式：将产品和产品建造过程解耦——建造者模式。



二、建造者模式介绍

- 建造者模式：又称为生成器模式，是一种对象构建模式。它可以将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
- 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们，，用户不需要知道内部的具体构建细节（买车不需要知道车的零件怎么制作）。

1、四个角色

- Product(产品角色)：一个具体的产品对象
- Builder（抽象建造者）：创建一个Product对象的各个部件指定的接口/抽象类。
- ConcreteBuilder（具体建造者）：实现接口，构建和装配各个部件。
- Director(指挥者)：构建一个使用Builder接口的对象，它主要用于创建一个复杂的对象。有两个作用：隔离客户与对象的生产过程，负责控制产品对象的生产过程。

product：

```java
package builder.improve;

/**
 * @Author: sam
 * @Description:
 **/

//产品 product
public class House {
    private String base;
    private String wall;
    private String roofed;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    public String getRoofed() {
        return roofed;
    }

    public void setRoofed(String roofed) {
        this.roofed = roofed;
    }
}

```

Builder:

```java
package builder.improve;

/**
 * @Author: sam
 * @Description:
 **/

//抽象的建造者
public abstract class HouseBuilder {
    protected House house = new House();

    //将建造的流程写好，抽象的方法
    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void roofed();

    //建造房子 将产品（房子） 返回
    public House buildHouse(){
        return house;
    }
}

```

ConcreteBuilder:

```java
package builder.improve;

/**
 * @Author: sam
 * @Description:
 **/
public class CommonHouse extends HouseBuilder{
    @Override
    public void buildBasic() {
        System.out.println(" 普通房子打地基5米");
    }

    @Override
    public void buildWalls() {
        System.out.println(" 普通砌墙10cm");
    }

    @Override
    public void roofed() {
        System.out.println(" 普通房子的屋顶");
    }
}


/**
 * @Author: sam
 * @Description:
 **/
public class HighBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println(" 高楼的打地基100米");
    }

    @Override
    public void buildWalls() {
        System.out.println(" 高楼的砌墙20cm");
    }

    @Override
    public void roofed() {
        System.out.println(" 高楼的透明屋顶");
    }
}

```

Director:

```java
package builder.improve;

/**
 * @Author: sam
 * @Description:
 **/

//指挥者，动态去指定制作流程
public class HouseDirector {

    HouseBuilder houseBuilder = null;

    //构造器传入
    public HouseDirector(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    //setter方法
    public void setHouseBuilder(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    //指挥 如何处理建造房子的流程，交给指挥者
    public House constructorHouse() {
        houseBuilder.buildBasic();
        houseBuilder.buildWalls();
        houseBuilder.roofed();
        return houseBuilder.buildHouse();
    }
}
```



三、StringBuilder建造者模式

- Appendable:接口定义了多个append方法（抽象方法），即Appendable为抽象建造者，定义了抽象方法
- AbstractStringBuilder实现了Appendable接口方法，这里的AbstractStringBuilder已经是建造者，只是不能实例化。
- StringBuilder即充当了指挥者角色，同时充当了具体的建造者，建造方法的实现是由AbstractStringBuilder完成，而StringBuilder继承了AbstractStringBuilder



四、建造者模式的注意事项和细节：

- 客户端（使用程序）不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。
- 每一个具体建造者都相对独立，而与其它的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。
- 可以更加精细地控制产品的创建过程，将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程
- 增加新的具体建造者无需修改原有类库的代码，指挥者类针对抽象建造者类编程，系统扩展方便，符合“开闭原则”。
- 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似。如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。
- 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。要考虑是否选择建造者模式。
- 抽象工厂模式VS建造者模式
  - 抽象工厂模式实现对产品家族的创建，一个产品家族是这样的一系列产品：具有不同分类维度的产品组成，采用抽象工厂模式不需要关心构建过程，只关心什么产品由什么工厂生产即可。而建造者模式则要求按照指定的蓝图建造产品，它的主要目的是通过组装零配件而产生一个新产品。