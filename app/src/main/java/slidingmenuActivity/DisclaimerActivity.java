package slidingmenuActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import utils.SharedUtil;


/**
 * Created by 陈师表 on 2016/11/15.
 * 免责声明
 */

public class DisclaimerActivity extends Activity {
    private TextView tv_;
    private LinearLayout ll_disclaimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer_layout);
        ll_disclaimer= (LinearLayout) findViewById(R.id.ll_disclaimer);
        //夜间模式
        if ("1".equals(SharedUtil.getInstance(this).get_NightMode())){
            ll_disclaimer.setBackgroundColor(getResources().getColor(R.color.background_night));
        }else {
            ll_disclaimer.setBackgroundColor(getResources().getColor(R.color.background_day));
        }
    }

    //返回按钮
    public void btn_Back(View view){
        finish();
    }

}
