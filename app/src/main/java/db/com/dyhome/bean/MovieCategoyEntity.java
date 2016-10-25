package db.com.dyhome.bean;

/**
 * 电影分类
 * Created by Administrator on 2015/12/19.
 */
public class MovieCategoyEntity {

    private int id;
    private String moviecategoryName;
    private String movieHref;

    public String getMoviecategoryName() {
        return moviecategoryName;
    }

    public void setMoviecategoryName(String moviecategoryName) {
        this.moviecategoryName = moviecategoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMovieHref() {
        return movieHref;
    }

    public void setMovieHref(String movieHref) {
        this.movieHref = movieHref;
    }
}
