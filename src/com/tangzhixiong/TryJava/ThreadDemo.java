package com.tangzhixiong.TryJava;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by tzx on 2016/10/22.
 */
public class ThreadDemo {
    public static void main(String[] args) throws Exception {
        // new FirstThread().start();
        // new SecondThread().run();
        // new Thread(new SecondThread(), "main thread").start();
        // ThirdThread.main(args);
        // new JoinThread("main thread").start();
        // DaemonThread.main(args);
        // testSleep();
        // YieldTest.main(args);
        // TODO: 线程同步
    }

    public static void testSleep() {
        try {
            for (int i = 0; i < 10 ; ++i) {
                System.out.println("当前时间: " + new Date());
                Thread.sleep(1000);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 第一个线程：通过 extends Thread，并覆盖 run() 函数
class FirstThread extends Thread {
    private int i ;
    // 重写 run 方法，run 方法的方法体就是线程执行体
    @Override
    public void run() {
        for (; i < 100 ; ++i) {
            // 当线程类继承 Thread 类时，直接使用 this 即可获取当前线程
            // Thread 对象的 getName() 返回当前该线程的名字
            System.out.println(getName() +  " " + i);
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100;  ++i) {
            // 调用 Thread 的 currentThread 方法获取当前线程
            System.out.println(Thread.currentThread().getName() +  " " + i);
            if (i == 20) {
                // 创建、并启动第一条线程
                new FirstThread().start();
                // 创建、并启动第二条线程
                new FirstThread().start();
            }
        }
    }
}

// 第二个线程：通过实现 Runnable 接口来创建线程类
class SecondThread implements Runnable {
    private int i ;
    // run 方法同样是线程执行体
    public void run() {
        for (; i < 100 ; ++i) {
            // 当线程类实现 Runnable 接口时，
            // 如果想获取当前线程，只能用 Thread.currentThread() 方法。
            System.out.println(Thread.currentThread().getName() + "  " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100;  i++) {
            System.out.println(Thread.currentThread().getName()
                    + "  " + i);
            if (i == 20) {
                SecondThread st = new SecondThread();
                // 通过new Thread(target , name) 方法创建新线程
                new Thread(st , "新线程1").start();
                new Thread(st , "新线程2").start();
            }
        }
    }
}

// 第三个线程：callable 接口
class ThirdThread {
    public static void main(String[] args) {
        //  创建Callable 对象
        ThirdThread rt = new ThirdThread();
        // 先使用 Lambda 表达式创建 Callable<Integer> 对象
        // 使用 FutureTask 来包装 Callable 对象
        FutureTask<Integer> task = new FutureTask<Integer>((Callable<Integer>)() -> {
            int i = 0;
            for (; i < 100 ; ++i) {
                System.out.println(Thread.currentThread().getName() + " 的循环变量 i 的值：" + i);
            }
            // call() 方法可以有返回值
            return i;
        });
        for (int i = 0 ; i < 100 ; ++i) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量 i 的值：" + i);
            if (i == 20) {
                // 实质还是以 Callable 对象来创建、并启动线程
                new Thread(task , "有返回值的线程").start();
            }
        }
        try {
            // 获取线程返回值
            System.out.println("子线程的返回值：" + task.get());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class JoinThread extends Thread {
    public JoinThread(String name) {        // 提供一个有参数的构造器，用于设置该线程的名字
        super(name);
    }

    public void run() {
        for (int i = 0; i < 100 ; ++i) {
            System.out.println(getName() + "  " + i);
        }
    }
    public static void main(String[] args) throws Exception {
        // 启动子线程
        new JoinThread("新线程").start();
        for (int i = 0; i < 100 ; ++i) {
            if (i == 20) {
                JoinThread jt = new JoinThread("被 Join 的线程");
                jt.start();
                // main线程调用了
                // jt 线程的 join() 方法，main 线程
                // 必须等 jt 执行结束才会向下执行
                jt.join();
            }
            System.out.println(Thread.currentThread().getName() + "  " + i);
        }
    }
}

class DaemonThread extends Thread {
    // 定义后台线程的线程执行体与普通线程没有任何区别
    public void run() {
        for (int i = 0; i < 1000 ; ++i) {
            System.out.println(getName() + "  " + i);
        }
    }
    public static void main(String[] args) {
        DaemonThread t = new DaemonThread();
        // 将此线程设置成后台线程
        t.setDaemon(true);
        // 启动后台线程
        t.start();
        for (int i = 0 ; i < 50 ; ++i) {
            System.out.println(Thread.currentThread().getName() + "  " + i);
        }
        // ----- 程序执行到此处，前台线程（main 线程）结束 ------
        // 后台线程也应该随之结束
    }
}

class YieldTest extends Thread {
    public YieldTest(String name) {
        super(name);
    }

    public void run() {
        for (int i = 0; i < 50 ; ++i) {
            System.out.println(getName() + "  " + i);
            // 当 i 等于 20 时，使用 yield 方法让当前线程让步
            if (i == 20) {
                Thread.yield();
            }
        }
    }
    // 启动两条并发线程
    public static void main(String[] args) throws Exception {
        YieldTest yt1 = new YieldTest("高级");
        YieldTest yt2 = new YieldTest("低级");
        yt1.setPriority(Thread.MAX_PRIORITY);           // 将 ty1 线程设置成最高优先级
        yt2.setPriority(Thread.MIN_PRIORITY);           // 将 yt2 线程设置成最低优先级
        // Thread.MAX_PRIORITY: 10
        // Thread.MIN_PRIORITY: 1
        // Thread.NORM_PRIORITY: 5
        // Thread.currentThread().getPriority();
        yt2.start();
        yt1.start();                                      // 即使放在后面，也先执行
    }
}
