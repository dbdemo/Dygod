package db.com.dygod.module.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.bean.MainEntity;
import db.com.dygod.module.main.news.adapter.MainNewsAdapter;
import db.com.dygod.module.main.news.fragment.MainHotFragment;
import db.com.dygod.module.main.news.fragment.MainNewsFragment;
import db.com.dygod.network.GetMainDataServant;
import db.com.dygod.network.base.NetWorkListener;

/**
 * Created by zdb on 2016/5/17.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    private ViewPager mMainfViewPager;
    private RadioButton mMainNews;
    private RadioButton mMainhots;
    private MainEntity mMainEntity;
    public final static String ENTITY_NAME="entity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_main=inflater.inflate(R.layout.fragment_main, null);
        initView(fragment_main);
        getNetData();
        return fragment_main;
    }

    public void getNetData() {
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(false,true,mNetWorkListener);
        mainDataServant.getMainData(true,false,mNetWorkListener);
    }

    private void initView(View fragment_main) {
        mMainfViewPager = (ViewPager) fragment_main.findViewById(R.id.mainf_viewPager);
        mMainNews = (RadioButton) fragment_main.findViewById(R.id.Main_news);
        mMainhots = (RadioButton)fragment_main.findViewById(R.id.Main_hots);
        mMainhots.setOnClickListener(this);
        mMainNews.setOnClickListener(this);

    }

    public void initFragments(){

        ArrayList<BaseFragment> mainfData=new ArrayList<>();
        MainNewsFragment mainNewsFragment=new MainNewsFragment();
        Bundle bundleNew=new Bundle();
        bundleNew.putParcelableArrayList(ENTITY_NAME,mMainEntity.getMainNesEntities());
        mainNewsFragment.setArguments(bundleNew);
        mainNewsFragment.setmMainFragment(this);

        /*getChildFragmentManager().beginTransaction().add(mainNewsFragment,"tag1");
        getChildFragmentManager().beginTransaction().add(mainNewsFragment,"tag2");*/

        MainHotFragment mainReleaseFragment= new MainHotFragment();
        Bundle bundleHot=new Bundle();
        bundleHot.putParcelableArrayList(ENTITY_NAME,mMainEntity.getMainReleEntities());
        mainNewsFragment.setArguments(bundleHot);
        mainReleaseFragment.setArguments(bundleHot);
        mainReleaseFragment.setmMainFragment(this);

        mainfData.add(mainNewsFragment);
        mainfData.add(mainReleaseFragment);

        mMainfViewPager.setAdapter(new MainNewsAdapter(this.getChildFragmentManager(), mainfData));
        mMainfViewPager.addOnPageChangeListener(mViewPagerSelectLinner);
    }

    private NetWorkListener mNetWorkListener = new NetWorkListener<MainEntity>() {

        @Override
        public void successful(MainEntity mainEntity) {
            mMainEntity=mainEntity;
            initFragments();
        }

        @Override
        public void failure(IOException e) {
            System.err.println("mainFragment:"+e.toString());
        }
    };

    private  ViewPager.OnPageChangeListener mViewPagerSelectLinner= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mMainNews.setChecked(true);
                    break;
                case 1:
                    mMainhots.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Main_news:
                mMainfViewPager.setCurrentItem(0);
                break;
            case R.id.Main_hots:
                mMainfViewPager.setCurrentItem(1);
                break;
        }
    }

    public MainEntity getmMainEntity() {
        return mMainEntity;
    }

    public void setmMainEntity(MainEntity mainEntity) {
        this.mMainEntity = mainEntity;
    }
}
