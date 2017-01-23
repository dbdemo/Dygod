package db.com.dyhome.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/19.
 */

public class DownloadEntity implements Parcelable{
        private String name;
    private String url;
    private List<String >contents;

    public DownloadEntity(Parcel in) {
        name = in.readString();
        url = in.readString();
        contents = in.createStringArrayList();
    }

    public static final Creator<DownloadEntity> CREATOR = new Creator<DownloadEntity>() {
        @Override
        public DownloadEntity createFromParcel(Parcel in) {
            return new DownloadEntity(in);
        }

        @Override
        public DownloadEntity[] newArray(int size) {
            return new DownloadEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeStringList(contents);
    }
}
