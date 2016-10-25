package db.com.dyhome.utils;

import android.widget.Toast;

import db.com.dyhome.DyGodApplication;

/**
 * 弹出toast
 */
public class ToastUtil {

    public static void showMsg(int resId) {
        Toast.makeText(DyGodApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }


    public static void showMsg(String message) {
        Toast.makeText(DyGodApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}