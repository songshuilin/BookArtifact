package com.example.edu.bookartifact;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import utils.ScreenManager;

/**
 * Created by roor on 2016/11/16.
 */

public class MyApplication extends Application {
    public static ScreenManager screenManager1;
    public static String nickName;
    public static String userIconUrl;
    public static boolean isLogined;
    @Override
    public void onCreate() {
        super.onCreate();

        //58057a98   582bab0e
        //初始化讯飞组件
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"=58057a98");

        screenManager1 = ScreenManager.getScreenManager1();
    }
}
