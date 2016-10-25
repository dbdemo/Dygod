package db.com.dyhome.utils;

import db.com.dyhome.DyGodApplication;

/**
 * Created by zdb on 2016/5/17.
 */
public class StringUtils {

    /**
     * 得到Strings文件里的文字资源
     * @param id
     * @return
     */
    public static String getString(int id){
        return DyGodApplication.getInstance().getResources().getString(id);
    }
}
