package slidingmenuActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.bookartifact.LuanchActivity;
import com.example.edu.bookartifact.MainActivity;
import com.example.edu.bookartifact.MyApplication;
import com.example.edu.bookartifact.R;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.LaunchAdAdapter;
import butterknife.OnClick;
import utils.SharedUtil;

import static android.util.Log.i;

import static com.example.edu.bookartifact.MyApplication.isLogined;
import static com.example.edu.bookartifact.MyApplication.nickName;
import static com.example.edu.bookartifact.MyApplication.userIconUrl;


/**
 * Created by 陈师表 on 2016/11/14.
 */

public class SettingActivity extends Activity {
    private LinearLayout ll_book_sort;//书架排序
    private Switch sw_update_info;//更新通知
    private Switch sw_save_flow;//省流量模式
    private LinearLayout ll_disclaimer;//免责声明
    private LinearLayout ll_join_us;//加入我们
    private LinearLayout ll_feedback;//意见反馈
    private LinearLayout ll_share_software;//分享追书神器
    private LinearLayout ll_change;//切换账号
    private LinearLayout ll_logout;//注销账号
    private TextView tv_book_by;//书架排序文字

    private RadioButton rb_update_time;//更新时间
    private RadioButton rb_recent_read;//最近阅读

    private SharedUtil sharedUtil;
    private Tencent mTencent;
    private static final String APPID = "1105760907";
    private String scope;
    private IUiListener loginListener;
    private IUiListener userInfoListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        init_Setting();
        initData();

    }

    //初始化控件
    public void init_Setting() {
        ll_book_sort = (LinearLayout) findViewById(R.id.lin_book_sort);
        sw_update_info = (Switch) findViewById(R.id.sw_update_info);
        sw_save_flow = (Switch) findViewById(R.id.sw_save_flow);
        ll_disclaimer = (LinearLayout) findViewById(R.id.lin_disclaimer);
        ll_join_us = (LinearLayout) findViewById(R.id.lin_join_us);
        ll_feedback = (LinearLayout) findViewById(R.id.lin_feedback);
        ll_share_software = (LinearLayout) findViewById(R.id.lin_share_software);
        ll_change= (LinearLayout) findViewById(R.id.lin_change);
        ll_logout= (LinearLayout) findViewById(R.id.lin_logout);
        tv_book_by = (TextView) findViewById(R.id.tv_sort_by);//书架排序设置

        //点击事件
        ll_book_sort.setOnClickListener(clickListener);
        ll_disclaimer.setOnClickListener(clickListener);
        ll_join_us.setOnClickListener(clickListener);
        ll_feedback.setOnClickListener(clickListener);
        ll_share_software.setOnClickListener(clickListener);
        ll_change.setOnClickListener(clickListener);
        ll_logout.setOnClickListener(clickListener);

        //Switch开关设置
        sw_save_flow.setOnCheckedChangeListener(checkChange);
        sw_update_info.setOnCheckedChangeListener(checkChange);


        sharedUtil = SharedUtil.getInstance(SettingActivity.this);
        //0表示关闭  1表示打开
        String sw_state=sharedUtil.get_save();
        if ("0".equals(sw_state)){
            sw_save_flow.setChecked(false);
        }else if ("1".equals(sw_state)){
            sw_save_flow.setChecked(true);
        }

    }

    //返回按钮
    public void btn_Back(View view) {
        finish();
    }

    //省流量模式设置,更新通知设置
    public CompoundButton.OnCheckedChangeListener checkChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            switch (compoundButton.getId()) {
                case R.id.sw_save_flow:
                    save_flow(isChecked);//省流量模式 开/闭
                break;
                case R.id.sw_update_info:
                    if (isChecked){
                        Toast.makeText(SettingActivity.this,"更新打开",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SettingActivity.this,"更新关闭",Toast.LENGTH_SHORT).show();
                    }
                break;
                default:
                    break;
            }
        }
    };


    //点击事件
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lin_book_sort:
                    book_sort();
                    break;
                case R.id.lin_disclaimer:
                    Toast.makeText(SettingActivity.this, "免责声明", Toast.LENGTH_SHORT).show();
                    Intent intent_disclaimer = new Intent(SettingActivity.this, DisclaimerActivity.class);
                    startActivity(intent_disclaimer);
                    break;
                case R.id.lin_join_us:
                    Toast.makeText(SettingActivity.this, "加入我们", Toast.LENGTH_SHORT).show();//浏览器打开
                    //地址：http://www.lagou.com/center/company_493.html?speedShow=true&m=1
                    Intent intent_join_us = new Intent();
                    intent_join_us.setAction("android.intent.action.VIEW");
                    Uri url = Uri.parse("http://www.lagou.com/center/company_493.html?speedShow=true&m=1");
                    intent_join_us.setData(url);
                    startActivity(intent_join_us);
                    break;
                case R.id.lin_feedback:
                    Toast.makeText(SettingActivity.this, "意见反馈", Toast.LENGTH_SHORT).show();
                    Intent intent_feedback=new Intent(SettingActivity.this,FeedBackActivity.class);
                    startActivity(intent_feedback);
                    break;
                case R.id.lin_share_software:
                    Toast.makeText(SettingActivity.this, "分享追书神器", Toast.LENGTH_SHORT).show();
                    Intent intent_share = new Intent(Intent.ACTION_SEND);
                    String uri="http://www.lagou.com/center/company_493.html?speedShow=true&m=1";
                    intent_share.setType("text/plain");
                    intent_share.putExtra(Intent.EXTRA_TEXT, "我正在使用追书神器看小说，下载地址："+uri);
                    startActivity(intent_share);
                    break;
                //切换账号
                case R.id.lin_change:
                    login();

                    break;
                //注销账号
                case R.id.lin_logout:
                    Intent intent = new Intent(SettingActivity.this,LuanchActivity.class);
                    isLogined = false;
                    userIconUrl = "0";
                    nickName = "0";
                    SharedPreferences sp = SharedUtil.getSharedPreferences(SettingActivity.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username","0");
                    edit.putString("userIconUrl","0");
                    edit.commit();
                    MyApplication.screenManager1.popAllActivityExceptMain(Activity.class);
                    startActivity(intent);



                    finish();
                    break;
                default:
                    break;
            }
        }
    };


    //书架排序的方法设置
    public void book_sort() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("书架排序方式");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        final View view = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_book_sort_layout, null);
        builder.setView(view);
        rb_update_time = (RadioButton) view.findViewById(R.id.dialog_rb_update_time);
        rb_recent_read = (RadioButton) findViewById(R.id.dialog_rb_recent_read);
        String state_rb = sharedUtil.get_sort();
        Log.e("TAG", "state=" + state_rb);
        if ("0".equals(state_rb)) {
            rb_update_time.setChecked(true);
//            rb_recent_read.setChecked(false);
        } else if ("1".equals(state_rb)) {
            rb_update_time.setChecked(false);
//            rb_recent_read.setChecked(true);
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (rb_update_time.isChecked()) {
                    //0表示按更新时间
                    sharedUtil.put_sort("0");
                    Log.e("TAG", "0、按更新时间");
                    //发广播
                    Intent intent_sort = new Intent("MainActivity");
                    intent_sort.putExtra("sort", "书架排序:0、按更新时间");
                    sendBroadcast(intent_sort);
                    //改变文字提醒
                    tv_book_by.setText("按更新时间");
                } else {
                    //1表示按最近阅读
                    sharedUtil.put_sort("1");
                    Log.e("TAG", "1、按最近阅读");
                    Intent intent_sort = new Intent("MainActivity");
                    intent_sort.putExtra("sort", "书架排序:1、按最近阅读");
                    sendBroadcast(intent_sort);
                    tv_book_by.setText("按最近阅读");
                }

            }
        });
        builder.show();
    }

    //省流量设置
    public void save_flow(boolean isOpen){
        //0表示关闭  1表示打开
        if (isOpen){
            sharedUtil.put_saveflow("1");

            //发送一个广播
            Intent intent_sort = new Intent("MainActivity");
            intent_sort.putExtra("save", "省流量:1、打开");
            sendBroadcast(intent_sort);

            Toast.makeText(SettingActivity.this,"省流量打开",Toast.LENGTH_SHORT).show();
        }else {
            sharedUtil.put_saveflow("0");

            //发送一个广播
            Intent intent_sort = new Intent("MainActivity");
            intent_sort.putExtra("save", "省流量:0、关闭");
            sendBroadcast(intent_sort);

            Toast.makeText(SettingActivity.this,"省流量关闭",Toast.LENGTH_SHORT).show();
        }

    }

    private UserInfo userInfo;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case R.id.btn_login:
                    if(mTencent.getQQToken() == null){
                        System.out.println("qqtoken == null");
                    }
                    userInfo = new UserInfo(SettingActivity.this, mTencent.getQQToken());
                    userInfo.getUserInfo(userInfoListener);
                    //startActivity(new Intent(SettingActivity.this,MainActivity.class));
                    finish();
                    break;
                case R.id.loginError:

                    break;

                default:
                    break;
            }
        }
    };









    private void initData() {
        mTencent = Tencent.createInstance(APPID, SettingActivity.this);

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
                                mTencent.reAuth(SettingActivity.this, "all", new IUiListener() {

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

                        SettingActivity.this.runOnUiThread(r);
                    }else{
                        nickName = jo.getString("nickname");
                        String gender = jo.getString("gender");
                        userIconUrl = jo.getString("figureurl_qq_1");
                        handler.sendEmptyMessageDelayed(R.id.userUrl,500);

                        SharedPreferences sp = SharedUtil.getSharedPreferences(SettingActivity.this);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", nickName);
                        editor.putString("userIconUrl", userIconUrl);
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
            mTencent.login(SettingActivity.this, scope, loginListener);

        }else {
            mTencent.logout(SettingActivity.this);
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








}
