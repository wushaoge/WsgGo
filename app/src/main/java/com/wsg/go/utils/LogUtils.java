package com.wsg.go.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2016-10-11 11:25
 * 描    述：打印日志工具类
 * 修订历史：
 * ================================================
 */
public class LogUtils {
    public static String customTagPrefix = "";
    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;
    public static CustomLogger customLogger;

    private LogUtils() {
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        tag = TextUtils.isEmpty(customTagPrefix)?tag:customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if(allowD) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.d(tag, content);
            } else {
                Log.d(tag, content);
            }

        }
    }

    public static void d(String content, Throwable tr) {
        if(allowD) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.d(tag, content, tr);
            } else {
                Log.d(tag, content, tr);
            }

        }
    }

    public static void e(String content) {
        if(allowE) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.e(tag, content);
            } else {
                Log.e(tag, content);
            }

        }
    }

    public static void e(String content, Throwable tr) {
        if(allowE) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.e(tag, content, tr);
            } else {
                Log.e(tag, content, tr);
            }

        }
    }

    public static void i(String content) {
        if(allowI) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.i(tag, content);
            } else {
                Log.i(tag, content);
            }

        }
    }

    public static void i(String content, Throwable tr) {
        if(allowI) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.i(tag, content, tr);
            } else {
                Log.i(tag, content, tr);
            }

        }
    }

    public static void v(String content) {
        if(allowV) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.v(tag, content);
            } else {
                Log.v(tag, content);
            }

        }
    }

    public static void v(String content, Throwable tr) {
        if(allowV) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.v(tag, content, tr);
            } else {
                Log.v(tag, content, tr);
            }

        }
    }

    public static void w(String content) {
        if(allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.w(tag, content);
            } else {
                Log.w(tag, content);
            }

        }
    }

    public static void w(String content, Throwable tr) {
        if(allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.w(tag, content, tr);
            } else {
                Log.w(tag, content, tr);
            }

        }
    }

    public static void w(Throwable tr) {
        if(allowW) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.w(tag, tr);
            } else {
                Log.w(tag, tr);
            }

        }
    }

    public static void wtf(String content) {
        if(allowWtf) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.wtf(tag, content);
            } else {
                Log.wtf(tag, content);
            }

        }
    }

    public static void wtf(String content, Throwable tr) {
        if(allowWtf) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.wtf(tag, content, tr);
            } else {
                Log.wtf(tag, content, tr);
            }

        }
    }

    public static void wtf(Throwable tr) {
        if(allowWtf) {
            StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
            String tag = generateTag(caller);
            if(customLogger != null) {
                customLogger.wtf(tag, tr);
            } else {
                Log.wtf(tag, tr);
            }

        }
    }

    public interface CustomLogger {
        void d(String var1, String var2);

        void d(String var1, String var2, Throwable var3);

        void e(String var1, String var2);

        void e(String var1, String var2, Throwable var3);

        void i(String var1, String var2);

        void i(String var1, String var2, Throwable var3);

        void v(String var1, String var2);

        void v(String var1, String var2, Throwable var3);

        void w(String var1, String var2);

        void w(String var1, String var2, Throwable var3);

        void w(String var1, Throwable var2);

        void wtf(String var1, String var2);

        void wtf(String var1, String var2, Throwable var3);

        void wtf(String var1, Throwable var2);
    }
}
