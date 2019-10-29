package com.mryt.kayson.test.classPool;

import com.mryt.kayson.test.data.Foo;
import javassist.ClassPool;
import javassist.CtClass;

/**
 * @Author: Kayson Yang
 * @Date: 2019/10/28 11:40 PM
 * @Desc:
 */
public class ClassPoolTest {

    public static void main(String[] args) {
        try {
            ClassPool classPool = ClassPool.getDefault();
// get ctClass (compile time class)
            CtClass fooClass = classPool.get("com.mryt.kayson.test.data.Foo");
// modify class
            fooClass.setSuperclass(classPool.get("com.mryt.kayson.test.data.Bar"));
// write to some path
            //fooClass.writeFile("/tmp/");
            Class fooClazz = fooClass.toClass();

            Foo o = (Foo) fooClazz.newInstance();
            o.print();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
