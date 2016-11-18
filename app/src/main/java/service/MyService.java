package service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import bean.Music;

import static android.R.attr.process;


/**
 * Created by Administrator on 2016/11/16.
 */

public class MyService extends Service {
    private MediaPlayer player=new MediaPlayer();
    private boolean isStop=false;
    private int state=1;// 1表示未播放状态，2表示播放状态，3表示暂停
    private Service_receiver receiver;
    private Music music;

    private int current=0,duration=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        receiver=new Service_receiver();
        IntentFilter filter=new IntentFilter("service");
        registerReceiver(receiver,filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent1=new Intent("activity");
                intent1.putExtra("over",true);
                sendBroadcast(intent1);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    //定义服务的一个广播接收类
    public class Service_receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("ddddd","dddd");
            int new_music=intent.getIntExtra("new_music",0);
            int control=intent.getIntExtra("control",0);
            if (new_music==1){
                music= (Music) intent.getSerializableExtra("music");
                play_(music);
                state=2;//播放状态
            }else if (control==1){
                if (state==2){
                    state=3;//暂停状态
                    player.pause();//音乐暂停
                }else if (state==3){
                    state=2;//播放状态
                    player.start();
                }
            }
            int process=intent.getIntExtra("process",0);
            if (process>0){
                player.seekTo((int)(process/(100*1.0)*duration));
            }
            Intent intent2=new Intent("activity");
            intent2.putExtra("dur", player.getDuration());
            intent2.putExtra("cur", player.getCurrentPosition());
            intent2.putExtra("state", state);
            intent2.putExtra("music", music);
            sendBroadcast(intent2);
        }
    }
    public void play_(Music music){
        try {
            player.stop();
            player.reset();
            player.setDataSource(music.path);
            player.prepare();
            player.start();
            duration=player.getDuration();

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    while (!isStop){
                        while (state==2&&current<duration){
                            try {
                                sleep(1000);
                                current=player.getCurrentPosition();
                                Intent intent=new Intent("activity");
                                intent.putExtra("cur",current);
                                intent.putExtra("dur",duration);
                                intent.putExtra("music",MyService.this.music);
                                intent.putExtra("state",state);
                                sendBroadcast(intent);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            unregisterReceiver(receiver);
        }
        isStop=true;
    }
}
