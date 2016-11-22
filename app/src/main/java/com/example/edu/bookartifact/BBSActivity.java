package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import adapter.TopicAdapter;
import bean.TopicEnity;
import bean.TopicItemInShow;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.BBSToServer;
import utils.CycListener;
import utils.LoadIconFromNet;

import static android.util.Log.i;

public class BBSActivity extends Activity implements OnRefreshListener {

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.right)
    ImageButton right;
    //
    public static final String IP = "169.254.231.237";

    @BindView(R.id.tv_topic_error)
    TextView tvTopicError;
    @BindView(R.id.swipe_target)
    RecyclerView rvBbsTopic;

//    @BindView(R.id.refresh)
//    SwipeToLoadLayout refresh;
//
//    @BindView(R.id.refresh_head)
//    TopicRefreshHeadView refreshHead;

    private List<TopicEnity> topicList;
    private List<TopicItemInShow> topicItemList = new ArrayList<>();
    private TopicAdapter adapter;
    private AlertDialog dialog;
    private String path;
    private SwipeToLoadLayout refresh;
    private boolean isRefesh = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bbs);
        ButterKnife.bind(this);


        Loading();
        refresh = (SwipeToLoadLayout) findViewById(R.id.refresh);
//        final TopicRefreshHeadView refreshHead = (TopicRefreshHeadView) findViewById(R.id.refresh_head);


        refresh.setOnRefreshListener(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvHead.setText("综合讨论区");
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BBSActivity.this, NewTopicActivity.class));
            }
        });


        right.setBackgroundResource(R.drawable.ic_add_topic);
        path = "http://" + IP + ":8080/Shujia/servlet/GetAllPinglunServlet";
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


        rvBbsTopic.setLayoutManager(linearLayoutManager);
//        topicItemList.add(new TopicItemInShow("123","123",123,null,"123","123"));
        adapter = new TopicAdapter(topicItemList);
        adapter.setOnItemClickListener(new CycListener() {
            @Override
            public void onItemClick(View view, int position) {
                TopicEnity user_topic = topicList.get(position);
                Intent intent = new Intent(BBSActivity.this, TopicItemContentActivity.class);
                intent.putExtra("item", user_topic);
                startActivity(intent);
            }
        });
        rvBbsTopic.setAdapter(adapter);
        BBSToServer.getAllTopicFromServer(handler, path, R.id.topic_result);
        //i("@@@@", "onCreate" + path);


//        i("@@@", "onCreate" + path);


    }

    private void Loading() {
        dialog = new AlertDialog.Builder(this, R.style.WaitDialog).create();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.wait_dialog_layout, null);
        ImageView img_wait = (ImageView) dialogView.findViewById(R.id.img_wait);
        AnimationDrawable animationDrawable = (AnimationDrawable) img_wait.getDrawable();
        animationDrawable.start();
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.topic_result:

                    i("@@@@", "handleMessage" + (String) msg.obj);
                    parseTopicResult((String) msg.obj);
                    loadPic();
                    dialog.dismiss();

                    break;
                case R.id.topic_result_error:

                    showError();
                    dialog.dismiss();
                    break;
                case R.id.loadPicWhat:
                    adapter.addData((TopicItemInShow) msg.obj);
                    break;
                case R.id.loadPicRefreshWhat:
                    topicItemList.add((TopicItemInShow) msg.obj);
                    //topicItemList.remove(topicItemList.size()-1);
                    i("@@@@", "handleMessage" + topicItemList.size());
                    adapter.addData((TopicItemInShow) msg.obj);

                    break;
                case R.id.topic_result_refresh:
                    parseTopicResult((String) msg.obj);
                    loadPicRefresh();
                    break;


            }
        }
    };

    private void loadPicRefresh() {
        for (TopicEnity t : topicList) {
            LoadIconFromNet.load(handler, R.id.loadPicRefreshWhat, this, t);

        }
    }


    private void loadPic() {
        i("@@@@", "loadPic" + topicList.get(0).getName());
        for (TopicEnity t : topicList) {
            //String date, String name, int pid, Bitmap pic, String title, String content

            LoadIconFromNet.load(handler, R.id.loadPicWhat, this, t);
//                topicItemList.add();
            //Glide.with(this).load(t.getPic()).asBitmap().centerCrop().into(100,100).get()


//                adapter.addData();


        }
    }

    private void showError() {
        rvBbsTopic.setVisibility(View.INVISIBLE);
        tvTopicError.setText("网络异常，无法加载相关信息");
    }

    private void parseTopicResult(String obj) {
        i("@@@@@", "parseTopicResult: " + obj);
        topicList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            JSONObject object = jsonArray.getJSONObject(1);
//            i("@@@@@", "parseTopicResult" + object.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                topicList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), TopicEnity.class));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


//        topicList = gson.fromJson(obj, (Type) topicList);


    }


    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                i("@@@@", "run" + "complete");
                topicItemList.clear();
//                topicList.clear();

                BBSToServer.getAllTopicFromServer(handler, path, R.id.topic_result_refresh);
                refresh.setRefreshing(false);


            }
        }, 1000);

    }
}
