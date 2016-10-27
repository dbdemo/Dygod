package db.com.dyhome.utils;

import android.app.Activity;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import db.com.dyhome.R;

/**
 * Created by zdb on 2016/10/27.
 * 分享工具类
 */

public class ShareUtils {

    private static String shareTitle = "电影之家";
    private static String shareContext = "更多更新更高清电影";
    private static UMImage shareImage = null;

    public static void init() {
        PlatformConfig.setWeixin("wxcce8b94d27636bc5", "74984a5823824351eeb5f291bd715052");
        PlatformConfig.setQQZone("1105782914", "uk7sTIetTQ9p1xWV");
    }

    /***
     * 分享app
     */
    public static void shareApk(Activity activity) {
        shareImage = new UMImage(activity, R.mipmap.ic_launcher);
        new ShareAction(activity)
                .withMedia(shareImage)
                .withTargetUrl("")
                .withText(shareContext)
                .withTitle(shareTitle)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(umShareListener).open();
    }

    /**
     * 分享电影
     */
    public static void shareFilm(Activity activity, String name, String downloadUrl) {
        shareImage = new UMImage(activity, R.mipmap.ic_launcher);
        shareContext = "";
        shareContext += downloadUrl + " 复制链接在迅雷中下载，更多资源请下载电影之家app";
        new ShareAction(activity)
                .withMedia(shareImage)
                .withText(shareContext)
                .withTargetUrl("")
                .withTitle(name)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(umShareListener).open();
    }


    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("dbLog", "platform" + platform);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.d("dbLog", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

}