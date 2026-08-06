package com.demo.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 简化封装。
 */
public final class SpUtils {

    private static final String DEFAULT_NAME = "demo_app_sp";
    private static SharedPreferences sp;

    private SpUtils() {
    }

    public static void init(Context context) {
        if (sp == null && context != null) {
            sp = context.getApplicationContext()
                    .getSharedPreferences(DEFAULT_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void putString(String key, String value) {
        ensureInitialized();
        sp.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        ensureInitialized();
        return sp.getString(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static void putInt(String key, int value) {
        ensureInitialized();
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        ensureInitialized();
        return sp.getInt(key, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static void putLong(String key, long value) {
        ensureInitialized();
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        ensureInitialized();
        return sp.getLong(key, defaultValue);
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static void putBoolean(String key, boolean value) {
        ensureInitialized();
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        ensureInitialized();
        return sp.getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static void putFloat(String key, float value) {
        ensureInitialized();
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        ensureInitialized();
        return sp.getFloat(key, defaultValue);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public static boolean contains(String key) {
        ensureInitialized();
        return sp.contains(key);
    }

    public static void remove(String key) {
        ensureInitialized();
        sp.edit().remove(key).apply();
    }

    public static void clear() {
        ensureInitialized();
        sp.edit().clear().apply();
    }

    private static void ensureInitialized() {
        if (sp == null) {
            throw new IllegalStateException("SpUtils not initialized, call init(context) first");
        }
    }
}
