package db.com.dyhome;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.db.DbSQLiteOpenHelper;

/**
 * Created by zdb on 2015/12/19.
 */
public class DyGodApplication extends Application {
    private static ImageLoader mImageLoader = ImageLoader.getInstance();
    private static DyGodApplication application = null;
    // 图片组件配置
    private ImageLoaderConfiguration mConfig;

    @Override
    public void onCreate() {
        super.onCreate();
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

}
