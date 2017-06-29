package com.Polynt.utils;

import android.util.Log;

/**
 * Created by Alex on 9/19/2014.
 */
public class LogUtil {

    // set level to DISABLE to disable log
    public static final int DISABLED      = 0;
    public static final int ERROR         = 1;
    public static final int WARNING       = 2;
    public static final int INFO          = 3;
    public static final int DEBUG         = 4;
    public static final int VERBOSE       = 5;

    private static int mLevel = VERBOSE;
    public static void setLevel(int level) {
        mLevel = level;
    }
    public static int getLevel() {
        return mLevel;
    }

    private static final String TAG = "Polynt";

    // log without prefix
    public static void v(String content) {
        v(null, content);
    }
    public static void d(String content) {
        d(null, content);
    }
    public static void i(String content) {
        i(null, content);
    }
    public static void w(String content) {
        w(null, content);
    }
    public static void e(String content) {
        e(null, content);
    }

    // log with prefix
    public static void v(String prefix, String content) {
        if (mLevel >= VERBOSE) {
            Log.v(TAG, addPrefix(prefix, content));
        }
    }
    public static void d(String prefix, String content) {
        if (mLevel >= DEBUG) {
            Log.d(TAG, addPrefix(prefix, content));
        }
    }
    public static void i(String prefix, String content) {
        if (mLevel >= INFO) {
            Log.i(TAG, addPrefix(prefix, content));
        }
    }
    public static void w(String prefix, String content) {
        if (mLevel >= WARNING) {
            Log.w(TAG, addPrefix(prefix, content));
        }
    }
    public static void e(String prefix, String content) {
        if (mLevel >= ERROR) {
            Log.e(TAG, addPrefix(prefix, content));
        }
    }

    private static String addPrefix(String prefix, String content) {
        if (prefix != null && prefix.length() > 0) {
            content = "[" + prefix + "] " + content;
        }
        return content;
    }

}
