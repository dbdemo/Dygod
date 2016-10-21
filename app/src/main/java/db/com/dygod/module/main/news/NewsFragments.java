package db.com.dygod.module.main.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.network.GetNewsServant;
import db.com.dygod.network.base.NetWorkListener;
import db.com.dygod.utils.ToastUtil;

/**
 * 最新电影
 * Created by zdb on 2016/5/17.
 */
public class NewsFragments extends BaseFragment {
    private View newsView;
    private int currentPageIndex=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        newsView = View.inflate(mContext, R.layout.fragment_news, null);
        getNetData();
        return newsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void getNetData() {
        GetNewsServant mainDataServant = new GetNewsServant();
        mainDataServant.getNewsData(true, false, currentPageIndex,mNetWorkListener);
    }
    private NetWorkListener mNetWorkListener = new NetWorkListener<List<MainNesEntity>>() {

        @Override
        public void successful(List<MainNesEntity> mainNesEntities) {
            System.out.println("========"+mainNesEntities.size());
        }

        @Override
        public void failure(IOException e) {
            ToastUtil.showMsg(R.string.toast_server_err);
        }
    };
}