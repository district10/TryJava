package com.tangzhixiong.TryJava;

import java.math.BigDecimal;

/**
 * Created by tzx on 2016/10/20.
 */
public class BigDecimalDemo {
    public static void main(String[] args) {
        BigDecimal f1 = new BigDecimal("0.05");
        BigDecimal f2 = BigDecimal.valueOf(0.01);
        BigDecimal f3 = new BigDecimal(0.05);
        System.out.println("使用 String 作为 BigDecimal 构造器参数：");
        System.out.println("0.05 + 0.01 = " + f1.add(f2));
        System.out.println("0.05 - 0.01 = " + f1.subtract(f2));
        System.out.println("0.05 * 0.01 = " + f1.multiply(f2));
        System.out.println("0.05 / 0.01 = " + f1.divide(f2));
        System.out.println("使用 double 作为 BigDecimal 构造器参数：");
        System.out.println("0.05 + 0.01 = " + f3.add(f2));
        System.out.println("0.05 - 0.01 = " + f3.subtract(f2));
        System.out.println("0.05 * 0.01 = " + f3.multiply(f2));
        System.out.println("0.05 / 0.01 = " + f3.divide(f2));
        // 看来 double 是不精确的。String 是。
        /*
        使用 String 作为 BigDecimal 构造器参数：
        0.05 + 0.01 = 0.06
        0.05 - 0.01 = 0.04
        0.05 * 0.01 = 0.0005
        0.05 / 0.01 = 5
        使用 double 作为 BigDecimal 构造器参数：
        0.05 + 0.01 = 0.06000000000000000277555756156289135105907917022705078125
        0.05 - 0.01 = 0.04000000000000000277555756156289135105907917022705078125
        0.05 * 0.01 = 0.0005000000000000000277555756156289135105907917022705078125
        0.05 / 0.01 = 5.000000000000000277555756156289135105907917022705078125
         */
        System.out.println("试试自己封装的函数");
        Arith.main(args);
    }
}

class Arith
{
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    // 构造器私有，让这个类不能实例化
    private Arith() {}
    // 提供精确的加法运算。
    public static double add(double v1,double v2) {
        return BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2)).doubleValue();
    }
    // 提供精确的减法运算。
    public static double sub(double v1,double v2) {
        return BigDecimal.valueOf(v1).subtract(BigDecimal.valueOf(v2)).doubleValue();
    }
    // 提供精确的乘法运算。
    public static double mul(double v1,double v2)
    {
        return BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(v2)).doubleValue();
    }
    // 提供（相对）精确的除法运算，当发生除不尽的情况时.
    // 精确到小数点以后10位的数字四舍五入。
    public static double div(double v1,double v2)
    {
        return BigDecimal.valueOf(v1).divide(BigDecimal.valueOf(v2), DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static void main(String[] args) {
        System.out.println("0.05 + 0.01 = " + Arith.add(0.05 , 0.01));
        System.out.println("1.0 - 0.42 = " + Arith.sub(1.0 , 0.42));
        System.out.println("4.015 * 100 = " + Arith.mul(4.015 , 100));
        System.out.println("123.3 / 100 = " + Arith.div(123.3 , 100));
    }
}
