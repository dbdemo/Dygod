package db.com.dygod.network.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zdb on 2016/5/18.
 */
public abstract  class BaseServant <T>{


    public void getDocument(final String url,final NetWorkListener mNetWorkListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document  doc=null;
                try {
                    doc = Jsoup.connect(url).timeout(5000).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    if(mNetWorkListener!=null){
                        mNetWorkListener.failure(e);
                    }
                }
                Document content = Jsoup.parse(doc.toString());
                if(mNetWorkListener!=null){
                    mNetWorkListener.successful(parseDocument(content));
                }
            }
        }).start();
    }

    /***
     * 解析html
     * @param content
     * @return
     */
    protected abstract ArrayList<T> parseDocument(Document content);
}
