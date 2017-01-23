package db.com.dyhome.network;

import android.os.Parcel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.bean.DownloadEntity;
import db.com.dyhome.bean.MovieInfoEntity;
import db.com.dyhome.network.base.BaseServant;
import db.com.dyhome.network.base.NetWorkListener;

/**
 * from zdb  create at 2016/5/24  10:09
 * 电影信息
 **/
public class GetMovieInfoServant extends BaseServant<MovieInfoEntity> {

    public void getMovieInfoData(String url, NetWorkListener mNetWorkListener) {
        getDocument(url, mNetWorkListener);
    }

    @Override
    protected MovieInfoEntity parseDocument(String doc) {

        MovieInfoEntity movieInfoEntity = new MovieInfoEntity();

        Document content = Jsoup.parse(doc);

        Elements movieTeail = content.getElementsByClass("moviedteail");
        if (movieTeail == null || movieTeail.size() == 0) {
            return movieInfoEntity;
        }
        Element movieTeailEle = movieTeail.get(0);
        movieInfoEntity.setMoveImg(movieTeailEle.getElementsByTag("img").attr("src"));
        movieInfoEntity.setGrade("豆瓣评分："+movieTeailEle.getElementsByClass("rt").get(0).text());
        movieInfoEntity.setName(movieTeailEle.getElementsByClass("moviedteail_tt").get(0).text());
        Elements moviedteail_list = movieTeailEle.getElementsByClass("moviedteail_list").get(0).getElementsByTag("li");
        if (moviedteail_list != null || moviedteail_list.size() > 0) {
            for (int i=0;i<moviedteail_list.size();i++) {
                Element list_li_item = moviedteail_list.get(i);
                String tagName = list_li_item.text();
                if(tagName.startsWith("又名")){
                    movieInfoEntity.setSecondName(tagName);
                }else if(tagName.startsWith("标签")){
                    movieInfoEntity.setTag(tagName);
                }else if(tagName.startsWith("地区")){
                    movieInfoEntity.setArea(tagName);
                }else if(tagName.startsWith("年份")){
                    movieInfoEntity.setYear(tagName);
                }else if(tagName.startsWith("导演")){
                    movieInfoEntity.setDirector(tagName);
                }else if(tagName.startsWith("编剧")){
                    movieInfoEntity.setScriptwriter(tagName);
                }else if(tagName.startsWith("主演")){
                    movieInfoEntity.setActor(tagName);
                }else if(tagName.startsWith("imdb")){
                    movieInfoEntity.setImdbName(tagName);
                }
            }
        }

        Elements tinfoS = content.getElementsByClass("tinfo");
        List<DownloadEntity> data=new ArrayList<>();
        if(tinfoS!=null&&tinfoS.size()>0){
            for(int i=0;i<tinfoS.size();i++){
                DownloadEntity downloadEntity=new DownloadEntity(Parcel.obtain());
                Element tinfo = tinfoS.get(i);
                downloadEntity.setName(tinfo.getElementsByTag("a").attr("title"));
                downloadEntity.setUrl(tinfo.getElementsByTag("a").attr("href"));
                Elements downloadLis = tinfo.getElementsByTag("li");
                if(downloadLis!=null&&downloadLis.size()>0){
                    List<String> info=new ArrayList<>();
                    for(int j=0;j<downloadLis.size();j++){
                        Element downloadLi = downloadLis.get(j);
                        info.add(downloadLi.text());
                    }
                    downloadEntity.setContents(info);
                }
                data.add(downloadEntity);
            }
            movieInfoEntity.setDownloads(data);
        }
        return movieInfoEntity;
    }
}
