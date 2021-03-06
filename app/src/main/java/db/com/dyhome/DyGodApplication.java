package db.com.dyhome;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
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
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.lang.reflect.Method;
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
        MobclickAgent.setDebugMode(false);//友盟测试
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
}
