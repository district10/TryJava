package com.tangzhixiong.TryJava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Created by tzx on 2016/10/20.
 */
public class CollectionDemo {

    public static void main(String[] args) {
        testCollectionStream();
        // testIntStream();// BUGGY
        // testPredicate();
        // testRemoveIf();
        // testForEachRemaining();  // it.forEachRemaining((obj) -> {...});
        // testIterator();          // Iterator it = books.iterator(); it.hasNext(); it.next()
        // testForEach();           // books.forEach((arg) -> { ... });
        // testForEach2();          // for (Object book : books) { ... }
    }

    public static void testSet() { }
    public static void testQueue() { }
    public static void testList() { }

    public static void testCollection() {
        Collection c = new ArrayList();

        // 添加元素
        c.add("孙悟空");
        // 虽然集合里不能放基本类型的值，但 Java 支持自动装箱
        c.add(6);
        System.out.println("c 集合的元素个数为:" + c.size()); // 输出 2

        // 删除指定元素
        c.remove(6);
        System.out.println("c 集合的元素个数为:" + c.size()); // 输出 1

        // 判断是否包含指定字符串
        System.out.println("c 集合的是否包含 \"孙悟空\" 字符串:" + c.contains("孙悟空")); // 输出 true

        c.add("轻量级 Java EE 企业应用实战");
        System.out.println("c 集合的元素：" + c);

        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        System.out.println("c 集合是否完全包含 books 集合？" + c.containsAll(books)); // 输出 false

        // 用 c 集合减去 books 集合里的元素
        c.removeAll(books);
        System.out.println("c 集合的元素：" + c);

        // 删除 c 集合里所有元素
        c.clear();

        System.out.println("c 集合的元素：" + c);
        // 控制 books 集合里只剩下 c 集合里也包含的元素

        books.retainAll(c);
        // remove: A\B
        // retail: A&B
        System.out.println("\nbooks 集合的元素:" + books);
    }

    public static void testForEach() {
        // 创建一个集合
        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        books.add("疯狂 Android 讲义");
        // 调用 forEach() 方法遍历集合
        books.forEach((obj) -> {System.out.println("迭代集合元素：" + obj);});
     // books.forEach( obj  -> System.out.println("迭代集合元素：" + obj)); // 两个一样的
    }

    public static void testForEach2() {
        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        books.add("疯狂 Android 讲义");
        for (Object obj : books) {
            // 此处的book变量也不是集合元素本身
            String book = (String)obj;
            System.out.println(book);
        }
        System.out.println(books);
    }

    public static void testForEachRemaining() {
        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        books.add("疯狂 Android 讲义");
        Iterator it = books.iterator();
        // 使用 Lambda 表达式（目标类型是 Comsumer）来遍历集合元素
        it.forEachRemaining(obj -> System.out.println("迭代集合元素：" + obj));
    }


    public static void testIterator() {
        // 创建集合、添加元素的代码与前一个程序相同
        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        books.add("疯狂 Android 讲义");

        // 获取 books 集合对应的迭代器

        Iterator it = books.iterator();
        while (it.hasNext()) {
            // it.next() 方法返回的数据类型是 Object 类型，因此需要强制类型转换
            String book = (String)it.next(); // 获取拷贝，而不是引用
            System.out.println(book);
            if (book.equals("疯狂 Java 讲义")) {
                // 从集合中删除上一次 next 方法返回的元素
                it.remove();
            }
            // 对 book 变量赋值，不会改变集合元素本身
            book = "测试字符串";
        }
        System.out.println(books);

        // 应当注意迭代器失效的问题，比如下面，就是出错的：
        /*
        Iterator it = books.iterator();
        while (it.hasNext()) {
            String book = (String)it.next();
            System.out.println(book);
            if (book.equals("疯狂 Android 讲义")) {
                // 使用 Iterator 迭代过程中，不可修改集合元素, 下面代码引发异常
                books.remove(book);
            }
        }
        */
    }

