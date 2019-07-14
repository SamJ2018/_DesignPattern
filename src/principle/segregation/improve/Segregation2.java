package principle.segregation.improve;

public class Segregation2 {

    public static void main(String[] args) {
        A a = new A();
        a.dependd1(new B());//A类通过接口去依赖B类
        a.dependd2(new B());
        a.dependd3(new B());

        C c = new C();
        c.dependd1(new D());//C类通过接口去依赖（使用）D类
        c.dependd4(new D());
        c.dependd5(new D());
    }
}

/**
 * 问题：
 * 类A通过接口interface1依赖类B，类C通过接口interface1依赖类D
 * 如果接口interface1对于类A和类C来说不是最小接口，那么类B和类D必须去实现他们不需要的方法
 * <p>
 * 解决方案：
 * 将接口interface1拆分为独立的几个接口，类A和类C分别与他们需要的接口建立依赖关系。也就是采用接口隔离原则
 * 接口inteterface1中出现的方法，根据实际情况拆分为三个接口
 * 代码实现
 */
interface Interface1 {
    void operation1();
}

interface interface2 {

    void operation2();

    void operation3();
}

interface interface3 {

    void operation4();

    void operation5();
}


class B implements Interface1,interface2 {

    @Override
    public void operation1() {
        System.out.println("B 实现了operation1 ");
    }

    @Override
    public void operation2() {
        System.out.println("B 实现了operation2");
    }

    @Override
    public void operation3() {
        System.out.println("B 实现了operation3");
    }
}

class D implements Interface1 ,interface3{

    @Override
    public void operation1() {
        System.out.println("D 实现了operation1 ");
    }

    @Override
    public void operation4() {
        System.out.println("D 实现了operation4");
    }

    @Override
    public void operation5() {
        System.out.println("D 实现了operation5");
    }
}

class A { //A 类通过接口interface1,interface2 依赖（使用） D类，但是只会用到1，2，3方法
    public void dependd1(Interface1 i) {
        i.operation1();
    }

    public void dependd2(interface2 i) {
        i.operation2();
    }

    public void dependd3(interface2 i) {
        i.operation3();
    }
}

class C { //A 类通过接口interface1,interface3 依赖（使用） D类，但是只会用到1，4，5方法
    public void dependd1(Interface1 i) {
        i.operation1();
    }

    public void dependd4(interface3 i) {
        i.operation4();
    }

    public void dependd5(interface3 i) {
        i.operation5();
    }
}