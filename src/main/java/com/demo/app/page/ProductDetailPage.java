package com.demo.app.page;

import com.demo.app.adapter.ProductAdapter;
import com.demo.app.adapter.View;
import com.demo.app.api.ApiService;
import com.demo.app.model.Product;
import com.demo.app.net.ApiResponse;
import com.demo.app.net.HttpClient;

/**
 * 商品详情页。
 */
public class ProductDetailPage {

    private final ApiService apiService;
    private final View rootView;
    private final ProductAdapter.TextView nameView;
    private final ProductAdapter.TextView subtitleView;
    private final ProductAdapter.TextView priceView;
    private final ProductAdapter.TextView originalPriceView;
    private final ProductAdapter.TextView descriptionView;
    private final ProductAdapter.TextView salesView;
    private final ProductAdapter.TextView stockView;
    private final ProductAdapter.TextView brandView;
    private Product current;
    private Long productId;

    public ProductDetailPage(View rootView, Long productId) {
        this.rootView = rootView;
        this.productId = productId;
        HttpClient client = new HttpClient().setBaseUrl("https://api.demo.com");
        this.apiService = new ApiService(client);
        this.nameView = new ProductAdapter.TextView();
        this.subtitleView = new ProductAdapter.TextView();
        this.priceView = new ProductAdapter.TextView();
        this.originalPriceView = new ProductAdapter.TextView();
        this.descriptionView = new ProductAdapter.TextView();
        this.salesView = new ProductAdapter.TextView();
        this.stockView = new ProductAdapter.TextView();
        this.brandView = new ProductAdapter.TextView();
    }

    /**
     * 加载商品详情。
     */
    public void loadDetail() {
        if (productId == null) {
            return;
        }
        new Thread(() -> {
            ApiResponse<Product> resp = apiService.getProduct(productId);
            if (resp.isSuccess() && resp.getData() != null) {
                current = resp.getData();
                render();
            }
        }).start();
    }

    private void render() {
        if (current == null) {
            return;
        }
        nameView.setText(current.getName());
        subtitleView.setText(current.getSubtitle());
        priceView.setText(formatPrice(current.getPrice()));
        originalPriceView.setText(formatPrice(current.getOriginalPrice()));
        if (current.getOriginalPrice() > current.getPrice()) {
            originalPriceView.setVisible(true);
        } else {
            originalPriceView.setVisible(false);
        }
        descriptionView.setText(current.getDescription());
        salesView.setText("已售 " + current.getSales());
        stockView.setText("库存 " + current.getStock());
        brandView.setText(current.getBrand() == null ? "" : current.getBrand());
    }

    private String formatPrice(double price) {
        if (price == (long) price) {
            return "¥" + (long) price;
        }
        return "¥" + String.format("%.2f", price);
    }

    /**
     * 加入购物车。
     */
    public void addToCart(int quantity) {
        if (current == null || current.getId() == null) {
            return;
        }
        new Thread(() -> apiService.addToCart(current.getId(), quantity)).start();
    }

    /**
     * 立即购买。
     */
    public void buyNow(int quantity, Long addressId) {
        if (current == null || current.getId() == null) {
            return;
        }
        // 简化：先加入购物车再创建订单
        new Thread(() -> {
            apiService.addToCart(current.getId(), quantity);
            apiService.createOrder(addressId, null);
        }).start();
    }

    public Product getCurrent() {
        return current;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
