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

    private String grade;//电影评分
    private String tag;//标签
    private String area;//地区
    private String year;//年份
    private String director;//导演
    private String scriptwriter;//编剧
    private String Actor;//主演
    private String imdbName;//imdb名称

    private List<DownloadEntity> Downloads;


    protected MovieInfoEntity(Parcel in) {
        address = in.createStringArrayList();
        introduce = in.readString();
        moveImg = in.readString();
        movieCapture = in.readString();
        name = in.readString();
        grade = in.readString();
        tag = in.readString();
        area = in.readString();
        year = in.readString();
        director = in.readString();
        scriptwriter = in.readString();
        Actor = in.readString();
        imdbName = in.readString();
        Downloads = in.createTypedArrayList(DownloadEntity.CREATOR);
        secondName = in.readString();
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

    public List<DownloadEntity> getDownloads() {
        return Downloads;
    }

    public void setDownloads(List<DownloadEntity> downloads) {
        Downloads = downloads;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    private String secondName;//又名

    public MovieInfoEntity(){

    }



    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScriptwriter() {
        return scriptwriter;
    }

    public void setScriptwriter(String scriptwriter) {
        this.scriptwriter = scriptwriter;
    }

    public String getActor() {
        return Actor;
    }

    public void setActor(String actor) {
        Actor = actor;
    }

    public String getImdbName() {
        return imdbName;
    }

    public void setImdbName(String imdbName) {
        this.imdbName = imdbName;
    }

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
        dest.writeString(grade);
        dest.writeString(tag);
        dest.writeString(area);
        dest.writeString(year);
        dest.writeString(director);
        dest.writeString(scriptwriter);
        dest.writeString(Actor);
        dest.writeString(imdbName);
        dest.writeTypedList(Downloads);
        dest.writeString(secondName);
    }
}
