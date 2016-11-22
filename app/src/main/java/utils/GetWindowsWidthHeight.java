package utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by 陈师表 on 2016/11/22.
 * 功能是：
 *   @ 获取屏幕的宽高
 *   @ 需要传入ontent
 *   @ 返回一个数组  长度为2  a[0]=屏幕宽度  a[1]=屏幕高度
 */

public class GetWindowsWidthHeight {
    private static int[] arr=new int[2];

    public static int[] getScreenHW(Context context){
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

//        Display display = manager.getDefaultDisplay();
//        int width =display.getWidth();
//        int height=display.getHeight();
//        Log.d("width", String.valueOf(width));
//        Log.d("height", String.valueOf(height));        //第一种方法

        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        int width2=dm.widthPixels;
        arr[0]=width2;
        int height2=dm.heightPixels;
        arr[1]=height2;
        Log.d("width2", String.valueOf(width2));
        Log.d("height2", String.valueOf(height2));     //第二种方法
        return arr;
    }
}
