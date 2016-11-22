package slidingmenuActivity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.edu.bookartifact.R;

import utils.OpenAndroidApp;


/**
 * Created by 陈师表 on 2016/11/15.
 */

public class FeedBackActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
    }

    /**
     * 返回按钮
     * @param view
     */
    public void btn_Back(View view){
        finish();
    }

    /**
     * 关注按钮，点击实现复制功能
     * @param view
     */
    public void btn_feed_back(View view){
        //获取剪贴板服务
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText("追书神器");
        Toast.makeText(FeedBackActivity.this,"已经复制到剪贴板，可以到微信搜索粘贴查找并关注公众号。",Toast.LENGTH_SHORT).show();
        if (OpenAndroidApp.isWeixinAvilible(FeedBackActivity.this)){
            OpenAndroidApp.openApplicaton(FeedBackActivity.this,"微信");
        }else {
            Toast.makeText(FeedBackActivity.this,"你似乎还没有安装微信哦，请安装后关注！",Toast.LENGTH_LONG).show();
        }
    }

}
