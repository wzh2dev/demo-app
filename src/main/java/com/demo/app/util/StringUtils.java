package com.demo.app.util;

/**
 * 字符串工具。
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String trim(String s) {
        return s == null ? null : s.trim();
    }

    public static String defaultIfBlank(String s, String defaultValue) {
        return isBlank(s) ? defaultValue : s;
    }

    public static String truncate(String s, int maxLen) {
        if (s == null || s.length() <= maxLen) {
            return s;
        }
        return s.substring(0, maxLen);
    }

    public static String truncateWithEllipsis(String s, int maxLen) {
        if (s == null || s.length() <= maxLen) {
            return s;
        }
        if (maxLen <= 3) {
            return "...".substring(0, maxLen);
        }
        return s.substring(0, maxLen - 3) + "...";
    }

    public static String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 7) {
            return mobile;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
    }

    public static String maskEmail(String email) {
        if (email == null) {
            return null;
        }
        int at = email.indexOf('@');
        if (at <= 1) {
            return email;
        }
        return email.substring(0, 1) + "***" + email.substring(at);
    }

    public static boolean isMobile(String s) {
        return s != null && s.matches("^1[3-9]\\d{9}$");
    }

    public static boolean isEmail(String s) {
        return s != null && s.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isUrl(String s) {
        return s != null && s.startsWith("http://") || s != null && s.startsWith("https://");
    }

    public static String capitalize(String s) {
        if (isEmpty(s)) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String reverse(String s) {
        if (s == null) {
            return null;
        }
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equalsIgnoreCase(b);
    }
}
