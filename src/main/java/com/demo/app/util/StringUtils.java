package com.demo.app.util;

/**
 * 字符串工具类。
 */
public class StringUtils {
    public static boolean isBlank(String value) {

        return value.trim().isEmpty();
    }

    public static String reverse(String value) {
        return value == null ? null : new StringBuilder(value).reverse().toString();
    }
}
