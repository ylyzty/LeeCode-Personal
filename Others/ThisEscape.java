package Others;

/**
 * this 逃逸问题
 */
public class ThisEscape {
    public String name;

    public ThisEscape(String name) {
        new Thread(new EscapeRunnable()).start();
        try {
            Thread.sleep(2);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.name = name;
    }

    private class EscapeRunnable implements Runnable {
        @Override
        public void run() {
            // 打印 null 
            // Escape 还未初始化完成
            System.out.println(ThisEscape.this.name);
        }
    }

    public static void main(String[] args) {
        ThisEscape thisEscape = new ThisEscape("test");
    }
}
