package com.zyinux.zson.log;

/**
 * Log
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
public class Log {

    public static final String TAG = "Zson.";

    public static final int LEVEL_DEBUG = 0, LEVEL_INFO = 1, LEVEL_ERROR = 2, NONA = 999;

    public static int currentLevel = NONA;

    public static void e(Object msg) {
        if (currentLevel <= LEVEL_ERROR) {
            println("ERROR: " + msg);
        }
    }

    public static void debug(Object msg) {
        if (currentLevel <= LEVEL_DEBUG) {
            println("DEBUG: " + msg);
        }
    }

    public static void info(Object msg) {
        if (currentLevel <= LEVEL_INFO) {
            println("INFO: " + msg);
        }
    }

    private static void println(Object msg) {
        System.out.println(TAG + msg);
    }
}
