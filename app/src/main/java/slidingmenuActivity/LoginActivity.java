package slidingmenuActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.edu.bookartifact.R;


/**
 * Created by 陈师表 on 2016/11/15.
 */

public class LoginActivity extends Activity {
    private CheckBox checkBox;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        checkBox = (CheckBox) findViewById(R.id.cb_);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(click);
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login:
                    Boolean ischeck = checkBox.isChecked();
                    if (ischeck) {
                        Log.e("TAG", "已选择!!!!");
                        Toast.makeText(LoginActivity.this, "已选择!!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("TAG", "没有选择!!!!");
                        Toast.makeText(LoginActivity.this, "没有选择!!!!", Toast.LENGTH_SHORT).show();
                    }
                break;
                default:
                    break;
            }
        }
    };

}
