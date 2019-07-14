package principle.segregation;

/**
 *  接口隔离原则：
 *      客户端不应该依赖它不需要的接口，即一个类对另一个类的依赖应该建立在最小的接口上
 *
 *   如：类A通过接口interface1依赖类B，类C通过接口interface1依赖类D，如果接口interface1对于类A和类C来说不是最小接口，那么类B和类D必须去实现他们不需要的方法
 *   解决方法：
 *      将接口interface1拆分为独立的几个接口(三个接口)，类A和类C分别与他们需要的接口建立依赖关系。也就是采用接口隔离原则
 */
public class Segregation1 {

    public static void main(String[] args) {

    }
}

/**
 * 问题：
 * 类A通过接口interface1依赖类B，类C通过接口interface1依赖类D
 * 如果接口interface1对于类A和类C来说不是最小接口，那么类B和类D必须去实现他们不需要的方法
 *
 * 解决方案：
 * 将接口interface1拆分为独立的几个接口，类A和类C分别与他们需要的接口建立依赖关系。也就是采用接口隔离原则
 * 接口inteterface1中出现的方法，根据实际情况拆分为三个接口
 * 代码实现
 */
interface Interface1 {
    void operation1();

    void operation2();

    void operation3();

    void operation4();

    void operation5();
}

class B implements Interface1 {

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

    @Override
    public void operation4() {
        System.out.println("B 实现了operation4");
    }

    @Override
    public void operation5() {
        System.out.println("B 实现了operation5");
    }
}

class D implements Interface1 {

    @Override
    public void operation1() {
        System.out.println("D 实现了operation1 ");
    }

    @Override
    public void operation2() {
        System.out.println("D 实现了operation2");
    }

    @Override
    public void operation3() {
        System.out.println("D 实现了operation3");
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
class A { //A 类通过接口interface1 依赖（使用） D类，但是只会用到1，2，3方法
    public void dependd1(Interface1 i) {
        i.operation1();
    }

    public void dependd2(Interface1 i) {
        i.operation2();
    }

    public void dependd3(Interface1 i) {
        i.operation3();
    }
}

class C { //A 类通过接口interface1 依赖（使用） D类，但是只会用到1，4，5方法
    public void dependd1(Interface1 i) {
        i.operation1();
    }

    public void dependd4(Interface1 i) {
        i.operation4();
    }

    public void dependd5(Interface1 i) {
        i.operation5();
    }
}