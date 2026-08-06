package com.demo.app.adapter;

import com.demo.app.model.Order;
import com.demo.app.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表适配器。
 */
public class OrderAdapter {

    private final List<Order> items = new ArrayList<>();
    private OnOrderClickListener listener;

    public void setOnOrderClickListener(OnOrderClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Order> orders) {
        items.clear();
        if (orders != null) {
            items.addAll(orders);
        }
    }

    public void appendItems(List<Order> orders) {
        if (orders != null) {
            items.addAll(orders);
        }
    }

    public void clear() {
        items.clear();
    }

    public int getItemCount() {
        return items.size();
    }

    public Order getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = getItem(position);
        if (order == null || holder == null) {
            return;
        }
        holder.orderNo.setText("订单号: " + order.getOrderNo());
        holder.status.setText(statusLabel(order.getStatus()));
        holder.totalAmount.setText("合计: ¥" + String.format("%.2f", order.getPayAmount()));
        StringBuilder itemSummary = new StringBuilder();
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (int i = 0; i < order.getItems().size() && i < 3; i++) {
                OrderItem oi = order.getItems().get(i);
                if (oi != null) {
                    if (i > 0) {
                        itemSummary.append("; ");
                    }
                    itemSummary.append(oi.getProductName())
                            .append(" x").append(oi.getQuantity());
                }
            }
            if (order.getItems().size() > 3) {
                itemSummary.append(" 等").append(order.getItems().size()).append("件商品");
            }
        }
        holder.itemSummary.setText(itemSummary.toString());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderClick(order, position);
            }
        });
    }

    private String statusLabel(String status) {
        if (status == null) {
            return "";
        }
        switch (status) {
            case "PENDING_PAYMENT":
                return "待付款";
            case "PENDING_SHIPMENT":
                return "待发货";
            case "PENDING_DELIVERY":
                return "待收货";
            case "COMPLETED":
                return "已完成";
            case "CANCELLED":
                return "已取消";
            default:
                return status;
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order, int position);
    }

    public static class OrderViewHolder {
        public View itemView;
        public ProductAdapter.TextView orderNo;
        public ProductAdapter.TextView status;
        public ProductAdapter.TextView totalAmount;
        public ProductAdapter.TextView itemSummary;

        public OrderViewHolder(View itemView) {
            this.itemView = itemView;
            this.orderNo = new ProductAdapter.TextView();
            this.status = new ProductAdapter.TextView();
            this.totalAmount = new ProductAdapter.TextView();
            this.itemSummary = new ProductAdapter.TextView();
        }
    }
}
