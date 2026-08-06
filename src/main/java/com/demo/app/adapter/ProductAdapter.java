package com.demo.app.adapter;

import com.demo.app.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表适配器（模拟 Android Adapter 行为）。
 */
public class ProductAdapter {

    private final List<Product> items = new ArrayList<>();
    private OnProductClickListener listener;

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Product> products) {
        items.clear();
        if (products != null) {
            items.addAll(products);
        }
    }

    public void appendItems(List<Product> products) {
        if (products != null) {
            items.addAll(products);
        }
    }

    public void clear() {
        items.clear();
    }

    public int getItemCount() {
        return items.size();
    }

    public Product getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    public long getItemId(int position) {
        Product p = getItem(position);
        return p == null ? -1L : (p.getId() == null ? -1L : p.getId());
    }

    /**
     * 模拟绑定 ViewHolder。
     */
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = getItem(position);
        if (product == null || holder == null) {
            return;
        }
        holder.name.setText(product.getName());
        holder.subtitle.setText(product.getSubtitle());
        holder.price.setText(formatPrice(product.getPrice()));
        holder.originalPrice.setText(formatPrice(product.getOriginalPrice()));
        holder.sales.setText("已售 " + product.getSales());
        if (product.getOriginalPrice() > product.getPrice()) {
            holder.discountTag.setText(product.discountRate() + "折");
            holder.discountTag.setVisible(true);
        } else {
            holder.discountTag.setVisible(false);
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product, position);
            }
        });
    }

    private String formatPrice(double price) {
        if (price == (long) price) {
            return "¥" + (long) price;
        }
        return "¥" + String.format("%.2f", price);
    }

    /**
     * 商品点击回调。
     */
    public interface OnProductClickListener {
        void onProductClick(Product product, int position);
    }

    /**
     * 模拟 ViewHolder。
     */
    public static class ProductViewHolder {
        public Object itemView;
        public TextView name;
        public TextView subtitle;
        public TextView price;
        public TextView originalPrice;
        public TextView sales;
        public TextView discountTag;

        public ProductViewHolder(View itemView) {
            this.itemView = itemView;
            this.name = new TextView();
            this.subtitle = new TextView();
            this.price = new TextView();
            this.originalPrice = new TextView();
            this.sales = new TextView();
            this.discountTag = new TextView();
        }
    }

    /**
     * 简易 TextView 模拟。
     */
    public static class TextView extends View {
        private String text;
        private boolean visible = true;

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public boolean isVisible() {
            return visible;
        }
    }
}
