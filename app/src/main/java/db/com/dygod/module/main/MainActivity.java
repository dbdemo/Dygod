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
import db.com.dygod.module.main.JapanOrSouth.JapanOrSouth;
import db.com.dygod.module.main.Local.LocalFragment;
import db.com.dygod.module.main.Xvdieo.XvdieoFragments;
import db.com.dygod.module.main.adapter.FragmentMainAdapter;
import db.com.dygod.module.main.news.NewsFragments;
import db.com.dygod.module.main.recommend.RecommendMainFragment;
import db.com.dygod.utils.StringUtils;
import db.com.dygod.widget.chameleonPagerTabStrip.ChameleonPagerTabStrip;

public class MainActivity extends BaseActivity {
    private long mExitTime;
    private ViewPager mMainViewPager;
    private RecommendMainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChameleonPagerTabStrip mStrip= (ChameleonPagerTabStrip) findViewById(R.id.main_strip);
        mMainViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        ArrayList<BaseFragment> mainData=new ArrayList<>();
        mMainFragment=new RecommendMainFragment();
        mainData.add(mMainFragment);
        mainData.add(new NewsFragments());
        mainData.add(new JapanOrSouth());
        mainData.add(new LocalFragment());
        mainData.add(new XvdieoFragments());
        mainData.add(new VarietyFragment());
        mMainViewPager.setAdapter(new FragmentMainAdapter(this.getSupportFragmentManager(), mainData));
        mStrip.setViewPager(mMainViewPager);
    }

    @Override
    protected int setBodyView() {
        return  R.layout.activity_main;
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
