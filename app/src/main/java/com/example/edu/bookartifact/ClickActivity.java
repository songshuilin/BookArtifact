package com.example.edu.bookartifact;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 获取发现模块界面传递过来的标识，根据这个标识通过Webview分别加载显示游戏中心页
 * 面、咪咕阅读界面、一元夺宝界面、情感问答网页
 * Created by 方铃平 on 2016/11/15.
 */

public class ClickActivity extends Activity {

    private WebView content;//展示网页的部分
    private ImageButton back;//顶部返回按钮
    private ImageButton nav_back;//后退按钮
    private ImageButton nav_forward;//前进按钮
    private ImageButton reload;//刷新按钮
    private TextView tv_head;//标题
    private ImageButton right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.click_layout);
        //控件初始化
        tv_head= (TextView)findViewById(R.id.tv_head);
        right=(ImageButton) findViewById(R.id.right);
        right.setVisibility(View.GONE);
        content= (WebView)findViewById(R.id.content);
        back=(ImageButton)findViewById(R.id.back);
        nav_back=(ImageButton)findViewById(R.id.nav_back);
        nav_forward=(ImageButton)findViewById(R.id.nav_forward);
        reload=(ImageButton)findViewById(R.id.reload);
        //设置WebView属性，能够执行Javascript脚本
        content.getSettings().setJavaScriptEnabled(true);
        //支持内容重新布局
        content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //设置content自适应屏幕
        content.getSettings().setLoadWithOverviewMode(true);
        //设置一些按钮的点击事件监听
        nav_back.setOnClickListener(listener);
        nav_forward.setOnClickListener(listener);
        reload.setOnClickListener(listener);
        back.setOnClickListener(listener);

        init_();
    }
    public void init_(){
        switch (DiscoverActivity.flag){
            case  1:
                tv_head.setText("游戏中心");
                content.loadUrl("http://zssq.open.7724.com/");
                content.setWebViewClient(new Client());
            break;
            case  2:
                tv_head.setText("咪咕阅读");
                content.loadUrl("http://wap.cmread.com/r/p/index.jsp;jsessionid=0CE65615AE6E30F8EED46E8283B5E498.8ngFwlxGA.2.0?sqId=CP&dataSrcId=&vt=3&dataSrcId=&sqId=CP");
                content.setWebViewClient(new Client());
                break;
            case  3:
                tv_head.setText("一元夺宝");
                content.loadUrl("http://m.1.163.com/?from=baidu_mbrand");
                content.setWebViewClient(new Client());
                break;
            case  4:
                tv_head.setText("情感问答");
                content.loadUrl("http://www.yidianling.com/ask");
                content.setWebViewClient(new Client());
                break;
        }
    }
    //Web视图
    private class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    //点击返回键后，返回WebView的上一页面，当WebView返回到最开始的时候退出当前activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode== KeyEvent.KEYCODE_BACK)&&content.canGoBack()){
            content.goBack();//goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    //前进键、后退键、刷新键、返回键的监听事件
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.nav_back:
                    content.goBack();
                break;
                case  R.id.nav_forward:
                    content.goForward();
                    break;
                case  R.id.reload:
                    content.reload();
                    break;
                case  R.id.back:
                    finish();
                    break;
            }
        }
    };
}
