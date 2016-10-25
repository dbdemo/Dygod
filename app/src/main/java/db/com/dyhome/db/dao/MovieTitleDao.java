package db.com.dyhome.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import db.com.dyhome.bean.MovieCategoyEntity;
import db.com.dyhome.db.DBHelper;

/**
 * Created by zdb on 2016/5/18.
 * 电影分类
 */
public class MovieTitleDao {

    private final String tabName="movieTitle";

    public SQLiteDatabase db= DBHelper.getWritableDatabase();

    /**
     * 增加分类
     */
    public void insertCategory(ArrayList<MovieCategoyEntity> data){
        if(data!=null&&data.size()>0){
            for (int i=0;i<data.size();i++){
                ContentValues values=new ContentValues();
                values.put("name",data.get(i).getMoviecategoryName());
                values.put("url",data.get(i).getMovieHref());
                db.insert(tabName,null,values);
            }
        }
    }

    /**
     * 获取总共有多少条数据
     * @return
     */
    public int getDataCount(){
        Cursor cour = db.rawQuery("select count(*) as counts from " + tabName, null);
        if(cour==null||!cour.moveToFirst()){
            return 0;
        }
      int cout=  cour.getInt(0);
        cour.close();
        return cout;
    }
}
