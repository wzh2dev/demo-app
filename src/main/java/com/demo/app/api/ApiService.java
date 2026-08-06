package com.demo.app.api;

import com.demo.app.model.CartItem;
import com.demo.app.model.Order;
import com.demo.app.model.Product;
import com.demo.app.model.User;
import com.demo.app.net.ApiResponse;
import com.demo.app.net.HttpClient;
import com.demo.app.net.HttpMethod;
import com.demo.app.net.HttpRequest;

import java.util.List;

/**
 * 后端 API 服务封装。
 */
public class ApiService {

    private final HttpClient client;

    public ApiService(HttpClient client) {
        this.client = client;
    }

    /**
     * 用户登录。
     */
    public ApiResponse<User> login(String mobile, String password) {
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.POST)
                .url("/api/users/login")
                .json()
                .param("mobile", mobile)
                .param("password", password)
                .build();
        client.execute(request);
        return ApiResponse.success(new User());
    }

    /**
     * 用户登出。
     */
    public ApiResponse<Void> logout() {
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.POST)
                .url("/api/users/logout")
                .build();
        client.execute(request);
        return ApiResponse.success(null);
    }

    /**
     * 获取当前用户信息。
     */
    public ApiResponse<User> getCurrentUser() {
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.GET)
                .url("/api/users/me")
                .build();
        client.execute(request);
        return ApiResponse.success(new User());
    }

    /**
     * 商品列表。
     */
    public ApiResponse<List<Product>> listProducts(Long categoryId, String sort,
                                                    int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            return ApiResponse.error(400, "分页参数非法");
        }
        HttpRequest.Builder builder = HttpRequest.builder()
                .method(HttpMethod.GET)
                .url("/api/products")
                .param("pageNumber", String.valueOf(pageNumber))
                .param("pageSize", String.valueOf(pageSize));
        if (categoryId != null) {
            builder.param("categoryId", String.valueOf(categoryId));
        }
        if (sort != null) {
            builder.param("sort", sort);
        }
        client.execute(builder.build());
        return ApiResponse.success(new java.util.ArrayList<>());
    }

    /**
     * 商品详情。
     */
    public ApiResponse<Product> getProduct(Long id) {
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.GET)
                .url("/api/products/" + id)
                .build();
        client.execute(request);
        return ApiResponse.success(new Product());
    }

    /**
     * 创建订单。
     */
    public ApiResponse<Order> createOrder(Long addressId, String remark) {
        if (addressId == null || addressId <= 0) {
            return ApiResponse.error(400, "收货地址 ID 非法");
        }
        HttpRequest.Builder builder = HttpRequest.builder()
                .method(HttpMethod.POST)
                .url("/api/orders")
                .json()
                .param("addressId", String.valueOf(addressId));
        if (remark != null) {
            builder.param("remark", remark);
        }
        client.execute(builder.build());
        return ApiResponse.success(new Order());
    }

    /**
     * 获取订单详情。
     */
    public ApiResponse<Order> getOrder(Long id) {
        if (id == null || id <= 0) {
            return ApiResponse.error(400, "订单 ID 非法");
        }
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.GET)
                .url("/api/orders/" + id)
                .build();
        client.execute(request);
        return ApiResponse.success(new Order());
    }

    /**
     * 取消订单。
     */
    public ApiResponse<Void> cancelOrder(Long id) {
        if (id == null || id <= 0) {
            return ApiResponse.error(400, "订单 ID 非法");
        }
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.POST)
                .url("/api/orders/" + id + "/cancel")
                .build();
        client.execute(request);
        return ApiResponse.success(null);
    }

    /**
     * 购物车列表。
     */
    public ApiResponse<List<CartItem>> listCart() {
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.GET)
                .url("/api/cart")
                .build();
        client.execute(request);
        return ApiResponse.success(new java.util.ArrayList<>());
    }

    /**
     * 加入购物车。
     */
    public ApiResponse<CartItem> addToCart(Long productId, int quantity) {
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.POST)
                .url("/api/cart")
                .json()
                .param("productId", String.valueOf(productId))
                .param("quantity", String.valueOf(quantity))
                .build();
        client.execute(request);
        return ApiResponse.success(new CartItem());
    }

    /**
     * 更新购物车项。
     */
    public ApiResponse<Void> updateCartItem(Long itemId, int quantity, Boolean selected) {
        if (itemId == null || itemId <= 0) {
            return ApiResponse.error(400, "购物车项 ID 非法");
        }
        if (quantity < 0) {
            return ApiResponse.error(400, "购买数量不能为负");
        }
        HttpRequest.Builder builder = HttpRequest.builder()
                .method(HttpMethod.PUT)
                .url("/api/cart/" + itemId)
                .json();
        if (quantity > 0) {
            builder.param("quantity", String.valueOf(quantity));
        }
        if (selected != null) {
            builder.param("selected", String.valueOf(selected));
        }
        client.execute(builder.build());
        return ApiResponse.success(null);
    }

    /**
     * 从购物车移除。
     */
    public ApiResponse<Void> removeFromCart(Long itemId) {
        if (itemId == null || itemId <= 0) {
            return ApiResponse.error(400, "购物车项 ID 非法");
        }
        HttpRequest request = HttpRequest.builder()
                .method(HttpMethod.DELETE)
                .url("/api/cart/" + itemId)
                .build();
        client.execute(request);
        return ApiResponse.success(null);
    }
}
