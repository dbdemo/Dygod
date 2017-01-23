package db.com.dyhome.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import db.com.dyhome.bean.MovieCategoyEntity;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.network.base.BaseServant;
import db.com.dyhome.network.base.NetWorkListener;

/**
 * Created by zdb on 2016/5/18.
 */
public class GetTitleDataServant extends BaseServant<ArrayList<MovieCategoyEntity>> {


    public void getTitleData(NetWorkListener mNetWorkListener) {
        getDocument(UrlConstant.mainUrl, mNetWorkListener);
    }

    @Override
    protected ArrayList<MovieCategoyEntity> parseDocument(String doc) {
        Document content = Jsoup.parse(doc);

        Elements divs = content.select("#menu");
        Document divcontions = Jsoup.parse(divs.toString());
        Elements element = divcontions.getElementsByTag("li");

        ArrayList<MovieCategoyEntity> categoryList = new ArrayList<MovieCategoyEntity>();

        for (Element links : element) {
            String title = links.getElementsByTag("a").text();
            String link = links.select("a").attr("href").replace("/", "").trim();
            String url = link;

            switch (title) {

                case "留言":
                    break;
                case "收藏本站":
                    break;
                case "设为主页":
                    break;
                case "游戏下载":
                    break;
                default:
                    MovieCategoyEntity category = new MovieCategoyEntity();
                    category.setMoviecategoryName(title);
                    category.setMovieHref(url);
                    categoryList.add(category);
                    break;
            }
        }

        return categoryList;
    }
}
