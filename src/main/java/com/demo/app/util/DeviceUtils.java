package com.demo.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 设备 / 应用信息工具。
 */
public final class DeviceUtils {

    private DeviceUtils() {
    }

    /**
     * 获取应用版本名。
     */
    public static String getVersionName(Context context) {
        PackageInfo info = getPackageInfo(context);
        return info == null ? "" : info.versionName;
    }

    /**
     * 获取应用版本号。
     */
    public static int getVersionCode(Context context) {
        PackageInfo info = getPackageInfo(context);
        return info == null ? 0 : info.versionCode;
    }

    /**
     * 获取设备型号。
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备品牌。
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取系统版本号。
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取 SDK 版本号。
     */
    public static int getSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取屏幕宽度（px）。
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm == null ? 0 : dm.widthPixels;
    }

    /**
     * 获取屏幕高度（px）。
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm == null ? 0 : dm.heightPixels;
    }

    /**
     * 获取屏幕密度。
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm == null ? 0f : dm.density;
    }

    /**
     * dp 转 px。
     */
    public static int dp2px(Context context, float dp) {
        float density = getScreenDensity(context);
        return (int) (dp * density + 0.5f);
    }

    /**
     * px 转 dp。
     */
    public static int px2dp(Context context, float px) {
        float density = getScreenDensity(context);
        return (int) (px / density + 0.5f);
    }

    /**
     * sp 转 px。
     */
    public static int sp2px(Context context, float sp) {
        float scaledDensity = getDisplayMetrics(context) == null
                ? 1f : getDisplayMetrics(context).scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    private static PackageInfo getPackageInfo(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (context == null) {
            return null;
        }
        try {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (wm == null) {
                return context.getResources().getDisplayMetrics();
            }
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            return dm;
        } catch (Exception e) {
            return null;
        }
    }
}
