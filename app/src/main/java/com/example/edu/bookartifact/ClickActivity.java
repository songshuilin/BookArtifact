package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fragment.DiscoverFragment;


/**
 * 获取发现模块界面传递过来的标识，根据这个标识通过Webview分别加载显示游戏中心页
 * 面、咪咕阅读界面、一元夺宝界面、情感问答网页
 * Created by 方铃平 on 2016/11/15.
 */

public class ClickActivity extends Activity {

    private ScrollWebView content;//展示网页的部分
    private ImageButton back;//顶部返回按钮
    private ImageButton nav_back;//后退按钮
    private ImageButton nav_forward;//前进按钮
    private ImageButton reload;//刷新按钮
    private TextView tv_head;//标题
    private ImageButton right;
    private Animation translate;//平移
    private RelativeLayout lay_;
    private ImageButton f_share;
    private String uri;//web视图显示的网址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.click_layout);
        init_();
        onclick_();
        //设置WebView属性，能够执行Javascript脚本
        content.getSettings().setJavaScriptEnabled(true);
        //支持内容重新布局
        content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置content自适应屏幕
        content.getSettings().setLoadWithOverviewMode(true);
        //设置content的监听事件
        content.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                //滑动到底部
                lay_.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                //滑动到顶部
                lay_.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //滑动中
                lay_.setVisibility(View.GONE);
//                if ()
//                translate=new TranslateAnimation(0.0f,0.0f,0.0f,100.0f);
//                translate.setDuration(1000);
//                translate.setFillAfter(true);
//                lay_.setAnimation(translate);

               // content.setOnTouchListener(touchListener);
                //触摸事件监听
//                View.OnTouchListener touchListener = new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                            lay_.requestDisallowInterceptTouchEvent(false);
//                      } else {
//                            lay_.requestDisallowInterceptTouchEvent(true);
//                        }
//                        switch (motionEvent.getAction()) {
//                            case MotionEvent.ACTION_UP:
////                                translate=new TranslateAnimation(0.0f,0.0f,0.0f,200.0f);
////                                translate.setDuration(1000);
////                                lay_.setAnimation(translate);
//                                lay_.setVisibility(View.VISIBLE);
//                                break;
//                            case MotionEvent.ACTION_DOWN:
//                                translate = new TranslateAnimation(0.0f, 0.0f, 0.0f, 200.0f);
//                                translate.setDuration(1000);
//                                translate.setFillAfter(true);
//                                lay_.setAnimation(translate);
//                                //lay_.setVisibility(View.VISIBLE);
//                                break;
//                        }
//                        return true;
//                    }
//                };
              }
        });

    }
    public void onclick_() {
        switch (DiscoverFragment.flag) {
            case 1:
                tv_head.setText("游戏中心");
                uri="http://zssq.open.7724.com/";
                content.loadUrl(uri);
                content.setWebViewClient(new Client());
                break;
            case 2:
                tv_head.setText("咪咕阅读");
                uri="http://wap.cmread.com/r/p/index.jsp;jsessionid=0CE65615AE6E30F8EED46E8283B5E498.8ngFwlxGA.2.0?sqId=CP&dataSrcId=&vt=3&dataSrcId=&sqId=CP";
                content.loadUrl(uri);
                content.setWebViewClient(new Client());
                break;
            case 3:
                tv_head.setText("一元夺宝");
                uri="http://m.1.163.com/?from=baidu_mbrand";
                content.loadUrl(uri);
                content.setWebViewClient(new Client());
                break;
            case 4:
                tv_head.setText("情感问答");
                uri="http://www.yidianling.com/ask";
                content.loadUrl(uri);
                content.setWebViewClient(new Client());
                break;
        }

    }

    //Web视图
    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            uri=url;
            Log.i("@@@@",uri +"----------");
            return true;
        }

    }

    //点击返回键后，返回WebView的上一页面，当WebView返回到最开始的时候退出当前activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && content.canGoBack()) {
            content.goBack();//goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //前进键、后退键、刷新键、返回键的监听事件
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav_back:
                    content.goBack();
                    break;
                case R.id.nav_forward:
                    content.goForward();
                    break;
                case R.id.reload:
                    content.reload();
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.f_share:
                    Intent intent_share = new Intent(Intent.ACTION_SEND);
                   // String uri="http://www.lagou.com/center/company_493.html?speedShow=true&m=1";
                    intent_share.setType("text/plain");
                    intent_share.putExtra(Intent.EXTRA_TEXT, "下载地址："+uri);
                    startActivity(intent_share);
                    break;
            }
        }
    };
    //控件初始化
    public void init_(){
        tv_head = (TextView) findViewById(R.id.tv_head);
        right = (ImageButton) findViewById(R.id.right);
        right.setVisibility(View.GONE);
        content = (ScrollWebView) findViewById(R.id.content);
        back = (ImageButton) findViewById(R.id.back);
        nav_back = (ImageButton) findViewById(R.id.nav_back);
        nav_forward = (ImageButton) findViewById(R.id.nav_forward);
        reload = (ImageButton) findViewById(R.id.reload);
        f_share=(ImageButton) findViewById(R.id.f_share);
        lay_ = (RelativeLayout) findViewById(R.id.lay_);
        //设置一些按钮的点击事件监听
        nav_back.setOnClickListener(listener);
        nav_forward.setOnClickListener(listener);
        reload.setOnClickListener(listener);
        back.setOnClickListener(listener);
        f_share.setOnClickListener(listener);
    }

}
