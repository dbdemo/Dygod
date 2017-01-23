package db.com.dyhome.network;

import android.os.Parcel;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import db.com.dyhome.bean.FillmEntity;
import db.com.dyhome.bean.MainNesEntity;
import db.com.dyhome.db.dao.NetwokCacheDao;
import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.network.base.BaseServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.DateUtils;

/**
 * 搜索
 * Created by zdb on 2016/10/23.
 */
public class SearchServant extends BaseServant<ArrayList<FillmEntity>> {

    public void getSearchData(String searchString,int currentPageIndex, NetWorkListener<ArrayList<FillmEntity>> mNetWorkListener) {
        getDocument(UrlConstant.searchUrl+searchString+"&PageNo="+currentPageIndex,mNetWorkListener);
    }

    @Override
    protected ArrayList<FillmEntity> parseDocument(String doc) {

        ArrayList<FillmEntity> data = new ArrayList<>();

        if("".equals(doc.trim())){
            return data;
        }

        Document content = Jsoup.parse(doc);
        Elements elsements = content.getElementsByClass("item cl");

        for(int i=0;i<elsements.size();i++){
            Element itemElse = elsements.get(i);
            if(itemElse.getElementsByClass("title").size()==0){
                continue;
            }
            Element divTitle = itemElse.getElementsByClass("title").get(0);

            Elements titlePs = divTitle.getElementsByTag("p");
            FillmEntity mainEntity = new FillmEntity(Parcel.obtain());
            for(int j=0;j<titlePs.size();j++){
                Element  pelEment=titlePs.get(j);
                switch (j){
                    case 0://解析时间和电影名称,连接地址
                        mainEntity.setTime(pelEment.getElementsByTag("span").text());
                        mainEntity.setTitle(pelEment.getElementsByTag("a").get(0).text());
                        mainEntity.setTitlinkle(pelEment.getElementsByTag("a").get(0).attr("href"));
                        break;
                    case 1://解析又名
                        mainEntity.setSecondTitle(pelEment.text());
                        break;
                    case 2://解析描述
                        mainEntity.setDesc(pelEment.text());
                        break;
                    case 3://解析豆瓣评分
                        mainEntity.setGrade(pelEment.text());
                        break;
                }
            }

            if(itemElse.getElementsByClass("litpic").size()>0){
                mainEntity.setImg(itemElse.getElementsByClass("litpic").get(0).getElementsByTag("img").get(0).attr("src"));
            }

            data.add(mainEntity);
        }
        return data;
    }
}
