package db.com.dygod.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import db.com.dygod.DyGodApplication;
import db.com.dygod.R;


/**
 * 加载图片的util  定义各种DisplayImageOptions
 */
public class ImageLoaderUtils {
    private static ImageLoader mImageLoader = DyGodApplication.getImageLoader();

    public static void displayAvatar(String uri, ImageView view) {
        mImageLoader.displayImage(uri, view, getAvatarOptions());
    }

    private static DisplayImageOptions getAvatarOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).showImageForEmptyUri(R.mipmap.public_default_icon).showImageOnFail(R.mipmap.public_default_icon)
                .showStubImage(R.mipmap.public_default_icon)/*.delayBeforeLoading(100)*/.displayer(new FadeInBitmapDisplayer(200)).build();
        return options;
    }

}
