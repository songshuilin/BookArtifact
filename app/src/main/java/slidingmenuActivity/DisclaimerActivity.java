package slidingmenuActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.edu.bookartifact.R;


/**
 * Created by 陈师表 on 2016/11/15.
 * 免责声明
 */

public class DisclaimerActivity extends Activity {
    private TextView tv_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disclaimer_layout);
    }

    //返回按钮
    public void btn_Back(View view){
        finish();
    }

}
