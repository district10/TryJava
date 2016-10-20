package com.tangzhixiong.TryJava;

import java.util.StringJoiner;

/**
 * Created by tzx on 2016/10/18.
 */
public class StringDemo {
    public static void main(String args[]) {
        String str = "0123456789abcdefABCDEF";
        System.out.print("\n--String:");
        System.out.print("\n----toString(): "+str);
        System.out.print("\n----subString(5): "+str.substring(5));
        System.out.print("\n----subString(5,7): "+str.substring(5,7));
        System.out.print("\n----subString(5,str.length()): "+str.substring(5,str.length()));
        // java.lang.StringIndexOutOfBoundsException: String index out of range: 100
        // System.out.print("\n----subString(5,100): "+str.substring(5,100));
        System.out.print("\n----concat(\"_tail\"): "+str.concat("_tail"));
        System.out.print("\n----codePointAt(2): "+str.codePointAt(2));
        System.out.print("\n----codePointBefore(2): "+str.codePointBefore(2));
        System.out.print("\n----codePointCount(0,5): "+str.codePointCount(0,5));
        System.out.print("\n----toLowerCase(): "+str.toLowerCase());
        System.out.print("\n----toUpperCase(): "+str.toUpperCase());
        // replaceall(regex, )
        CharSequence cs1 = "abc", cs2 = "xyz";
        System.out.print("\n----replace(charSequence1, cs2): "+str.replace(cs1, cs2));
        System.out.print("\n----replace(str1, str2): "+str.replace(cs1.toString().toUpperCase(), ""));
        System.out.print("\n----bytes[] getBytes(): ");
        byte[] buf = str.getBytes();
        for (byte b: buf) {
            System.out.print("["+b+ "]");
        }
        String strs[] = str.split(" ");
        System.out.print("\n----split(\" \").length: "+str.split(" ").length);
        System.out.print("\n----split(\" \").length: "+str.split(" ").length);
        System.out.print("\n----String.format(\"%s, %03d, %f\", \"good\", 3, 3.14): "+String.format("%s, %03d, %f", "good", 3, 3.14));
    }
}
