package db.com.dyhome.utils;

import android.os.Environment;

/**
 * Created by db on 2016/6/13.
 */
public class SdCardUtils {

    public static String getSdCardDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

}
