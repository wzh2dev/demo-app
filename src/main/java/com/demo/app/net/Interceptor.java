package com.demo.app.net;

/**
 * HTTP 请求拦截器。
 */
public interface Interceptor {
    HttpRequest intercept(Chain chain);

    interface Chain {
        HttpRequest request();
        HttpRequest proceed(HttpRequest request);
    }
}
