package slidingmenuActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.edu.bookartifact.R;


/**
 * Created by 陈师表 on 2016/11/15.
 */

public class FeedBackActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);
    }

    public void btn_Back(View view){
        finish();
    }

}
