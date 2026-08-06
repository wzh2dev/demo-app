package com.demo.app.net;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求封装。
 */
public class HttpRequest {
    private HttpMethod method = HttpMethod.GET;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private String body;
    private int timeoutMs = 15000;

    public HttpRequest() {
    }

    public HttpRequest(String url) {
        this.url = url;
    }

    public static Builder builder() {
        return new Builder();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers == null ? new HashMap<>() : headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params == null ? new HashMap<>() : params;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public String buildUrlWithParams() {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (!url.contains("?")) {
            sb.append("?");
        } else if (!url.endsWith("&") && !url.endsWith("?")) {
            sb.append("&");
        }
        boolean first = true;
        for (Map.Entry<String, String> e : params.entrySet()) {
            if (!first) {
                sb.append("&");
            }
            sb.append(urlEncode(e.getKey())).append("=").append(urlEncode(e.getValue()));
            first = false;
        }
        return sb.toString();
    }

    private static String urlEncode(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
                    || (c >= '0' && c <= '9')
                    || c == '-' || c == '_' || c == '.' || c == '~') {
                sb.append(c);
            } else if (c == ' ') {
                sb.append('+');
            } else {
                sb.append('%');
                String hex = Integer.toHexString(c & 0xff).toUpperCase();
                if (hex.length() < 2) {
                    sb.append('0');
                }
                sb.append(hex);
            }
        }
        return sb.toString();
    }

    public static class Builder {
        private final HttpRequest request = new HttpRequest();

        public Builder method(HttpMethod method) {
            request.method = method;
            return this;
        }

        public Builder url(String url) {
            request.url = url;
            return this;
        }

        public Builder header(String key, String value) {
            request.headers.put(key, value);
            return this;
        }

        public Builder param(String key, String value) {
            request.params.put(key, value);
            return this;
        }

        public Builder body(String body) {
            request.body = body;
            return this;
        }

        public Builder timeout(int timeoutMs) {
            request.timeoutMs = timeoutMs;
            return this;
        }

        public Builder auth(String token) {
            request.headers.put("Authorization", "Bearer " + token);
            return this;
        }

        public Builder json() {
            request.headers.put("Content-Type", "application/json");
            return this;
        }

        public HttpRequest build() {
            return request;
        }
    }
}
