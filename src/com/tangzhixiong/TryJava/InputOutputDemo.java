package com.tangzhixiong.TryJava;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by tzx on 2016/10/18.
 */
public class InputOutputDemo {
    public static void main(String args[]) {
        // testScanner();
        // testFileReader();
        // testFileInputStream();
        // try { testFileInputStream_alt(); } catch (Exception e ) { e.printStackTrace(); }
        // testPrintStream();
        // testStringReader();
        // testBufferReader();
        // testPushbackReader();
        // testSetInSetOut();
        // testGetErrorStream();
        // testGetOutpurStream(); // BUGGY
        // testRandomAccessFile();
        testWriteObject();
    }
    public static void testWriteObject() {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("README.md.bak"));
        ) {
            Person per = new Person("孙悟空", 500);
            oos.writeObject(per);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("README.md.bak"));
        ) {
            Person p = (Person)ois.readObject();
            System.out.println("名字为：" + p.getName() + "\n年龄为：" + p.getAge());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testRandomAccessFile() {
        try (
                RandomAccessFile raf = new RandomAccessFile("README.md" , "r");
                RandomAccessFile out = new RandomAccessFile("README.md.bak" , "rw");
        ) {
            out.seek(out.length());
            out.write("追加的内容！\r\n".getBytes());
            // 获取 RandomAccessFile 对象文件指针的位置，初始位置是0
            System.out.println("RandomAccessFile 的文件指针的初始位置：" + raf.getFilePointer());
            // 移动 raf 的文件记录指针的位置
            raf.seek(20);
            byte[] bbuf = new byte[1024];
            int hasRead = 0;
            while ((hasRead = raf.read(bbuf)) > 0) {
                System.out.print(new String(bbuf , 0 , hasRead ));
                // out.write(bbuf);                                 // 有乱码
                // out.write(bbuf.toString().getBytes());           // 不能有效输出
                out.write(new String(bbuf,0,hasRead).getBytes());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testGetOutpurStream() {
        try {
            Process p = Runtime.getRuntime().exec("date");
            PrintStream ps = new PrintStream(p.getOutputStream());
            ps.println("12345");
            ps.println("abcde");
            ps.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String buff = null;
            while ((buff = br.readLine()) != null) {
                System.out.println(buff);
            }
            System.out.println("END\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testGetErrorStream() {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("javac");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try(
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            ) {
                String buff = null;
                while((buff = br.readLine()) != null) {
                    System.out.println(buff);
                }
            }
            catch (Exception e ) {
                e.printStackTrace();
            }
        }
    }

    public static void testSetInSetOut() {
        try(
                FileInputStream fis = new FileInputStream("README.md");
                PrintStream ps = new PrintStream(new FileOutputStream("README.md.bak"));
        ) {
            System.setIn(fis);
            System.setOut(ps);
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");
            while(sc.hasNext()) {
                System.out.println("Input: " + sc.next());
            }
            System.out.println("That's the End.\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void testPushbackReader() {
        try (
                PushbackReader pr = new PushbackReader(new FileReader("README.md") , 64))
        {
            char[] buf = new char[32];
            String lastContent = "";
            int hasRead = 0;
            while ((hasRead = pr.read(buf)) > 0) {
                // 将读取的内容转换成字符串
                String content = new String(buf , 0 , hasRead);
                System.out.println("\t\t\""+content+"\"");
                int targetIndex = 0;
                // 将上次读取的字符串和本次读取的字符串拼起来，
                // 查看是否包含目标字符串, 如果包含目标字符串
                if ((targetIndex = (lastContent + content).indexOf("Java")) > 0) {
                    // 将本次内容和上次内容一起推回缓冲区
                    pr.unread((lastContent + content).toCharArray());
                    // 重新定义一个长度为targetIndex的char数组
                    if(targetIndex > 32) {
                        buf = new char[targetIndex];
                    }
                    // 再次读取指定长度的内容（就是目标字符串之前的内容）
                    pr.read(buf , 0 , targetIndex);
                    // 打印读取的内容
                    System.out.print(new String(buf , 0 ,targetIndex));
                    System.exit(0);
                } else {
                    // 打印上次读取的内容
                    System.out.print(lastContent);
                    // 将本次内容设为上次读取的内容
                    lastContent = content;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testBufferReader() {
        try (
                InputStreamReader reader = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(reader);
        ) {
            String line = null;
            int index = 0;
            while ((line = br.readLine()) != null) {
                if (line.equals("exit")) {
                    System.exit(1);
                }
                System.out.println(String.format("Line#%02d: ",++index)+line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testStringReader() {
        String src = "从明天起，做一个幸福的人\n"
                + "喂马，劈柴，周游世界\n"
                + "从明天起，关心粮食和蔬菜\n"
                + "我有一所房子，面朝大海，春暖花开\n"
                + "从明天起，和每一个亲人通信\n"
                + "one two three four\n"
                + "告诉他们我的幸福\n";
        char[] buffer = new char[32];
        int hasRead = 0;
        try (
                StringReader sr = new StringReader(src);
                StringWriter sw = new StringWriter();
        ) {
            System.out.println("Content in StringReader:\n");
            while((hasRead = sr.read(buffer)) > 0) {
                System.out.print("\""+new String(buffer ,0 , hasRead)+"\"");
            }
            sw.write("有一个美丽的新世界，\n");
            sw.write("她在远方等我,\n");
            sw.write("哪里有天真的孩子，\n");
            sw.write("还有姑娘的酒窝\n");
            System.out.println("Content in StringWriter:\n");
            System.out.println(sw.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void testPrintStream() {
        try (
                FileOutputStream fos = new FileOutputStream("README.md.bak");
                PrintStream ps = new PrintStream(fos);
        ) {
            ps.println("普通字符串");
            ps.printf("string: %s, int: %d, float: %f\n", "Hello", 12, 5.6);
            // 直接使用 PrintStream 输出对象
            ps.println(new Object());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 可以 throw IOException 给上面，所以你要用这个的话，你在调用的地方，就要处理这个 exception，
    // 或者，throw 这样的 exception。
    public static void testFileInputStream_alt() throws IOException {
        FileInputStream fis = new FileInputStream("README.md");
        byte[] bbuf = new byte[1024];
        int hasRead = 0;
        while ((hasRead = fis.read(bbuf)) > 0) {
            System.out.print(new String(bbuf, 0, hasRead));
        }
    }

    // 也可以处理掉 IOException
    public static void testFileInputStream() {
        try(
                FileInputStream fis = new FileInputStream("README.md");
                FileOutputStream fos = new FileOutputStream("README.md.bak")
        ) {
            fos.write(new String("FileOutputStream:\n").getBytes());
            byte[] bbuf = new byte[1024];
            int hasRead = 0;
            while ((hasRead = fis.read(bbuf)) > 0) {
                System.out.print(new String(bbuf, 0, hasRead));
                // 两种一样的：fos.write(bbuf);
                fos.write(bbuf, 0, hasRead) ;
            }
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testFileReader() {
        try(
                FileReader fr = new FileReader("README.md");
                FileWriter fw = new FileWriter("README.md.bak")
        ) {
            fw.write("FileReader -> FileWriter\n");     // 这个换行符没问题，但是流里面的，有点问题
            char[] cbuf = new char[32];
            int hasRead = 0;
            while ((hasRead = fr.read(cbuf)) > 0) {
                System.out.print(new String(cbuf , 0 , hasRead));
                fw.write(cbuf);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
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

class Person implements java.io.Serializable {
    private String name;
    private int age;
    // private Person() {}              // 即使没有这一行，new Person() 也是不能用的
    public Person(String name , int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return this.age;
    }

    // 自定义 readObject 和 writeObject（一点加密）
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 将 name 实例变量的值反转后写入二进制流
        out.writeObject(new StringBuffer(name).reverse());
        out.writeInt(age);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 将读取的字符串反转后赋给 name 实例变量
        this.name = ((StringBuffer)in.readObject()).reverse().toString();
        this.age = in.readInt();
    }

    /*
    // 重写 writeReplace 方法，程序在序列化该对象之前，
    // 先调用该方法，【转而】序列化对象
    private Object writeReplace()throws ObjectStreamException {
        System.out.println("Write replace.");
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(age);
        return list;
    }
    // 使用方法：
    //      System.out.println((ArrayList)ois.readObject());
    */
}
