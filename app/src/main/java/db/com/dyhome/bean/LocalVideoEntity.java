package db.com.dyhome.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by zdb on 2016/11/2.
 * 本地视频
 */

public class LocalVideoEntity implements Parcelable {

    private String title;
    private String path;
    private long size;
    private Bitmap image;

    public LocalVideoEntity(String title, String path, long size, Bitmap image) {
        this.title = title;
        this.path = path;
        this.size = size;
        this.image = image;
    }

    protected LocalVideoEntity(Parcel in) {
        title = in.readString();
        path = in.readString();
        size = in.readLong();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<LocalVideoEntity> CREATOR = new Creator<LocalVideoEntity>() {
        @Override
        public LocalVideoEntity createFromParcel(Parcel in) {
            return new LocalVideoEntity(in);
        }

        @Override
        public LocalVideoEntity[] newArray(int size) {
            return new LocalVideoEntity[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeParcelable(image, flags);
    }
}