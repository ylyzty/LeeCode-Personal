package Others;

public class ExceptionTest {
    
    /**
     * throws: 抛出异常，程序退出
     * try-catch: 捕获异常, 后续代码正常执行 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        // if (true) {
        //     throw new Exception("Exception Test!", null);
        // }

        try {
            throw new Exception("Exception Test!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Excute here!");
    }
}
