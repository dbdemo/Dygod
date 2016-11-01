package db.com.dyhome.module.main.recommend.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseFragment;
import db.com.dyhome.bean.MainEntity;
import db.com.dyhome.bean.MainNesEntity;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.module.common.MovieInfoActivity;
import db.com.dyhome.module.main.recommend.adapter.RecommendNewsRecyAdapter;
import db.com.dyhome.network.GetMainDataServant;
import db.com.dyhome.network.GetMovieInfoServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.ToastUtil;
import db.com.dyhome.widget.StyleDialog;

/**
 * from zdb  create at 2016/5/20  16:53
 * 主页2016 新片精品
 **/
public class RecommendNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private View ecommendNewsView;
    private int currentPageIndex = 1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<MainNesEntity> data = new ArrayList<>();
    private RecommendNewsRecyAdapter mAdapter;
    private StyleDialog mDialog;
    private boolean isAutoRefresh = true;
    private boolean isFirst = true;

    @Override
    protected void lazyLoad() {
        if (isFirst) {
            isFirst = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isVisible) {
                        autoRefresh();
                        getNetData();
                    }
                }
            }, 500);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (ecommendNewsView != null) {
            return ecommendNewsView;
        }
        System.out.println("news===");
        ecommendNewsView = View.inflate(mContext, R.layout.fragment_news, null);
        initView(ecommendNewsView);
        initCache();
        return ecommendNewsView;
    }

    private void initCache() {
        mAdapter = new RecommendNewsRecyAdapter(data, mItemClickListener, null);
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(false, true, mNetWorkListener);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     * 条目点击事件监听
     **/
    private RecommendNewsRecyAdapter.RecyclerViewItemClickListener mItemClickListener = new RecommendNewsRecyAdapter.RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mDialog == null) {
                mDialog = new StyleDialog(RecommendNewsFragment.this.getContext(), "正在获取数据");
            }
            mDialog.show();

            String url = data.get(position).getTitlinkle();
            if (TextUtils.isEmpty(url)) {
                mDialog.dismiss();
                ToastUtil.showMsg(R.string.toast_movie_info_err);
                return;
            }
            if (!url.startsWith("http")) {
                url = UrlConstant.mainUrl + url;
            }

            //根据地址获取电影信息
            GetMovieInfoServant movieInfoServant = new GetMovieInfoServant();
            movieInfoServant.getMovieInfoData(url, new NetWorkListener<MovieInfoEntity>() {
                @Override
                public void successful(MovieInfoEntity movieInfoEntity) {
                    mDialog.dismiss();
                    MovieInfoActivity.start(RecommendNewsFragment.this.getContext(), movieInfoEntity);
                }

                @Override
                public void failure(IOException e) {
                    mDialog.dismiss();
                    ToastUtil.showMsg(R.string.toast_movie_info_err);
                }
            });
        }
    };

    private void initView(View newsView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) newsView.findViewById(R.id.main_news_SwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) newsView.findViewById(R.id.main_news_list);
        (newsView.findViewById(R.id.main_news_fab)).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void getNetData() {
        GetMainDataServant mainDataServant = new GetMainDataServant();
        mainDataServant.getMainData(true, false, mNetWorkListener);
    }

    private NetWorkListener mNetWorkListener = new NetWorkListener<MainEntity>() {

        @Override
        public void successful(MainEntity mainEntity) {
            ArrayList<MainNesEntity> mainNesEntities = null;
            if (isAutoRefresh) {
                isAutoRefresh = false;
                stopRefresh();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (mainEntity != null) {
                mainNesEntities = mainEntity.getMainNesEntities();
            }
            if (mainNesEntities != null && mainNesEntities.size() > 0) {
                if (currentPageIndex == 1) {
                    data.clear();
                }
                data.addAll(mainNesEntities);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.setLoadErrInfo();
                ToastUtil.showMsg(R.string.toast_no_data);
            }
        }

        @Override
        public void failure(IOException e) {
            if (isAutoRefresh) {
                isAutoRefresh = false;
                stopRefresh();
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            mAdapter.setLoadErrInfo();
            ToastUtil.showMsg(R.string.toast_server_err);
        }
    };

    @Override
    public void onRefresh() {
        currentPageIndex = 1;
        getNetData();
    }

    /**
     * 自动刷新
     */
    private void autoRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    /**
     * 停止自动刷新
     */
    private void stopRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_news_fab:
                mRecyclerView.smoothScrollToPosition(0);
                break;
        }
    }
}
