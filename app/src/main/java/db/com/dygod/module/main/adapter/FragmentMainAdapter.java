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
        return data.get(position).getTitle();
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
