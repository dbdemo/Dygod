package db.com.dyhome.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import db.com.dyhome.DyGodApplication;
import db.com.dyhome.R;
import db.com.dyhome.module.main.MainActivity;
import db.com.dyhome.utils.ToastUtil;

public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private SystemBarTintManager tintManager;

    private SelfHandler mSelfHandler;
    public Toolbar mToolbar;
    public TextView mSearchText;
    public String searchString;
    private InputMethodManager imm;

    protected Handler getSelfHandler() {
        return mSelfHandler;
    }

    protected void onSelfMessage(Message msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.bgcolor);//通知栏所需颜色

        DyGodApplication applican = (DyGodApplication) getApplication();
        applican.addActivity(this);
        mSelfHandler = new SelfHandler(this);
        FrameLayout bodyLayout = (FrameLayout) findViewById(R.id.main_base_bodyLayout);
        bodyLayout.addView(View.inflate(this, setBodyView(), null));
        mToolbar = (Toolbar) findViewById(R.id.main_base_toolbar);
        mSearchText = (TextView) findViewById(R.id.base_search);
        mToolbar.setTitle("电影之家");
        mToolbar.setNavigationIcon(R.mipmap.toolbar_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationOnClick();
            }
        });
        mToolbar.inflateMenu(R.menu.base_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(this);
    }

    /**
     * 菜单按钮点击事件
     */
    public void NavigationOnClick() {
        if (this instanceof MainActivity) {
            ToastUtil.showMsg("菜单功能未开放");
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.toolbar_search) {
            if (mSearchText.getVisibility() == View.GONE) {
                mSearchText.setVisibility(View.VISIBLE);
                mSearchText.requestFocus();
                imm.showSoftInput(mSearchText, InputMethodManager.SHOW_FORCED);
            } else {
                View view = this.getWindow().peekDecorView();
                if (view != null) {
                    imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);
                }
                searchString = mSearchText.getText().toString().trim();
                if (TextUtils.isEmpty(searchString)) {
                    mSearchText.setVisibility(View.GONE);
                } else {
                    search();
                }
            }
        } else if (menuItemId == R.id.toolbar_share) {
            share();
        }
        return false;
    }

    /***
     * 设置toolbar是否显示
     *
     * @param b
     */
    public void setToolbarvisibility(int b) {
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

    public void search() {
        mSearchText.setVisibility(View.GONE);
        mSearchText.setText("");
        Intent intent = new Intent();
        intent.putExtra("searchText", searchString);
        intent.setClass(this, db.com.dyhome.module.search.SearchActivity.class);
        startActivity(intent);
    }

    public void share() {
        Toast.makeText(this, "分享功能未开放", Toast.LENGTH_SHORT).show();
    }
}
