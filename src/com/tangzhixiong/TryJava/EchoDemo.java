package com.tangzhixiong.TryJava;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by tzx on 2016/10/21.
 */
public class EchoDemo {
    public static void main(String[] args) {
        try(
                Scanner sc = new Scanner(System.in);
                PrintStream ps = new PrintStream(new FileOutputStream("README.md.bak"))
        ) {
            sc.useDelimiter("\n");
            while(sc.hasNext()) {
                ps.println("键盘输入的内容是：" + sc.next());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
