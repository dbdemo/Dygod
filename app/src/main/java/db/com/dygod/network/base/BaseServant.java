package db.com.dygod.network.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by zdb on 2016/5/18.
 */
public abstract  class BaseServant <T>{
    private int timeOut=1000*5;
    public void getDocument(final String url,final NetWorkListener mNetWorkListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document  doc=null;
                try {
                    doc = Jsoup.connect(url).timeout(timeOut).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    if(mNetWorkListener!=null){
                        mNetWorkListener.failure(e);
                    }
                }
                if(doc==null)return;
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
    protected abstract T parseDocument(Document content);
}
