package com.demo.app.util;

/**
 * 日期工具类。
 */
public class DateUtils {
    public static String format(long timestamp) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
    }
}
