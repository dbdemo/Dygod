package db.com.dygod.network.base;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by zdb on 2016/5/18.
 */
public abstract  class BaseServant <T>{

    private  Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case failMsg:

                    if(mNetWorkListener!=null){
                        mNetWorkListener.failure((IOException)msg.obj);
                    }
                    break;
                case succMsg:
                    if(mNetWorkListener!=null){
                        mNetWorkListener.successful(parseDocument(docString));
                    }
                    break;
            }
        }
    };
    private final int failMsg=1;
    private final int succMsg=2;
    private NetWorkListener mNetWorkListener;
    private String docString;


    private int timeOut=1000*5;
    public void getDocument(final String url, NetWorkListener netWorkListener){
        mNetWorkListener=netWorkListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document  doc=null;
                try {
                    doc = Jsoup.connect(url).timeout(timeOut).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message msg=Message.obtain();
                    msg.what=failMsg;
                    msg.obj=e;
                    mHandler.sendMessage(msg);
                }
                if(doc==null)return;
                docString= doc.toString();
                mHandler.sendEmptyMessage(succMsg);
            }
        }).start();
    }

    /***
     * 解析html
     * @param doc
     * @return
     */
    protected abstract T parseDocument(String doc);
}
