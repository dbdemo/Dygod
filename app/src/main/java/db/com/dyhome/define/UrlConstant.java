package db.com.dyhome.define;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
    public static String mainUrl = "http://www.xiaopian.com/";//主页
    public static String searchUrl = mainUrl + "e/search/index.php";//搜索页面
    public static String japanUrl = mainUrl + "html/gndy/rihan/index";//日韩电影


    /***
     * 海贼王专区
     */
    public Map<String, String> getpieceTv() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/onepiece.html");
        cache.put("url", "http://www.xiaopian.com/html/dongman/haizeiwangqu/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /***
     * 火影专区
     */
    public Map<String, String> getJumpTv() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/jump.html");
        cache.put("url", "http://www.xiaopian.com/html/dongman/hy/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }


    /***
     * 欧美电影
     *
     * @return
     */
    public Map<String, String> getXvdieoUrlCacheTv() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/oumeitv.html");
        cache.put("url", "http://www.xiaopian.com/html/tv/oumeitv/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /**
     * 欧美电影
     *
     * @return
     */
    public Map<String, String> getXvdieoUrlCache() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/oumei.html");
        cache.put("url", "http://www.xiaopian.com/html/gndy/oumei/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /***
     * 最新综艺
     *
     * @return
     */
    public Map<String, String> getVarietyUrlCache() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/local.html");
        cache.put("url", "http://www.xiaopian.com/html/zongyi2013/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /***
     * 国内电视
     *
     * @return
     */
    public Map<String, String> getLocalUrlCachetv() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/localTv.html");
        cache.put("url", "http://www.xiaopian.com/html/tv/hytv/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }


    /***
     * 国内电影
     *
     * @return
     */
    public Map<String, String> getLocalUrlCache() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/local.html");
        cache.put("url", "http://www.xiaopian.com/html/gndy/china/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /***
     * 日韩电影
     *
     * @return
     */
    public Map<String, String> getJndyUrlCache() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/jndy.html");
        cache.put("url", "http://www.xiaopian.com/html/gndy/rihan/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /***
     * 日韩电视
     *
     * @return
     */
    public Map<String, String> getJndyUrlCachetv() {
        Map<String, String> cache = new HashMap<>();
        InputStream ips = getClass().getResourceAsStream("/assets/jpan.html");
        cache.put("url", "http://www.xiaopian.com/html/tv/rihantv/index");
        cache.put("cache", getCacheData(ips));
        return cache;
    }

    /**
     * 获取最新电影
     *
     * @return
     */
    public Map<String, String> getNewsUrlCache() {
        Map<String, String> newsUrlCache = new HashMap<>();
        InputStream newsIps = getClass().getResourceAsStream("/assets/news.html");
        newsUrlCache.put("url", "http://www.xiaopian.com/html/gndy/dyzz/index");
        newsUrlCache.put("cache", getCacheData(newsIps));
        return newsUrlCache;
    }

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
