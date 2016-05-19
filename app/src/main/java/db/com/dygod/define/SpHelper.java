package db.com.dygod.define;

import android.content.Context;
import android.content.SharedPreferences;

import db.com.dygod.DyGodApplication;

/**
 * Sp管理类
 * Created by zdb on 2016/5/18.
 */
public class SpHelper {

    private static String appName="dygod";
    private static SharedPreferences sp= DyGodApplication.getInstance().getSharedPreferences(appName, Context.MODE_PRIVATE);

    private static String mIsFistOpenApp="isFistOpenApp";


    /***
     * 获取用户是否第一次打开app
     * @return
     */
    public static boolean isFistOpenApp() {
        return  sp.getBoolean(mIsFistOpenApp,true);
    }

    /**
     * 设置用户是否第一次打开app
     * @param isFistOpenApp
     */
    public static void setIsFistOpenApp(boolean isFistOpenApp) {
        sp.edit().putBoolean(mIsFistOpenApp,isFistOpenApp).commit();
    }
}
