package com.tangzhixiong.TryJava;

/**
 * Created by tzx on 2016/10/18.
 */

public class StringBufferDemo {
    public static void main(String args[]) {
        StringBuffer sb = new StringBuffer();
        System.out.print("\n--StringBuffer");
        System.out.print("\n----length(): "+ sb.length());
        sb.append("12345");
        sb.append('c');
        String str = " String ";
        sb.append(str.replace("ing", "ING"));
        sb.append(str);
        System.out.print("\n----append(String/char): "+sb.toString());
        sb.replace( 1, 5, "XYZ"); // [start, end)
        System.out.print("\n----replace(start,end,str): "+sb.toString());
        sb.append(new StringBuffer(sb.toString()));
        System.out.print("\n----append(new StringBuffer(sb.toString())): "+sb.toString());
        sb.insert( 4, "(insert at 4)");
        System.out.print("\n----capasity(): "+sb.capacity());
        System.out.print("\n----codePointAt(index=5): "+sb.codePointAt(5));
        System.out.print("\n----substring(0): "+sb.substring(0));
        System.out.print("\n----deleteCharAt(2): "+sb.deleteCharAt(2));
        System.out.print("\n----substring(0,5): "+sb.substring(0,5));
        System.out.print("\n----substring(2,5): "+sb.substring(2,5));
        sb.reverse();
        System.out.print("\n----reserve(): "+sb.toString());
        System.out.print("\n----reserve(): "+sb.reverse());
        // System.out.println();
    }
}
