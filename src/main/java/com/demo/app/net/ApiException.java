package com.demo.app.net;

/**
 * 网络错误。
 */
public class ApiException extends RuntimeException {
    private final int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ApiException of(int code, String message) {
        return new ApiException(code, message);
    }

    public static ApiException networkError() {
        return new ApiException(-1, "network error");
    }

    public static ApiException timeout() {
        return new ApiException(-2, "request timeout");
    }

    public static ApiException unauthorized() {
        return new ApiException(401, "unauthorized");
    }

    public static ApiException forbidden() {
        return new ApiException(403, "forbidden");
    }

    public static ApiException notFound() {
        return new ApiException(404, "not found");
    }

    public static ApiException serverError() {
        return new ApiException(500, "server error");
    }
}
