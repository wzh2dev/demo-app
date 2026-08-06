package com.demo.app.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * UI 工具类。
 */
public final class UiUtils {

    private UiUtils() {
    }

    /**
     * 显示短 Toast。
     */
    public static void toastShort(Context context, String text) {
        if (context == null || StringUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示长 Toast。
     */
    public static void toastLong(Context context, String text) {
        if (context == null || StringUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    /**
     * 隐藏软键盘。
     */
    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘。
     */
    public static void showKeyboard(Activity activity, View view) {
        if (activity == null || view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 切换软键盘显示状态。
     */
    public static void toggleKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
}
