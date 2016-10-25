package db.com.dyhome.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.network.base.BaseServant;
import db.com.dyhome.network.base.NetWorkListener;

/**
 * from zdb  create at 2016/5/24  10:09
 * 电影信息
 **/
public class GetMovieInfoServant extends BaseServant<MovieInfoEntity> {

    /**
     * 根据属性解析字符串
     */
    private String divStyle = "WORD-WRAP: break-word";

    public void getMovieInfoData(String url, NetWorkListener mNetWorkListener) {
        getDocument(url, mNetWorkListener);
    }

    @Override
    protected MovieInfoEntity parseDocument(String doc) {

        MovieInfoEntity movieInfoEntity = new MovieInfoEntity();

        String introduce = "";
        Document content = Jsoup.parse(doc);
        Document contextCo = Jsoup.parse(content.getElementsByClass("co_area2").toString());
        movieInfoEntity.setName(contextCo.getElementsByClass("title_all").text());
        Elements elsementP = contextCo.getElementsByTag("p");

        for (int i = 0; i < elsementP.size(); i++) {
            introduce += elsementP.get(i).text() + "\n";
        }
        Elements imgElse = contextCo.getElementsByTag("img");
        if (imgElse.size() >= 1) {
            movieInfoEntity.setMoveImg(imgElse.get(0).attr("src"));
        }
        if (imgElse.size() >= 2) {
            movieInfoEntity.setMovieCapture(imgElse.get(1).attr("src"));
        }
        //String address=content.getElementsByAttributeValueContaining("style", divStyle).text();
        Elements addressDocs = content.getElementsByAttributeValueContaining("style", "WORD-WRAP: break-word");
        List<String> addressData = new ArrayList<>();
        for (int i = 0; i < addressDocs.size(); i++) {
            String address = addressDocs.get(i).getElementsByTag("a").text();
            addressData.add(address);
        }
        movieInfoEntity.setAddress(addressData);
        movieInfoEntity.setIntroduce(introduce);

        return movieInfoEntity;
    }
}
