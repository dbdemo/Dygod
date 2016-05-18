package db.com.dygod;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import db.com.dygod.db.DBHelper;
import db.com.dygod.db.DbSQLiteOpenHelper;

/**
 * Created by zdb on 2015/12/19.
 */
public class DyGodApplication extends Application {
    private static DyGodApplication application=null;
    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        DbSQLiteOpenHelper.getInstance(this);

        DBHelper.getWritableDatabase();

    }


    public static DyGodApplication getInstance(){
        return application;
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
