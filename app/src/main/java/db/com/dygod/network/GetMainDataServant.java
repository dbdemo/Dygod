package db.com.dygod.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import db.com.dygod.bean.MainEntity;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.db.dao.NetwokCacheDao;
import db.com.dygod.define.HtmlCache;
import db.com.dygod.define.UrlConstant;
import db.com.dygod.network.base.BaseServant;
import db.com.dygod.network.base.NetWorkListener;
import db.com.dygod.utils.DateUtils;

/**
 * Created by zdb on 2016/5/18.
 * 获取主页最新数据
 */
public class GetMainDataServant extends BaseServant<MainEntity> {

    private boolean isAddCache = false;
    private boolean isReadCache = false;
    private static final String TAG = GetMainDataServant.class.getSimpleName();
    private static final int REQUEST_RATE = 1000 * 60 * 60;


    /**
     * 根据属性解析字符串
     */
    private String divStyle = "width:950px;height:auto;overflow:hidden;margin:10px 0 0 2px;";

    public void getMainData(boolean isAddCache, boolean isReadCache, NetWorkListener mNetWorkListener) {

        this.isAddCache = isAddCache;
        this.isReadCache = isReadCache;

        if (isReadCache || !DateUtils.isReachDIF(System.currentTimeMillis(), Long.parseLong(NetwokCacheDao.getUpdateTime(TAG)), REQUEST_RATE)) {
            String docString=NetwokCacheDao.getValueForID(TAG);
            if(docString!=null&&"".equals(docString)){
                docString=  HtmlCache.mainCache+HtmlCache.mainCache2;
            }
            mNetWorkListener.successful(parseDocument(docString));
        } else {

            getDocument(UrlConstant.mainUrl, mNetWorkListener);
        }
    }

    @Override
    protected MainEntity parseDocument(String doc) {

        if (isAddCache) {
            NetwokCacheDao.addValueForId(TAG, doc);
        }

        MainEntity mainEntity = new MainEntity();

        ArrayList<MainNesEntity> mainNesEntities = new ArrayList<>();
        ArrayList<MainNesEntity> mainReleEntities = new ArrayList<>();
        String title = "";
        String link = "";
        String time = "";
        Document content = Jsoup.parse(doc);
        Elements elsements = content.getElementsByAttributeValueContaining("style", divStyle);
        if(elsements==null||elsements.size()<=0){
            mainEntity.setMainNesEntities(mainNesEntities);
            mainEntity.setMainReleEntities(mainReleEntities);
            return mainEntity;
        }
        Elements elsementsTitle = elsements.get(0).getElementsByClass("co_content222");
        for (int i = 0; i < elsements.size(); i++) {
            Elements elsementsLi = elsementsTitle.get(i).getElementsByTag("li");

            for (int j = 0; j < elsementsLi.size(); j++) {
                MainNesEntity newEntity = new MainNesEntity();
                title = elsementsLi.get(j).getElementsByTag("a").text();
                link = elsementsLi.get(j).select("a").attr("href").trim();
                time = elsementsLi.get(j).select("font").text();
                newEntity.setTitle(title);
                newEntity.setTitlinkle(UrlConstant.mainUrl + link);
                newEntity.setTime(DateUtils.getToYear() + "-" + time);
                if (i == 0) {
                    mainNesEntities.add(newEntity);
                } else {
                    mainReleEntities.add(newEntity);
                }
            }
        }
        mainEntity.setMainNesEntities(mainNesEntities);
        mainEntity.setMainReleEntities(mainReleEntities);
        return mainEntity;
    }
}
