package db.com.dyhome.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/31.
 */

public class UrlMapEntity implements Parcelable {
    private Map<String,String> urlData;

    public UrlMapEntity() {
    }

    protected UrlMapEntity(Parcel in) {
    }

    public static final Creator<UrlMapEntity> CREATOR = new Creator<UrlMapEntity>() {
        @Override
        public UrlMapEntity createFromParcel(Parcel in) {
            return new UrlMapEntity(in);
        }

        @Override
        public UrlMapEntity[] newArray(int size) {
            return new UrlMapEntity[size];
        }
    };

    public Map<String, String> getUrlData() {
        return urlData;
    }

    public void setUrlData(Map<String, String> urlData) {
        this.urlData = urlData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
