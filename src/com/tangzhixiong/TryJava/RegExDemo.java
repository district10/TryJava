package com.tangzhixiong.TryJava;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tzx on 2016/10/20.
 */
public class RegExDemo {
    public static void main(String[] args) {
        testReplace();
        // testGroupEnd();
        // testMatchingWords();
        // testMatchingEmail();
    }
    public static void testGroupEnd() {
        System.out.println("一个 match 的 group(), start(), end()：");
        // 创建一个 Pattern 对象，并用它建立一个 Matcher 对象
        String regStr = "Java is very easy!";
        System.out.println("目标字符串是："+regStr);
        Matcher m = Pattern.compile("\\w+").matcher(regStr);
        while (m.find()) {
            System.out.println("["+m.start() + ", " + m.end()+"]: "+m.group());
        }
    }
    public static void testReplace() {
        System.out.println("matcher.replaceAll()：");
        String[] msgs = { "Java has regular expressions in 1.4",
                          "regular expressions now expressing in Java",
                          "Java represses oracular expressions" };
        Pattern p = Pattern.compile(" re\\w*");
        Matcher matcher = null;
        for (int i = 0 ; i < msgs.length ; i++) {
            if (matcher == null) {
                matcher = p.matcher(msgs[i]);
            } else {
                matcher.reset(msgs[i]);
            }
            System.out.println(matcher.replaceAll("哈哈:)"));
        }
    }
    public static void testMatchingWords() {
        String[] msgs = { "Java has regular expressions in 1.4", // Java has 哈哈:) expressions in 1.4
                          "regular expressions now expressing in Java",
                          "Java represses oracular expressions" };
        for (String msg : msgs) {
            System.out.println(msg.replaceFirst("re\\w*" , "哈哈:)"));
            System.out.println(Arrays.toString(msg.split(" ")));
        }
    }
    public static void testMatchingEmail() {
        String[] mails = { "kongyeeku@163.com" , "kongyeeku@gmail.com", "ligang@crazyit.org", "wawa@abc.xx" };
        String mailRegEx = "\\w{3,20}@\\w+\\.(com|org|cn|net|gov)";
        Pattern mailPattern = Pattern.compile(mailRegEx);
        Matcher matcher = null;
        for (String mail : mails) {
            if (matcher == null) {
                matcher = mailPattern.matcher(mail);
            } else {
                matcher.reset(mail);
            }
            String result = mail + (matcher.matches() ? "是" : "不是") + "一个有效的邮件地址！";
            System.out.println(result);
        }
    }
}
