package db.com.dygod.module.main.news.fragment;


import android.os.Bundle;
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
import db.com.dygod.module.main.fragment.MainFragment;
import db.com.dygod.module.main.news.adapter.MainNewsDataAdapter;
import db.com.dygod.network.GetMainDataServant;
import db.com.dygod.network.base.NetWorkListener;

/**
*  from zdb  create at 2016/5/20  16:53
*   新片电影
**/
public class MainNewsFragment extends BaseFragment {


    private PullToRefreshListView mNewsList;
    private ArrayList<MainNesEntity> mMainNesEntities = new ArrayList<>();
    private MainNewsDataAdapter mAdapter;
   private  MainFragment mMainFragment;

    public void setmMainFragment(MainFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    public MainNewsFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMainFragment!=null&&mAdapter!=null){
            mMainNesEntities.clear();
            mMainNesEntities.addAll(mMainFragment.getmMainEntity().getMainNesEntities());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_main_news, container, false);
        Bundle bundle = getArguments();
        ArrayList<MainNesEntity> entity = bundle.getParcelableArrayList(MainFragment.ENTITY_NAME);
        mMainNesEntities.addAll(entity);
        initView(newView);
        initData();
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
            if(mMainFragment!=null){
                mMainFragment.setmMainEntity(mainEntity);
            }
        }

        @Override
        public void failure(IOException e) {
            mNewsList.onRefreshComplete();
        }
    };

    public void getNetData() {
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(true,false,mNetWorkListener);
    }
}
