package db.com.dygod.module.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;

import db.com.dygod.DyGodApplication;
import db.com.dygod.R;
import db.com.dygod.base.BaseActivity;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.module.fragment.MainFragment;
import db.com.dygod.module.fragment.MainFragments;
import db.com.dygod.module.main.adapter.FragmentMainAdapter;
import db.com.dygod.utils.StringUtils;

public class MainActivity extends BaseActivity {
    private long mExitTime;
    private ViewPager mMainViewPager;
    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        ArrayList<BaseFragment> mainData=new ArrayList<>();
        mMainFragment=new MainFragment();
        mainData.add(mMainFragment);
        mainData.add(new MainFragments());
        mMainViewPager.setAdapter(new FragmentMainAdapter(this.getSupportFragmentManager(),mainData));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 1000) {
                Toast.makeText(this, StringUtils.getString(R.string.user_exit_hint_again),
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                DyGodApplication.getInstance().onTerminate();
                finish();
                overridePendingTransition(0, 0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
