package com.demo.app.adapter;

/**
 * View 基类，模拟 Android View。
 */
public class View {
    private OnClickListener clickListener;

    public void setOnClickListener(OnClickListener listener) {
        this.clickListener = listener;
    }

    public void performClick() {
        if (clickListener != null) {
            clickListener.onClick(this);
        }
    }
}
