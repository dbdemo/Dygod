package db.com.dyhome.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class CommonUtils {
    public static int dp2px(Context context, int dpVaule) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpVaule * density + 0.5f);
        return px;
    }

    public static String getVersionName(Context context) {
        String versionName = "0";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versioncode;
    }

}
