package db.com.dygod.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;

import db.com.dygod.DyGodApplication;
import db.com.dygod.R;

public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    private SystemBarTintManager tintManager;

    private SelfHandler mSelfHandler;
    private Toolbar mToolbar;

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
        FrameLayout bodyLayout= (FrameLayout) findViewById(R.id.main_base_bodyLayout);
        bodyLayout.addView(View.inflate(this,setBodyView(),null));
        mToolbar= (Toolbar) findViewById(R.id.main_base_toolbar);
        mToolbar.setTitle("电影之家");
        mToolbar.setNavigationIcon(R.mipmap.toolbar_menu);
        mToolbar.inflateMenu(R.menu.base_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.toolbar_search) {
            Toast.makeText(this , "点击了搜索" , Toast.LENGTH_SHORT).show();

        } else if (menuItemId == R.id.toolbar_feed) {
            Toast.makeText(this , "意见反馈" , Toast.LENGTH_SHORT).show();
        }else if (menuItemId == R.id.toolbar_share) {
            Toast.makeText(this , "分享" , Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /***
     * 设置toolbar是否显示
     * @param b
     */
    public void setToolbarvisibility(int b){
            mToolbar.setVisibility(b);
    }

    //这是子类布局
    protected abstract int setBodyView();


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
}
