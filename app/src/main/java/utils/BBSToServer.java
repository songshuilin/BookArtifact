package utils;

import android.os.Handler;
import android.os.Message;

import com.example.edu.bookartifact.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import bean.TopicEnity;

import static android.util.Log.i;

/**
 * Created by roor on 2016/11/17.
 */

public class BBSToServer {
    public static void getAllTopicFromServer(final Handler handler, final String urlStr, final int what) {

        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setConnectTimeout(5000);
                    huc.connect();
                    i("@@@@", "run" + huc.getResponseCode());
                    if (huc.getResponseCode() == 200) {
                        InputStream inputStream = huc.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        //stringBuilder.delete(0,stringBuilder.length()-1);
                        while ((len = inputStream.read(buffer)) != -1) {
                            stringBuilder.append(new String(buffer, 0, len));

                        }
                        Message message = new Message();
                        message.what = what;
                        message.obj = stringBuilder.toString();
                        i("@@@@", "run" + stringBuilder.toString());
                        handler.sendMessage(message);
                        inputStream.close();

                    } else {
                        handler.sendEmptyMessage(R.id.topic_result_error);
                    }

                } catch (Exception e) {
                    handler.sendEmptyMessage(R.id.topic_result_error);
                    e.printStackTrace();
                }

            }


        }.start();

    }

    public static void sendTopicToServer(final Handler handler, final String urlStr, final int what, final TopicEnity topicEnity) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String params = "title=" + topicEnity.getTitle() + "&content=" + topicEnity.getContent() +
                        "&pic=" + topicEnity.getPic() + "&date=" + topicEnity.getDate() + "&name=" + topicEnity.getName();


                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                    huc.setRequestMethod("POST");
                    huc.setDoOutput(true);
                    huc.setDoInput(true);
                    huc.setConnectTimeout(5000);
                    OutputStream out = huc.getOutputStream();
                    out.write(params.getBytes("utf-8"));




                    i("@@@@", "run" + huc.getResponseCode());
                    if (huc.getResponseCode() == 200) {
                        InputStream inputStream = huc.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) != -1) {
                            stringBuilder.append(new String(buffer, 0, len));

                        }
                        Message message = new Message();
                        message.what = what;
                        message.obj = stringBuilder.toString();
                        i("@@@@", "run" + stringBuilder.toString());
                        if (stringBuilder.toString().equals("true")){
                            handler.sendMessage(message);
                        }else {
                            handler.sendEmptyMessage(R.id.topic_result_error);
                        }

                        inputStream.close();

                    } else {
                        handler.sendEmptyMessage(R.id.topic_result_error);
                    }

                } catch (Exception e) {
                    handler.sendEmptyMessage(R.id.topic_result_error);
                    e.printStackTrace();
                }

            }


        }.start();
    }
}
