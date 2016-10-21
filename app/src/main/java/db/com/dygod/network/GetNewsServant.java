package db.com.dygod.network;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.db.dao.NetwokCacheDao;
import db.com.dygod.define.HtmlCache;
import db.com.dygod.define.UrlConstant;
import db.com.dygod.network.base.BaseServant;
import db.com.dygod.network.base.NetWorkListener;
import db.com.dygod.utils.DateUtils;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Created by zdb on 2016/10/21.
 * 获取最新电影
 */

public class GetNewsServant extends BaseServant<List<MainNesEntity>> {

    private boolean isAddCache;
    private boolean isReadCache;
    private int currentPageIndex;

    public void getNewsData(boolean isAddCache, boolean isReadCache, int currentPageIndex, NetWorkListener mNetWorkListener) {
        this.isAddCache = isAddCache;
        this.isReadCache = isReadCache;
        this.currentPageIndex = currentPageIndex;
        if (isReadCache) {
            isAddCache = false;
            String docString = NetwokCacheDao.getValueForID(TAG);
            if (docString != null && "".equals(docString)) {
                docString = HtmlCache.newsCache + HtmlCache.newsCache2;
                NetwokCacheDao.addValueForId(TAG, docString);
            }
            mNetWorkListener.successful(parseDocument(docString));
        } else {
            String url;

            if (currentPageIndex == 1) {
                url = UrlConstant.newsUrl + ".html";
            } else {
                url = UrlConstant.newsUrl + "_" + currentPageIndex + ".html";
            }
            getDocument(url, mNetWorkListener);
        }
    }

    @Override
    protected List<MainNesEntity> parseDocument(String doc) {
        if (isAddCache) {
            NetwokCacheDao.addValueForId(TAG, doc);
        }
        String rootDivClass = "co_content8";
        Document content = Jsoup.parse(doc);
        Elements rootDoc = content.getElementsByClass(rootDivClass);
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
