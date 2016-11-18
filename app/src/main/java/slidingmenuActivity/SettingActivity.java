package slidingmenuActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.bookartifact.R;

import utils.SharedUtil;


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
    private TextView tv_book_by;//书架排序文字

    private RadioButton rb_update_time;//更新时间
    private RadioButton rb_recent_read;//最近阅读

    private SharedUtil sharedUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        init_Setting();

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
        tv_book_by = (TextView) findViewById(R.id.tv_sort_by);//书架排序设置

        //点击事件
        ll_book_sort.setOnClickListener(clickListener);
        ll_disclaimer.setOnClickListener(clickListener);
        ll_join_us.setOnClickListener(clickListener);
        ll_feedback.setOnClickListener(clickListener);
        ll_share_software.setOnClickListener(clickListener);

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








}
