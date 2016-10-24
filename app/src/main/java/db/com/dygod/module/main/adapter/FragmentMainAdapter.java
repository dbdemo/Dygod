package db.com.dygod.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import db.com.dygod.base.BaseFragment;

/**
 * Created by zdb on 2016/5/17.
 * 主页分类适配器
 */
public class FragmentMainAdapter extends FragmentStatePagerAdapter {

    private ArrayList<BaseFragment> data;

    public FragmentMainAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentMainAdapter(FragmentManager fm, ArrayList<BaseFragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "推荐";
            case 1:
                return "最新电影";
            case 2:
                return "日韩电影";
            case 3:
                return "国内电影";
            case 4:
                return "欧美电影";
            case 5:
                return "最新综艺";
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
