package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import adapter.MusicAdapter;
import bean.Music;
import service.MyService;
import utils.MusicUtil;

/**
 * Created by Administrator on 2016/11/16.
 */

public class MusicActivity extends Activity {
    private ImageButton back;//返回按钮
    private TextView tv_head;//顶部标题
    private ImageButton right;//顶部右侧按钮
    private Button single;//单曲循环按钮
    private Button shuffle;//随机播放按钮
    private Button loop;//列表循环按钮
    private Button btn_play;//播放按钮
    private ListView list_view;//音乐列表
    private TextView start_time;//展示开始时间
    private TextView end_time;//展示结束时间
    private SeekBar sb;//播放进度条
    private Intent intent;
    private Music music;
    private int index=0;
    private Activity_receiver receiver;
    private int option;
    private List<Music> olist=new ArrayList<Music>();//定义一个音乐的列表集合
    private MusicAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_layout);
        init_();
        tv_head.setText("音乐");
        right.setVisibility(View.GONE);//头部右侧按钮隐藏
        olist= MusicUtil.getMusic(MusicActivity.this);
        if (olist!=null){
            adapter=new MusicAdapter(MusicActivity.this,olist);
            list_view.setAdapter(adapter);
        }
        intent=new Intent(this, MyService.class);
        startService(intent);

        receiver=new Activity_receiver();
        IntentFilter filter=new IntentFilter("activity");
        registerReceiver(receiver,filter);
    }

    public class Activity_receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int state=intent.getIntExtra("state",0);
            switch (state){
                case  2:
                    btn_play.setBackgroundResource(R.drawable.audiobook_pause_normal);
                break;
                case 3:
                    btn_play.setBackgroundResource(R.drawable.audiobook_play_normal);
                    break;
            }
            int current=intent.getIntExtra("cur",0);
            int duration=intent.getIntExtra("dur",0);
            if (current>0&duration>0){
                start_time.setText(getTime(current));
                end_time.setText(getTime(duration));
                sb.setProgress((int) (current/(duration*1.0)*100));
            }

            //判断音乐是否播放完成
            boolean over = intent.getBooleanExtra("over", false);
            if (over) {
                Intent intent1 = new Intent("service");
                intent1.putExtra("new_music", 1);
                switch (option){
                    case  1:
                        music = olist.get((++index % olist.size())); //列表播放按钮的操作方法
                    break;
                    case  2:
                       //单曲循环按钮的操作方法
                        break;
                    case  3:
                        Random random=new Random();
                        music = olist.get(random.nextInt(olist.size())); //随机播放按钮的操作方法
                        break;
                }
                intent1.putExtra("music", music);
                sendBroadcast(intent1);
            }
        }
    }
    public String getTime(int time) {
        int toal_second = time / 1000;
        int minute = toal_second / 60;
        int second = toal_second % 60;
        return zero(minute) + ":" + zero(second);
    }
    public String zero(int i) {
        return i < 10 ? "0" + i : i + "";
    }

    //按钮的点击事件
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case  R.id.back:
                    finish();
                break;
                case  R.id.btn_play:
                    option=1;
                    play_();
                    break;
                case  R.id.loop:
                    option=1;
                    Toast.makeText(MusicActivity.this,"列表循环", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.shuffle:
                    option=3;
                    Toast.makeText(MusicActivity.this,"随机播放", Toast.LENGTH_SHORT).show();
                    break;
                case  R.id.single:
                    option=2;
                    Toast.makeText(MusicActivity.this,"单曲循环", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    //Seekbar拖动事件的监听
    private SeekBar.OnSeekBarChangeListener sblistener=new SeekBar.OnSeekBarChangeListener() {
        //当拖动条发生变化时调用该方法
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }
        //当用户开始滑动滑块时调用该方法（即按下鼠调用一次）
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        //当用户结束对滑块滑动时,调用该方法（即松开鼠标）
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intent intent=new Intent("service");
            intent.putExtra("process",sb.getProgress());
            sendBroadcast(intent);
        }
    };

    //播放按钮的操作方法
    public void play_(){
        Intent intent=new Intent("service");
        intent.putExtra("control",1);
        if (music==null){
            music=olist.get(index);
            index=0;
            intent.putExtra("new_music",1);
            intent.putExtra("music",music);
        }
        sendBroadcast(intent);
    }
    //按钮初始化操作
    public void init_(){
        back=(ImageButton) findViewById(R.id.back);
        tv_head=(TextView) findViewById(R.id.tv_head);
        right=(ImageButton) findViewById(R.id.right);
        single=(Button) findViewById(R.id.single);
        shuffle=(Button) findViewById(R.id.shuffle);
        loop=(Button) findViewById(R.id.loop);
        btn_play=(Button) findViewById(R.id.btn_play);
        list_view=(ListView) findViewById(R.id.list_view);
        start_time=(TextView) findViewById(R.id.start_time);
        end_time=(TextView) findViewById(R.id.end_time);
        sb=(SeekBar) findViewById(R.id.sb);
        back.setOnClickListener(listener);
        btn_play.setOnClickListener(listener);
        single.setOnClickListener(listener);
        shuffle.setOnClickListener(listener);
        loop.setOnClickListener(listener);
        sb.setOnSeekBarChangeListener(sblistener);
        list_view.setOnItemClickListener(onitemclicklistener);
    }
    /**
     * 实现listview单项点击事件
     */
    private AdapterView.OnItemClickListener onitemclicklistener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            list_music_click(position);
            Intent intent=new Intent("service");
            intent.putExtra("control",1);
        }
    };
    /**
     * listview单项点击事件的方法
     */
    public void list_music_click(int position) {
        Intent intent = new Intent("service");
        music = olist.get(position);
        index = position;
        intent.putExtra("new_music", 1);
        intent.putExtra("music", music);
        sendBroadcast(intent);
    }
    @Override
    protected void onDestroy() {
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//        }
        super.onDestroy();
    }
}
