package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.Glide;
import com.example.edu.bookartifact.R;

import java.util.concurrent.ExecutionException;

import bean.TopicEnity;
import bean.TopicItemInShow;

/**
 * Created by roor on 2016/11/17.
 */

public class LoadIconFromNet {
    public static void load(final Handler handler, final int what, final Context context, final TopicEnity t){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Bitmap bitmap = Glide.with(context).load(t.getPic()).asBitmap().centerCrop().into(100,100).get();
                    Message message = new Message();
                    message.obj = new TopicItemInShow(t.getDate(),t.getName(),t.getPid(),bitmap
                            ,t.getTitle(),t.getContent());
                    message.what = what;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static void loadPic(final Handler handler, final int what, final Context context, final String url){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    Bitmap bitmap = Glide.with(context).load(url).asBitmap().error(R.drawable.question_dafult_icon).centerCrop().into(100,100).get();
                    Message message = new Message();
                    message.obj = bitmap;
                    message.what = what;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
