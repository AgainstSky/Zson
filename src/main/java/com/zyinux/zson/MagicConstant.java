package com.zyinux.zson;

/**
 * MagicConstant
 *
 * @author zyinux
 * @Desc 听说不要在项目中写魔法值，so？
 * @date 2020/12/19
 */
public class MagicConstant {

    /**
     * 魔法值，公式S(size) = 4n - 1 中的 4 也就是正常情况下一个键值对  >   K : V ,  < 共4位组成
     */
    public static final int OBJECT_KEY_VALUE_SIZE = 4;

    /**
     * 魔法值，公式S(size) = 2n - 1 中的 2 也就是正常情况下一个数组内部一位由 >  V ,  <共2位组成
     */
    public static final int ARRAY_VALUE_SIZE = 2;
}
