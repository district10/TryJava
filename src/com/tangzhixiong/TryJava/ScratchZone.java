package com.tangzhixiong.TryJava;

import java.lang.ref.WeakReference;

/**
 * Created by tzx on 2016/10/20.
 */
public class ScratchZone {
    public static void main(String[] args) {
        String str = "A String";
        WeakReference<String> wr = new WeakReference<String>(str);
        str = null;
        System.out.println(wr.get()); // A String
    }
}
