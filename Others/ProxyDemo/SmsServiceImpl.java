package Others.ProxyDemo;

public class SmsServiceImpl implements SmsService{

    @Override
    public String send(String message) {
        // TODO Auto-generated method stub
        System.out.println("send message:" + message);
        return message;
    }
    
}
