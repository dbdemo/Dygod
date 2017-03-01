package db.com.dyhome.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import java.util.ArrayList;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.base.BaseFragment;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.module.localvideo.LocalVideoActivity;
import db.com.dyhome.module.main.adapter.FragmentMainAdapter;
import db.com.dyhome.module.main.recommend.MovieListFragment;
import db.com.dyhome.utils.UpdateHelper;
import db.com.dyhome.widget.chameleonPagerTabStrip.ChameleonPagerTabStrip;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mMainViewPager;
    private MovieListFragment mMainFragment;
    private DrawerLayout drawerLayout;
    private ChameleonPagerTabStrip mStrip;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int setBodyView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UmengTool.getSignature(this);//友盟签名对照
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mStrip = (ChameleonPagerTabStrip) findViewById(R.id.main_strip);
        mMainViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        findViewById(R.id.main_local_video).setOnClickListener(this);
        mMainViewPager.setOffscreenPageLimit(1000);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                mToolbar,
                R.string.app_name,
                R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerView.setClickable(true);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);
        initData();
        UpdateHelper updateHelper=new UpdateHelper(this);
        updateHelper.execute();
    }

    private void initData() {
        ArrayList<BaseFragment> mainData = new ArrayList<>();
        mainData.add(MovieListFragment.newInstance(UrlConstant.mainUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.actionUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.warUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.plotUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.laveUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.fictionUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.suspenseUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.familyUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.crimeUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.terroristUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.animationtUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.comedyUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.thrillerUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.adventureUrl));
        mainData.add(MovieListFragment.newInstance(UrlConstant.TVUrl));

        mMainViewPager.setAdapter(new FragmentMainAdapter(this.getSupportFragmentManager(), mainData));
        mStrip.setViewPager(mMainViewPager);
    }

    @Override
    public void mainNavigationOnClick() {
        super.mainNavigationOnClick();
        drawerLayout.openDrawer(drawerLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
        drawerLayout.closeDrawers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_local_video:
                Intent intent = new Intent();
                intent.setClass(this, LocalVideoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
