package io.kimmking.rpcfx.cmmon;

import io.kimmking.rpcfx.api.RpcInvoker;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InvokerFactory {
    private static final ConcurrentHashMap<Class<?>, RpcInvoker> INVOKER_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong CLASS_COUNTER = new AtomicLong(0);

    public static RpcInvoker getInvoker(Class<?> clazz) throws Exception {
        RpcInvoker methodInvoker = INVOKER_MAP.get(clazz);
        if (methodInvoker != null) {
            return methodInvoker;
        }

        String className = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        Method[] methods = clazz.getDeclaredMethods();
        CtClass invokerClass = pool.makeClass(RpcInvoker.class.getName() + CLASS_COUNTER.getAndIncrement(), pool.getCtClass("io.kimmking.rpcfx.api.RpcInvoker"));
        StringBuilder methodStr = new StringBuilder("public Object invoke(Object service, String method,Object[] params){");

        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            String methodName = method.getName();
            methodStr.append("if(method.equals(\"").append(methodName).append("\"))");
            if (returnType.getName().equals("void")) {
                methodStr.append("{((").append(className).append(")service).").append(methodName).append("(");
            } else {
                methodStr.append("return ((").append(className).append(")service).").append(methodName).append("(");
            }
            int paramCount = method.getParameterCount();
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int j = 0; j < paramCount; j++) {
                Class<?> cls = parameterTypes[j];
                methodStr.append("(").append(cls.getName()).append(")params[").append(j).append("]");
                if ((j + 1) < paramCount)
                    methodStr.append(",");
            }
            methodStr.append(");");
            if (returnType.getName().equals("void")) {
                methodStr.append("return null;");
                methodStr.append("}");
            }
        }

        methodStr.append("return null;}");
        invokerClass.addMethod(CtNewMethod.make(methodStr.toString(), invokerClass));
        methodInvoker = (RpcInvoker) invokerClass.toClass().newInstance();
        INVOKER_MAP.putIfAbsent(clazz, methodInvoker);
        return methodInvoker;
    }
}
