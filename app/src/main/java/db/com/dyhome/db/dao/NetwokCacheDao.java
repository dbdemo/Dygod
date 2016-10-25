package db.com.dyhome.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import db.com.dyhome.db.DBHelper;

/**
 * Created by zhangdebao on 2016/2/25.
 * 专项联系的数据库操作类
 */
public class NetwokCacheDao  {

    static String ID = "_id";
    static String VALUE="value";
    static String DATE="date";

    public static String TABLE_Name="netWorkCache";
    public static SQLiteDatabase db= DBHelper.getWritableDatabase();

    /**
     * 根据id得到一条缓存的数据
     * @param id
     * @return 服务器返回的json字符串
     */
    public static synchronized String getValueForID(String id){
        Cursor culr = db.rawQuery("SELECT value FROM " + TABLE_Name + " WHERE " + ID + "=?", new String[]{id});
        String valueJson="";
       if(null!=culr && culr.moveToNext()){
           valueJson=culr.getString(0);
       }
        culr.close();
        return valueJson;
    }

    /**
     * 增加一条专项联系缓存数据，如果存在则修改，如果不存在则增加
     * @param id
     * @param value
     */
    public static synchronized void addValueForId(String id,String value){
        SQLiteDatabase db;
        if("".endsWith(getValueForID(id))){
            db = DBHelper.getWritableDatabase();
            db.beginTransaction();
            db.execSQL("INSERT INTO "+TABLE_Name+"("+ID+","+VALUE+","+DATE+") VALUES(?,?,?)",
                    new String[]{id,value, System.currentTimeMillis()+""});
        }else{
            db = DBHelper.getWritableDatabase();
            db.beginTransaction();
            db.execSQL("UPDATE "+TABLE_Name+" SET "+VALUE+"=?,date='"+System.currentTimeMillis()+"'WHERE _id=?",
                    new String[]{value,id});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 得到某个接口上次更新的时间
     * @param id
     */
    public static synchronized String getUpdateTime(String id){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor culr = db.rawQuery("SELECT date FROM " + TABLE_Name + " WHERE " + ID + "=?", new String[]{id});
        String valueJson="";
        if(null!=culr && culr.moveToNext()){
            valueJson=culr.getString(0);
        }
       // db.close();
        if(TextUtils.isEmpty(valueJson)){
            valueJson = System.currentTimeMillis()+"";
        }
        return valueJson;
    }

}
