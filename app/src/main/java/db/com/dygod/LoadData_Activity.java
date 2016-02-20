package db.com.dygod;

import android.os.Bundle;

import db.com.dygod.baseActivity.BaseActivity;
import db.com.dygod.db.DBOperation;

public class LoadData_Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaddata);
        LoadData_Manager loadDataManager=new LoadData_Manager();
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
