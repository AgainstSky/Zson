package com.zyinux.zson.ref;

import com.zyinux.zson.bean.Test1Bean;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassTypeTest
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/21
 */
class ClassTypeTest {

    @Test
    public void test(){
        ClassType classType=new ClassType();
        Map<String, Method> publicSetMethodsMap = classType.getPublicSetMethodsMap(Test1Bean.class);
        System.out.println(publicSetMethodsMap);
    }
}