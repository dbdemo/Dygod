package db.com.dyhome.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ImageLoaderHelper {

	private static DisplayImageOptions sOptions;

	static {
		sOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(false).bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true).build();
	}

	public static void initImageLoader(Context context, int maxWidth,
			int maxHeight) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context.getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.diskCacheExtraOptions(maxWidth, maxHeight, null)
				.tasksProcessingOrder(QueueProcessingType.FIFO).build();
		ImageLoader.getInstance().init(config);
	}

	public static String getLocalUri(String path) {
		return "file://" + path;
	}

	public static String getDrawableUri(int resId) {
		return "drawable://" + resId;
	}

	public static DisplayImageOptions getGlobalOptions() {
		return sOptions;
	}
}
