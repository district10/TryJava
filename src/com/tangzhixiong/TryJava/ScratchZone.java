package com.tangzhixiong.TryJava;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * Created by tzx on 2016/10/20.
 */
public class ScratchZone {
    public static void main(String[] args) {
    }
}

class DemoClass {
    static {
        System.out.println("Init DemoClass.");
    }
    final static String className = "DemoClass";
}
