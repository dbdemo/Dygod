package db.com.dygod;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import db.com.dygod.db.DBOperation;
import db.com.dygod.modu.MovieCategoy;
import db.com.thirdpartylibrary.utils.ConfigureManager;

/**
 * 加载数据管理类
 * Created by Administrator on 2015/12/19.
 */
public class LoadData_Manager {

    //public final static String MAININDEX="mainIndex";//主页文件

    /**
     * 获取电影分类
     */
    public void getTitles(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document  doc=null;
                try {
                     doc = Jsoup.connect(ConfigureManager.getInstance().getBaseAddress()).timeout(5000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Document content = Jsoup.parse(doc.toString());
                Elements divs = content.select("#menu");
                Document divcontions = Jsoup.parse(divs.toString());
                Elements element = divcontions.getElementsByTag("li");

               ArrayList<MovieCategoy> categoryList= new ArrayList<MovieCategoy>();

                for(Element links : element)
                {
                    String title = links.getElementsByTag("a").text();
                    String link   = links.select("a").attr("href").replace("/", "").trim();
                    String url  = link;
                    if(!"留言板".endsWith(title)||!"收藏本站".equals(title)||!"设为主页".equals(title)){
                        MovieCategoy category=new MovieCategoy();
                        category.setMoviecategoryName(title);
                        category.setMovieHref(url);
                        categoryList.add(category);
                        //System.out.println("标题："+title+" 链接："+url);
                    }
                }
                DBOperation.getInstents().seavCategory(categoryList);
            }
        }).start();
    }

    /**
     * 获取最新电影
     */
//    public void getNewMove(){
//
//        {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Document  doc=null;
//                    try {
//                        doc = Jsoup.connect(ConfigureManager.getInstance().getBaseAddress()).timeout(5000).get();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Document content = Jsoup.parse(doc.toString());
//                    Elements divs = content.select(".title_all");
//                    Document divcontions = Jsoup.parse(divs.toString());
//                    Elements element = divcontions.getElementsByTag("a");
//                    for (Element elsementData:element ) {
//                        String title = elsementData.getElementsByTag("a").text();
//                        String link   = elsementData.select("a").attr("href").replace("/", "").trim();
//                        System.out.println(title+"============"+link);
//                    }
//                }
//            }).start();
//        }
//    }

    /**
     * 获取最热电影
     */
    public void getHotMove(){

    }

}
