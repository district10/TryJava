package com.tangzhixiong.TryJava;

import java.time.*;
import java.util.Arrays;
import java.util.TimeZone;

/**
 * Created by tzx on 2016/10/20.
 */
public class DateTimeDemo {
    public static void main(String[] args) {
        // Date 和 Calendar 比较捉急，Joda-Time 比较流行，
        // Java 8 吸收了 Joda-Time，提供了一系列不错的事件处理类库
        // TODO: DateTimeFormater

        // ----- 下面是关于 Clock 的用法 -----
        // 获取当前 Clock
        Clock clock = Clock.systemUTC();
        // 通过 Clock 获取当前时刻
        System.out.println("当前时刻为：" + clock.instant());
        // 获取 clock 对应的毫秒数，与 System.currentTimeMillis() 输出相同
        System.out.println(clock.millis());
        System.out.println(System.currentTimeMillis());

        // ----- 下面是关于 Duration 的用法 -----
        Duration d = Duration.ofSeconds(6000);
        System.out.println("\n6000 秒相当于 " + d.toMinutes() + " 分");
        System.out.println("6000 秒相当于 " + d.toHours() + " 小时");
        System.out.println("6000 秒相当于 " + d.toDays() + " 天");
        // 在 clock 基础上增加 6000 秒，返回新的 Clock
        Clock clock2 = Clock.offset(clock, d);
        // 可看到 clock2 与 clock1 相差 1 小时 40 分
        System.out.println("当前时刻加 6000 秒为：" +clock2.instant());

        // ----- 下面是关于 Instant 的用法 -----
        // 获取当前时间
        Instant instant = Instant.now();
        System.out.println("\ninstant: "+instant);
        // instant 添加 6000 秒（即 100 分钟），返回新的 Instant
        Instant instant2 = instant.plusSeconds(6000);
        System.out.println(instant2);
        // 根据字符串中解析 Instant 对象
        Instant instant3 = Instant.parse("2014-02-23T10:12:35.342Z");
        System.out.println("从字符解析：\"2014-02-23T10:12:35.342Z\": "+instant3);
        // 在 instant3 的基础上添加 5 小时 4 分钟
        Instant instant4 = instant3.plus(Duration.ofHours(5).plusMinutes(4));
        System.out.println(instant4);
        // 获取 instant4 的 5 天以前的时刻
        Instant instant5 = instant4.minus(Duration.ofDays(5));
        System.out.println(instant5);
        // ----- 下面是关于 LocalDate 的用法 -----
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        // 获得 2014 年的第 146 天
        localDate = LocalDate.ofYearDay(2014, 146);
        System.out.println(localDate); // 2014-05-26
        // 设置为 2014 年 5 月 21 日
        localDate = LocalDate.of(2014, Month.MAY, 21);
        System.out.println(localDate); // 2014-05-21

        // ----- 下面是关于 LocalTime 的用法 -----
        // 获取当前时间
        LocalTime localTime = LocalTime.now();
        // 设置为 22 点 33 分
        localTime = LocalTime.of(22, 33);
        System.out.println("\nlocalTime: "+localTime); // 22:33
        // 返回一天中的第 5503 秒
        localTime = LocalTime.ofSecondOfDay(5503);
        System.out.println(localTime); // 01:31:43

        // ----- 下面是关于 localDateTime 的用法 -----
        // 获取当前日期、时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 当前日期、时间加上 25 小时３分钟
        LocalDateTime future = localDateTime.plusHours(25).plusMinutes(3);
        System.out.println("\n当前日期、时间的 25 小时 3 分之后：" + future);
        // 下面是关于 Year、YearMonth、MonthDay 的用法示例 -----
        Year year = Year.now(); // 获取当前的年份
        System.out.println("当前年份：" + year); // 输出当前年份
        year = year.plusYears(5); // 当前年份再加 5 年
        System.out.println("当前年份再过 5 年：" + year);
        // 根据指定月份获取 YearMonth
        YearMonth ym = year.atMonth(10);
        System.out.println("year 年 10 月：" + ym); // 输出 XXXX-10，XXXX 代表当前年份
        // 当前年月再加 5 年，减 3 个月
        ym = ym.plusYears(5).minusMonths(3);
        System.out.println("year 年 10 月再加 5 年、减 3 个月：" + ym);
        MonthDay md = MonthDay.now();
        System.out.println("当前月日：" + md); // 输出 --XX-XX，代表几月几日
        // 设置为 5 月 23 日
        MonthDay md2 = md.with(Month.MAY).withDayOfMonth(23);
        System.out.println("5 月 23 日为：" + md2); // 输出 --05-23

        // 时区
        // 取得 Java 所支持的所有时区 ID
        String[] ids = TimeZone.getAvailableIDs();
        System.out.println("Java 支持的时区有："+Arrays.toString(ids));
        TimeZone my = TimeZone.getDefault();
        // 获取系统默认时区的ID:Asia/Shanghai
        System.out.println("我的时区 ID："+my.getID()); // ID 就是一个字符串
        // 获取系统默认时区的名称：中国标准时间
        System.out.println(my.getDisplayName());
        // 获取指定 ID 的时区的名称：纽芬兰标准时间
        System.out.println("显示时间："+TimeZone.getTimeZone("CNT").getDisplayName());
    }
}
