package utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.edu.bookartifact.R;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

import static android.util.Log.i;

/**
 * Created by roor on 2016/11/16.
 * 与讯飞语音语义理解服务进行通信，
 * 使用文本理解功能并获取讯飞语音语义理解返回的Json数据和获取数据异常时返回错误信息
 */

public class GetChatResult {

    public static void getResultStr(final String question, final Context context, final Handler handler, final int what) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //创建文本语义理解对象
                TextUnderstander mTextUnderstander = TextUnderstander.createTextUnderstander(context, null);
                //初始化监听器
                TextUnderstanderListener searchListener = new TextUnderstanderListener(){
                    //语义结果回调
                    public void onResult(UnderstanderResult result){
                        String resultStr = result.getResultString();
                        i("@@@@@@", "onResult" + resultStr);
                        Message message = new Message();
                        message.what = what;
                        message.obj = resultStr;
                        handler.sendMessage(message);
                    }
                    //语义错误回调
                    public void onError(SpeechError error) {
                        i("@@@@@", "onError" + error.toString());
                        Message message = new Message();
                        message.what = R.id.chat_error_what;
                        message.obj = "网络异常，请小主检查网络";
                        handler.sendMessage(message);
                    }
                };
                //开始语义理解
                mTextUnderstander.understandText(question, searchListener);
            }
        }.start();
    }
}
