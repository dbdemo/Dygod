package db.com.dygod.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
*  from zdb  create at 2016/5/24  10:10
*   电影信息
**/
public class MovieInfoEntity implements Parcelable{
    private String address;//播放地址
    private String introduce;//介绍
    private String moveImg;//电影图片
    private String movieCapture;//电影截图

    private String name;//电影名称

    protected MovieInfoEntity(Parcel in) {
        address = in.readString();
        introduce = in.readString();
        moveImg = in.readString();
        movieCapture = in.readString();
        name = in.readString();
    }

    public MovieInfoEntity(){

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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(address);
        dest.writeString(introduce);
        dest.writeString(moveImg);
        dest.writeString(movieCapture);
        dest.writeString(name);
    }




    /* private String img;
    private String nameFilm;//译　　名
    private String years;//年代
    private String countries;//国家
    private String category;//类别
    private String language;//语言
    private String subtitles;//字幕
    private String ReleaseDate;//上映日期*/

}
