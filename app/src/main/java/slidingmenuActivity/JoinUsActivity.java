package slidingmenuActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
/**
 * Created by 陈师表 on 2016/11/16.
 */

public class JoinUsActivity extends Activity {
    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化WebView对象
        webview = new WebView(this);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webview.loadUrl("http://www.lagou.com/center/company_493.html?speedShow=true&m=1");
        //设置Web视图
        setContentView(webview);

    }
}
