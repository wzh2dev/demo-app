package com.demo.app.page;

import com.demo.app.adapter.CartAdapter;
import com.demo.app.adapter.ProductAdapter;
import com.demo.app.adapter.View;
import com.demo.app.api.ApiService;
import com.demo.app.model.CartItem;
import com.demo.app.net.ApiResponse;
import com.demo.app.net.HttpClient;

import java.util.List;

/**
 * 购物车页。
 */
public class CartPage {

    private final ApiService apiService;
    private final CartAdapter adapter;
    private final View rootView;
    private final ProductAdapter.TextView totalPriceView;
    private final ProductAdapter.TextView totalCountView;
    private final ProductAdapter.TextView checkoutButton;
    private final ProductAdapter.TextView selectAllCheckBox;
    private boolean selectAll = true;

    public CartPage(View rootView) {
        this.rootView = rootView;
        this.adapter = new CartAdapter();
        HttpClient client = new HttpClient().setBaseUrl("https://api.demo.com");
        this.apiService = new ApiService(client);
        this.totalPriceView = new ProductAdapter.TextView();
        this.totalCountView = new ProductAdapter.TextView();
        this.checkoutButton = new ProductAdapter.TextView();
        this.selectAllCheckBox = new ProductAdapter.TextView();
        this.checkoutButton.setText("去结算");
    }

    /**
     * 加载购物车数据。
     */
    public void loadCart() {
        new Thread(() -> {
            ApiResponse<List<CartItem>> resp = apiService.listCart();
            if (resp.isSuccess()) {
                adapter.setItems(resp.getData());
                updateFooter();
            }
        }).start();
    }

    /**
     * 切换某项选中状态。
     */
    public void toggleItem(int position) {
        adapter.toggleSelected(position);
        updateFooter();
        CartItem item = adapter.getItem(position);
        if (item != null) {
            apiService.updateCartItem(item.getId(), -1, item.isSelected());
        }
    }

    /**
     * 全选/取消全选。
     */
    public void toggleSelectAll() {
        selectAll = !selectAll;
        adapter.selectAll(selectAll);
        updateFooter();
        for (CartItem item : adapter.getItems() == null
                ? java.util.Collections.emptyList() : adapter.getItems()) {
            apiService.updateCartItem(item.getId(), -1, selectAll);
        }
    }

    /**
     * 删除选中项。
     */
    public void removeSelected() {
        int removed = adapter.removeSelected();
        if (removed > 0) {
            updateFooter();
        }
    }

    /**
     * 修改数量。
     */
    public void updateQuantity(int position, int quantity) {
        CartItem item = adapter.getItem(position);
        if (item == null || quantity < 1) {
            return;
        }
        item.setQuantity(quantity);
        updateFooter();
        apiService.updateCartItem(item.getId(), quantity, null);
    }

    private void updateFooter() {
        double total = adapter.selectedTotal();
        int count = adapter.selectedCount();
        totalPriceView.setText("合计: ¥" + String.format("%.2f", total));
        totalCountView.setText("(" + count + "件)");
        checkoutButton.setVisible(count > 0);
    }

    /**
     * 去结算。
     */
    public void checkout(Long addressId) {
        if (adapter.selectedCount() == 0) {
            return;
        }
        apiService.createOrder(addressId, null);
    }

    public CartAdapter getAdapter() {
        return adapter;
    }

    public View getRootView() {
        return rootView;
    }
}
