package db.com.dygod.module.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.module.main.news.adapter.MainNewsAdapter;
import db.com.dygod.module.main.news.fragment.MainNewsFragment;

/**
 * Created by zdb on 2016/5/17.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    private ViewPager mMainfViewPager;
    private RadioButton mMainNews;
    private RadioButton mMainhots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_main=inflater.inflate(R.layout.fragment_main, null);
        initView(fragment_main);
        return fragment_main;
    }

    private void initView(View fragment_main) {
        mMainfViewPager = (ViewPager) fragment_main.findViewById(R.id.mainf_viewPager);
        mMainNews = (RadioButton) fragment_main.findViewById(R.id.Main_news);
        mMainhots = (RadioButton)fragment_main.findViewById(R.id.Main_hots);

        mMainhots.setOnClickListener(this);
        mMainNews.setOnClickListener(this);

        ArrayList<BaseFragment> mainfData=new ArrayList<>();
        MainNewsFragment mainNewsFragment=new MainNewsFragment();

        MainFragments mainFragments= new MainFragments();
        mainfData.add(mainNewsFragment);
        mainfData.add(mainFragments);

        mMainfViewPager.setAdapter(new MainNewsAdapter(this.getChildFragmentManager(), mainfData));
        mMainfViewPager.addOnPageChangeListener(mViewPagerSelectLinner);
    }

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
}
