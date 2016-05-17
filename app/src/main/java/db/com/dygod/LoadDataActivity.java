package db.com.dygod;

import android.os.Bundle;

import db.com.dygod.baseActivity.BaseActivity;
import db.com.dygod.db.DBOperation;

public class LoadDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        LoadDataManager loadDataManager=new LoadDataManager();
        /**
         * 加载电影分类
         */
        if(DBOperation.getInstents().isCategoryMovieNull()){
            loadDataManager.getTitles();
        }
        /**
         * 加载最新电影
         */
       // loadDataManager.getNewMove();

    }
}
