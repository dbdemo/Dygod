package db.com.dyhome.module.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

import java.io.File;

import db.com.dyhome.R;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.network.GetDownLoadInfo;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.HexUtils;
import db.com.dyhome.utils.ImageLoaderUtils;
import db.com.dyhome.utils.ToastUtil;
import db.com.dyhome.widget.StyleDialog;

/**
 * 电影详情
 * Created by zdb on 15/12/28.
 */
public class MovieInfoActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String entityName = "movieInfoEntity";
    private MovieInfoEntity movieInfoEntity;
    private ImageView headImage;
    private TextView movieinfoDes;
    private StyleDialog mDialog;
    private Button go_moveinfo;
    private LinearLayout download_info_layout;

    public static void start(Context context, MovieInfoEntity movieInfoEntity) {
        Intent intent = new Intent(context, MovieInfoActivity.class);
        intent.putExtra(entityName, movieInfoEntity);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieinfo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);

        movieInfoEntity = getIntent().getParcelableExtra(entityName);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movieInfoEntity.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieInfoActivity.this.finish();
            }
        });
        initView();
        initData();
        //     获取要嵌入广告条的布局
        final LinearLayout bannerLayout = (LinearLayout) findViewById(R.id.ll_banner);
        View bannerView = BannerManager.getInstance(this)
                .getBannerView(this, new BannerViewListener() {
                    @Override
                    public void onRequestSuccess() {
                        bannerLayout.setVisibility(View.VISIBLE);
                        Log.d("dblog", "onRequestSuccess");
                    }

                    @Override
                    public void onSwitchBanner() {
                        Log.d("dblog", "onSwitchBanner");
                    }

                    @Override
                    public void onRequestFailed() {
                        bannerLayout.setVisibility(View.GONE);
                        Log.d("dblog", "onRequestFailed");
                    }
                });


// 将广告条加入到布局中
        bannerLayout.addView(bannerView);
    }

    private void initData() {
        ImageLoaderUtils.displayAvatar(movieInfoEntity.getMoveImg(), headImage);
        movieinfoDes.setText(
                movieInfoEntity.getSecondName() + "\n" +
                        movieInfoEntity.getImdbName() + "\n" +
                        movieInfoEntity.getGrade() + "\n" +
                        movieInfoEntity.getTag() + "\n" +
                        movieInfoEntity.getArea() + "\n" +
                        movieInfoEntity.getYear() + "\n" +
                        movieInfoEntity.getActor() + "\n" +
                        movieInfoEntity.getDirector() + "\n" +
                        movieInfoEntity.getScriptwriter() + "\n");
        if (movieInfoEntity.getDownloads() != null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            for (int i = 0; i < movieInfoEntity.getDownloads().size(); i++) {
                View itemView = inflater.inflate(R.layout.item_movieinfo_list, null, false);
                TextView itemViewName = (TextView) itemView.findViewById(R.id.item_movieinfo_name);
                TextView itemViewDes = (TextView) itemView.findViewById(R.id.item_movieinfo_des);
                itemViewName.setText(movieInfoEntity.getDownloads().get(i).getName());
                String des = "";
                for (int j = 0; j < movieInfoEntity.getDownloads().get(i).getContents().size(); j++) {
                    des = des + movieInfoEntity.getDownloads().get(i).getContents().get(j) + "\n";
                }
                itemViewDes.setText(des);
                final int finalI = i;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDialog == null) {
                            mDialog = new StyleDialog(MovieInfoActivity.this, "正在获取数据");
                        }
                        mDialog.show();
                        new GetDownLoadInfo().getMovieInfoData(UrlConstant.mainUrl + movieInfoEntity.getDownloads().get(finalI).getUrl(), null, mListener);
                    }
                });
                download_info_layout.addView(itemView);
            }
        }
    }

    /**
     * 下载完成种子文件
     */
    private NetWorkListener mListener = new NetWorkListener() {
        @Override
        public void successful(Object o) {
            mDialog.dismiss();
            if (HexUtils.getHaveXunLei(MovieInfoActivity.this)) {
                File f = new File((String) o);
                openFile(f);
            } else {
                ToastUtil.showMsg("请安装迅雷");
            }
        }

        @Override
        public void failure(Object e) {
            mDialog.dismiss();
            ToastUtil.showMsg("获取下载地址失败");
        }
    };

    private void initView() {
        headImage = (ImageView) findViewById(R.id.backdrop);
        movieinfoDes = (TextView) findViewById(R.id.movieinfo_des);
//        movieinfo_recv = (RecyclerView) findViewById(R.id.movieinfo_recv);
        download_info_layout = (LinearLayout) findViewById(R.id.download_info);
        go_moveinfo = (Button) findViewById(R.id.go_moveinfo);
        go_moveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(movieInfoEntity.getIntroduce())) {
                    WebViewActivity.newInstance(MovieInfoActivity.this, UrlConstant.getInstance().mainUrl + movieInfoEntity.getIntroduce(), movieInfoEntity.getName());
                }
            }
        });
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void openFile(File f) {
        if (f == null || "".equals(f.getPath())) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(f), "application/x-bittorrent");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BannerManager.getInstance(this).onDestroy();
    }
}
