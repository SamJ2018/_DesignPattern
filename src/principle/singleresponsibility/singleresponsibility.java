package principle.singleresponsibility;

/**
 * 解决 耦合性、内聚性、可维护性、可扩展性、重用、灵活性
 * 单一职责：一个类应该只负责一项职责
 * 当职责1需求变更而改变A时，可能造成职责2执行错误  所以需要将类A的粒度分解为A1，A2
 */
public class singleresponsibility {

    public static void main(String[] args) {
        Vehicle vehicle=new Vehicle();

        vehicle.run("摩托车");
        vehicle.run("汽车");
        vehicle.run("飞机");
    }
}

//交通工具类
//方式1

/**
 * 1、在方式1的run方法中，违反了单一职责的原则  因为飞机汽车都在公路上运行了
 * 2、解决的方案： 根据交通工具运行方法不同，分解成不同类
 *
 */
class Vehicle{
    public void run(String vehicle){
        System.out.println(vehicle+"在公路上运行");
    }
}
