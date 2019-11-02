package com.mryt.kayson.test.introspection;

import com.mryt.kayson.test.data.Foo;
import javassist.*;
import javassist.bytecode.MethodInfo;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @Author: Kayson Yang
 * @Date: 2019/10/31 2:01 PM
 * @Desc:
 */
public class ReadingClass {
    public static void main(String[] args) throws Exception {
        //read();
        readFromBytes();
    }

    public static void read() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException {
        ClassPool classPool = ClassPool.getDefault();
        // get ctClass (compile time class)
        CtClass fooClass = classPool.get("com.mryt.kayson.test.data.Foo");
        // modify class
        fooClass.setSuperclass(classPool.get("com.mryt.kayson.test.data.Bar"));
        // write to some path
        fooClass.writeFile("/tmp/");
        Class fooClazz = fooClass.toClass();
        Foo o = (Foo) fooClazz.newInstance();
        o.print();
    }

    public static void readFromBytes() {
        /**
         * 动态加载javaagent主要是在程序运行过程中通过 ByteBuddyAgent.install();
         * 获得 Instrumentation inst 对象，
         * 而不是在启动的时候通过加入-javaagent 来获得Instrumentation inst对象。
         */
        Instrumentation instrumentation = ByteBuddyAgent.install();
        instrumentation.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            ClassPool classPool = ClassPool.getDefault();
            try {
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
                for (CtMethod declaredMethod : declaredMethods) {
                    MethodInfo methodInfo = declaredMethod.getMethodInfo();
                    if (methodInfo.getCodeAttribute() == null) {
                        continue;
                    }
                    declaredMethod.insertBefore("System.out.println(\"Before Method\");");
                    declaredMethod.insertAfter("System.out.println(\"After Method\");");
                }
                return ctClass.toBytecode();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
            return classfileBuffer;
        });
        Foo.hello();
    }
}
