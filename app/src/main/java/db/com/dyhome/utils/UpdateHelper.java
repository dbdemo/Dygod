package db.com.dyhome.utils;

import net.youmi.android.AdManager;
import net.youmi.android.update.AppUpdateInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 *  这里示例一个调用更新应用接口的工具类，由开发者自定义，继承自 AsyncTask
 */
public class UpdateHelper extends AsyncTask<Void, Void, AppUpdateInfo> {
    private Context mContext;
    public UpdateHelper(Context context) {
        mContext = context;
    }

    @Override
    protected AppUpdateInfo doInBackground(Void... params) {
        Log.d("dbLog","doInBackground");
        try {
            // 在 doInBackground 中调用 AdManager 的 checkAppUpdate 即可从有米服务器获得应用更新信息。
            return AdManager.getInstance(mContext).syncCheckAppUpdate();
            // 此方法务必在非 UI 线程调用，否则有可能不成功。
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(final AppUpdateInfo result) {
        super.onPostExecute(result);
        try {
            if (result == null || result.getUrl() == null) {
                // 如果 AppUpdateInfo 为 null 或它的 url 属性为 null，则可以判断为没有新版本。

                return;
            }
            final UpdataDialog dialog=new UpdataDialog(mContext);
            dialog.setContext("发现新版本");
            dialog.setConfirm("马上升级", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(result.getUrl()) );
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
            dialog.setCancle("下次再说", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}