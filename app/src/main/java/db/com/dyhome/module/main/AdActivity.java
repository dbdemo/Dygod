package db.com.dyhome.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;


import com.igexin.sdk.PushManager;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.module.common.GeTuiIntentService;
import db.com.dyhome.module.common.GeTuiPushService;

public class AdActivity extends BaseActivity implements View.OnClickListener {

    private int timeMsg = 1;
    private TextView ad_time;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (ad_time.getText().equals("0")) {
                extNext();
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
        mHandler.sendEmptyMessage(timeMsg);
        PushManager.getInstance().initialize(this.getApplicationContext(), db.com.dyhome.module.common.GeTuiPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), db.com.dyhome.module.common.GeTuiIntentService.class);
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_ad;
    }

    @Override
    public void onClick(View v) {
        ad_time.setText("0");
        mHandler.removeCallbacksAndMessages(null);
        extNext();
    }

    /**
     * 进入下一个activity
     */
    public void extNext() {
        Intent intent = null;
        /*if(SpHelper.isFistOpenApp()){
            intent=new Intent(AdActivity.this,SplashActivity.class);
        }else{
            intent=new Intent(AdActivity.this,MainActivity.class);
        }*/
        intent = new Intent(AdActivity.this, MainActivity.class);
        startActivity(intent);
        AdActivity.this.finish();
    }

}
