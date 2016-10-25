package db.com.dyhome.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zdb on 2016/5/18.
 */
public class DbSQLiteOpenHelper extends SQLiteOpenHelper {
    private static String dbName = "dygod";
    private static int versions = 1;
    private static DbSQLiteOpenHelper mDbSQLiteOpenHelper;

    public DbSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DbSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, dbName, factory, versions, null);
    }

    public DbSQLiteOpenHelper(Context context) {
        this(context, dbName, null, versions, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS movieTitle (id integer primary key autoincrement, name varchar(20), url varchar(20))");
        db.execSQL("create table if not exists netWorkCache (_id integer ,value varchar(20) , date char(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DbSQLiteOpenHelper getInstance() {
        return mDbSQLiteOpenHelper;
    }

    public static DbSQLiteOpenHelper getInstance(Context context) {
        synchronized (DbSQLiteOpenHelper.class) {
            if (mDbSQLiteOpenHelper == null) {
                mDbSQLiteOpenHelper = new DbSQLiteOpenHelper(context);
            }
            return mDbSQLiteOpenHelper;
        }
    }
}
