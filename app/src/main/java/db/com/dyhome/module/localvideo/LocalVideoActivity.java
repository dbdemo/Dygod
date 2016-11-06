package db.com.dyhome.module.localvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.bean.LocalVideoEntity;
import db.com.dyhome.module.localvideo.adapter.LocalVideoAdapter;
import db.com.dyhome.module.localvideo.provider.LocalProvider;
import db.com.dyhome.module.start.StartVideoActiviy;
import db.com.dyhome.utils.ToastUtil;

/**
 * Created by Administrator on 2016/11/2.
 */

public class LocalVideoActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView listView;
    private List<LocalVideoEntity> data;
    private List<LocalVideoEntity> deleteData = new ArrayList<>();
    private TextView nodata;
    private LocalProvider localProvider;
    private LinearLayout detal_layout;
    private LinearLayout dele_layout;
    private LinearLayout local_video_haveData;
    private LocalVideoAdapter adapter;
    private TextView local_video_delete_to;

    @Override
    protected int setBodyView() {
        return R.layout.activity_local_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("本地视频");
        initView();
        initData();
    }

    private void initData() {
        local_video_haveData.setVisibility(View.GONE);
        nodata.setVisibility(View.VISIBLE);
        nodata.setText("正在加载数据");
        localProvider = new LocalProvider(linner);
        localProvider.getAllList();
    }

    private void initView() {
        listView = (RecyclerView) findViewById(R.id.localVideoList);
        nodata = (TextView) findViewById(R.id.local_video_nodata);
        nodata.setOnClickListener(this);
        detal_layout = (LinearLayout) findViewById(R.id.local_video_detal_layout);
        dele_layout = (LinearLayout) findViewById(R.id.local_video_delete_layout);
        local_video_haveData = (LinearLayout) findViewById(R.id.local_video_haveData);
        findViewById(R.id.local_video_refresh).setOnClickListener(this);
        findViewById(R.id.local_video_delete).setOnClickListener(this);
        findViewById(R.id.local_video_all).setOnClickListener(this);
        local_video_delete_to = (TextView) findViewById(R.id.local_video_delete_to);
        local_video_delete_to.setOnClickListener(this);
        findViewById(R.id.local_video_cancel).setOnClickListener(this);
    }

    private LocalVideoAdapter.ItemClickListener itemClickListener = new LocalVideoAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            LocalVideoEntity entity = data.get(position);
            entity.setImage(null);
            Intent intent = new Intent();
            intent.putExtra(StartVideoActiviy.Entity_tag, entity);
            intent.setClass(LocalVideoActivity.this, StartVideoActiviy.class);
            startActivity(intent);
        }
    };

    private LocalVideoAdapter.ItemCheckedListener itemCheckedListener = new LocalVideoAdapter.ItemCheckedListener() {
        @Override
        public void onCheckedListener(boolean check, int position) {
            if (check) {
                deleteData.add(data.get(position));
            } else {
                deleteData.remove(data.get(position));
            }
            local_video_delete_to.setTextColor(getResources().getColor(R.color.bgcolor10));
            local_video_delete_to.setText("删除(" + deleteData.size() + ")");
        }
    };
    private LocalProvider.LocalVideoLinner linner = new LocalProvider.LocalVideoLinner() {
        @Override
        public void success(List<LocalVideoEntity> list) {
            data = list;
            if (data == null || data.size() == 0) {
                nodata.setText("没有加载到数据");
                return;
            }
            local_video_haveData.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            nodata.setText("加载数据完成");
            adapter = new LocalVideoAdapter(data, itemClickListener, itemCheckedListener);
            listView.setHasFixedSize(true);
            listView.setAdapter(adapter);
            listView.setLayoutManager(new LinearLayoutManager(LocalVideoActivity.this));
            listView.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        public void fail() {
            listView.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            nodata.setText("加载数据错误，点击重试");
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.local_video_refresh:
                initData();
                break;
            case R.id.local_video_delete:
                adapter.setCb(false);
                adapter.notifyDataSetChanged();
                detal_layout.setVisibility(View.GONE);
                dele_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.local_video_delete_to:
                if (deleteData != null && deleteData.size() == 0) {
                    ToastUtil.showMsg("请选择需要删除的文件");

                    return;
                }
                for (int i = 0; i < deleteData.size(); i++) {
                    File file = new File(deleteData.get(i).getPath());
                    file.delete();
                    data.remove(deleteData.get(i));
                    adapter.notifyDataSetChanged();
                }
                local_video_delete_to.setText("删除(0)");
                break;
            case R.id.local_video_all:
                adapter.setAllCheck(!adapter.isAllCheck());
                adapter.notifyDataSetChanged();
                break;
            case R.id.local_video_cancel:
                adapter.setCb(true);
                adapter.notifyDataSetChanged();
                detal_layout.setVisibility(View.VISIBLE);
                dele_layout.setVisibility(View.GONE);
                break;
        }
    }
}