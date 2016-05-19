package db.com.dygod.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import db.com.dygod.bean.MovieCategoyEntity;
import db.com.dygod.define.UrlConstant;
import db.com.dygod.network.base.BaseServant;
import db.com.dygod.network.base.NetWorkListener;

/**
 * Created by zdb on 2016/5/18.
 */
public class GetTitleDataServant extends BaseServant<ArrayList<MovieCategoyEntity>> {


    public void getTitleData(NetWorkListener mNetWorkListener) {
        getDocument(UrlConstant.mainUrl, mNetWorkListener);
    }

    @Override
    protected ArrayList<MovieCategoyEntity> parseDocument(Document content) {

        Elements divs = content.select("#menu");
        Document divcontions = Jsoup.parse(divs.toString());
        Elements element = divcontions.getElementsByTag("li");

        ArrayList<MovieCategoyEntity> categoryList = new ArrayList<MovieCategoyEntity>();

        for (Element links : element) {
            String title = links.getElementsByTag("a").text();
            String link = links.select("a").attr("href").replace("/", "").trim();
            String url = link;
            if(!title.endsWith("留言板")||!title.endsWith("收藏本站")||!title.endsWith("设为主页")) {
                MovieCategoyEntity category = new MovieCategoyEntity();
                category.setMoviecategoryName(title);
                category.setMovieHref(url);
                categoryList.add(category);
            }
        }

        return categoryList;
    }
}
