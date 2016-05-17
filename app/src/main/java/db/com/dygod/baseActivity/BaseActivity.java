package db.com.dygod.baseActivity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import java.lang.ref.WeakReference;

import db.com.dygod.DyGodApplication;

public class BaseActivity extends Activity {

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
        DyGodApplication applican=(DyGodApplication)getApplication();
        applican.addActivity(this);
        mSelfHandler = new SelfHandler(this);
    }
}
