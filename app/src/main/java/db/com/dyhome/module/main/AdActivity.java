package db.com.dyhome.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.igexin.sdk.PushManager;

import net.youmi.android.AdManager;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.module.common.push.GeTuiIntentService;
import db.com.dyhome.module.common.push.GeTuiPushService;
import db.com.dyhome.utils.ExtraLog;

public class AdActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTintColor("#00000000");
        setToolbarvisibility(View.GONE);
        AdManager.getInstance(this).init("6e0bda910df28bc6", "c2d38fd48637244f",true,true);
        PushManager.getInstance().initialize(this.getApplicationContext(), GeTuiPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);

        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setAutoJumpToTargetWhenShowFailed(true);
        splashViewSettings.setTargetClass(MainActivity.class);
        // 使用默认布局参数
        ViewGroup youmiAd= (ViewGroup) findViewById(R.id.ad_youmi);
        splashViewSettings.setSplashViewContainer(youmiAd);
        SpotManager.getInstance(this).showSplash(this,
                splashViewSettings, new SpotListener() {
                    @Override
                    public void onShowSuccess() {
                        ExtraLog.d("dblog","onShowSuccess");
                    }

                    @Override
                    public void onShowFailed(int i) {
                        ExtraLog.d("dblog","onShowFailed");
                    }

                    @Override
                    public void onSpotClosed() {
                        ExtraLog.d("dblog","onSpotClosed");
                    }

                    @Override
                    public void onSpotClicked(boolean b) {
                        ExtraLog.d("dblog","onSpotClicked");
                    }
                });
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_ad;
    }


    /**
     * 进入下一个activity
     */
    private void extNext() {
        Intent intent = new Intent(AdActivity.this, MainActivity.class);
        startActivity(intent);
        AdActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onDestroy();
    }
}
