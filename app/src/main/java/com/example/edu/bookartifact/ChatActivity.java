package com.example.edu.bookartifact;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import bean.Answer;
import bean.ChatItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.GetChatResult;


public class ChatActivity extends Activity {


    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.right)
    ImageButton right;
    //聊天界面的对话条目展示的recyclerView
    private RecyclerView rvChat;
    //聊天界面的输入框
    private EditText etBottomContent;
    //聊天界面的发送按钮
    private TextView tvBottomSend;

    //保存聊天对话条目
    private List<ChatItem> chatItemList = new ArrayList<>();
    //默认客服名称
    public static final String ANSWER_NAME = "林妹妹";
    //未登陆时用户名称
    public static final String QUESTION_NAME = "游客";
    //默认客服头像
    public Bitmap answer_icon;
    //未登录是用户头像
    public Bitmap question_icon_dafult;
    //recyclerview适配器
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        init();


    }

    /**
     * 初始化控件及功能
     */
    private void init() {
        right.setVisibility(View.INVISIBLE);
        tvHead.setText("智能机器人林妹妹");
        tvHead.setGravity(Gravity.CENTER_VERTICAL);
        tvHead.setPadding(20,0,0,0);
        tvBottomSend = (TextView) findViewById(R.id.tv_bottom_send);
        etBottomContent = (EditText) findViewById(R.id.et_bottom_content);
        rvChat = (RecyclerView) findViewById(R.id.rv_chat);
        question_icon_dafult = BitmapFactory.decodeResource(this.getResources(), R.drawable.question_dafult_icon);
        answer_icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.answer_girl_icon);
        //设置布局管理器
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        //添加
        chatItemList.add(new ChatItem(ANSWER_NAME, "hi,来一起唠嗑唠嗑！", answer_icon));
        //chatItemList.add(new ChatItem(QUESTION_NAME, "hhhhhhh", question_icon_dafult));
        //设置adapter
        myAdapter = new MyAdapter(chatItemList);
        rvChat.setAdapter(myAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        //设置Item增加、移除动画
//        rv_chat.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
//        rv_chat.addItemDecoration(null);

    }

    //handler处理正常回复数据和一场回复数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.activity_chat:
                    //正常情况下解析数据并添加回复条目
                    selectAnswerType((String) msg.obj);
                    break;
                case R.id.chat_error_what:
                    //出现异常无法获取正常回复信息，回复错误信息
                    myAdapter.addData(new ChatItem(ANSWER_NAME, (String) msg.obj, answer_icon));
                    scrollToEnd();

                default:
                    break;
            }
        }
    };

    /**
     * 按回复Json数据格式解析数据
     *
     * @param resultStr
     */
    private void selectAnswerType(String resultStr) {
        JSONObject jsonObject = null;
        String type = "";
        try {
            jsonObject = new JSONObject(resultStr);
            type = jsonObject.getString("rc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("4".equals(type)) {
            String result = null;
            try {
                resultStr = jsonObject.getString("text");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myAdapter.addData(new ChatItem(ANSWER_NAME, resultStr, answer_icon));
            scrollToEnd();
        } else if ("0".equals(type)) {
            Gson gson = new Gson();
            Answer answer = gson.fromJson(resultStr, Answer.class);
            myAdapter.addData(new ChatItem(ANSWER_NAME, answer.getAnswer().getText(), answer_icon));
            scrollToEnd();
        }

    }

    /**
     * 底部发送按钮点击事件
     *
     * @param view
     */
    public void tv_bottom_send(View view) {
        String sendContent = etBottomContent.getText().toString();
        if (TextUtils.isEmpty(sendContent)) {
            Toast.makeText(this, "消息不能为空", Toast.LENGTH_SHORT).show();
        } else {

            GetChatResult.getResultStr(sendContent, this, handler, R.id.activity_chat);
            myAdapter.addData(new ChatItem(QUESTION_NAME, sendContent, question_icon_dafult));

            scrollToEnd();
            etBottomContent.setText("");
        }

    }

    /**
     * 聊天内容增加后自动滑动到末端
     */
    private void scrollToEnd() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.scrollToPosition(myAdapter.getItemCount() - 1);
    }
}
