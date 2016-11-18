package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 陈师表 on 2016/11/15.
 */

public class IsNetworkAvailable {
    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}