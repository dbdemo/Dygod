package db.com.dygod.network.base;

import android.inputmethodservice.Keyboard;
import android.os.Handler;
import android.os.Message;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zdb on 2016/5/18.
 */
public abstract class BaseServant<T> {

    private String keyboardStr;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case failMsg:

                    if (mNetWorkListener != null) {
                        mNetWorkListener.failure((IOException) msg.obj);
                    }
                    break;
                case succMsg:
                    if (mNetWorkListener != null) {
                        mNetWorkListener.successful(parseDocument(docString));
                    }
                    break;
            }
        }
    };
    private final int failMsg = 1;
    private final int succMsg = 2;
    private NetWorkListener mNetWorkListener;
    private String docString;


    private int timeOut = 1000 * 5;

    public void getDocument(final String url, NetWorkListener netWorkListener) {
        mNetWorkListener = netWorkListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).timeout(timeOut).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = failMsg;
                    msg.obj = e;
                    mHandler.sendMessage(msg);
                }
                if (doc == null) return;
                docString = doc.toString();
                mHandler.sendEmptyMessage(succMsg);
            }
        }).start();
    }

    public void getSearchDocument(final String url,  String keyboard, NetWorkListener netWorkListener) {
        keyboardStr = keyboard;
        try {
           keyboardStr = URLEncoder.encode(keyboard, "GBK");
            System.out.println("gbk  " + keyboardStr);
            keyboardStr=URLEncoder.encode(keyboardStr, "GBK");
            System.out.println("=== "+keyboardStr);
             /*
            String strUTF8 = URLDecoder.decode("%25BE%25F8%25B5%25D8%25CC%25D3%25CD%25F6", "UTF-8");
            System.out.println("urf" + strUTF8);
            */
        } catch (Exception e) {

        }

        mNetWorkListener = netWorkListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).header("Content-Type", "application/x-www-form-urlencoded").header("Charset","gbk").method(Connection.Method.POST).timeout(timeOut).data("show", "title").data("tempid", "1").data("keyboard", keyboardStr).ignoreContentType(true).post();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = failMsg;
                    msg.obj = e;
                    mHandler.sendMessage(msg);
                }
                if (doc == null) return;
                docString = doc.toString();
                mHandler.sendEmptyMessage(succMsg);
            }
        }).start();
    }

    private Map<String, String> setHeader() {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", "www.xiaopian.com");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
        header.put("Connection", "keep-alive");
        header.put("Accept-Encoding", "gzip, deflate");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        return header;
    }

    /***
     * 解析html
     *
     * @param doc
     * @return
     */
    protected abstract T parseDocument(String doc);
}
