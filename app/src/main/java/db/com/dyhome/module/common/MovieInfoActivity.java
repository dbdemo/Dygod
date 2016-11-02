package db.com.dyhome.module.common;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.utils.HexUtils;
import db.com.dyhome.utils.ImageLoaderUtils;
import db.com.dyhome.utils.ShareUtils;
import db.com.dyhome.utils.ToastUtil;

import static db.com.dyhome.module.common.UMKey.download_move;

/**
 * 电影详情
 * Created by zdb on 15/12/28.
 */
public class MovieInfoActivity extends BaseActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private final static String entityName = "movieInfoEntity";
    private TextView name;
    private TextView introduce;
    private ImageView img;
    private ImageView capture;
    private RadioGroup downloadRg;
    private CheckBox checkAll;

    public static void start(Context context, MovieInfoEntity movieInfoEntity) {
        Intent intent = new Intent(context, MovieInfoActivity.class);
        intent.putExtra(entityName, movieInfoEntity);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieInfoEntity = getIntent().getParcelableExtra(entityName);
        initView();
        initData();
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_movieinfo;
    }

    private void initData() {
        name.setText(movieInfoEntity.getName());
        introduce.setText(movieInfoEntity.getIntroduce());
        ImageLoaderUtils.displayAvatar(movieInfoEntity.getMoveImg(), img);
        addDownLoadLayout(movieInfoEntity.getAddress());
        ImageLoaderUtils.displayAvatar(movieInfoEntity.getMovieCapture(), capture);
    }

    /**
     * 设置下载链接
     *
     * @param address
     */
    private void addDownLoadLayout(List<String> address) {
        for (int i = 0; i < address.size(); i++) {
            CheckBox cb = new CheckBox(this);
            if (i == 0) {
                cb.setChecked(true);
            }
            cb.setText(address.get(i));
            cb.setTextColor(getResources().getColor(R.color.bgcolor4_1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 10);
            cb.setLayoutParams(params);
            downloadRg.addView(cb);
        }
    }

    private void initView() {
        name = (TextView) findViewById(R.id.movieinfo_name);
        introduce = (TextView) findViewById(R.id.movieinfo_introduce);
        img = (ImageView) findViewById(R.id.movieinfo_img);
        capture = (ImageView) findViewById(R.id.movieinfo_Capture);
        img.setOnClickListener(this);
        capture.setOnClickListener(this);
        downloadRg = (RadioGroup) findViewById(R.id.download_rg);
        checkAll = (CheckBox) findViewById(R.id.checkAll);
        checkAll.setOnCheckedChangeListener(this);
        findViewById(R.id.movieinfo_start).setOnClickListener(this);
        findViewById(R.id.movieinfo_cody).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.movieinfo_start:
                ArrayList<String> links = getCheckedDatas();
                ArrayList<String> linkData = (ArrayList<String>) movieInfoEntity.getAddress();
                if (linkData.size() > 0) {
                    if (links.size() > 0) {
                        String link = "";
                        for (int i = 0; i < links.size(); i++) {
                            link = link + links.get(i);
                        }
                        if (!TextUtils.isEmpty(link)) {
                            if (HexUtils.getHaveXunLei(this)) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                intent.addCategory("android.intent.category.DEFAULT");
                                startActivity(intent);
                                MobclickAgent.onEvent(this, download_move);
                            } else {
                                ToastUtil.showMsg("无法打开下载链接，请安装迅雷");
                            }
                        }
                    } else {
                        ToastUtil.showMsg("请选择需要下载的电影链接");
                    }
                } else {
                    ToastUtil.showMsg("没有获取到下载链接");
                }
                break;
            case R.id.movieinfo_cody:
                ArrayList<String> linkscody = getCheckedDatas();
                ArrayList<String> linkDatacody = (ArrayList<String>) movieInfoEntity.getAddress();
                if (linkDatacody.size() > 0) {
                    if (linkscody.size() > 0) {
                        String link = "";
                        for (int i = 0; i < linkscody.size(); i++) {
                            link = link + linkscody.get(i);
                        }
                        ClipboardManager c = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        c.setText(link);
                        ToastUtil.showMsg("下载地址已复制到剪切板");
                    } else {
                        ToastUtil.showMsg("请选择需要下载的电影链接");
                    }
                } else {
                    ToastUtil.showMsg("没有获取到下载链接");
                }
                break;
            case R.id.movieinfo_Capture:
                EnlargementImageActivity.start(this, movieInfoEntity.getMovieCapture());
                break;
            case R.id.movieinfo_img:
                EnlargementImageActivity.start(this, movieInfoEntity.getMoveImg());
                break;
        }
    }

    public ArrayList<String> getCheckedDatas() {
        ArrayList<String> arrs = new ArrayList<>();
        for (int i = 0; i < downloadRg.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) downloadRg.getChildAt(i);
            if (checkBox.isChecked()) {
                arrs.add(checkBox.getText().toString().trim());
            }
        }
        return arrs;
    }

    public String getCheckedStr() {
        String urls = "";
        for (int i = 0; i < downloadRg.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) downloadRg.getChildAt(i);
            if (checkBox.isChecked()) {
                urls += checkBox.getText().toString().trim();
            }
        }
        return urls;
    }


    /***
     * 设置全选或者全不选
     *
     * @param isChecked
     */
    public void setChecked(Boolean isChecked) {
        for (int i = 0; i < downloadRg.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) downloadRg.getChildAt(i);
            checkBox.setChecked(isChecked);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setChecked(isChecked);
    }

    @Override
    public void shareStr() {
        ShareUtils.shareFilm(this, movieInfoEntity.getName(), movieInfoEntity.getMoveImg(), getCheckedStr());
    }
}
