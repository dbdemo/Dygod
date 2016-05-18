package db.com.dygod.module.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;

/**
 * Created by Administrator on 2016/5/17.
 */
public class MainFragment extends BaseFragment{
    @Override
    protected void setTitles() {
        setTitle("主页");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_main=inflater.inflate(R.layout.fragment_main, null);
        return fragment_main;
    }
}
