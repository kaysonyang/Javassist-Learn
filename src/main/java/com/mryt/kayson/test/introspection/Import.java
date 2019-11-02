package com.mryt.kayson.test.introspection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
/**
 * @Author: Kayson Yang
 * @Date: 2019/11/2 3:56 PM
 * @Desc:
 */
public class Import {
    public static void main(String[] args) throws Exception {
        useImport();
    }

    private static void useImport() throws CannotCompileException {
        ClassPool classPool = ClassPool.getDefault();
        classPool.importPackage("java.util");
        CtClass ctClass = classPool.makeClass("Test");
        CtField make = CtField.make("private Map map;", ctClass);
        ctClass.addField(make);
    }
}
