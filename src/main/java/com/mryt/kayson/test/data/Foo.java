package com.mryt.kayson.test.data;

import javassist.CtClass;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Kayson Yang
 * @Date: 2019/10/29 12:08 AM
 * @Desc:
 */
public class Foo {

    public Foo() {
        System.out.println("Constructor of foo");
    }

    static {
        CtClass.debugDump = "/tmp/javassist";
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            hello();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static void hello() {
        System.out.println("Hello");
    }

    public void print() {
        System.out.println("Print...");
    }

    @Profile
    public static int method(String input) {
        System.out.println(input);
        return 1;
    }
}
