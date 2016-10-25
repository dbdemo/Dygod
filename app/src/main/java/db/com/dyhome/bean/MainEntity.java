package db.com.dyhome.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zdb on 2016/5/19.
 * 主页实体
 */
public class MainEntity implements Parcelable {

    private ArrayList<MainNesEntity> mainNesEntities;//最新电影
    private ArrayList<MainNesEntity> mainReleEntities;//最新发布电影

    public MainEntity(){

    }

    protected MainEntity(Parcel in) {
        mainNesEntities = in.createTypedArrayList(MainNesEntity.CREATOR);
        mainReleEntities = in.createTypedArrayList(MainNesEntity.CREATOR);
    }

    public static final Creator<MainEntity> CREATOR = new Creator<MainEntity>() {
        @Override
        public MainEntity createFromParcel(Parcel in) {
            return new MainEntity(in);
        }

        @Override
        public MainEntity[] newArray(int size) {
            return new MainEntity[size];
        }
    };

    public ArrayList<MainNesEntity> getMainReleEntities() {
        return mainReleEntities;
    }

    public void setMainReleEntities(ArrayList<MainNesEntity> mainReleEntities) {
        this.mainReleEntities = mainReleEntities;
    }

    public ArrayList<MainNesEntity> getMainNesEntities() {
        return mainNesEntities;
    }

    public void setMainNesEntities(ArrayList<MainNesEntity> mainNesEntities) {
        this.mainNesEntities = mainNesEntities;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mainNesEntities);
        dest.writeTypedList(mainReleEntities);
    }
}
