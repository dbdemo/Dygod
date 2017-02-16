package db.com.dyhome.module.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import db.com.dyhome.R;
import db.com.dyhome.base.BaseActivity;

/**
 * Created by zdb on 2017/2/7.
 */

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private String url;
    private String title;
    private ProgressBar mProGb;

    @Override
    protected int setBodyView() {
        return R.layout.activity_webveiw;
    }

    public static void newInstance(Context context, String url, String title) {
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = (WebView) findViewById(R.id.webView);
        mProGb = (ProgressBar) findViewById(R.id.webView_progb);
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
        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(mWebChromClient);
    }

    private WebChromeClient mWebChromClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProGb.setProgress(newProgress);
            if (newProgress == 100) {
                mProGb.setVisibility(View.GONE);
            }
        }
    };

    private WebViewClient client = new WebViewClient() {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProGb.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProGb.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
