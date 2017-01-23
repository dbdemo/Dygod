package db.com.dyhome.network;


import com.squareup.okhttp.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import db.com.dyhome.define.UrlConstant;
import db.com.dyhome.network.base.BaseServant;
import db.com.dyhome.network.base.NetWorkListener;
import db.com.dyhome.utils.OkHttpUtls;

/**
 * 获取下载地址
 */
public class GetDownLoadInfo extends BaseServant<String> {

    private NetWorkListener mNetWorkListener;
    private NetWorkListener mNetWorDownLoadkListener;

    public void getMovieInfoData(String url, NetWorkListener mNetWorkListener, NetWorkListener mNetWorDownLoadkListener) {
        this.mNetWorkListener = mNetWorkListener;
        this.mNetWorDownLoadkListener = mNetWorDownLoadkListener;
        getDocument(url, mNetWorkListener);
    }

    @Override
    protected String parseDocument(String doc) {

        Document content = Jsoup.parse(doc);
        Elements formTags = content.getElementsByTag("form");
        if (formTags != null && formTags.size() > 0) {
            Element formTag = formTags.get(0);
            String action = formTag.attr("action");
            Elements inputs = formTag.getElementsByTag("input");
            if (inputs != null && inputs.size() > 0) {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < inputs.size(); i++) {
                    Element inputTag = inputs.get(i);
                    if (!"imageField".equals(inputTag.attr("name"))) {
                        params.put(inputTag.attr("name"), inputTag.attr("value"));
                    }
                }
                //执行获取下载地址
                new OkHttpUtls().post(UrlConstant.mainUrl + action, params, new OkHttpUtls.okHttpLinner() {
                    @Override
                    public void Success(String res) {
                        if (mNetWorDownLoadkListener != null) {
                            mNetWorDownLoadkListener.successful(res);
                        }
                    }

                    @Override
                    public void Failure(Response response) {
                        if (mNetWorDownLoadkListener != null) {
                            mNetWorDownLoadkListener.failure(response);
                        }
                    }
                });
            }
        }
        return "";
    }
}
