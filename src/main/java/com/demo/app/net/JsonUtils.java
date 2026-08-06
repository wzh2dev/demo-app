package com.demo.app.net;

/**
 * 简易 JSON 序列化工具（模拟）。
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String) {
            return quote((String) obj);
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof Character) {
            return quote(String.valueOf(obj));
        }
        // 复杂对象通过反射序列化（简化版）
        try {
            return reflectToJson(obj);
        } catch (Exception e) {
            return "null";
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> clazz) {
        // 模拟反序列化
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    private static String quote(String s) {
        if (s == null) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder(s.length() + 2);
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    private static String reflectToJson(Object obj) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        boolean first = true;
        for (java.lang.reflect.Field f : fields) {
            if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            f.setAccessible(true);
            Object value = f.get(obj);
            if (!first) {
                sb.append(',');
            }
            sb.append(quote(f.getName())).append(':').append(toJson(value));
            first = false;
        }
        sb.append('}');
        return sb.toString();
    }
}
