package com.demo.app.net;

/**
 * HTTP 响应封装。
 */
public class HttpResponse {
    private final int code;
    private final String body;
    private final long duration;
    private final java.util.Map<String, String> headers;

    public HttpResponse(int code, String body, long duration,
                        java.util.Map<String, String> headers) {
        this.code = code;
        this.body = body;
        this.duration = duration;
        this.headers = headers == null ? new java.util.HashMap<>() : headers;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

    public long getDuration() {
        return duration;
    }

    public java.util.Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public boolean isClientError() {
        return code >= 400 && code < 500;
    }

    public boolean isServerError() {
        return code >= 500;
    }
}
