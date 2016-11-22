package com.example.edu.bookartifact;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import bean.TopicEnity;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import utils.LoadIconFromNet;

public class TopicItemContentActivity extends Activity {

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.right)
    ImageButton right;
    @BindView(R.id.topic_icon)
    CircleImageView topicIcon;
    @BindView(R.id.tv_topic_username)
    TextView tvTopicUsername;
    @BindView(R.id.tv_topic_time)
    TextView tvTopicTime;
    @BindView(R.id.tv_topic_title)
    TextView tvTopicTitle;
    @BindView(R.id.tv_topic_content)
    TextView tvTopicContent;
    private String userName;
    private String title;
    private String time;
    private String content;
    String pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_item_content);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initData() {
        TopicEnity topic = (TopicEnity) getIntent().getExtras().getSerializable("item");
        userName = topic.getName();
        title = topic.getTitle();
        time = topic.getDate();
        content = topic.getContent();
        pic = topic.getPic();
        LoadIconFromNet.loadPic(handler,R.id.activity_topic_item_content,this,pic);


    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             switch (msg.what){
                         case R.id.activity_topic_item_content:
                             topicIcon.setImageBitmap((Bitmap) msg.obj);

                            break;

                     }
        }
    };

    private void initView() {
        right.setVisibility(View.INVISIBLE);
        tvHead.setText("详情");
        tvHead.setGravity(Gravity.CENTER_VERTICAL);
        tvHead.setPadding(20,0,0,0);
        tvTopicUsername.setText(userName);
        tvTopicTime.setText(time);
        tvTopicTitle.setText(title);
        tvTopicContent.setText(content);


    }


}