    public static void testRemoveIf() {
        // predicate
        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        books.add("疯狂 iOS 讲义");
        books.add("疯狂 Ajax 讲义");
        books.add("疯狂 Android 讲义");
        System.out.println("before remove len<10, #"+books.size()+": "+books);
        // 使用 Lambda 表达式（目标类型是 Predicate）过滤集合
        books.removeIf(ele -> ((String)ele).length() < 10);
        System.out.println("after remove len<10, #"+books.size()+": "+books);
    }

    public static void testPredicate() {
        Collection books = new HashSet();
        books.add("轻量级 Java EE 企业应用实战");
        books.add("疯狂 Java 讲义");
        books.add("疯狂 iOS 讲义");
        books.add("疯狂 Ajax 讲义");
        books.add("疯狂 Android 讲义");
        // 统计书名包含“疯狂”子串的图书数量
        System.out.println(calAll(books , ele->((String)ele).contains("疯狂")));
        // 统计书名包含“Java”子串的图书数量
        System.out.println(calAll(books , ele->((String)ele).contains("Java")));
        // 统计书名字符串长度大于 10 的图书数量
        System.out.println(calAll(books , ele->((String)ele).length() > 10));
    }

    public static int calAll(Collection books , Predicate p) {
        int total = 0;
        for (Object obj : books) {
            // 使用 Predicate 的 test() 方法判断该对象是否满足 Predicate 指定的条件
            if (p.test(obj)) {
                ++total;
            }
        }
        return total;
    }

    public static void testIntStream() {
        IntStream is = IntStream.builder().add(20).add(13).add(-2).add(18).build();
        // 下面调用聚集方法的代码每次只能执行一个
        System.out.println("is 所有元素的最大值：" + is.max().getAsInt());
        System.out.println("is 所有元素的最小值：" + is.min().getAsInt());
        System.out.println("is 所有元素的总和：" + is.sum());
        System.out.println("is 所有元素的总数：" + is.count());
        System.out.println("is 所有元素的平均值：" + is.average());
        System.out.println("is 所有元素的平方是否都大于 20:" + is.allMatch(ele -> ele * ele > 20));
        System.out.println("is 是否包含任何元素的平方大于 20:" + is.anyMatch(ele -> ele * ele > 20));
        // 将 is 映射成一个新 Stream，新 Stream 的每个元素是原 Stream 元素的 2 倍 +1
        IntStream newIs = is.map(ele -> ele * 2 + 1);
        // 使用方法引用的方式来遍历集合元素
        newIs.forEach(System.out::println); // 输出 41 27 -3 37
    }

    public static void testCollectionStream() {
        Collection books = new HashSet();
        books.add("轻量级Java EE企业应用实战");
        books.add("疯狂Java讲义");
        books.add("疯狂iOS讲义");
        books.add("疯狂Ajax讲义");
        books.add("疯狂Android讲义");

        // 统计书名包含“疯狂”子串的图书数量
        System.out.println(books.stream().filter(ele->((String)ele).contains("疯狂")).count());     // 输出 4
        // 统计书名包含“Java”子串的图书数量
        System.out.println(books.stream().filter(ele->((String)ele).contains("Java") ).count());    // 输出 2

        // 统计书名字符串长度大于 10 的图书数量
        System.out.println(books.stream().filter(ele->((String)ele).length() > 10).count());        // 输出 2

        // 先调用 Collection 对象的 stream() 方法将集合转换为 Stream,
        // 再调用 Stream 的 mapToInt() 方法获取原有的 Stream 对应的 IntStream
        books.stream().mapToInt(ele -> ((String)ele).length()).forEach(System.out::println);        // 输出 8  11  16  7  8
        // 调用 forEach() 方法遍历 IntStream 中每个元素
    }
    // Set: HashSet, EnumSet, TreeSet
    // HASH
}
