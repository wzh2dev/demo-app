package com.demo.app.net;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP 客户端，模拟 OkHttp 行为。
 */
public class HttpClient {

    private final List<Interceptor> interceptors = new ArrayList<>();
    private String baseUrl;
    private int defaultTimeout = 15000;
    private boolean retryOnFailure = true;
    private int maxRetries = 3;

    public HttpClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpClient addInterceptor(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
        return this;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpClient setDefaultTimeout(int ms) {
        this.defaultTimeout = ms;
        return this;
    }

    public int getDefaultTimeout() {
        return defaultTimeout;
    }

    public HttpClient setRetryOnFailure(boolean retry) {
        this.retryOnFailure = retry;
        return this;
    }

    public boolean isRetryOnFailure() {
        return retryOnFailure;
    }

    public HttpClient setMaxRetries(int max) {
        this.maxRetries = max;
        return this;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * 同步执行请求。
     */
    public HttpResponse execute(HttpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        HttpRequest processed = applyInterceptors(request);
        return doExecute(processed);
    }

    /**
     * 异步执行请求。
     */
    public void enqueue(HttpRequest request, final Callback callback) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("callback is null");
        }
        new Thread(() -> {
            try {
                HttpResponse response = execute(request);
                callback.onResponse(response);
            } catch (Exception e) {
                callback.onFailure(e);
            }
        }).start();
    }

    private HttpRequest applyInterceptors(HttpRequest request) {
        HttpRequest current = request;
        for (Interceptor interceptor : interceptors) {
            if (interceptor != null) {
                Interceptor.Chain chain = new SimpleChain(current);
                current = interceptor.intercept(chain);
                if (current == null) {
                    current = request;
                }
            }
        }
        return current;
    }

    private HttpResponse doExecute(HttpRequest request) {
        if (baseUrl != null && request.getUrl() != null
                && !request.getUrl().startsWith("http")) {
            String fullUrl = baseUrl + (request.getUrl().startsWith("/")
                    ? request.getUrl() : "/" + request.getUrl());
            request.setUrl(fullUrl);
        }
        long start = System.currentTimeMillis();
        int attempt = 0;
        Exception lastError = null;
        while (attempt <= (retryOnFailure ? maxRetries : 0)) {
            try {
                // 模拟请求：返回空响应
                int code = 200;
                String body = "{}";
                long duration = System.currentTimeMillis() - start;
                return new HttpResponse(code, body, duration, new java.util.HashMap<>());
            } catch (Exception e) {
                lastError = e;
                attempt++;
            }
        }
        throw lastError instanceof RuntimeException
                ? (RuntimeException) lastError
                : new RuntimeException(lastError);
    }

    private static class SimpleChain implements Interceptor.Chain {
        private final HttpRequest request;

        SimpleChain(HttpRequest request) {
            this.request = request;
        }

        @Override
        public HttpRequest request() {
            return request;
        }

        @Override
        public HttpRequest proceed(HttpRequest request) {
            return request;
        }
    }

    /**
     * 异步回调。
     */
    public interface Callback {
        void onResponse(HttpResponse response);
        void onFailure(Exception e);
    }
}
