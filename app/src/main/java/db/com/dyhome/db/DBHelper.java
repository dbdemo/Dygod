package db.com.dyhome.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zdb on 2016/5/18.
 */
public class DBHelper {

    /***
     * 获取可写数据库
     * @return
     */
    public static SQLiteDatabase getWritableDatabase() {
        return DbSQLiteOpenHelper.getInstance().getWritableDatabase();
    }

    /***
     * 获取可读数据
     * @return
     */
    public static SQLiteDatabase getReadableDatabase() {
        return DbSQLiteOpenHelper.getInstance().getReadableDatabase();
    }
}
