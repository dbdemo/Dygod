package db.com.dyhome.network.base;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
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
                    T result = parseDocument(docString);
                    if (mNetWorkListener != null) {
                        if(TextUtils.isEmpty(docString)){
                            mNetWorkListener.successful(null);
                            return;
                        }
                        mNetWorkListener.successful(result);
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
                    return;
                }
                docString = doc.toString();
                mHandler.sendEmptyMessage(succMsg);
            }
        }).start();
    }

    public void getSearchDocument(final String url, final String keyboard, NetWorkListener netWorkListener) {
        keyboardStr = keyboard;
        mNetWorkListener = netWorkListener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                Map<String, String> formParams = setPostPrams();
                StringBuffer sb = new StringBuffer();
                //设置表单参数
                for (String key : formParams.keySet()) {
                    sb.append(key + "=" + formParams.get(key) + "&");
                }
                RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=gb2312"), sb.toString());

                //创建请求
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                OkHttpClient mOkHttpClient = new OkHttpClient();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Message msg = Message.obtain();
                        msg.what = failMsg;
                        msg.obj = e;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        docString = response.body().string();
                        mHandler.sendEmptyMessage(succMsg);
                    }
                });
            }
        }
        ).start();
    }


    public Map<String, String> setPostPrams() {
        Map<String, String> formParams = new HashMap<String, String>();
        formParams.put("keyboard", keyboardStr);
        formParams.put("show", "title");
        formParams.put("tempid", "1");
        return formParams;
    }


    /***
     * 解析html
     *
     * @param doc
     * @return
     */
    protected abstract T parseDocument(String doc);
}
