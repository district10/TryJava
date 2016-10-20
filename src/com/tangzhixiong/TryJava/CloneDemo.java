package com.tangzhixiong.TryJava;

/**
 * Created by tzx on 2016/10/20.
 */
class Address {
    String detail;
    public Address(String detail) {
        this.detail = detail;
    }
}
// 实现 Cloneable 接口
class User implements Cloneable {
    int age;
    Address address;
    public User(int age) {
        this.age = age;
        address = new Address("广州天河");
    }
    // 通过调用 super.clone() 来实现 clone() 方法
    public User clone() throws CloneNotSupportedException {
        return (User)super.clone();
    }
}
public class CloneDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        User u1 = new User(29);
        // clone 得到 u1 对象的副本。
        User u2 = u1.clone();
        // 判断 u1、u2 是否相同
        System.out.println(u1 == u2);
        // 判断 u1、u2 的 address 是否相同
        System.out.println(u1.address == u2.address);
    }
}
