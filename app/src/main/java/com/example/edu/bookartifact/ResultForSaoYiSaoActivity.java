package com.example.edu.bookartifact;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import fragment.CommunityFragment;

/**
 * Created by Administrator on 2016/11/21.
 */

public class ResultForSaoYiSaoActivity extends Activity {
    //private TextView webview_;
    private WebView webview_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultforsaoyisao_layout);
        webview_=(WebView) findViewById(R.id.webview_);
       // webview_=(TextView) findViewById(R.id.webview_);
        //设置WebView属性，能够执行Javascript脚本
        webview_.getSettings().setJavaScriptEnabled(true);
        //支持内容重新布局
        webview_.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置content自适应屏幕
        webview_.getSettings().setLoadWithOverviewMode(true);
        webview_.setWebViewClient(new Client());
        webview_.loadUrl(CommunityFragment.info);
    }
    private class Client extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode== KeyEvent.KEYCODE_BACK)&&webview_.canGoBack()){
            webview_.goBack();//goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
