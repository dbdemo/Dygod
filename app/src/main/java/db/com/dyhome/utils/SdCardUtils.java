package db.com.dyhome.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by db on 2016/6/13.
 */
public class SdCardUtils {
    static String directoryName = "dbtorrent";

    public static String getSdCardDirectory() {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + directoryName + File.separator;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        return path;
    }

    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        String end = fName.substring(fName.lastIndexOf(".")
                + 1, fName.length()).toLowerCase();
        if (end.equals("torrent")) {
            type = "application/octet-stream";
        } else {
            type = "*";
        }
        return "*";
    }
}
