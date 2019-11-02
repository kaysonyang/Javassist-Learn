package com.mryt.kayson.test.classloader;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Scanner;

import com.mryt.kayson.test.data.Foo;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.util.HotSwapAgent;
import javassist.util.HotSwapper;
import net.bytebuddy.agent.ByteBuddyAgent;
/**
 * @Author: Kayson Yang
 * @Date: 2019/11/2 3:33 PM
 * @Desc:
 */
public class ReloadClassAtRuntime {
    public static void main(String[] args) throws Throwable {
        hotSwapAgent();
    }
    /**
     *In contrast to standard Java, where the hotswap is limited to in-body code changes, the DCEVM + HotswapAgent allow following code changes:
     *
     * Add/remove/modify class fields.
     * Add/remove/modify methods. Add/remove/modify method annotations
     * Add/remove/modify classes including anonymous classes. HotswapAgent handless correct anonymous class redefinitions.
     * Add/remove static member of classes. HotswapAgent handles static member initialization.
     * Add/remove enum values
     * Refresh framework and application server settings
     * The only unsupported operation is hierarchy change (change the superclass or remove an interface).
     *
     * DCEVM realizes hotswap on JVM level. HotwapAgent does the same on level of Java frameworks and servlet containers. Both projects used together forms excellent combination for daily development not only in Java but also in another JVM languages.
     */
    private static void hotSwapAgent() throws Throwable {
//        HotSwapAgent.createAgentJarFile()
        // mock using javaagent
        Instrumentation install = ByteBuddyAgent.install();
        HotSwapAgent.agentmain("", install);
        Class<Foo> fooClass = Foo.class;
        Foo.hello();
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("com.mryt.kayson.test.data.Foo");
        CtMethod helloMethod = ctClass.getDeclaredMethod("hello");
        helloMethod.setBody("System.out.println(\"New hello implementation\");");
        HotSwapAgent.redefine(fooClass, ctClass);
        Foo.hello();
    }

    /**
     * Note: If use jdk8 in macos, you may encounter 'Can't attach symbolicator to the process', upgrade jdk to jdk11 will solve this.
     *
     * To use this class, the JVM must be launched with the following
     *
     * java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address={port}
     * @throws Exception
     */
    private static void hotSwapper() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String line;
        while (!"quit".equals((line = scanner.nextLine()))) {
            String[] splits = line.split("\\s");
            // params: port className targetMethod classPath
            String port = splits[0];
            String className = splits[1];
            String targetMethod = splits[2];
            String extraClassPath = splits[3];
            ClassPool classPool = ClassPool.getDefault();
            classPool.insertClassPath(extraClassPath);

            CtClass ctClass = classPool.get(className);
            CtMethod declaredMethod = ctClass.getDeclaredMethod(targetMethod);
            declaredMethod.insertBefore("System.out.println($class.toString());");
            byte[] modifiedBytes = ctClass.toBytecode();
            reload(Integer.parseInt(port), className, modifiedBytes);
        }
    }

    private static void reload(int port, String className, byte[] newBytes) throws IOException, IllegalConnectorArgumentsException {
        HotSwapper hotSwapper = new HotSwapper(port);
        hotSwapper.reload(className, newBytes);
    }
}
