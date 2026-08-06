package com.demo.app.adapter;

import com.demo.app.model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址适配器。
 */
public class AddressAdapter {

    private final List<Address> items = new ArrayList<>();
    private OnAddressClickListener listener;

    public void setOnAddressClickListener(OnAddressClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Address> addresses) {
        items.clear();
        if (addresses != null) {
            items.addAll(addresses);
        }
    }

    public int getItemCount() {
        return items.size();
    }

    public Address getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    public void onBindViewHolder(AddressViewHolder holder, int position) {
        Address address = getItem(position);
        if (address == null || holder == null) {
            return;
        }
        holder.receiver.setText(address.getReceiver());
        holder.mobile.setText(maskMobile(address.getMobile()));
        holder.detail.setText(address.fullAddress());
        holder.defaultTag.setVisible(address.isDefaultAddress());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddressClick(address, position);
            }
        });
    }

    private String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 7) {
            return mobile;
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
    }

    public interface OnAddressClickListener {
        void onAddressClick(Address address, int position);
    }

    public static class AddressViewHolder {
        public View itemView;
        public ProductAdapter.TextView receiver;
        public ProductAdapter.TextView mobile;
        public ProductAdapter.TextView detail;
        public ProductAdapter.TextView defaultTag;

        public AddressViewHolder(View itemView) {
            this.itemView = itemView;
            this.receiver = new ProductAdapter.TextView();
            this.mobile = new ProductAdapter.TextView();
            this.detail = new ProductAdapter.TextView();
            this.defaultTag = new ProductAdapter.TextView();
        }
    }
}
