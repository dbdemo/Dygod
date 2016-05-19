package db.com.dygod.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import db.com.dygod.bean.MainEntity;
import db.com.dygod.bean.MainNesEntity;
import db.com.dygod.define.UrlConstant;
import db.com.dygod.network.base.BaseServant;
import db.com.dygod.network.base.NetWorkListener;

/**
 * Created by zdb on 2016/5/18.
 * 获取主页最新数据
 */
public class GetMainDataServant extends BaseServant<MainEntity> {

    public void getMainData(NetWorkListener mNetWorkListener) {
        getDocument(UrlConstant.mainUrl, mNetWorkListener);
    }

    @Override
    protected MainEntity parseDocument(Document content) {
        Elements divs = content.select(".co_content8");
        Document divcontions = Jsoup.parse(divs.toString());
        Elements element = divcontions.getElementsByTag("tr");
        MainEntity mainEntity=new MainEntity();
        ArrayList<MainNesEntity> newEntitys=new ArrayList<>();
        for (Element links : element) {
            Elements td = links.getElementsByTag("td");
            for(int i=0;i<td.size();i++){
              String text=td.get(i).text();
                String title="";
                String link="";
                String time="";
                if(text.contains("最新电影下载")){
                    MainNesEntity entity=new MainNesEntity();
                    Elements as=td.get(0).getElementsByTag("a");
                    title = as.get(1).text();
                    link = as.get(1).attr("href").trim();
                    time=td.get(1).text();
                    entity.setTitle(title);
                    entity.setTime(time);
                    entity.setTitlinkle(UrlConstant.mainUrl+link);
                    newEntitys.add(entity);
                }
            }
        }
        mainEntity.setMainNesEntities(newEntitys);
        return mainEntity;
    }
}
