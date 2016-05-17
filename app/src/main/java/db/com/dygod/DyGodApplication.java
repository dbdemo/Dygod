package db.com.dygod;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import db.com.dygod.db.DbCread;
import db.com.thirdpartylibrary.utils.ConfigureManager;

/**
 * Created by zdb on 2015/12/19.
 */
public class DyGodApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConfigureManager.getInstance(getApplicationContext());//初始化配置文件
        DbCread.init(getApplicationContext());
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
