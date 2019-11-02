package com.mryt.kayson.test.defineNew;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

import java.io.IOException;

/**
 * @Author: Kayson Yang
 * @Date: 2019/11/2 3:05 PM
 * @Desc:
 */
public class DefineNewClass {

    public static void main(String[] args) throws Exception {
        createNewClass();
    }

    private static void createNewClass() throws CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass newClass = classPool.makeClass("MakeNewClass");
        newClass.writeFile("/tmp/");
        CtClass newInterface = classPool.makeInterface("MakeNewInterface");
        newInterface.writeFile("/tmp");
    }
}
