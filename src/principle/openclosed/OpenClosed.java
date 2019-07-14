package principle.openclosed;

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
    }
}

//用于绘图的类(使用方）
//改进思路：把创建Shape类做成抽象类，并提供一个抽象的draw方法，让子类去实现即可
//这样当有新的图形种类，只需让新的图形类继承Shape，并实现draw方法就可以了，使用方的代码不需要修改----开闭原则
class GraphicEditor {
    //接受Shape对象，根据type 来绘制不同的图形
    public void drawShape(Shape s) {
        if (s.m_type == 1)
            drawRectangle(s);
        else if (s.m_type == 2)
            drawCircle(s);
        else
            drawTriangle(s);
    }

    public void drawRectangle(Shape r) {
        System.out.println("绘制矩形");
    }

    public void drawCircle(Shape r) {
        System.out.println("绘制圆形");
    }

    public void drawTriangle(Shape r) {
        System.out.println("绘制三角形");
    }
}

class Shape {
    int m_type;
}

class Rectangle extends Shape {

    Rectangle() {
        super.m_type = 1;
    }
}

class Circle extends Shape {

    Circle() {
        super.m_type = 2;
    }
}

//新增一个类型，则需要修改使用方
class Triangle extends Shape {

    Triangle() {
        super.m_type = 3;
    }
}