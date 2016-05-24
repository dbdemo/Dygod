package db.com.dygod.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import db.com.dygod.bean.MovieInfoEntity;
import db.com.dygod.network.base.BaseServant;
import db.com.dygod.network.base.NetWorkListener;

/**
 * from zdb  create at 2016/5/24  10:09
 * 电影信息
 **/
public class GetMovieInfoServant extends BaseServant<MovieInfoEntity> {

    /**
     * 根据属性解析字符串
     */
    private String divStyle = "WORD-WRAP: break-word";

    public void getMovieInfoData(String url,NetWorkListener mNetWorkListener) {
        getDocument(url, mNetWorkListener);
    }

    @Override
    protected MovieInfoEntity parseDocument(String doc) {

        MovieInfoEntity movieInfoEntity = new MovieInfoEntity();

        String introduce="";
        Document content = Jsoup.parse(doc);
        Document contextCo=Jsoup.parse(content.getElementsByClass("co_area2").toString());

        movieInfoEntity.setName(contextCo.getElementsByClass("title_all").text());
        Elements elsementP = contextCo.getElementsByTag("p");

        for(int i=0;i<elsementP.size();i++){
            introduce+=elsementP.get(i).text();
        }
        Elements imgElse = contextCo.getElementsByTag("img");
        if(imgElse.size()>=1){
            movieInfoEntity.setMoveImg(imgElse.get(0).attr("href"));
        }
        if(imgElse.size()>=2){
            movieInfoEntity.setMovieCapture(imgElse.get(1).attr("href"));
        }
        String address=content.getElementsByAttributeValueContaining("style", divStyle).text();
        movieInfoEntity.setAddress(address);
        movieInfoEntity.setIntroduce(introduce);

        return movieInfoEntity;
    }
}
