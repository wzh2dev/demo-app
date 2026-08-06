package com.demo.app.page;

import com.demo.app.adapter.ProductAdapter;
import com.demo.app.api.ApiService;
import com.demo.app.model.Product;
import com.demo.app.net.ApiResponse;
import com.demo.app.net.HttpClient;
import com.demo.app.adapter.View;

import java.util.List;

/**
 * 首页（商品列表）。
 */
public class HomePage {

    private final ApiService apiService;
    private final ProductAdapter adapter;
    private final View rootView;
    private final ProductAdapter.TextView titleView;
    private Long categoryId;
    private String sort;
    private int pageNumber = 1;
    private int pageSize = 20;
    private boolean loading = false;
    private boolean hasMore = true;

    public HomePage(View rootView) {
        this.rootView = rootView;
        this.adapter = new ProductAdapter();
        HttpClient client = new HttpClient().setBaseUrl("https://api.demo.com");
        this.apiService = new ApiService(client);
        this.titleView = new ProductAdapter.TextView();
        this.titleView.setText("精选好物");
    }

    /**
     * 加载首页数据。
     */
    public void loadFirstPage() {
        if (loading) {
            return;
        }
        loading = true;
        pageNumber = 1;
        hasMore = true;
        new Thread(() -> {
            try {
                ApiResponse<List<Product>> resp = apiService.listProducts(
                        categoryId, sort, pageNumber, pageSize);
                if (resp.isSuccess()) {
                    adapter.setItems(resp.getData());
                }
            } finally {
                loading = false;
            }
        }).start();
    }

    /**
     * 加载下一页。
     */
    public void loadNextPage() {
        if (loading || !hasMore) {
            return;
        }
        loading = true;
        new Thread(() -> {
            try {
                ApiResponse<List<Product>> resp = apiService.listProducts(
                        categoryId, sort, pageNumber + 1, pageSize);
                if (resp.isSuccess()) {
                    List<Product> data = resp.getData();
                    if (data == null || data.isEmpty()) {
                        hasMore = false;
                    } else {
                        adapter.appendItems(data);
                        pageNumber++;
                    }
                }
            } finally {
                loading = false;
            }
        }).start();
    }

    /**
     * 切换分类。
     */
    public void switchCategory(Long categoryId) {
        this.categoryId = categoryId;
        loadFirstPage();
    }

    /**
     * 切换排序。
     */
    public void switchSort(String sort) {
        this.sort = sort;
        loadFirstPage();
    }

    public void setOnProductClickListener(ProductAdapter.OnProductClickListener listener) {
        adapter.setOnProductClickListener(listener);
    }

    public ProductAdapter getAdapter() {
        return adapter;
    }

    public View getRootView() {
        return rootView;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasMore() {
        return hasMore;
    }
}
