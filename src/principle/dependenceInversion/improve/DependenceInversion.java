package principle.dependenceInversion.improve;

public class DependenceInversion {
    public static void main(String[] args) {
        //客户端无序改变
        Person person = new Person();
        person.receive(new Email());

        person.receive(new WeiXin());
    }
}

//方式2
interface IReceiver {
    String getInfo();
}

class Email implements IReceiver{
    public String getInfo() {
        return "电子邮件信息：hello，world";
    }
}

class WeiXin implements IReceiver{
    public String getInfo() { return "微信：hello,world"; }
}

class Person {
    //这里我们是对接口的依赖
    public void receive(IReceiver receiver) {
        System.out.println(receiver.getInfo());
    }
}
