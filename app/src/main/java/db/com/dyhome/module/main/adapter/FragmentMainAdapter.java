package db.com.dyhome.module.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import db.com.dyhome.base.BaseFragment;

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
        switch (position) {
            case 0:
                return "推荐";
            case 1:
                return "动作";
            case 2:
                return "战争";
            case 3:
                return "剧情";
            case 4:
                return "爱情";
            case 5:
                return "科幻";
            case 6:
                return "悬疑";
            case 7:
                return "家庭";
            case 8:
                return "犯罪";
            case 9:
                return "恐怖";
            case 10:
                return "动画";
            case 11:
                return "喜剧";
            case 12:
                return "惊悚";
            case 13:
                return "冒险";
            case 14:
                return "电视剧";
            case 15:
                return "美国";
            case 16:
                return "日本";
            case 17:
                return "香港";
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
