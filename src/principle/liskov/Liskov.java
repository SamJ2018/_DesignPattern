package principle.liskov;

/**
 * OO中的继承性的思考和说明
 * 1、继承包含这样一层含义：父类中凡是已经实现好的方法，实际上是在设定规范和契约，虽然它不强制要求所有的子类必须遵循这些契约，但是如果子类对这些已经实现的方法任意修改
 * 就会对整个继承体系造成破坏
 * 2、继承在给程序设计带来便利的同时，也带来了弊端。比如使用继承会给程序带来侵入性，程序的可移植性降低，增加对象间的耦合性，如果一个类被其它的类所继承，则当这个类
 * 需要修改时，必须考虑到所有的子类，并且父类修改后，所有涉及到子类的功能都有可能出现故障
 * <p>
 * 如果对每个类型为T1的对象o1，都有类型为T2的对象o2，使得以T1定义的所有程序p在所有的对象o1都代换成o2时，程序p的行为没有发生变化，那么类型T2是类型T1的子类型
 * 换句话说，所有引用基类的地方必须能透明低使用其子类的对象
 * <p>
 * 可以通过聚合、组合、依赖来解决问题
 */
public class Liskov {
    public static void main(String[] args) {
        A a = new A();
        System.out.println("11-3="+a.func1(11,3));
        System.out.println("1-8="+a.func1(1,8));

        System.out.println("-------------");

        B b = new B();
        System.out.println("11-3="+b.func1(11,3));//本意是求出11-3
        System.out.println("1-8="+b.func1(1,8));
        System.out.println("11+3+9="+b.func2(11,3));
    }
}

//A类
class A {
    //返回两个数的差
    public int func1(int num1, int num2) {
        return num1 - num2;
    }
}

//增加了一个新功能：完成两个数相加，然后和9相加
class B extends A {
    public int func1(int a, int b) {
        return a + b;
    }

    public int func2(int a, int b) {
        return func1(a, b) + 9;
    }
}

