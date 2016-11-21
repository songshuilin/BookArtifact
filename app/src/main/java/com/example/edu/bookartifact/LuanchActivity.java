package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.LaunchAdAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LuanchActivity extends Activity {

    @BindView(R.id.iv_launch_ad)
    ImageView ivLaunchAd;
    @BindView(R.id.btn_ignore)
    Button btnIgnore;
    @BindView(R.id.ll_launch_ad)
    LinearLayout llLaunchAd;
    @BindView(R.id.iv_launch_ad_pic1)
    ImageView ivLaunchAdPic1;
    @BindView(R.id.iv_launch_ad_pic2)
    ImageView ivLaunchAdPic2;
    @BindView(R.id.vp_ad)
    ViewPager vpAd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_unLogin)
    Button btnUnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luanch);
        ButterKnife.bind(this);
        initViewPager();
        ignore();
        btnUnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LuanchActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.activity_luanch:
                    llLaunchAd.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    private void ignore() {
        handler.sendEmptyMessageDelayed(R.id.activity_luanch, 4000);
    }


    private void initViewPager() {
        List<ImageView> imageViewList = new ArrayList<>();
        imageViewList.add(ivLaunchAdPic1);
        imageViewList.add(ivLaunchAdPic2);
        LaunchAdAdapter adAdapter = new LaunchAdAdapter(imageViewList);
        vpAd.setAdapter(adAdapter);
        vpAd.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpAd.setCurrentItem(0);

    }

    @OnClick(R.id.btn_ignore)
    public void onClick() {

        llLaunchAd.setVisibility(View.GONE);
    }

}
