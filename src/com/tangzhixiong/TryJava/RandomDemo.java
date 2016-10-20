package com.tangzhixiong.TryJava;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tzx on 2016/10/20.
 */
public class RandomDemo {
    public static void main(String[] args) {
        Random rand = new Random(50); // 同样的 seed 值，同样的调用方法，出来的随机数都是一样的
        // Random rand = new Random(); // 这样倒是每次都不一样的 seed 值
        // ThreadLocalRandom rand = ThreadLocalRandom.current(); // 本线程的 random，更少的线程资源竞争，更好的线程安全。

        System.out.println("rand.nextBoolean()："+rand.nextBoolean());

        byte[] buffer = new byte[16];
        rand.nextBytes(buffer);
        System.out.println(Arrays.toString(buffer));

        // 生成 0.0~1.0 之间的伪随机 double 数
        System.out.println("rand.nextDouble()："+rand.nextDouble());
        // 生成 0.0~1.0 之间的伪随机 float 数
        System.out.println("rand.nextFloat()："+rand.nextFloat());
        // 生成平均值是 0.0，标准差是 1.0 的伪高斯数
        System.out.println("rand.nextGaussian()："+rand.nextGaussian());
        // 生成一个处于 int 整数取值范围的伪随机整数
        System.out.println("rand.nextInt()："+rand.nextInt());
        // 生成 0~26 之间的伪随机整数
        System.out.println("rand.nextInt(26)："+rand.nextInt(26));
        // 生成一个处于 long 整数取值范围的伪随机整数
        System.out.println("rand.nextLong()："+rand.nextLong());
    }
}
