package db.com.dygod.module.main.news.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dygod.R;
import db.com.dygod.base.BaseFragment;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.network.GetMainDataServant;
import db.com.dygod.network.base.NetWorkListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainNewsFragment extends BaseFragment {


    private ListView mNewsList;
    private PtrClassicFrameLayout ptrFrame;
    private ArrayList<MainNesEntity> mainNesEntities;

    public MainNewsFragment() {
        
    }


    @Override
    protected void setTitles() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newView=inflater.inflate(R.layout.fragment_main_news, container, false);
        initView(newView);
        initData();
        return newView;
    }

    private void initData() {
        if(mainNesEntities==null){
            GetMainDataServant mainDataServant=new GetMainDataServant();
            mainDataServant.getMainData(mNetWorkListener);
        }
    }

    private void initView(View newView) {
        ptrFrame = (PtrClassicFrameLayout) newView.findViewById(R.id.main_news_ptr_frame);
        mNewsList = (ListView) newView.findViewById(R.id.main_news_list);
        ptrFrame.setPtrHandler(mPtrDefaultHandler);
    }

    private PtrDefaultHandler mPtrDefaultHandler=new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {



        }
    };
    private NetWorkListener mNetWorkListener=new NetWorkListener() {
        @Override
        public void successful(Object o) {


        }

        @Override
        public void failure(IOException e) {



        }
    };
}
