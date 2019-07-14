### 建造者模式

---

一、问题引入

盖房子的需求

* 需要建造房子：这一过程为打桩、砌墙、封顶
* 房子有各种各样的，比如普通房、高楼、别墅，各种房子的过程虽然一样，但是要求不要相同的

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

* 优点是好理解，简单易操作
* 设计的程序结构，过于简单，没有设计缓存层对象，程序的扩展和维护不好，也就是说，这种设计方案，把产品（房子）和创建产品的过程（建房子流程）封装到一起，耦合性增强
* 解决方式：将产品和产品建造过程解耦——建造者模式。



二、建造者模式介绍

* 建造者模式：又称为生成器模式，是一种对象构建模式。它可以将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
* 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们，，用户不需要知道内部的具体构建细节（买车不需要知道车的零件怎么制作）。

1、四个角色

* Product(产品角色)：一个具体的产品对象
* Builder（抽象建造者）：创建一个Product对象的各个部件指定的接口/抽象类。
* ConcreteBuilder（具体建造者）：实现接口，构建和装配各个部件。
* Director(指挥者)：构建一个使用Builder接口的对象，它主要用于创建一个复杂的对象。有两个作用：隔离客户与对象的生产过程，负责控制产品对象的生产过程。

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

* Appendable:接口定义了多个append方法（抽象方法），即Appendable为抽象建造者，定义了抽象方法
* AbstractStringBuilder实现了Appendable接口方法，这里的AbstractStringBuilder已经是建造者，只是不能实例化。
* StringBuilder即充当了指挥者角色，同时充当了具体的建造者，建造方法的实现是由AbstractStringBuilder完成，而StringBuilder继承了AbstractStringBuilder



四、建造者模式的注意事项和细节：

* 客户端（使用程序）不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。
* 每一个具体建造者都相对独立，而与其它的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。
* 可以更加精细地控制产品的创建过程，将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程
* 增加新的具体建造者无需修改原有类库的代码，指挥者类针对抽象建造者类编程，系统扩展方便，符合“开闭原则”。
* 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似。如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。
* 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。要考虑是否选择建造者模式。
* 抽象工厂模式VS建造者模式
  * 抽象工厂模式实现对产品家族的创建，一个产品家族是这样的一系列产品：具有不同分类维度的产品组成，采用抽象工厂模式不需要关心构建过程，只关心什么产品由什么工厂生产即可。而建造者模式则要求按照指定的蓝图建造产品，它的主要目的是通过组装零配件而产生一个新产品。