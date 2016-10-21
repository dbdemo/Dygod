package db.com.dygod.module.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import db.com.dygod.R;
import db.com.dygod.base.BaseActivity;
import db.com.dygod.bean.MovieInfoEntity;
import db.com.dygod.utils.ImageLoaderUtils;

/**
 * Created by zdb on 15/12/28.
 */
public class MovieInfoActivity extends BaseActivity implements View.OnClickListener{

    private final static String entityName="movieInfoEntity";

    private MovieInfoEntity movieInfoEntity;
    private TextView name;
    private TextView introduce;
    private ImageView img;
    private ImageView capture;

    public static void start(Context context,MovieInfoEntity movieInfoEntity) {
        Intent intent = new Intent(context, MovieInfoActivity.class);
        intent.putExtra(entityName, movieInfoEntity);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieInfoEntity=getIntent().getParcelableExtra(entityName);
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
        ImageLoaderUtils.displayAvatar(movieInfoEntity.getMoveImg(),img);
        ImageLoaderUtils.displayAvatar(movieInfoEntity.getMovieCapture(),capture);
    }

    private void initView() {
        name = (TextView) findViewById(R.id.movieinfo_name);
        introduce = (TextView) findViewById(R.id.movieinfo_introduce);
        img = (ImageView) findViewById(R.id.movieinfo_img);
        capture = (ImageView) findViewById(R.id.movieinfo_Capture);
        findViewById(R.id.movieinfo_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.movieinfo_start:
                String link = movieInfoEntity.getAddress();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
                //StartVideoActiviy.onStartActivity(this,movieInfoEntity.getAddress());
                break;
        }

    }
}
