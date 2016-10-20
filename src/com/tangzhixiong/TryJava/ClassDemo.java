package com.tangzhixiong.TryJava;

/**
 * Created by tzx on 2016/10/20.
 */
public class ClassDemo {
    public static void main(String[] args) {
        testInstanceOf();
    }
    public static void testInstanceOf() {
        String str = new String();
        if (str instanceof Object) { // true
            System.out.println("String() is an object.");
        } else{
            System.out.println("String() is NOT an object.");
        }
    }
}
