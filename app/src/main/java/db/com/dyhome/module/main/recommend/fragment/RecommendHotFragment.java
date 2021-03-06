package db.com.dyhome.module.main.recommend.fragment;


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

import db.com.dyhome.R;
import db.com.dyhome.base.BaseFragment;
import db.com.dyhome.bean.MainEntity;
import db.com.dyhome.bean.MainNesEntity;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.module.common.MovieInfoActivity;
import db.com.dyhome.module.main.recommend.RecommendMainFragment;
import db.com.dyhome.module.main.recommend.adapter.RecommendNewsRecyAdapter;
import db.com.dyhome.network.GetMainDataServant;
import db.com.dyhome.network.GetMovieInfoServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.ToastUtil;
import db.com.dyhome.widget.StyleDialog;

/**
 * from zdb  create at 2016/5/20  14:46
 * 主页2016最新必看大片
 **/
public class RecommendHotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mReleaseList;
    private ArrayList<MainNesEntity> mMainNesEntities = new ArrayList<>();
    private RecommendNewsRecyAdapter mAdapter;
    private RecommendMainFragment mMainFragment;
    private StyleDialog mDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View ReleView;

    public void setmMainFragment(RecommendMainFragment mMainFragment) {
        this.mMainFragment = mMainFragment;
    }

    public RecommendHotFragment() {
    }

    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMainFragment != null && mAdapter != null) {
            mMainNesEntities.clear();
            mMainNesEntities.addAll(mMainFragment.getmMainEntity().getMainReleEntities());
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (ReleView != null) {
            return ReleView;
        }
        ReleView = inflater.inflate(R.layout.fragment_recommend_hot, container, false);
        initView(ReleView);
        Bundle bundle = getArguments();
        ArrayList<MainNesEntity> entity = bundle.getParcelableArrayList(RecommendMainFragment.ENTITY_NAME);
        mMainNesEntities.addAll(entity);
        initData();
        return ReleView;
    }

    private void initData() {
        mAdapter = new RecommendNewsRecyAdapter(mMainNesEntities, mItemClickListener, null);
        mAdapter.setOnItemClickListener(mItemClickListener);
        mReleaseList.setHasFixedSize(true);
        mReleaseList.setAdapter(mAdapter);
        mReleaseList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mReleaseList.setItemAnimator(new DefaultItemAnimator());
        // autoRefresh();不需要刷新
        // getNetData();不需要获取数据
    }

    private void initView(View releView) {
        mReleaseList = (RecyclerView) releView.findViewById(R.id.main_hot_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) releView.findViewById(R.id.main_hot_SwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private NetWorkListener mNetWorkListener = new NetWorkListener<MainEntity>() {

        @Override
        public void successful(MainEntity mainEntity) {
            mSwipeRefreshLayout.setRefreshing(false);

            if (mainEntity != null && mainEntity.getMainNesEntities().size() > 0) {
                mMainNesEntities.clear();
                mMainNesEntities.addAll(mainEntity.getMainReleEntities());
                mAdapter.notifyDataSetChanged();
                if (mMainFragment != null) {
                    mMainFragment.setmMainEntity(mainEntity);
                }
            } else {
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

    /**
     * 条目点击事件监听
     **/
    private RecommendNewsRecyAdapter.RecyclerViewItemClickListener mItemClickListener = new RecommendNewsRecyAdapter.RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mDialog == null) {
                mDialog = new StyleDialog(RecommendHotFragment.this.getContext(), "正在获取数据");
            }
            mDialog.show();
            //根据地址获取电影信息
            GetMovieInfoServant movieInfoServant = new GetMovieInfoServant();
            movieInfoServant.getMovieInfoData(mMainNesEntities.get(position).getTitlinkle(), new NetWorkListener<MovieInfoEntity>() {
                @Override
                public void successful(MovieInfoEntity movieInfoEntity) {
                    mDialog.dismiss();
                    MovieInfoActivity.start(RecommendHotFragment.this.getContext(), movieInfoEntity);
                }

                @Override
                public void failure(IOException e) {
                    mDialog.dismiss();
                    ToastUtil.showMsg(R.string.toast_movie_info_err);
                }
            });
        }
    };

    /***
     * 下拉刷新监听
     */
    @Override
    public void onRefresh() {
        getNetData();
    }


}
