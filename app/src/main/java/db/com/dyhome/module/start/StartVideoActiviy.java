package db.com.dyhome.module.start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.bean.LocalVideoEntity;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class StartVideoActiviy extends BaseActivity implements View.OnClickListener {

    public static final String Entity_tag = "entity";
    private LocalVideoEntity entity;
    private VideoView videoView;
    private LinearLayout nameLayout;
    private TextView nameView;
    private LinearLayout controlLayout;
    private ImageView pauseView;
    private TextView currentTimeView;
    private SeekBar seekBarView;
    private TextView totalTimeView;
    private MediaController mediaController;
    private Handler mHandler;
    private static final int smg_Visibility = 1;//隐藏标题layout,控制layout

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
      /*  if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }*/
        mToolbar.setVisibility(View.GONE);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        entity = getIntent().getParcelableExtra(Entity_tag);
        initView();
        InitData();
    }

    @Override
    protected void onSelfMessage(Message msg) {
        switch (msg.what) {
            case smg_Visibility:
            /*    controlLayout.setVisibility(View.GONE);
                nameLayout.setVisibility(View.GONE);*/
                break;
        }
    }

    private void InitData() {
        videoView.setVideoPath(entity.getPath());
        mediaController = new StyleMediaController(this);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        mHandler = getSelfHandler();
        mHandler.sendEmptyMessageDelayed(smg_Visibility, 3000);//三秒后隐藏控制布局
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.start_VideoView);

       /* nameLayout = (LinearLayout) findViewById(R.id.start_name_layout);
        nameView = (TextView) findViewById(R.id.mediacontroller_file_name);
        controlLayout = (LinearLayout) findViewById(R.id.start_control_layout);
        pauseView = (ImageView) findViewById(R.id.mediacontroller_pause);
        pauseView.setOnClickListener(this);
        currentTimeView = (TextView) findViewById(R.id.mediacontroller_time_current);
        seekBarView = (SeekBar) findViewById(R.id.mediacontroller_progress);
        totalTimeView = (TextView) findViewById(R.id.mediacontroller_time_total);*/
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_start_video;
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.start_VideoView:
                nameLayout.setVisibility(View.VISIBLE);
                controlLayout.setVisibility(View.VISIBLE);
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(smg_Visibility, 3000);//三秒后隐藏控制布局
                break;
            case R.id.mediacontroller_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pauseView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.play_button));
                } else {
                    pauseView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.pause_button));
                    videoView.start();
                }
                break;
        }*/
    }
}
