package db.com.dygod.module.main.news.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.bean.MainEntity;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.module.main.news.adapter.MainNewsDataAdapter;
import db.com.dygod.network.GetMainDataServant;
import db.com.dygod.network.base.NetWorkListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainNewsFragment extends BaseFragment {


    private PullToRefreshListView mNewsList;
    private ArrayList<MainNesEntity> mMainNesEntities = new ArrayList<>();
    private MainNewsDataAdapter mAdapter;

    public MainNewsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_main_news, container, false);
        initView(newView);
        initData();
        getNetData();
        return newView;
    }

    private void initData() {
        mAdapter = new MainNewsDataAdapter(getActivity(), mMainNesEntities);
        mNewsList.setAdapter(mAdapter);
    }

    private void initView(View newView) {
        mNewsList = (PullToRefreshListView) newView.findViewById(R.id.main_news_list);
        mNewsList.setOnRefreshListener(mOnRefreshListener);
    }

    private PullToRefreshBase.OnRefreshListener<ListView> mOnRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            getNetData();
        }
    };
    private NetWorkListener mNetWorkListener = new NetWorkListener<MainEntity>() {

        @Override
        public void successful(MainEntity mainEntity) {
            mNewsList.onRefreshComplete();
            mMainNesEntities.clear();
            mMainNesEntities.addAll(mainEntity.getMainNesEntities());
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void failure(IOException e) {
            mNewsList.onRefreshComplete();
        }
    };

    public void getNetData() {
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(mNetWorkListener);
    }
}
