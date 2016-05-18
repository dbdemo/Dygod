package db.com.dygod.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.base.BaseActivity;
import db.com.dygod.bean.MovieCategoyEntity;
import db.com.dygod.db.dao.MovieCategoryDao;
import db.com.dygod.define.SpHelper;
import db.com.dygod.network.GetTitleDataServant;
import db.com.dygod.network.base.NetWorkListener;

public class AdActivity extends BaseActivity {

    private int timeMsg = 1;
    private TextView ad_time;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(ad_time.getText().equals("1")){
                Intent intent=null;
                if(SpHelper.isFistOpenApp()){
                     intent=new Intent(AdActivity.this,SplashActivity.class);
                }else{
                     intent=new Intent(AdActivity.this,MainActivity.class);
                }
                startActivity(intent);
                AdActivity.this.finish();
                return;
            }
            ad_time.setText(String.valueOf(Integer.parseInt(ad_time.getText() + "") - 1));
            mHandler.sendEmptyMessageDelayed(timeMsg, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ad_time = (TextView) findViewById(R.id.ad_time);
        ad_time.setText("1");
        mHandler.sendEmptyMessageDelayed(timeMsg, 1000);
        MovieCategoryDao dao=new MovieCategoryDao();
        if(dao.getDataCount()>0)return;
       GetTitleDataServant getTitleDataServant=new GetTitleDataServant();
        getTitleDataServant.getTitleData(new NetWorkListener<MovieCategoyEntity>() {
            @Override
            public void successful(ArrayList<MovieCategoyEntity> t) {
                MovieCategoryDao dao = new MovieCategoryDao();
                dao.insertCategory(t);
                System.out.print(dao.getDataCount());
            }

            @Override
            public void failure(IOException e) {

            }
        });
    }
}
