package db.com.dyhome.module.common.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import db.com.dyhome.DyGodApplication;
import db.com.dyhome.R;
import db.com.dyhome.bean.PushEntity;
import db.com.dyhome.module.common.WebViewActivity;
import db.com.dyhome.utils.CommonUtils;

/**
 * Created by zdb on 2017/2/7.
 * 根据接口文档解析json字符串规定将要做的事情
 */
public class ActionParse {

    public final static int actionMsg = 1;

    private PushEntity mPushEntity;
    private Context mContext;

    public ActionParse(Context context, String json) {
        mContext = context;
        mPushEntity = PushEntity.parse(json);
        doAction();
    }

    public void doAction() {
        if (mPushEntity == null) {
            return;
        }
        switch (mPushEntity.getAction()) {
            case 1://推送升级
                if(mPushEntity.getVersionCode()>CommonUtils.getVersionCode(mContext)){
                    String title = mPushEntity.getTitle();
                    String content = mPushEntity.getContent();
                    String url = mPushEntity.getUrl();
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("shareUrl", url);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    showNotification(title, content, pendingIntent);
                }
                break;
            case 2://推送一条电影信息
                Message msg = new Message();
                msg.what = actionMsg;
                msg.obj = mPushEntity;
                DyGodApplication.getInstance().mHandler.sendMessage(msg);
                break;
            default:
                break;
        }
    }

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
        final NotificationManager manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        //设置当有消息是的提示，图标和提示文字
        builder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title);
        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
        builder.setContentInfo(mContext.getResources().getString(R.string.app_name));
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
