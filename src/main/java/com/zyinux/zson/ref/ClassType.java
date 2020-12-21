package com.zyinux.zson.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * ClassType
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/21
 */
public class ClassType {

    /**
     * 获取当前类型的所有成员
     *
     * @param clzz
     * @return
     */
    public Set<Field> getAllField(Class clzz) {
        Set<Field> fields = new HashSet<>();
        fields.addAll(Arrays.asList(clzz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clzz.getFields()));
        return fields;
    }

    /**
     * 获取所有的 public 的 set方法。
     *
     * @param clzz
     * @return
     */
    public List<Method> getPublicSetMethods(Class clzz) {
        //获取当前类和父类所有的public方法
        Method[] methods = clzz.getMethods();
        List<Method> result = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                result.add(method);
            }
        }
        return result;
    }

    /**
     * 获取所有的 public 的 set方法。
     *
     * @param clzz
     * @return
     */
    public Map<String, Method> getPublicSetMethodsMap(Class clzz) {
        //获取当前类和父类所有的public方法
        Method[] methods = clzz.getMethods();
        Map<String, Method> result = new HashMap<>(methods.length);
        for (Method method : methods) {
            //获取所有set开头且只有一个参数的public方法。
            if (method.getName().startsWith("set") && method.getParameterCount() == 1) {
                result.put(generatorKeyByMethod(method), method);
            }
        }
        return result;
    }

    /**
     * 获取方法的key
     * @param method
     * @return
     */
    private String generatorKeyByMethod(Method method) {
        String methodName = method.getName();
//        String name = method.getParameters()[0].getType().getName();
        return methodName;
    }

}
