package db.com.dyhome.module.start;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.logging.Logger;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;
import db.com.dyhome.bean.LocalVideoEntity;
import db.com.dyhome.utils.DateUtils;
import db.com.dyhome.utils.UiViewCompat;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class StartVideoActiviy extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    public static final String Entity_tag = "entity";
    public static final String defaultProgress_tag = "defaultProgress";
    private LocalVideoEntity entity;
    private VideoView videoView;
    private LinearLayout nameLayout;
    private TextView nameView;
    private AudioManager audioManager;
    private LinearLayout controlLayout;
    private ImageView pauseView;
    private TextView currentTimeView;
    private SeekBar seekBarView;
    private TextView totalTimeView;
    private Handler mHandler;
    /**
     * 是否是在屏幕左边滑动
     */
    private boolean isTouchLeft;
    private int maxVolume;
    /**
     * 最大音量与屏幕高的比例
     */
    private float maxVolumeScreenHeightScale;
    private int currentVolume;
    /**
     * 屏幕亮度与屏幕高的比例
     */
    private float brightnessScreenHeightScale;
    /**
     * 当前屏幕亮度
     */
    /**
     * 手势监测器
     */
    private GestureDetector gestureDetector;
    private static final int smg_Visibility = 1;//隐藏标题layout,控制layout
    private static final int smg_seek_bar = 2;//2秒更新视频进度
    private static final int smg_volume_alhpa = 3;//隐藏声音对话框
    private LinearLayout start_volume_alpha_layout;
    private TextView start_volume_alpha;
    private ProgressBar start_volume_alpha_progressbar;
    private View start_view_brightness;
    private float distanceXX;

    private float screenWidth;
    private ImageView start_full_screen_normal;
    private LinearLayout start_root_layout;
    private RelativeLayout start_video_layout;

    private int defaultProgress;
    private boolean scrollX;
    private boolean scrollY;
    private long currentProgree;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mToolbar.setVisibility(View.GONE);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        Intent freshIntent = new Intent();
        freshIntent.setAction("com.android.music.musicservicecommand.pause");
        freshIntent.putExtra("command", "pause");
        sendBroadcast(freshIntent);

        entity = getIntent().getParcelableExtra(Entity_tag);
        defaultProgress = getIntent().getIntExtra(defaultProgress_tag, 0);
        initView();
        screenWidth = UiViewCompat.getScreenWidth(this);
        InitData();
    }

    @Override
    public void onPause() {
        super.onPause();
        currentProgree = videoView.getCurrentPosition();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (videoView != null) {
            videoView.findFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.seekTo(currentProgree);
                }
            });
        }
    }

    @Override
    protected void onSelfMessage(Message msg) {
        switch (msg.what) {
            case smg_Visibility:
                controlLayout.setVisibility(View.GONE);
                nameLayout.setVisibility(View.GONE);
                break;
            case smg_seek_bar:
                seekBarView.setProgress((int) videoView.getCurrentPosition());
                currentTimeView.setText(DateUtils.formatDuration(videoView.getCurrentPosition()));
                mHandler.sendEmptyMessageDelayed(smg_seek_bar, 1000);
                scrollY = true;
                break;
            case smg_volume_alhpa:
                start_volume_alpha_layout.setVisibility(View.GONE);
                scrollX = true;
                break;
        }
    }

    private void InitData() {
        videoView.setVideoPath(entity.getPath());
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
                videoView.seekTo(defaultProgress);
                mHandler = getSelfHandler();
                mHandler.sendEmptyMessageDelayed(smg_Visibility, 3000);//三秒后隐藏控制布局
                currentTimeView.setText(DateUtils.formatDuration(videoView.getCurrentPosition()));
                totalTimeView.setText(DateUtils.formatDuration(videoView.getDuration()));
                seekBarView.setMax((int) videoView.getDuration());
                seekBarView.setProgress((int) videoView.getCurrentPosition());
                mHandler.sendEmptyMessageDelayed(smg_seek_bar, 1000);//1秒后更新播放时间
            }
        });
        gestureDetector = new GestureDetector(this, listener);
        seekBarView.setOnSeekBarChangeListener(videoSeekBarChangeListener);
        videoView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initVolume();
                maxVolumeScreenHeightScale = ((float) maxVolume) / videoView.getMeasuredHeight();
                brightnessScreenHeightScale = 1.0f / videoView.getMeasuredHeight();
            }
        });
    }

    /**
     * 视频快进快退SeekBar改变监听器
     */
    private SeekBar.OnSeekBarChangeListener videoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                videoView.seekTo(progress);    // 快进或已退
            }
        }
    };

    private void initView() {
        videoView = (VideoView) findViewById(R.id.start_VideoView);
        videoView.setOnTouchListener(this);
        nameLayout = (LinearLayout) findViewById(R.id.start_name_layout);
        nameView = (TextView) findViewById(R.id.start_name);
        nameView.setText(entity.getTitle());
        controlLayout = (LinearLayout) findViewById(R.id.start_control_layout);
        start_root_layout = (LinearLayout) findViewById(R.id.start_root_layout);
        pauseView = (ImageView) findViewById(R.id.start_pause);
        pauseView.setOnClickListener(this);
        currentTimeView = (TextView) findViewById(R.id.start_time_current);
        seekBarView = (SeekBar) findViewById(R.id.start_progress);
        totalTimeView = (TextView) findViewById(R.id.start_time_total);
        start_volume_alpha_layout = (LinearLayout) findViewById(R.id.start_volume_alpha_layout);
        start_video_layout = (RelativeLayout) findViewById(R.id.start_video_layout);
        start_volume_alpha = (TextView) findViewById(R.id.start_volume_alpha);
        start_volume_alpha_progressbar = (ProgressBar) findViewById(R.id.start_volume_alpha_progressbar);
        start_volume_alpha_layout.setVisibility(View.GONE);
        start_view_brightness = findViewById(R.id.start_view_brightness);
        start_view_brightness.setVisibility(View.VISIBLE);
        ViewHelper.setAlpha(start_view_brightness, 0);
        start_full_screen_normal = (ImageView) findViewById(R.id.start_full_screen_normal);
        start_full_screen_normal.setOnClickListener(this);
    }

    @Override
    protected int setBodyView() {
        return R.layout.activity_start_video;
    }

    GestureDetector.OnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {


        @Override
        public boolean onSingleTapUp(MotionEvent e) {


            return true;
        }

        // 长按
        @Override
        public void onLongPress(MotionEvent e) {
        }

        // 双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        // 单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            showControlLayout();
            return true;
        }

        public boolean onDown(MotionEvent e) {
            isTouchLeft = e.getX() < UiViewCompat.getScreenWidth(StartVideoActiviy.this) / 2;
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mHandler.removeMessages(smg_volume_alhpa);
            mHandler.sendEmptyMessageDelayed(smg_volume_alhpa, 1000);
            float distanceYY = e1.getY() - e2.getY();    // 计算移动的距离
            distanceXX = e2.getX() - e1.getX();
            if (Math.abs(distanceXX) > 20 && scrollX) {
                scrollY = false;
                showControlLayout();
                changeSeekBar(distanceXX);
                return true;
            }
            if (Math.abs(distanceYY) > 20 && scrollY) {
                scrollX = false;
                if (isTouchLeft) {
                    // 改变屏幕亮度
                    changeBrightness(distanceYY);
                } else {
                    // 改变音量值
                    changeVolume(distanceYY);
                }
            }
            return true;
        }
    };

    private void changeSeekBar(float distanceXX) {

        float distancex = screenWidth / distanceXX * 50;

        float seekBarPross = seekBarView.getMax() / distancex;

        float result = seekBarView.getProgress() + seekBarPross;

        if (result < 0) {
            result = 0;
        } else if (result > seekBarView.getMax()) {
            result = seekBarView.getMax();
        }
        //System.out.println("dbLog:" + DateUtils.formatDuration((long) result));
        seekBarView.setProgress((int) result);
        videoView.seekTo((int) result);
        currentTimeView.setText(DateUtils.formatDuration(videoView.getCurrentPosition()));
    }

    /**
     * 改变屏幕亮度
     *
     * @param distanceYY 滑动距离
     */
    protected void changeBrightness(float distanceYY) {
        //		4、	计算滑动距离对应的屏幕亮度
        float value = -distanceYY * brightnessScreenHeightScale;
        //			5、	在原来的基础上进行增减
        float result = ViewHelper.getAlpha(start_view_brightness) + value;

        // 预防音量值赶出范围
        if (result < 0) {
            result = 0;
        } else if (result > 0.8f) {
            result = 0.8f;
        }
        updataVolumeLayout(0.8f, result);
    }

    private void changeVolume(float distanceYY) {
        //			4、	计算滑动距离对应的音量值
        int value = (int) (distanceYY * maxVolumeScreenHeightScale);
        //			5、	在原来的基础上进行增减
        int result = currentVolume + value;
        // 预防音量值赶出范围
        if (result < 0) {
            result = 0;
        } else if (result > maxVolume) {
            result = maxVolume;
        }
        updataVolumeLayout(maxVolume, result);
    }

    private void updataVolumeLayout(float maxVolume, float result) {
        start_volume_alpha_layout.setVisibility(View.VISIBLE);
        if (isTouchLeft) {
            // 改变屏幕亮度
            start_volume_alpha.setText("亮度");
            ViewHelper.setAlpha(start_view_brightness, result);
            int maxAlpha = (int) (maxVolume * 100);
            int resultAlpha = (int) ((1 - result) * 100);
            start_volume_alpha_progressbar.setMax(maxAlpha);
            start_volume_alpha_progressbar.setProgress(resultAlpha);
        } else {
            start_volume_alpha.setText("声音");
            setVolume((int) result, 0);
            start_volume_alpha_progressbar.setMax((int) maxVolume);
            start_volume_alpha_progressbar.setProgress((int) result);
        }

    }


    /**
     * 改变当前音量
     *
     * @param progress 音量值
     * @param flags    如果是1，则显示系统的悬浮界面，如果是0则不显示
     */
    private void setVolume(int progress, int flags) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, flags);
    }

    private void showControlLayout() {
        // 隐藏或显示控制面板
        nameLayout.setVisibility(View.VISIBLE);
        controlLayout.setVisibility(View.VISIBLE);
        mHandler.removeMessages(smg_Visibility);
        mHandler.sendEmptyMessageDelayed(smg_Visibility, 3000);//三秒后隐藏控制布局
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_full_screen_normal:
                Intent intent = new Intent(this, StartVideoFullActiviy.class);
                intent.putExtra(Entity_tag, entity);
                intent.putExtra(defaultProgress_tag, (int) videoView.getCurrentPosition());
                startActivity(intent);
                videoView.stopPlayback();
                this.finish();
                break;
            case R.id.start_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pauseView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.play_button));
                } else {
                    pauseView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pause_button));
                    videoView.start();
                }
                break;
        }
    }

    private void initVolume() {
        // 音频管理器
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = getCurrentVolume();
    }

    private int getCurrentVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }
}
