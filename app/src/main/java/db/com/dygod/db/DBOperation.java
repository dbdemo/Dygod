package db.com.dygod.db;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import db.com.dygod.modu.MovieCategoy;

/**
 * Created by Administrator on 2015/12/19.
 */
public class DBOperation {
    private final static DBOperation dbOperation = new DBOperation();
    private DbUtils dbUtils = DbCread.init();

    public static DBOperation getInstents() {
        return dbOperation;
    }

    /**
     * 保存电影分类
     * @param categoryList 电影分类集合
     */
    public void seavCategory(List<MovieCategoy> categoryList){
       for (int i=0;i<categoryList.size();i++){
           try {
               dbUtils.save(categoryList.get(i));
           } catch (DbException e) {
               e.printStackTrace();
           }
       }
    }

    /**
     * 判断电影分类是否为空
     */
    public boolean isCategoryMovieNull(){
        try {
            MovieCategoy movieCategory = dbUtils.findFirst(MovieCategoy.class);
            if(movieCategory==null){
                return true;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }
//
//    /**
//     * 获取全部电影分类
//     */
//    public List<MovieCategoy>  getAllMovieCagory(){
//        List<MovieCategoy>  movieCategory =null;
//        try {
//            movieCategory= dbUtils.findAll(MovieCategoy.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return movieCategory;
//    }



}
