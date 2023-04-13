package Others.ProxyDemo;

public class JDKProxyTest {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JDKProxyFactory.getJDKProxy(new SmsServiceImpl());
        smsService.send("message");
        smsService.print();
    }
}
