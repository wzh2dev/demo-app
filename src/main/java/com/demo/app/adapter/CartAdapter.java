package com.demo.app.adapter;

import com.demo.app.model.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车适配器，支持选中状态切换。
 */
public class CartAdapter {

    private final List<CartItem> items = new ArrayList<>();
    private OnCartItemClickListener listener;

    public void setOnCartItemClickListener(OnCartItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<CartItem> cartItems) {
        items.clear();
        if (cartItems != null) {
            items.addAll(cartItems);
        }
    }

    public void addItem(CartItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
        }
    }

    public void clear() {
        items.clear();
    }

    public int getItemCount() {
        return items.size();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public CartItem getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    /**
     * 切换某项的选中状态。
     */
    public void toggleSelected(int position) {
        CartItem item = getItem(position);
        if (item != null) {
            item.setSelected(!item.isSelected());
        }
    }

    /**
     * 全选 / 全不选。
     */
    public void selectAll(boolean selected) {
        for (CartItem item : items) {
            item.setSelected(selected);
        }
    }

    /**
     * 已选中项的小计合计。
     */
    public double selectedTotal() {
        double total = 0d;
        for (CartItem item : items) {
            if (item.isSelected()) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    /**
     * 已选中项数量。
     */
    public int selectedCount() {
        int n = 0;
        for (CartItem item : items) {
            if (item.isSelected()) {
                n += item.getQuantity();
            }
        }
        return n;
    }

    /**
     * 移除已选中的项。
     */
    public int removeSelected() {
        int before = items.size();
        items.removeIf(CartItem::isSelected);
        return before - items.size();
    }

    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = getItem(position);
        if (item == null || holder == null) {
            return;
        }
        holder.productName.setText(item.getProductName());
        holder.price.setText("¥" + String.format("%.2f", item.getUnitPrice()));
        holder.quantity.setText("x" + item.getQuantity());
        holder.subtotal.setText("小计: ¥" + String.format("%.2f", item.getSubtotal()));
        holder.checkBox.setSelected(item.isSelected());
        holder.itemView.setOnClickListener(v -> {
            toggleSelected(position);
            holder.checkBox.setSelected(item.isSelected());
            if (listener != null) {
                listener.onCartItemClick(item, position);
            }
        });
    }

    public interface OnCartItemClickListener {
        void onCartItemClick(CartItem item, int position);
    }

    public static class CartViewHolder {
        public View itemView;
        public ProductAdapter.TextView productName;
        public ProductAdapter.TextView price;
        public ProductAdapter.TextView quantity;
        public ProductAdapter.TextView subtotal;
        public CheckBox checkBox;

        public CartViewHolder(View itemView) {
            this.itemView = itemView;
            this.productName = new ProductAdapter.TextView();
            this.price = new ProductAdapter.TextView();
            this.quantity = new ProductAdapter.TextView();
            this.subtotal = new ProductAdapter.TextView();
            this.checkBox = new CheckBox();
        }
    }

    public static class CheckBox extends View {
        private boolean selected;

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
