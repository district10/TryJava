package com.tangzhixiong.TryJava;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tzx on 2016/10/20.
 */
public class SystemDemo {
    public static void main(String[] args) {
        // testGetEnv();
        // testGetEnv();
        testRuntime();
    }

    public static void testGetEnv() {
        Map<String,String> env = System.getenv();
        for (String name : env.keySet()) {
            System.out.println(name + " ---> " + env.get(name));
        }
        System.out.println("\n%PATH%: "+System.getenv("PATH"));
    }

    public static void testGetProperties() {
        Properties props = System.getProperties();
        System.out.println("\nSystem.getProperties(): "+props.toString());
        System.out.println("\nos.name: "+System.getProperty("os.name"));
        /*
        try {
            FileOutputStream fos = new FileOutputStream("props.txt"); // 输出到了根目录
            props.store(fos, "System Properties");
            fos.close();
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
        */
    }

    public static void testRuntime() {
        // 获取 Java 程序关联的运行时对象
        Runtime rt = Runtime.getRuntime();
        System.out.println("处理器数量："+rt.availableProcessors());
        System.out.println("空闲内存数："+rt.freeMemory());
        System.out.println("总内存数："+rt.totalMemory());
        System.out.println("可用最大内存数："+rt.maxMemory());
        try {
            rt.exec("notepad"); // 打开记事本
        }
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
