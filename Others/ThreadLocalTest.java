package Others;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalTest {
    // 提供线程局部变量, 每个线程拥有自己的副本, 各个线程之间互不干扰
    // 每个线程有一个实例变量 ThreadLoacl.ThreadLocalMap threadLocals
    // ThreadLocalMap Entry 的 key 是弱引用
    private static ThreadLocal<Integer> localInt = ThreadLocal.withInitial(() -> {return 10;});
    private static int num = 1;
    
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(){
                public void run() {
                    ThreadLocalTest.localInt.set(num++);
                    System.out.println(ThreadLocalTest.localInt.get());
                }
            }.start();
        }

        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    }
}
