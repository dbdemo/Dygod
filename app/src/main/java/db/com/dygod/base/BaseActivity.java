package db.com.dygod.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;

import db.com.dygod.DyGodApplication;
import db.com.dygod.R;

public class BaseActivity extends FragmentActivity {
    private SystemBarTintManager tintManager;

    private static class SelfHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRef.get().onSelfMessage(msg);
        }

        public SelfHandler(BaseActivity mActivity) {
            mRef = new WeakReference<BaseActivity>(mActivity);
        }

        private WeakReference<BaseActivity> mRef;
    }

    private SelfHandler mSelfHandler;


    protected Handler getSelfHandler() {
        return mSelfHandler;
    }

    protected void onSelfMessage(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.bgcolor);//通知栏所需颜色


        DyGodApplication applican = (DyGodApplication) getApplication();
        applican.addActivity(this);
        mSelfHandler = new SelfHandler(this);
    }

    public void setTintColor(String color) {
        if (tintManager != null && !TextUtils.isEmpty(color))
            tintManager.setStatusBarTintColor(Color.parseColor(color));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
