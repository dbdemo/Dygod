package db.com.dyhome.module.search;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.bean.MainNesEntity;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.module.common.MovieInfoActivity;
import db.com.dyhome.module.main.recommend.adapter.RecommendNewsRecyAdapter;
import db.com.dyhome.network.GetMovieInfoServant;
import db.com.dyhome.network.SearchServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.ToastUtil;
import db.com.dyhome.widget.StyleDialog;

/**
 * Created by zdb on 2016/10/22.
 *
 */
public class SearchActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<MainNesEntity> dataEntitys;
    private RecommendNewsRecyAdapter mAdapter;
    private TextView mDataErr;
    private StyleDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarvisibility(View.VISIBLE);
        String searchText=getIntent().getStringExtra("searchText");
        getSearchData(searchText);
        mToolbar.setNavigationIcon(R.mipmap.toolbar_back);
        initView();
    }

    private void initView() {

        mRecyclerView= (RecyclerView) findViewById(R.id.search_list);
         mDataErr= (TextView) findViewById(R.id.no_data);
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_search;
    }

    private NetWorkListener mNetWorkListener=new NetWorkListener<List<MainNesEntity>>(){

        @Override
        public void successful(List<MainNesEntity> data) {

            mDialog.cancel();
            if(data==null||data.size()==0){
                mDataErr.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                ToastUtil.showMsg("没有搜索到内容");
                return;
            }
            mDataErr.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            dataEntitys=data;
            mAdapter = new RecommendNewsRecyAdapter(dataEntitys,mItemClickListener,null);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        public void failure(IOException e) {
            mDialog.cancel();
        }
    };

    /**
     * 条目点击事件监听
     **/
    private RecommendNewsRecyAdapter.RecyclerViewItemClickListener mItemClickListener = new RecommendNewsRecyAdapter.RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mDialog == null) {
                mDialog = new StyleDialog(SearchActivity.this, "正在获取数据");
            }
            mDialog.show();
            //根据地址获取电影信息
            GetMovieInfoServant movieInfoServant = new GetMovieInfoServant();
            movieInfoServant.getMovieInfoData(UrlConstant.mainUrl+dataEntitys.get(position).getTitlinkle(), new NetWorkListener<MovieInfoEntity>() {
                @Override
                public void successful(MovieInfoEntity movieInfoEntity) {
                    mDialog.dismiss();
                    MovieInfoActivity.start(SearchActivity.this, movieInfoEntity);
                }

                @Override
                public void failure(IOException e) {
                    mDialog.dismiss();
                    ToastUtil.showMsg(R.string.toast_movie_info_err);
                }
            });
        }
    };

    @Override
    public void search() {
        getSearchData(mSearchText.getText().toString().trim());
    }
    public void getSearchData(String searchStr){
        if (mDialog == null) {
            mDialog = new StyleDialog(SearchActivity.this, "正在获取数据");
        }
        mDialog.show();
        SearchServant searchServant=new SearchServant();
        searchServant.getSearchData(searchStr, 0, mNetWorkListener);
    }
}