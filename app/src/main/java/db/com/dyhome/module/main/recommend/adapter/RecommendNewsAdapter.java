package db.com.dyhome.module.main.recommend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import db.com.dyhome.base.BaseFragment;

/**
 * Created by Administrator on 2016/5/19.
 */
public class RecommendNewsAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> data;

    public RecommendNewsAdapter(FragmentManager fm) {
        super(fm);
    }

    public RecommendNewsAdapter(FragmentManager fm, ArrayList<BaseFragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "最新电影";
            case 1:
                return "最热电影";
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
