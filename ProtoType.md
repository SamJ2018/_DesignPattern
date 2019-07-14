### 原型模式

---

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

* 原型模式（prototype模式）是指：用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象。
* 原型模式是一种创建型设计模式，允许一个对象再创建另一个可定制的对象，无需知道如何创建的细节。
* 工作原理是：通过将一个原型对象传给那个要发动创建的对象，这个要发动创建的对象通过请求原型对象拷贝它们自己来实施创建，即对象.clone()
* 形象理解：孙悟空拔出猴毛变出其它大圣

![1563107863894](image\原型模式1.png)

* Prototype：原型类，声明一个克隆自己的接口
* ConcretePrototype：具体的原型类，实现一个克隆自己的操作
* Client：让一个原型对象克隆自己，从而创建一个新的对象（属性一样）

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

* Spring中原型bean的创建、就是原型模式的应用

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

* 对于数据类型是基本数据类型的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。
* 对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组、某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。因为实际上两个对象的该成员变量都指向同一个实例。在这种情况下，在一个对象中修改该成员变量会影响到另一对象的该成员变量值。
* 前面的克隆羊例子就是浅拷贝
* 浅拷贝是使用默认的clone()方法来实现，  sheep=(Sheep)super.clone();

2、深拷贝

* 复制对象的所有基本数据类型的成员变量值

* 为所有引用数据类型的成员变量申请存储空间，并复制每一个引用数据类型成员变量所引用的对象，知道该对象可达的所有对象。也就是说，对象进行深拷贝要对整个对象进行拷贝。

* 

* 实现方式：

  * 深拷贝实现方式1：重写clone方法来实现深拷贝
  * 深拷贝实现方式2：通过对象序列化实现深拷贝

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

* 深拷贝2：通过对象的序列化实现（推荐）

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

* 创建新的对象比较复杂时，可以利用原型模式简化对象的创建过程，同时也能够提高效率
* 不用重新初始化对象，而是动态地获得对象运行时的状态
* 如果原始对象发生变化（增加或者减少属性），其它克隆对象也会发生相应的变化，而无需修改代码
* 在实现深克隆的时候可能需要比较复杂的代码
* 缺点：需要为每一类配备一个克隆方法，这对全新的类来说不是很难，但对已有的类进行改造时，违背了ocp原则。