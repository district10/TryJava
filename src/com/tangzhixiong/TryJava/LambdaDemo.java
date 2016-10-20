package com.tangzhixiong.TryJava;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by tzx on 2016/10/20.
 */
public class LambdaDemo {
    @FunctionalInterface
    interface Converter {
        Integer convert(String from);
    }
    public static void main(String[] args) {
        Runnable r = ()-> {
            for (int i = 0; i < 10; ++i) {
                System.out.println(i);
            }
        };
        Converter c = (from) -> Integer.valueOf(from);
        Integer val = c.convert("99");
        System.out.println(val);

        Converter c2 = Integer::valueOf;
        // System.out.println(c2("999"));
    }
}
