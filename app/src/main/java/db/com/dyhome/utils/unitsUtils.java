package db.com.dyhome.utils;

/**
 * Created by Administrator on 2016/11/2.
 */

public class UnitsUtils {

    /**
     * 把byte 转换为m 或者g
     *
     * @param size
     * @return
     */
    public static String byteToPart(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
}
