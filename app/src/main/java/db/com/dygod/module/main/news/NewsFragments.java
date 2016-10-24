package db.com.dygod.module.main.news;

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
import java.util.List;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.bean.MovieInfoEntity;
import db.com.dygod.define.UrlConstant;
import db.com.dygod.module.common.MovieInfoActivity;
import db.com.dygod.module.main.recommend.adapter.RecommendNewsRecyAdapter;
import db.com.dygod.network.GetMovieInfoServant;
import db.com.dygod.network.GetClassifyServant;
import db.com.dygod.network.base.NetWorkListener;
import db.com.dygod.utils.ToastUtil;
import db.com.dygod.widget.StyleDialog;

/**
 * 最新电影
 * Created by zdb on 2016/5/17.
 */
public class NewsFragments extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener , View.OnClickListener {
    private View newsView;
    private int currentPageIndex = 1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<MainNesEntity> data = new ArrayList<>();
    private RecommendNewsRecyAdapter mAdapter;
    private StyleDialog mDialog;
    private boolean isAutoRefresh=true;
    private boolean isPrepared;

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        System.out.println("最新电影lazyLoad");
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(newsView!=null){
            isPrepared = false;
            System.out.println("最新电影onCreateView===不为空");
//            lazyLoad();
            return newsView;
        }
        newsView = View.inflate(mContext, R.layout.fragment_news, null);
        initView(newsView);
        isPrepared = true;
        System.out.println("最新电影onCreateView");
        lazyLoad();
        return newsView;
    }

    private void initData() {
        mAdapter = new RecommendNewsRecyAdapter(data,mItemClickListener,mLoadMoreListener);
        GetClassifyServant mainDataServant = new GetClassifyServant();
        mainDataServant.getNewsData(UrlConstant.getInstance().getNewsUrlCache(),false, true, currentPageIndex, mNetWorkListener);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getNetData();
    }

    private RecommendNewsRecyAdapter.RecyclerViewLoadMoreListener mLoadMoreListener=new RecommendNewsRecyAdapter.RecyclerViewLoadMoreListener() {
        @Override
        public void onLoadMore() {
            currentPageIndex++;
            getNetData();
        }
    };

    /**
     * 条目点击事件监听
     **/
    private RecommendNewsRecyAdapter.RecyclerViewItemClickListener mItemClickListener = new RecommendNewsRecyAdapter.RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mDialog == null) {
                mDialog = new StyleDialog(NewsFragments.this.getContext(), "正在获取数据");
            }
            mDialog.show();
            //根据地址获取电影信息
            GetMovieInfoServant movieInfoServant = new GetMovieInfoServant();
            movieInfoServant.getMovieInfoData(UrlConstant.mainUrl+data.get(position).getTitlinkle(), new NetWorkListener<MovieInfoEntity>() {
                @Override
                public void successful(MovieInfoEntity movieInfoEntity) {
                    mDialog.dismiss();
                    MovieInfoActivity.start(NewsFragments.this.getContext(), movieInfoEntity);
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
        GetClassifyServant mainDataServant = new GetClassifyServant();
        mainDataServant.getNewsData(UrlConstant.getInstance().getNewsUrlCache(),true, false, currentPageIndex, mNetWorkListener);
    }

    private NetWorkListener mNetWorkListener = new NetWorkListener<List<MainNesEntity>>() {

        @Override
        public void successful(List<MainNesEntity> mainNesEntities) {
            if(isAutoRefresh){
                isAutoRefresh=false;
                stopRefresh();
            }else{
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (mainNesEntities != null && mainNesEntities.size() > 0) {
                if(currentPageIndex==1){
                    data.clear();
                }
                data.addAll(mainNesEntities);
                mAdapter.notifyDataSetChanged();
            }else{
                mAdapter.setLoadErrInfo();
                ToastUtil.showMsg(R.string.toast_no_data);
            }
        }

        @Override
        public void failure(IOException e) {
            if(isAutoRefresh){
                isAutoRefresh=false;
                stopRefresh();
            }else{
                mSwipeRefreshLayout.setRefreshing(false);
            }
            mAdapter.setLoadErrInfo();
            ToastUtil.showMsg(R.string.toast_server_err);
        }
    };

    @Override
    public void onRefresh() {
        currentPageIndex=1;
        getNetData();
    }
    /**
     * 自动刷新
     */
    private void autoRefresh(){
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
    private void stopRefresh(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_news_fab:
                mRecyclerView.smoothScrollToPosition(0);
                break;
        }
    }
}