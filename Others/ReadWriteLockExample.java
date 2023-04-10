package Others;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private Resource resource;
    private ReadWriteLock readWriteLock;
  
    public ReadWriteLockExample(Resource resource) {
      this.resource = resource;
      this.readWriteLock = new ReentrantReadWriteLock();
    }
  
    public void read() {
      readWriteLock.readLock().lock();
      try {
        System.out.println(Thread.currentThread().getName() + " start to read data.");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " end to read data: " + resource.getNum());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        readWriteLock.readLock().unlock();
      }
    }
  
    public void write(Resource resource) {
      readWriteLock.writeLock().lock();
      try {
        System.out.println(Thread.currentThread().getName() + " start to write data.");
        Thread.sleep((long) (Math.random() * 1000));
        this.resource = resource;
        System.out.println(Thread.currentThread().getName() + " end to write data: " + this.resource.getNum());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        readWriteLock.writeLock().unlock();
      }
    }
  
    public static void main(String[] args) {
      ReadWriteLockExample example = new ReadWriteLockExample(new Resource(0));
  
      // 分别启动5个读写数据线程
      for (int i = 0; i < 5; i++) {
        new Thread() {
          @Override
          public void run() {
            while (true) {
              example.read();
            }
          }
        }.start();
  
        new Thread() {
          @Override
          public void run() {
            while (true) {
              Resource resource = new Resource((int) (Math.random() * 1000));
              example.write(resource);
            }
          }
        }.start();
      }
    }
  }


  class Resource {
    private int num;
    
    public Resource() {}
    
    public Resource(int num) {
      this.num = num;
    }
    
    public int getNum() {
      return num;
    }
    
    public void setNum(int num) {
      this.num = num;
    }
  
    public void doSomething() {
      System.out.println("Do some operation.");
    }
  
    public void doLogging() {
      System.out.println("Do logging.");
    }
  }
