package com.tangzhixiong.TryJava;

import java.util.Scanner;

/**
 * Created by tzx on 2016/10/18.
 */
public class InputOutputDemo {
    public static void main(String args[]) {
        testScanner();
    }
    public static void testScanner() {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        System.out.println("useDelimiter(\"\\n\"), next(): "+sc.next());
        if (sc.hasNextLine()) {
            System.out.println("nextLine: "+sc.nextLine());
        }
        sc.useDelimiter(" ");
        if (sc.hasNext()) { System.out.print("("+sc.next()+") "); }
        if (sc.hasNext()) { System.out.print("("+sc.next()+") "); }
        if (sc.hasNextLong()) {
            System.out.print("nextLong(): "+sc.nextLong());
        }
        System.out.println();
    }
}