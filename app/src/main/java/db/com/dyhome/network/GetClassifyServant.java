package db.com.dyhome.network;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.com.dyhome.bean.MainNesEntity;
import db.com.dyhome.db.dao.NetwokCacheDao;
import db.com.dyhome.network.base.BaseServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.DateUtils;

/**
 * Created by zdb on 2016/10/21.
 * 获取最新电影
 */

public class GetClassifyServant extends BaseServant<List<MainNesEntity>> {

    private boolean isAddCache;
    private boolean isReadCache;
    private int currentPageIndex;
    private String url;

    public void getNewsData(Map<String, String> urlMap, boolean isAddCache, boolean isReadCache, int currentPageIndex, NetWorkListener mNetWorkListener) {
        this.isAddCache = isAddCache;
        this.isReadCache = isReadCache;
        this.currentPageIndex = currentPageIndex;

        String url = urlMap.get("url");
        this.url=url;
        String cache = urlMap.get("cache");
        if (isReadCache) {
            isAddCache = false;
            String docString = NetwokCacheDao.getValueForID(url);
            if (docString != null && "".equals(docString)) {
                docString = cache;
                NetwokCacheDao.addValueForId(url, docString);
            }
            mNetWorkListener.successful(parseDocument(docString));
        } else {
            isAddCache=true;
            String urlpage;
            if (currentPageIndex == 1) {
                urlpage = url + ".html";
            } else {
                urlpage = url + "_" + currentPageIndex + ".html";
            }
            getDocument(urlpage, mNetWorkListener);
        }
    }

    @Override
    protected List<MainNesEntity> parseDocument(String doc) {
        if (isAddCache) {
            NetwokCacheDao.addValueForId(url, doc);
        }
        String rootDivClass = "co_content8";
        Document content = Jsoup.parse(doc);
        Elements rootDoc = content.getElementsByClass(rootDivClass);
        List<MainNesEntity> data = new ArrayList<>();
        if (rootDoc != null && rootDoc.size() > 0) {
            Elements tables = rootDoc.get(0).getElementsByTag("table");
            if (tables != null && tables.size() >= 0) {
                for (int i = 0; i < tables.size(); i++) {
                    MainNesEntity infoEntity = new MainNesEntity();
                    String href;
                    String title;
                    Element table = tables.get(i);
                    Elements aTexts = table.getElementsByClass("ulink");
                    if (aTexts != null && aTexts.size() >= 0) {
                        Element aObj;
                        if(aTexts.size()==2){
                            aObj = aTexts.get(1);
                        }else {
                            aObj = aTexts.get(0);
                        }
                        href = aObj.attr("href").trim();
                        title = aObj.attr("title").trim();
                        infoEntity.setTitlinkle(href);
                        infoEntity.setTitle(title);
                    }
                    //获取时间
                    Elements tdTimes = table.getElementsByAttributeValue("color", "#8F8C89");
                    if (tdTimes != null && tdTimes.size() > 0) {
                        String time = tdTimes.get(0).text();
                        if (!TextUtils.isEmpty(time) && time.length() >= 13) {
                            infoEntity.setTime(time.substring(3, 13));
                        } else {
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
