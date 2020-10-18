package com.Week_01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

  private final String path = "D:" + File.separator;

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    try {
      // 加载字节数组
      byte[] data = loadFileClass(name);
      // 将字节数组转换为class实例
      return defineClass(name, data, 0, data.length);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ClassNotFoundException();
    }
  }

  private byte[] loadFileClass(String name) throws IOException {
    String classPath = path + name + ".class";
    FileInputStream fis = new FileInputStream(classPath);
    int len = fis.available();
    byte[] data = new byte[len];
    fis.read(data);
    fis.close();
    return data;
  }

  public static void main(String[] args) throws Exception {
    MyClassLoader myClassLoader = new MyClassLoader();
    Class clazz = myClassLoader.findClass("Hello");
    Object o = clazz.newInstance();
    Method hello = clazz.getMethod("hello", null);
    hello.invoke(o, null);
  }
}
