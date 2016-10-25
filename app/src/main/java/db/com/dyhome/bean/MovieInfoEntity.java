package db.com.dyhome.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
*  from zdb  create at 2016/5/24  10:10
*   电影信息
**/
public class MovieInfoEntity implements Parcelable{
    private List<String> address;//播放地址
    private String introduce;//介绍
    private String moveImg;//电影图片
    private String movieCapture;//电影截图
    private String name;//电影名称


    public MovieInfoEntity(){

    }

    protected MovieInfoEntity(Parcel in) {
        address = in.createStringArrayList();
        introduce = in.readString();
        moveImg = in.readString();
        movieCapture = in.readString();
        name = in.readString();
    }

    public static final Creator<MovieInfoEntity> CREATOR = new Creator<MovieInfoEntity>() {
        @Override
        public MovieInfoEntity createFromParcel(Parcel in) {
            return new MovieInfoEntity(in);
        }

        @Override
        public MovieInfoEntity[] newArray(int size) {
            return new MovieInfoEntity[size];
        }
    };

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getMoveImg() {
        return moveImg;
    }

    public void setMoveImg(String moveImg) {
        this.moveImg = moveImg;
    }

    public String getMovieCapture() {
        return movieCapture;
    }

    public void setMovieCapture(String movieCapture) {
        this.movieCapture = movieCapture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringList(address);
        dest.writeString(introduce);
        dest.writeString(moveImg);
        dest.writeString(movieCapture);
        dest.writeString(name);
    }
}
