package db.com.dygod.module.main.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import db.com.dygod.base.BaseFragment;

/**
 * Created by Administrator on 2016/5/19.
 */
public class MainNewsAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> data;

    public MainNewsAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainNewsAdapter(FragmentManager fm, ArrayList<BaseFragment> data) {
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
