package com.tangzhixiong.TryJava;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by tzx on 2016/10/21.
 */
public class FileDemo {
    public static void main(String[] args)  throws IOException {
        // testFile();
        testFilter();
    }

    public static void testFilter() {
        // File file = new File("./com/tangzhixiong/TryJava/");
        File file = new File(".");
        // 使用 Lambda 表达式（目标类型为 FilenameFilter）实现文件过滤器。
        // 如果文件名以 .java 结尾，或者文件对应一个路径，返回 true
        String[] nameList = file.list((dir, name) -> name.endsWith(".java") || new File(name).isDirectory());
        System.out.println(Arrays.toString(nameList));
    }

    public static void testFile() throws IOException {
        // 以当前路径来创建一个 File 对象
        File file = new File(".");
        System.out.println(file.getName());
        System.out.println(file.getParent());                   // 获取相对路径的父路径可能出错，下面代码输出null
        System.out.println(file.getAbsoluteFile());             // 获取绝对路径
        System.out.println(file.getAbsoluteFile().getParent()); // 获取上一级路径

        // 在当前路径下创建一个临时文件
        File tmpFile = File.createTempFile("aaa", ".txt", file);
        // 指定当 JVM 退出时删除该文件
        tmpFile.deleteOnExit();

        // 以系统当前时间作为新文件名来创建新文件
        File newFile = new File(System.currentTimeMillis() + "");
        System.out.println("newFile 对象是否存在：" + newFile.exists());
        newFile.createNewFile();
        newFile.mkdir();                                        // 已经有文件了，就不能创建目录

        // 使用 list() 方法来列出当前路径下的所有文件和路径
        String[] fileList = file.list();
        System.out.println("当前路径下所有文件和路径如下："+Arrays.toString(fileList));

        // listRoots() 静态方法列出所有的磁盘根路径。
        File[] roots = File.listRoots();
        System.out.println("系统所有根路径如下："+Arrays.toString(roots));
    }
}
