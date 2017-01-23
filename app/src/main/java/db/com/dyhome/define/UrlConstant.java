package db.com.dyhome.define;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static db.com.dyhome.define.UrlConstant.mainUrl;

/**
 * Created by zdb on 2016/5/17.
 * 网络地址存放
 */
public class UrlConstant {

    private static final UrlConstant instance = new UrlConstant();

    private UrlConstant() {
    }

    public static UrlConstant getInstance() {
        return instance;
    }

    /**
     * 主页地址
     */
    public static String mainUrl = "http://www.bttt.la/";//主页
    public static String actionUrl = mainUrl + "category.php?/动作/";//动作
    public static String warUrl = mainUrl + "category.php?/战争/";//战争
    public static String plotUrl = mainUrl + "category.php?/剧情/";//剧情
    public static String laveUrl = mainUrl + "category.php?/爱情/";//
    public static String fictionUrl = mainUrl + "category.php?/科幻/";//
    public static String suspenseUrl = mainUrl + "category.php?/悬疑/";//
    public static String familyUrl = mainUrl + "category.php?/家庭/";//
    public static String crimeUrl = mainUrl + "category.php?/犯罪/";//
    public static String terroristUrl = mainUrl + "category.php?/恐怖/";//
    public static String animationtUrl = mainUrl + "category.php?/动画/";//
    public static String comedyUrl = mainUrl + "category.php?/喜剧/";//
    public static String thrillerUrl = mainUrl + "category.php?/惊悚/";//
    public static String adventureUrl = mainUrl + "category.php?/冒险/";//
    public static String TVUrl = mainUrl + "category.php?/电视剧/";//

    public static String searchUrl = mainUrl + "s.php?sitesearch=www.bttt.la&domains=bttt.la&hl=zh-CN&ie=UTF-8&oe=UTF-8&q=";//搜索页面+搜索内容

    public String getCacheData(InputStream newsIps) {
        String str = "";
        byte[] b = new byte[1024];
        int lan;
        try {
            while ((lan = newsIps.read(b)) != -1) {
                str += new String(b, "gb2312");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (newsIps != null) {
                    newsIps.close();
                }
                b = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
