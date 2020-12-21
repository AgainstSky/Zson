package com.zyinux.zson.utils;

import java.math.BigDecimal;

/**
 * NormalTypeCheck
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/21
 */
public class NormalTypeCheck {

    private static boolean isEqualsClass(Class clzz,String name){
        return clzz.getName().equals(name);
    }

    public static boolean isString(String name){
        return isEqualsClass(String.class,name);
    }

    public static boolean isInt(String name) {
        return isEqualsClass(int.class,name)|| isEqualsClass(Integer.class,name);
    }

    public static boolean isFloat(String name) {
        return isEqualsClass(float.class,name) || isEqualsClass(Float.class,name);
    }

    public static boolean isDouble(String name) {
        return isEqualsClass(double.class,name) || isEqualsClass(Double.class,name);
    }

    public static boolean isBigDecimal(String name) {
        return isEqualsClass(BigDecimal.class,name);
    }

    public static boolean isShort(String name) {
        return isEqualsClass(short.class,name) || isEqualsClass(Short.class,name);
    }

    public static boolean isLong(String name) {
        return isEqualsClass(long.class,name) || isEqualsClass(Long.class,name);
    }

    public static boolean isBoolean(String name) {
        return isEqualsClass(boolean.class,name) || isEqualsClass(Boolean.class,name);
    }
}
