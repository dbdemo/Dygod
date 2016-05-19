package db.com.dygod.bean;

import java.io.Serializable;

/**
 * Created by zdb on 2016/5/19.
 * 主页最新电影
 */
public class MainNesEntity implements Serializable {

    private String title;
    private String titlinkle;
    private String time;

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
}
