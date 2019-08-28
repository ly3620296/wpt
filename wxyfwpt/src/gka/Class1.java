package gka;

import java.util.*;
import java.util.Random;

class Class1 {
    private static Class1 obj = new Class1();
    public static int counter1;
    public static int counter2 = 0;
    static int[] a = new int[new Random().nextInt(10)];
    static int[] a1 = new int[]{2};

    private Class1() {
        System.out.println("--------");
        counter1++;
        counter2++;
    }

    public static Class1 getInstance() {
        return obj;
    }

    public static void main(String[] args) {
        System.out.println(a.length);
//        System.out.println(a[0]);
//        System.out.println(a1[0]);
//        a = a1;
//        System.out.println(a[0]);
//        System.out.println(a1[0]);
//        a[0]=11;
//        System.out.println(a[0]);
//        System.out.println(a1[0]);
//        Class1 obj = new Class1();
//        Class1 obj1 = new Class1();
//        Class1 obj1 = new Class1();
//        System.out.println("Class1.counter1==" + obj.counter1);
//        System.out.println("Class1.counter2==" + obj.counter2);
    }
}