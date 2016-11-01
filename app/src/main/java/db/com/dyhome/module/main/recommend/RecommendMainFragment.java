package db.com.dyhome.module.main.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseFragment;
import db.com.dyhome.module.main.recommend.adapter.RecommendNewsAdapter;
import db.com.dyhome.module.main.recommend.fragment.RecommendHotFragment;
import db.com.dyhome.module.main.recommend.fragment.RecommendNewsFragment;

/**
 * Created by zdb on 2016/5/17.
 */
public class RecommendMainFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mMainfViewPager;
    private RadioButton mMainNews;
    private RadioButton mMainhots;
    public boolean isFirst = true;
    public View fragment_main;
    private ArrayList<BaseFragment> mainfData;
    private RecommendNewsFragment mainNewsFragment;
    private RecommendHotFragment mainReleaseFragment;

    @Override
    protected void lazyLoad() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragment_main != null) {
            return fragment_main;
        }
        System.out.println("main===");

        mainNewsFragment = null;
        mainReleaseFragment = null;
        mainfData = null;
        fragment_main = inflater.inflate(R.layout.fragment_recommend, null);
        initView(fragment_main);
        initFragments();
        return fragment_main;
    }

    private void initView(View fragment_main) {
        mMainfViewPager = (ViewPager) fragment_main.findViewById(R.id.mainf_viewPager);
        mMainNews = (RadioButton) fragment_main.findViewById(R.id.Main_news);
        mMainhots = (RadioButton) fragment_main.findViewById(R.id.Main_hots);
        mMainhots.setOnClickListener(this);
        mMainNews.setOnClickListener(this);
    }

    public void initFragments() {
        mainfData = new ArrayList<>();
        mainNewsFragment = new RecommendNewsFragment();
        mainReleaseFragment = new RecommendHotFragment();
        mainfData.add(mainNewsFragment);
        mainfData.add(mainReleaseFragment);

        mMainfViewPager.setAdapter(new RecommendNewsAdapter(this.getChildFragmentManager(), mainfData));
        mMainfViewPager.addOnPageChangeListener(mViewPagerSelectLinner);
    }


    private ViewPager.OnPageChangeListener mViewPagerSelectLinner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
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
        switch (v.getId()) {
            case R.id.Main_news:
                mMainfViewPager.setCurrentItem(0);
                break;
            case R.id.Main_hots:
                mMainfViewPager.setCurrentItem(1);
                break;
        }
    }
}
