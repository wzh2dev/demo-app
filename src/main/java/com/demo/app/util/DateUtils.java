package com.demo.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间格式化工具。
 */
public final class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String FRIENDLY_FORMAT = "MM月dd日 HH:mm";

    private DateUtils() {
    }

    public static String format(Long timestamp) {
        return format(timestamp, DEFAULT_FORMAT);
    }

    public static String format(Long timestamp, String pattern) {
        if (timestamp == null || timestamp <= 0) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return sdf.format(new Date(timestamp));
        } catch (Exception e) {
            return "";
        }
    }

    public static Long parse(String dateStr, String pattern) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static String now() {
        return format(System.currentTimeMillis());
    }

    public static String today() {
        return format(System.currentTimeMillis(), DATE_FORMAT);
    }

    /**
     * 友好时间显示，例如 "刚刚"、"5 分钟前"、"昨天"。
     */
    public static String friendly(Long timestamp) {
        if (timestamp == null || timestamp <= 0) {
            return "";
        }
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        if (diff < 0) {
            return format(timestamp, FRIENDLY_FORMAT);
        }
        if (diff < 60_000L) {
            return "刚刚";
        }
        if (diff < 3_600_000L) {
            return (diff / 60_000L) + " 分钟前";
        }
        if (diff < 86_400_000L) {
            return (diff / 3_600_000L) + " 小时前";
        }
        if (diff < 2L * 86_400_000L) {
            return "昨天 " + format(timestamp, TIME_FORMAT);
        }
        if (diff < 7L * 86_400_000L) {
            return (diff / 86_400_000L) + " 天前";
        }
        return format(timestamp, FRIENDLY_FORMAT);
    }

    public static long startOfDay(Long timestamp) {
        if (timestamp == null) {
            return 0L;
        }
        return (timestamp / 86_400_000L) * 86_400_000L;
    }

    public static long endOfDay(Long timestamp) {
        if (timestamp == null) {
            return 0L;
        }
        return startOfDay(timestamp) + 86_400_000L - 1;
    }
}
