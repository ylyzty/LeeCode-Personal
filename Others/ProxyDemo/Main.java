package Others.ProxyDemo;

public class Main {
    public static void main(String[] args) {
        SmsService smsService = (SmsService) JDKProxyFactory.getJDKProxy(new SmsServiceImpl());
        smsService.send("message");
    }
}
