package db.com.dyhome.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.bean.MovieCategoyEntity;
import db.com.dyhome.db.dao.MovieTitleDao;
import db.com.dyhome.define.SpHelper;
import db.com.dyhome.network.GetTitleDataServant;
import db.com.dyhome.network.base.NetWorkListener;

public class AdActivity extends BaseActivity implements View.OnClickListener {

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
        setTintColor("#00000000");
        setToolbarvisibility(View.GONE);
        ad_time = (TextView) findViewById(R.id.ad_time);
        ad_time.setText("5");
        ad_time.setOnClickListener(this);
        mHandler.sendEmptyMessageDelayed(timeMsg, 1000);
        MovieTitleDao dao=new MovieTitleDao();
        if(dao.getDataCount()>0)return;
       GetTitleDataServant getTitleDataServant=new GetTitleDataServant();
        getTitleDataServant.getTitleData(new NetWorkListener<ArrayList<MovieCategoyEntity>>() {
            @Override
            public void successful(ArrayList<MovieCategoyEntity> t) {
                MovieTitleDao dao = new MovieTitleDao();
                dao.insertCategory(t);
            }

            @Override
            public void failure(IOException e) {

            }
        });
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_ad;
    }

    @Override
    public void onClick(View v) {
        ad_time.setText("1");
    }
}
