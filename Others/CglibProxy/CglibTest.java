package Others.CglibProxy;

public class CglibTest {
    public static void main(String[] args) {
        SmsServiceClass smsServiceClass = (SmsServiceClass) CglibProxyFactory.getCglibProxy(new SmsServiceClass());
        smsServiceClass.send("message");
        smsServiceClass.print();
    }
}
