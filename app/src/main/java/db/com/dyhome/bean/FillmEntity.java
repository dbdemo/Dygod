package db.com.dyhome.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class FillmEntity implements Parcelable {

    private String title;
    private String titlinkle;
    private String time;
    private String img;//图片
    private String secondTitle;
    private String grade;//豆瓣评分
    private String desc;//介绍

    public FillmEntity(Parcel in) {
        title = in.readString();
        titlinkle = in.readString();
        time = in.readString();
        img = in.readString();
        secondTitle = in.readString();
        grade = in.readString();
        desc = in.readString();
    }

    public static final Creator<FillmEntity> CREATOR = new Creator<FillmEntity>() {
        @Override
        public FillmEntity createFromParcel(Parcel in) {
            return new FillmEntity(in);
        }

        @Override
        public FillmEntity[] newArray(int size) {
            return new FillmEntity[size];
        }
    };

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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
        dest.writeString(img);
        dest.writeString(secondTitle);
        dest.writeString(grade);
        dest.writeString(desc);
    }
}
