package com.wsg.go.utils;

import android.content.Context;

/**
 * ================================================
 * 作    者：wushaoge
 * 版    本：1.0
 * 创建日期：2018-12-13 16:42
 * 描    述：SP操作类（可替换为 MMKV）
 * 修订历史：
 * ================================================
 */
public class KvUtils {

    public static void putString(Context context, String key, String value) {
        SPUtils.putString(context, key, value);
    }

    public static String getString(Context context, String key) {
        return SPUtils.getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        return SPUtils.getString(context, key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        SPUtils.putInt(context, key, value);
    }

    public static int getInt(Context context, String key) {
        return SPUtils.getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return SPUtils.getInt(context, key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        SPUtils.putLong(context, key, value);
    }

    public static long getLong(Context context, String key) {
        return SPUtils.getLong(context, key, 0L);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return SPUtils.getLong(context, key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SPUtils.putBoolean(context, key, value);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return SPUtils.getBoolean(context, key, defaultValue);
    }

    public static void clear(Context context) {
        SPUtils.clear(context);
    }
}
