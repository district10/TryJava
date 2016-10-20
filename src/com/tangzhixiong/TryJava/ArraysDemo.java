package com.tangzhixiong.TryJava;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * Created by tzx on 2016/10/20.
 */
public class ArraysDemo {
    public static void main(String[] args) {
        int[] a = new int[]{ 0,1,2,3,4,5 };
        int[] b = new int[3];
        int[] c = Arrays.copyOf(a, 3);
        int[] d = Arrays.copyOfRange(a, 3, 5);
        for (int i: a) { System.out.print(i+" "); } System.out.println();
        for (int i: b) { System.out.print(i+" "); } System.out.println();
        for (int i: c) { System.out.print(i+" "); } System.out.println();
        for (int i: d) { System.out.print(i+" "); } System.out.println();
        System.out.println("Arrays.toString(a): "+Arrays.toString(a));
        System.out.println("Arrays.toString(b): "+Arrays.toString(b));
        System.out.println("Arrays.toString(c): "+Arrays.toString(c));
        System.out.println("Arrays.toString(d): "+Arrays.toString(d));
        System.out.println("Arrays.equals(a,b): "+Arrays.equals(a,b));
        System.out.println("Arrays.binarySearch(a,0,6,3): "+a[Arrays.binarySearch(a, 0, 6, 3)]);
        // Ararys.deepEquals
        Arrays.fill(a, 0, 3, 9);
        System.out.println("Arrays.fill(a,0,3,9): "+Arrays.toString(a));
        // IntStream ds = Arrays.stream(a);
        // Arrays.parallelPrefix();
        // Arrays.parallelSetAll();

        int[] arr1 = new int[]{3, -4 , 25, 16, 30, 18};
        System.out.println("\nbefore Arrays.parallelSort(): "+Arrays.toString(arr1));
        // 对数组arr1进行并发排序
        Arrays.parallelSort(arr1);
        System.out.println("after Arrays.parallelSort(): "+Arrays.toString(arr1));

        int[] arr2 = new int[]{3, -4 , 25, 16, 30, 18};
        System.out.println("\nbefore Arrays.parallesPrefix(): [left*right]: "+Arrays.toString(arr2));
        Arrays.parallelPrefix(arr2, new IntBinaryOperator()
        {
            // left 代表数组中前一个所索引处的元素，计算第一个元素时，left 为 1
            // right 代表数组中当前索引处的元素
            public int applyAsInt(int left, int right)
            {
                return left * right;
            }
        });
        System.out.println("after Arrays.parallesPrefix(): "+Arrays.toString(arr2));

        int[] arr3 = new int[5];
        System.out.println("\nbefore Arrays.parallesSetAll(): "+Arrays.toString(arr3));
        Arrays.parallelSetAll(arr3 , new IntUnaryOperator()
        {
            // operand 代表正在计算的元素索引
            public int applyAsInt(int operand)
            {
                return operand * 5; // return -> arr[operand]
            }
        });
        System.out.println("after Arrays.parallesSetAll(): "+Arrays.toString(arr3));
    }
}
