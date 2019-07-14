package principle.demeter;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;

/**
 * 迪米特法则：
 * 1、一个对象应该对其它对象保持最少的了解
 * 2、类与类关系越密切，耦合度越大
 * 3、迪米特又叫最少知识原则  即一个类对自己依赖的类知道的越少越好，也就是说，对于被依赖的类不管多么复杂，都尽量将逻辑封装在类的内部
 * 对外除了提供的public方法，不对外泄漏任何信息
 * 4、更简单的定义：只与直接的朋友通信
 * 5、直接的朋友：每个对象都会与其它对象有耦合关系，只要两个对象之间有耦合关系。我们就说着两个对象之间是朋友关系。 耦合的方式有很多：依赖、关联、组合、聚合
 * 我们称出现成员变量、方法参数、方法返回值中的类为直接的朋友，而出现在局部变量中的类不是直接的朋友。陌生的类最好不要以局部变量的形式出现在类的内部
 *
 *
 * 使用的具体事项：
 * 迪米特法则的核心是降低类之间的耦合
 * 由于每个类都减少了不必要的依赖，因此迪米特法则只是要求降低类（对象间）耦合关系，并不是要求完全没有依赖关系
 */
public class Demeter {
    //客户端
    public static void main(String[] args) {
        System.out.println("使用迪米特法则的改进");
        SchoolManager schoolManager=new SchoolManager();
        //输出学院的员工id 和学校总部的员工信息
        schoolManager.printAllEmployee(new CollegeManager());
    }
}

//学校总部员工类
class Employee {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

//学院总部员工类
class CollegeEmployee {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

//管理学院员工的管理类
class CollegeManager {
    //返回学院的所有员工
    public List<CollegeEmployee> getAllEmployee() {
        ArrayList<CollegeEmployee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {//增加了10个员工到list中
            CollegeEmployee emp = new CollegeEmployee();
            emp.setId("学院员工id=" + i);
            list.add(emp);
        }
        return list;
    }

    //输出学院员工的信息
    public void printEmployee(){
        List<CollegeEmployee> list1 = this.getAllEmployee();
        System.out.println("------------学院员工----------------");

        for (CollegeEmployee employee : list1) {
            System.out.println(employee.getId());
        }
    }
}

//学校管理类
//分析SchoolManager类的直接朋友类  Employee、CollegeManager、
//不是直接朋友CollegeEmployee这样就违背了迪米特原则 不是直接朋友 而是陌生类
//CollegeEmployee是以局部变量的形式出现在SchoolManager中
//3、违反了 迪米特法则
class SchoolManager{
    //返回学校总部的员工
    public List<Employee> getAllEmployee() {
        ArrayList<Employee> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {//增加了5个员工到list中
            Employee emp = new Employee();
            emp.setId("学校总部员工id=" + i);
            list.add(emp);
        }
        return list;
    }

    //输出学校总部和学院员工信息（id）
    void printAllEmployee(CollegeManager sub){
        sub.printEmployee();
       /*
        不要将自己实现的方法写在别人的类中
        List<CollegeEmployee> list1 = sub.getAllEmployee();
        System.out.println("------------学院员工----------------");

        for (CollegeEmployee employee : list1) {
            System.out.println(employee.getId());
        }
        */
        //获取到学校员工
        List<Employee> list2 = this.getAllEmployee();
        System.out.println("--------------学校总部员工----------");
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}