package com.tangzhixiong.TryJava;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by tzx on 2016/10/22.
 */
public class LoaderDemo {
    public static void main(String[] args) throws Exception {
        // testLoader(); // BUGGY
        // CrazyObjectFactory.main(args); // BUGGY
        // CrazyArrayFactory.main(args);
        GenericTest.main(args);
    }

    public static void testLoader() {
        try {
            System.out.println("ClassLoader.getSystemClassLoader().loadClass(...);");
            // ClassLoader.getSystemClassLoader().loadClass(DemoClass.className);
            ClassLoader.getSystemClassLoader();// .loadClass("ScratchZone");
            System.out.println("Class.forName(...);");
            // Class.forName(DemoClass.className);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class CrazyObjectFactory {
    public static Object getInstance(String className) {
        try {
            // BUGGY
            // Class cls = Class.forName(className);
            // return cls.newInstance();
            return null;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String str = (String)CrazyObjectFactory.getInstance("String");
        if( str instanceof String) {
            System.out.println("String");
        } else {
            System.out.println("NOT String");
        }
    }
}

class CrazyArrayFactory {
    // 对 Array 的 newInstance 方法进行包装
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<T> componentType, int length) {
        return (T[]) Array.newInstance(componentType , length);
    }
    public static void main(String[] args) {
        String[] arr = CrazyArrayFactory.newInstance(String.class , 10);
        arr[5] = "疯狂 Java 讲义";
        System.out.println(arr[5]);

        // 使用 CrazyArrayFactory 的 newInstance() 创建二维数组
        // 在这种情况下，只要设置数组元素的类型是int[]即可。
        int[][] intArr = CrazyArrayFactory.newInstance(int[].class , 5);
        // intArr 是二维数组，初始化该数组的第二个数组元素，二维数组的元素必须是一维数组
        intArr[1] = new int[]{23, 12};
        System.out.println(intArr[1][1]);
    }
}

class GenericTest {
    private Map<String , Integer> score;
    public static void main(String[] args) throws Exception {
        Class<GenericTest> clazz = GenericTest.class;
        Field f = clazz.getDeclaredField("score");
        // 直接使用getType()取出的类型只对普通类型的成员变量有效
        Class<?> a = f.getType();
        // 下面将看到仅输出java.util.Map
        System.out.println("score 的类型是:" + a);
        // 获得成员变量f的泛型类型
        Type gType = f.getGenericType();
        // 如果gType类型是ParameterizedType对象
        if(gType instanceof ParameterizedType) {
            // 强制类型转换
            ParameterizedType pType = (ParameterizedType)gType;
            // 获取原始类型
            Type rType = pType.getRawType();
            System.out.println("原始类型是：" + rType);
            // 取得泛型类型的泛型参数
            Type[] tArgs = pType.getActualTypeArguments();
            System.out.println("泛型信息是:");
            for (int i = 0; i < tArgs.length; i++) {
                System.out.println("第 " + i + " 个泛型类型是：" + tArgs[i]);
            }
        } else {
            System.out.println("获取泛型类型出错！");
        }
    }
}
