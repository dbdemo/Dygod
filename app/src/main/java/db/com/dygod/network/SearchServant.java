package db.com.dygod.network;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.define.UrlConstant;
import db.com.dygod.network.base.BaseServant;
import db.com.dygod.network.base.NetWorkListener;
import db.com.dygod.utils.DateUtils;

/**
 * Created by Administrator on 2016/10/23.
 */
public class SearchServant extends BaseServant<List<MainNesEntity>> {

    private String keyboard;

    public void getSearchData(String searchString,int currentPageIndex, NetWorkListener mNetWorkListener) {
        getSearchDocument(UrlConstant.searchUrl,searchString,mNetWorkListener);
    }

    @Override
    protected List<MainNesEntity> parseDocument(String doc) {
        System.out.println("========"+doc);
        String rootDivClass = "co_content8";
        String errTable = "tableborder";
        Document content = Jsoup.parse(doc);
        Elements rootDoc = content.getElementsByClass(rootDivClass);
        Elements errTableDoc = content.getElementsByClass(errTable);
        if(errTableDoc.size()>0){
            return null;
        }
        List<MainNesEntity> data=new ArrayList<>();
        if (rootDoc != null && rootDoc.size() > 0) {
            Elements tables = rootDoc.get(0).getElementsByTag("table");
            if (tables != null && tables.size() >= 0) {
                for (int i = 0; i < tables.size(); i++) {
                    MainNesEntity infoEntity=new MainNesEntity();
                    String href;
                    String title;

                    Element table = tables.get(i);
                    Elements aTexts = table.getElementsByClass("ulink");
                    if (aTexts != null && aTexts.size() >= 0) {
                        Element aObj = aTexts.get(0);
                        href = aObj.attr("href").trim();
                        title = aObj.attr("title").trim();
                        infoEntity.setTitlinkle(href);
                        infoEntity.setTitle(title);
                    }
                    //获取时间
                    Elements tdTimes = table.getElementsByAttributeValue("color", "#8F8C89");
                    if (tdTimes != null && tdTimes.size() >= 0) {
                        String time = tdTimes.get(0).text();
                        if(!TextUtils.isEmpty(time)&&time.length()>=13){
                            infoEntity.setTime(time.substring(3, 13));
                        }else{
                            infoEntity.setTime(DateUtils.getCurrDate("yyyy-MM-dd"));
                        }
                    }
                    data.add(infoEntity);
                }
            }
        }
        return data;
    }
}
