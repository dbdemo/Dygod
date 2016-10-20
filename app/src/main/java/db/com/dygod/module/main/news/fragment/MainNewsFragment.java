package db.com.dygod.module.main.news.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.bean.MainEntity;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.bean.MovieInfoEntity;
import db.com.dygod.module.common.MovieInfoActivity;
import db.com.dygod.module.main.fragment.MainFragment;
import db.com.dygod.module.main.news.adapter.MainNewsRecyAdapter;
import db.com.dygod.network.GetMainDataServant;
import db.com.dygod.network.GetMovieInfoServant;
import db.com.dygod.network.base.NetWorkListener;
import db.com.dygod.utils.ToastUtil;
import db.com.dygod.widget.StyleDialog;

/**
 * from zdb  create at 2016/5/20  16:53
 * 主页2016 新片精品
 **/
public class MainNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mNewsList;
    private ArrayList<MainNesEntity> mMainNesEntities = new ArrayList<>();
//    private MainNewsDataAdapter mAdapter;
    private MainNewsRecyAdapter mAdapter;

    private MainFragment mMainFragment;
    private StyleDialog mDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public void setmMainFragment(MainFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    public MainNewsFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMainFragment != null && mAdapter != null) {
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
        mAdapter = new MainNewsRecyAdapter(mMainNesEntities,mItemClickListener);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mNewsList.setHasFixedSize(true);
        mNewsList.setAdapter(mAdapter);
        mNewsList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mNewsList.setItemAnimator(new DefaultItemAnimator());
        getNetData();
    }

    private void initView(View newView) {
        mNewsList = (RecyclerView) newView.findViewById(R.id.main_news_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) newView.findViewById(R.id.main_news_swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }


    /**
     * 条目点击事件监听
     **/
    private MainNewsRecyAdapter.RecyclerViewItemClickListener mItemClickListener = new MainNewsRecyAdapter.RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mDialog == null) {
                mDialog = new StyleDialog(MainNewsFragment.this.getContext(), "正在获取数据");
            }
            mDialog.show();
            //根据地址获取电影信息
            GetMovieInfoServant movieInfoServant = new GetMovieInfoServant();
            movieInfoServant.getMovieInfoData(mMainNesEntities.get(position).getTitlinkle(), new NetWorkListener<MovieInfoEntity>() {

                @Override
                public void successful(MovieInfoEntity movieInfoEntity) {
                    mDialog.dismiss();
                    MovieInfoActivity.start(MainNewsFragment.this.getContext(), movieInfoEntity);
                }

                @Override
                public void failure(IOException e) {
                    mDialog.dismiss();
                    ToastUtil.showMsg(R.string.toast_movie_info_err);
                }
            });
        }
    };

    private NetWorkListener mNetWorkListener = new NetWorkListener<MainEntity>() {
        @Override
        public void successful(MainEntity mainEntity) {
            mSwipeRefreshLayout.setRefreshing(false);
            if(mainEntity!=null&&mainEntity.getMainNesEntities().size()>0) {
                mMainNesEntities.clear();
                mMainNesEntities.addAll(mainEntity.getMainNesEntities());
                mAdapter.notifyDataSetChanged();
                if (mMainFragment != null) {
                    mMainFragment.setmMainEntity(mainEntity);
                }
            }else{
                ToastUtil.showMsg(R.string.toast_no_data);
            }
        }

        @Override
        public void failure(IOException e) {
            mSwipeRefreshLayout.setRefreshing(false);
            ToastUtil.showMsg(R.string.toast_server_err);
        }
    };

    public void getNetData() {
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(true, false, mNetWorkListener);
    }

    @Override
    public void onRefresh() {
        getNetData();
    }
}
