package com.zyinux.zson.log;

/**
 * Log
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
public class Log {

    public static final String TAG = "Zson:";

    public static final int LEVEL_DEBUG = 0, LEVEL_INFO = 1, LEVEL_ERROR = 2, NONA = 999;

    public static int currentLevel = LEVEL_DEBUG;

    public static void e(String msg) {
        if (currentLevel <= LEVEL_ERROR) {
            println(msg);
        }
    }

    public static void debug(String msg) {
        if (currentLevel <= LEVEL_DEBUG) {
            println(msg);
        }
    }

    public static void info(String msg) {
        if (currentLevel <= LEVEL_INFO) {
            println(msg);
        }
    }

    private static void println(String msg) {
        System.out.println(TAG + msg);
    }
}
