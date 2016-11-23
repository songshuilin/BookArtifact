package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.LaunchAdAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.ScreenManager;
import utils.SharedUtil;

import static android.util.Log.i;
import static com.example.edu.bookartifact.MyApplication.isLogined;
import static com.example.edu.bookartifact.MyApplication.nickName;
import static com.example.edu.bookartifact.MyApplication.userIconUrl;

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
    private static final String APPID = "1105760907";


    public static Tencent mTencent;
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private String scope;

    private UserInfo userInfo;
    private SharedPreferences sp ;

    boolean isJump;
    private TranslateAnimation mHiddenAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luanch);
         MyApplication.screenManager1.pushActivity(this);
        ButterKnife.bind(this);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(1000);
        mHiddenAction.setFillAfter(true);


        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLaunchAd.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }
        });

        sp = SharedUtil.getSharedPreferences(LuanchActivity.this);
        nickName = sp.getString("username","0");
        userIconUrl = sp.getString("userIconUrl","0");
        isLogined();
        initViewPager();
        ignore();
        setupViews();
        initData();
        btnUnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(LuanchActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private boolean isLogined() {

        if (!("0".equals(nickName))){
            isLogined = true;
            return true;

        }
        isLogined = false;
        return false;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.activity_luanch:
                    llLaunchAd.startAnimation(mHiddenAction);
                    //mHiddenAction.setStartOffset(9000);


                    break;
                case R.id.btn_login:
                    if(mTencent.getQQToken() == null){
                        System.out.println("qqtoken == null");
                    }
                    userInfo = new UserInfo(LuanchActivity.this, mTencent.getQQToken());
                    userInfo.getUserInfo(userInfoListener);
                    startActivity(new Intent(LuanchActivity.this,MainActivity.class));
                    finish();
                    break;
                case R.id.loginError:

                    break;
                case R.id.above:
                    if (!isJump){
                        isJump = false;
                        i("@@@", "handleMessage" + "ssssssssssss");

                        startActivity(new Intent(LuanchActivity.this,MainActivity.class));
                        finish();

                    }
                    break;
                case R.id.userUrl:
                    i("@@@", "handleMessage" + userIconUrl);
                default:
                    break;
            }
        }
    };

    private void ignore() {
        if (isLogined) {
            llLaunchAd.startAnimation(mHiddenAction);
            handler.sendEmptyMessageDelayed(R.id.above,800);
        }else {


                handler.sendEmptyMessageDelayed(R.id.activity_luanch,4000);


        }

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
        llLaunchAd.startAnimation(mHiddenAction);

        if (isLogined){
            isJump = true;
            llLaunchAd.startAnimation(mHiddenAction);

            startActivity(new Intent(LuanchActivity.this,MainActivity.class));
            finish();

        }
    }
    private void setupViews() {

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                login();
            }
        });


    }

    private void initData() {
        mTencent = Tencent.createInstance(APPID, LuanchActivity.this);

        scope = "all";
        loginListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                handler.sendEmptyMessage(R.id.loginError);

            }

            /**
             * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
             * "pf":"desktop_m_qq-10000144-android-2002-",
             * "query_authority_cost":448,
             * "authority_cost":-136792089,
             * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
             * "expires_in":7776000,
             * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
             * "msg":"",
             * "access_token":"A2455F491478233529D0106D2CE6EB45",
             * "login_cost":499}
             */
            @Override
            public void onComplete(Object value) {
                if (value == null) {
                    return;
                }

                try {
                    i("@@@", "onComplete" + "success");
                    JSONObject jo = (JSONObject) value;

                    String msg = jo.getString("msg");

                    System.out.println("json=" + String.valueOf(jo));

                    System.out.println("msg="+msg);
                    if ("sucess".equals(msg)) {

                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);


                    }
                    handler.sendEmptyMessage(R.id.btn_login);


                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };

        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

            /**
             * {"is_yellow_year_vip":"0","ret":0,
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "nickname":"��������ţ","yellow_vip_level":"0","is_lost":0,"msg":"",
             * "city":"�Ƹ�","
             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
             * "vip":"0","level":"0",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "province":"����",
             * "is_yellow_vip":"0","gender":"��",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
             */
            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    if(ret == 100030){
                        //Ȩ�޲�������Ҫ������Ȩ
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(LuanchActivity.this, "all", new IUiListener() {

                                    @Override
                                    public void onError(UiError arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onComplete(Object arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub

                                    }
                                });
                            }
                        };

                        LuanchActivity.this.runOnUiThread(r);
                    }else{
                        nickName = jo.getString("nickname");
                        String gender = jo.getString("gender");
                        userIconUrl = jo.getString("figureurl_qq_1");
                        handler.sendEmptyMessageDelayed(R.id.userUrl,500);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", nickName);
                        editor.putString("userIconUrl", userIconUrl);
                        isLogined = true;
                        editor.commit();

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }


            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };
    }

    private void login() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(LuanchActivity.this, scope, loginListener);

        }else {
            mTencent.logout(LuanchActivity.this);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.RESULT_LOGIN) {
                Tencent.handleResultData(data, loginListener);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.screenManager1.popActivity(this);
    }
}
