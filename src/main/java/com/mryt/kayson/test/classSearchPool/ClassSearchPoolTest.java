package com.mryt.kayson.test.classSearchPool;

import javassist.ClassPool;
import javassist.ByteArrayClassPath;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.NotFoundException;
/**
 * @Author: Kayson Yang
 * @Date: 2019/11/2 3:09 PM
 * @Desc:
 */
public class ClassSearchPoolTest {
    public static void main(String[] args) throws Exception {
        registerClassPath();
    }

    private static void registerClassPath() throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        // this statement registers the classpath that was used to loading the `ClassSearchPath.class`.
        classPool.insertClassPath(new ClassClassPath(ClassSearchPoolTest.class));

        // You can register a directory name as the class search path
        classPool.insertClassPath("/tmp/");

        // add a byte array
        classPool.insertClassPath(new ByteArrayClassPath("foo", new byte[]{1, 2, 3}));
    }
}
