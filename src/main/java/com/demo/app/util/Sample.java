package com.demo.app.util;

/**
 * 样例代码：演示工具类调用。
 */
public final class Sample {

    private Sample() {
    }

    public static String greet(String name) {
        if (StringUtils.isBlank(name)) {
            return "Hello, Guest!";
        }
        return "Hello, " + StringUtils.capitalize(name) + "!";
    }
}
