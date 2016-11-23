package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import bean.TopicEnity;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.BBSToServer;
import utils.SharedUtil;

import static com.example.edu.bookartifact.BBSActivity.IP;

public class NewTopicActivity extends Activity {

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.right)
    Button right;
    @BindView(R.id.et_topic_title)
    EditText etTopicTitle;
    @BindView(R.id.et_topic_content)
    EditText etTopicContent;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_topic);
        ButterKnife.bind(this);

        getDataStr();

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataStr();
                if (!(TextUtils.isEmpty(title.trim())&& TextUtils.isEmpty(content.trim()))){
                    showTip();
                }else {
                    finish();
                }

            }
        });

    }

    private void getDataStr() {
        title = etTopicTitle.getText().toString();
        content = etTopicContent.getText().toString();
    }

    private void showTip() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("离开将丢失已输入的内容，确定离开？");
        dialog.setPositiveButton( "留在此页", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setNegativeButton( "离开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("@@@@@@", "onKeyDown" + event.getKeyCode());
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                getDataStr();

                if (!(TextUtils.isEmpty(title.trim())&& TextUtils.isEmpty(content.trim()))){
                    showTip();
                }else {
                    finish();
                }

                break;


        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        tvHead.setText("编辑话题");
        tvHead.setGravity(Gravity.CENTER_VERTICAL);
        tvHead.setPadding(20,0,0,0);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataStr();
                if (!(TextUtils.isEmpty(title.trim())||TextUtils.isEmpty(content.trim()))) {
                    addToServer();
                }else{
                    Toast.makeText(NewTopicActivity.this, "亲，标题和内容都不能为空哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             switch (msg.what){
                         case R.id.topic_result:
                             Toast.makeText(NewTopicActivity.this, "发布成功！请刷新查看", Toast.LENGTH_SHORT).show();
                             finish();

                            break;
                 case R.id.topic_result_error:
                     Toast.makeText(NewTopicActivity.this, "网络不佳，发布失败，请小主检查网络~", Toast.LENGTH_SHORT).show();
                     break;
                     }
        }
    };

    private void addToServer() {
        StringBuilder dateStr = new StringBuilder();
        Calendar c = Calendar.getInstance();


        dateStr.append(c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH));

        String urlStr = "http://" + IP + ":8080/Shujia/servlet/InsertPinglunServlet";
        TopicEnity topic = new TopicEnity();
        topic.setContent(content);
        topic.setTitle(title);
        topic.setDate(dateStr.toString());
        SharedPreferences sp = SharedUtil.getSharedPreferences(this);
        topic.setName(sp.getString("username","0"));
        topic.setPic(sp.getString("userIconUrl","0"));

        BBSToServer.sendTopicToServer(handler,urlStr,R.id.topic_result,topic );


    }

}
