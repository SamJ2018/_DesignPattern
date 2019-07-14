package principle.openclosed.improve;

/**
 * 开闭原则 是编程中最基础、最重要的设计原则
 * 一个软件实体如类、模块和函数 应该对拓展开放（提供方），对修改关闭（使用方）。用抽象构建框架，用实现拓展细节
 * 当软件需要变化时，尽量通过扩展软件实体的行为类实现变化，而不是通过修改已有代码来实现变化
 * 遵循其它原则、以及使用设计模式的目的就是遵循开闭原则
 */
public class OpenClosed {

    public static void main(String[] args) {
        GraphicEditor graph = new GraphicEditor();
        graph.drawShape(new Rectangle());
        graph.drawShape(new Circle());
        graph.drawShape(new Triangle());
        graph.drawShape(new OtherGraphic());
    }
}

//用于绘图的类(使用方）
//改进思路：把创建Shape类做成抽象类，并提供一个抽象的draw方法，让子类去实现即可
//这样当有新的图形种类，只需让新的图形类继承Shape，并实现draw方法就可以了，使用方的代码不需要修改----开闭原则
class GraphicEditor {
    //接受Shape对象，根据type 来绘制不同的图形  屏蔽修改
    public void drawShape(Shape s) {
        s.draw();
    }
}

abstract class Shape {
    int m_type;

    public abstract void draw();//抽象方法
}

class Rectangle extends Shape {

    Rectangle() {
        super.m_type = 1;
    }

    @Override
    public void draw() {
        System.out.println("绘制正方形");
    }
}

class Circle extends Shape {

    Circle() {
        super.m_type = 2;
    }

    @Override
    public void draw() {
        System.out.println("绘制圆形");
    }
}

class Triangle extends Shape {

    Triangle() {
        super.m_type = 3;
    }

    @Override
    public void draw() {
        System.out.println("绘制三角形");
    }
}

//新增一个图形   拓展开放
class OtherGraphic extends Shape{

    OtherGraphic(){
        super.m_type=4;
    }

    @Override
    public void draw() {
        System.out.println("其它图形");
    }
}