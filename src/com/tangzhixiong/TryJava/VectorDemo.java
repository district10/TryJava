package com.tangzhixiong.TryJava;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by tzx on 2016/10/18.
 */

public class VectorDemo {
    public class CollectionItem {
        public int value;
    }
    public static void main(String args[]) {
        Vector v = new Vector();
        for (int i = 0; i < 5; ++i) {
            // int a = System.in.read();           // read a char
            v.addElement(i+1);
        }

        v.add(2, 100);
        // v.add(new Double(4.3));
        // v.add(new String("A string"));
        Enumeration en = v.elements();
        while (en.hasMoreElements()) {
            Integer i = (Integer) en.nextElement();
            System.out.println(i);
        }
    }
}
