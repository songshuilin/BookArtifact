package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 发现模块的主界面，展示了游戏中心、咪咕阅读、一元夺宝、情感问答四个模块，并实现它们的点击跳转
 * Created by Administrator on 2016/11/17.
 */

public class DiscoverActivity extends Activity {
    private ImageButton back;//顶部返回按钮
    private ImageButton right;//顶部右边按钮
    private TextView tv_head;//标题
    private RelativeLayout onclick_layout1;//游戏中心
    private RelativeLayout onclick_layout2;//咪咕阅读
    private RelativeLayout onclick_layout3;//一元夺宝
    private RelativeLayout onclick_layout4;//情感问答
    public static int flag=1;//判断点击事件的标识
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.f_discover_layout);
        init_();
        right.setVisibility(View.GONE);//隐藏顶部右键
        tv_head.setText("发现");
    }
    //控件的监听事件
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //游戏中心的点击事件
                case R.id.onclick_layout1:
                    flag=1;
                    Intent intent=new Intent(DiscoverActivity.this,ClickActivity.class);
                    startActivity(intent);
                    break;
                //咪咕阅读的点击事件
                case R.id.onclick_layout2:
                    flag=2;
                    Intent intent2=new Intent(DiscoverActivity.this,ClickActivity.class);
                    startActivity(intent2);
                    break;
                //一元夺宝的点击事件
                case R.id.onclick_layout3:
                    flag=3;
                    Intent intent3=new Intent(DiscoverActivity.this,ClickActivity.class);
                    startActivity(intent3);
                    break;
                //情感问答的点击事件
                case R.id.onclick_layout4:
                    flag=4;
                    Intent intent4=new Intent(DiscoverActivity.this,ClickActivity.class);
                    startActivity(intent4);
                    break;
                case  R.id.back:
                    finish();
                    break;
            }
        }
    };
    //控件初始化
    public void init_() {
        back=(ImageButton) findViewById(R.id.back);
        right=(ImageButton) findViewById(R.id.right);
        tv_head=(TextView) findViewById(R.id.tv_head);
        onclick_layout1 = (RelativeLayout) findViewById(R.id.onclick_layout1);
        onclick_layout2 = (RelativeLayout) findViewById(R.id.onclick_layout2);
        onclick_layout3 = (RelativeLayout) findViewById(R.id.onclick_layout3);
        onclick_layout4 = (RelativeLayout) findViewById(R.id.onclick_layout4);
        back.setOnClickListener(listener);
        onclick_layout4.setOnClickListener(listener);
        onclick_layout3.setOnClickListener(listener);
        onclick_layout2.setOnClickListener(listener);
        onclick_layout1.setOnClickListener(listener);
    }

}
