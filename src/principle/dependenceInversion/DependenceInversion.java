package principle.dependenceInversion;

/**
 * 依赖倒置原则：
 * 1、高层模块不应该依赖底层模块，两者都应该依赖其抽象
 * 2、抽象不应该改依赖细节，细节应该依赖抽象
 * 3、依赖倒置的中心思想是面向接口编程
 * 4、依赖倒置原则的设计理念：相对于细节的多变性，抽象的东西要稳定的多。以抽象为基础搭建的架构比以细节为基础的架构要稳定的多。
 * java中抽象的指的是接口或抽象类，细节就是具体的实现类
 * 5、使用接口或抽象类的目的就是制定好规范，而不设计任何具体的操作，把展现细节的任务交给他们的实现类去完成
 *
 * 使用依赖倒转原则的注意事项：
 * 1、底层模块尽量都要有抽象层或接口，或者两者都有，程序稳定性更好
 * 2、变量的声明类型尽量是抽象类或接口，这样我们的变量引用和实际对象间，就存在一个缓冲层，利于程序扩展和优化
 * 3、继承时遵循里氏替换原则
 */
public class DependenceInversion {
    public static void main(String[] args) {
        Person person = new Person();
        person.receive(new Email());
    }
}

class Email {
    public String getInfo() {
        return "电子邮件信息：hello，world";
    }
}

//方案1：传统方案实现  Person接受消息的功能
//1、简单，比较容易想到
//2、如果获取的对象是 微信、短信，则新增类，同时Persons也要增加相应的接收方法
//3、解决思路：引入一个抽象的IReceiver，表示接受者，这样Person类与接口IReceiver发生依赖
// 因为Email，Weixin都属于接受者的范围，他们各自实现IReceiver接口，这样就实现了依赖倒置的原则
class Person {
    public void receive(Email email) {
        System.out.println(email.getInfo());
    }
}

/**
 * *依赖关系传递的三种方式
 * 接口传递：
 * 构造方法传递
 * setter方式传递
 */
//2、使用构造器
interface IOpenAndClose {
    void open(); //抽象
}

interface ITV { //ITV接口
    void play();
}

class OpenAndClose implements IOpenAndClose {

    public ITV tv;//成员

    public OpenAndClose(ITV tv) {//通过构造器
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}

//使用setter方法
interface IOpenAndClose2 {
    void open(); //抽象
}

interface ITV2 { //ITV接口
    void play();
}

class OpenAndClose2 implements IOpenAndClose2 {

    public ITV2 tv;//成员

    public void setTv(ITV2 TV) {
        this.tv = tv;
    }

    @Override
    public void open() {
        this.tv.play();
    }
}