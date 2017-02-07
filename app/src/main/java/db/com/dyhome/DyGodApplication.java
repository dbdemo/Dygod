package db.com.dyhome;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.bean.PushEntity;
import db.com.dyhome.db.DbSQLiteOpenHelper;
import db.com.dyhome.module.common.MovieInfoActivity;
import db.com.dyhome.module.common.push.ActionParse;
import db.com.dyhome.network.GetMovieInfoServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.ShareUtils;
import db.com.dyhome.utils.ToastUtil;

/**
 * Created by zdb on 2015/12/19.
 */
public class DyGodApplication extends Application {
    private static ImageLoader mImageLoader = ImageLoader.getInstance();
    private static DyGodApplication application = null;
    // 图片组件配置
    private ImageLoaderConfiguration mConfig;

    {
        ShareUtils.init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        MobclickAgent.setDebugMode(true);//友盟测试
        application = this;
        DbSQLiteOpenHelper.getInstance(this);
        initImageConfig(this);
    }


    public static DyGodApplication getInstance() {
        return application;
    }


    /**
     * 初始化图片控件
     *
     * @param context
     */
    public void initImageConfig(Context context) {
        mImageLoader.init(getImageLoaderConfig());
    }

    public ImageLoaderConfiguration getImageLoaderConfig() {
        if (mConfig == null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true)
                    .cacheInMemory(true).imageScaleType(ImageScaleType.NONE).build();
            StorageUtils.getOwnCacheDirectory(this, Environment.getDataDirectory()
                    .getAbsolutePath() + File.separator + "cacheDir");
            mConfig = new ImageLoaderConfiguration.Builder(this)
                    .threadPoolSize(5)
                    .threadPriority(Thread.NORM_PRIORITY - 1)
                    .taskExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    .taskExecutorForCachedImages(AsyncTask.THREAD_POOL_EXECUTOR)
                    .denyCacheImageMultipleSizesInMemory()
                    .memoryCacheSize(2 * 1024 * 1024)
                    .memoryCacheSizePercentage(13)
                    .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .memoryCache(new WeakMemoryCache())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .imageDownloader(new BaseImageDownloader(application))
                    .defaultDisplayImageOptions(options).writeDebugLogs().build();
        }
        return mConfig;
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public ImageLoader getImageLoaders() {
        return mImageLoader;
    }

    /**
     * 应用退逻辑
     */
    private List<Activity> activities = new ArrayList<Activity>();

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            activity.finish();
        }
        System.exit(0);
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ActionParse.actionMsg:
                    PushEntity mPushEntity= (PushEntity) msg.obj;
                    String urlcase = mPushEntity.getUrl();
                    final String titlecase = mPushEntity.getTitle();
                    final String contentcase = mPushEntity.getContent();
                    GetMovieInfoServant movieInfoServant = new GetMovieInfoServant();
                    movieInfoServant.getMovieInfoData(urlcase, new NetWorkListener<MovieInfoEntity>() {
                        @Override
                        public void successful(MovieInfoEntity movieInfoEntity) {
                            Intent intent = new Intent(application, MovieInfoActivity.class);
                            intent.putExtra(MovieInfoActivity.entityName, movieInfoEntity);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            showNotification(titlecase, contentcase, pendingIntent);
                        }
                        @Override
                        public void failure(Object e) {
                            ToastUtil.showMsg(titlecase+"可以观看了");
                        }
                    });
                    break;
            }
        }
    };

    /***
     * 显示通知
     *
     * @param title         通知的标题
     * @param content       通知的内容
     * @param pendingIntent 通知点击以后的动作
     */
    private void showNotification(String title, String content, PendingIntent pendingIntent) {
        int requestCode = (int) System.currentTimeMillis();
        //消息通知栏
        //创建NotificationManager
        final NotificationManager manager = (NotificationManager) application.getSystemService(application.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(application);
        //设置当有消息是的提示，图标和提示文字
        builder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title);
        builder.setLargeIcon(BitmapFactory.decodeResource(application.getResources(), R.mipmap.ic_launcher));
        builder.setContentInfo(application.getResources().getString(R.string.app_name));
        builder.setContentTitle(title);
        builder.setContentText(content);
        //显示消息到达的时间，这里设置当前时间
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(pendingIntent);
        //获取一个通知对象
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_ALL;
        //发送(显示)通知
        manager.notify(requestCode, notification);
    }
}
