package db.com.dygod.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import java.lang.ref.WeakReference;

import db.com.dygod.DyGodApplication;

public class BaseActivity extends FragmentActivity {

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
