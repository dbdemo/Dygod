package db.com.dygod.db;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

/**
 * Created by Administrator on 2015/12/19.
 */
public class DbCread {

    private static DbUtils dbUtils;
    public static DbUtils init(Context context){
        if(dbUtils==null){
            dbUtils=DbUtils.create(context, "dyGod", 1, new DbUtils.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbUtils dbUtils, int i, int i1) {

                }
            });
        }
        return  dbUtils;
    }

    public static DbUtils init(){
        return  dbUtils;
    }
}
