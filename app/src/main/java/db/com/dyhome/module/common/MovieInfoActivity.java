package db.com.dyhome.module.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;

import db.com.dyhome.R;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.module.common.adapter.AdapterMovieInfo;
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
    private RecyclerView movieinfo_recv;
    private AdapterMovieInfo adapter;
    private AdapterMovieInfo.RecyclerViewItemClickListener mItemClickListener;
    private StyleDialog mDialog;

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
        tintManager.setStatusBarTintResource(0);

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
            adapter = new AdapterMovieInfo(movieInfoEntity.getDownloads(), this);
            movieinfo_recv.setLayoutManager(new LinearLayoutManager(this));
            movieinfo_recv.setAdapter(adapter);
            mItemClickListener = new AdapterMovieInfo.RecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (mDialog == null) {
                        mDialog = new StyleDialog(MovieInfoActivity.this, "正在获取数据");
                    }
                    mDialog.show();
                    new GetDownLoadInfo().getMovieInfoData(UrlConstant.mainUrl + movieInfoEntity.getDownloads().get(position).getUrl(), null, mListener);
                }
            };
            adapter.setmItemClickListener(mItemClickListener);
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
        movieinfo_recv = (RecyclerView) findViewById(R.id.movieinfo_recv);
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
}
