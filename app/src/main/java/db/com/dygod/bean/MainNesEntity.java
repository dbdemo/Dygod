package db.com.dygod.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zdb on 2016/5/19.
 * 主页最新电影
 */
public class MainNesEntity implements Parcelable {

    private String title;
    private String titlinkle;
    private String time;

    public MainNesEntity(){

    }
    protected MainNesEntity(Parcel in) {
        title = in.readString();
        titlinkle = in.readString();
        time = in.readString();
    }

    public static final Creator<MainNesEntity> CREATOR = new Creator<MainNesEntity>() {
        @Override
        public MainNesEntity createFromParcel(Parcel in) {
            return new MainNesEntity(in);
        }

        @Override
        public MainNesEntity[] newArray(int size) {
            return new MainNesEntity[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlinkle() {
        return titlinkle;
    }

    public void setTitlinkle(String titlinkle) {
        this.titlinkle = titlinkle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(titlinkle);
        dest.writeString(time);
    }
}
