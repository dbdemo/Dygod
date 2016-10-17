package db.com.dygod.module.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

import db.com.dygod.R;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;

public class StartVideoActiviy extends Activity {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media file
     * path.
     */
    private String path = "";
    private VideoView mVideoView;
    private EditText mEditText;

    public static void onStartActivity(Context context, String url) {
        Intent intent = new Intent(context, StartVideoActiviy.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_start_video);
        mEditText = (EditText) findViewById(R.id.url);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        path = getIntent().getStringExtra("url");
        downloadMovie(path);

    }

    /**下载电影
     * @param path**/
    private void downloadMovie(String path) {
        Thread t = new Thread(){
            @Override
            public void run() {
                String path = "ftp://a:a@dygod18.com:21/[电影天堂www.dy2018.com]阿里巴巴2所罗门封印HD国语中字.mkv";
                String target = Environment.getExternalStorageDirectory()+ "/[电影天堂www.dy2018.com]阿里巴巴2所罗门封印HD国语中字.mkv";
                HttpUtils utils = new HttpUtils();
                utils.download(path, target, new RequestCallBack<File>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        System.out.println("current:"+current+"/"+total);
                    }
                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        System.out.println("下载成功");
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        System.out.println("下载失败");
                    }
                });
            }
        };
        t.start();
    }

    public void startPlay(View view) {
        String url = mEditText.getText().toString();
        path = url;
        if (!TextUtils.isEmpty(url)) {
            mVideoView.setVideoPath(url);
        }
    }

    public void openVideo(View View) {
        mVideoView.setVideoPath(path);
    }

}
