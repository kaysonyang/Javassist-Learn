package com.mryt.kayson.test.introspection;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
/**
 * @Author: Kayson Yang
 * @Date: 2019/11/2 3:54 PM
 * @Desc:
 */
public class AlteringMethodBody {
    public static void main(String[] args) throws Exception {

    }

    private static void instrumentMethod() throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.mryt.kayson.test.data.Foo");
        CtMethod method = ctClass.getDeclaredMethod("hello");
        method.instrument(new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                super.edit(m);
            }
        });
    }
}
