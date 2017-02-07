package db.com.dyhome.module.common;

import android.net.http.SslError;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;

/**
 * Created by zdb on 2017/2/7.
 */

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private String url;
    private String title;

    @Override
    protected int setBodyView() {
        return R.layout.activity_webveiw;
    }

    public static void newInstance() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = (WebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        mToolbar.setTitle(title);
        initWebView();
        if (TextUtils.isEmpty(url)) {
            this.finish();
            return;
        }
        mWebView.loadUrl(url);
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        mWebView.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                          handler.proceed();
                                      }
                                  }
        );
    }
}
