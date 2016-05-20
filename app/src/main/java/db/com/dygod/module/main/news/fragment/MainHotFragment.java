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
*  from zdb  create at 2016/5/20  14:46
*   主页最新发布Fragment
**/
public class MainHotFragment extends BaseFragment {

    private PullToRefreshListView mReleaseList;
    private ArrayList<MainNesEntity> mMainNesEntities = new ArrayList<>();
    private MainNewsDataAdapter mAdapter;
    private MainFragment mMainFragment;

    public void setmMainFragment(MainFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    public MainHotFragment() {
    }
    @Override
    public void onResume() {
        super.onResume();
        if(mMainFragment!=null&&mAdapter!=null){
            mMainNesEntities.clear();
            mMainNesEntities.addAll(mMainFragment.getmMainEntity().getMainReleEntities());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ReleView=inflater.inflate(R.layout.fragment_main_release, container, false);
        Bundle bundle = getArguments();
        ArrayList<MainNesEntity> entity = bundle.getParcelableArrayList(MainFragment.ENTITY_NAME);
        mMainNesEntities.addAll(entity);
        initView(ReleView);
        initData();

        return ReleView;
    }

    private void initData() {
        mAdapter = new MainNewsDataAdapter(getActivity(), mMainNesEntities);
        mReleaseList.setAdapter(mAdapter);
    }

    private void initView(View releView) {
        mReleaseList = (PullToRefreshListView) releView.findViewById(R.id.main_release_list);
        mReleaseList.setOnRefreshListener(mOnRefreshListener);
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
            mReleaseList.onRefreshComplete();
            mMainNesEntities.clear();
            mMainNesEntities.addAll(mainEntity.getMainReleEntities());
            mAdapter.notifyDataSetChanged();
            if(mMainFragment!=null){
                mMainFragment.setmMainEntity(mainEntity);
            }
        }

        @Override
        public void failure(IOException e) {
            mReleaseList.onRefreshComplete();
        }
    };

    public void getNetData() {
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(true,false,mNetWorkListener);
    }
}
