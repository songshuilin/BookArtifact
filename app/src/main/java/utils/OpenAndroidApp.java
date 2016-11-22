package utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by 陈师表 on 2016/11/21.
 * 主要功能：
 *    @ 1、判断是否安装了微信、QQ
 *    @ 2、打开你要打开的手机应用
 */

public class OpenAndroidApp {

    /**
     * 判断微信是否可用/即是否安装了微信
     * @param context
     * @return 返回booblean true表示已经安装了，false表示没有安装
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断QQ是否可用/即是否安装了QQ
     * @param context
     * @return 返回booblean true表示已经安装了，false表示没有安装
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 打开应用
     * @param context
     * @param appName //app名称，比如QQ、微信。。。
     * @return 返回booblean true表示打开成功，false表示失败
     */
    public static boolean openApplicaton(Context context,String appName ){
        Intent intent;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list=pm.getInstalledPackages(0);
        for (int i = 0; i <list.size() ; i++) {
            String name =  list.get(i).packageName;
            try {
                String app= (String) pm.getApplicationLabel(pm.getApplicationInfo(name,PackageManager.GET_META_DATA));
                if (appName.equals(app)){
                    intent = pm.getLaunchIntentForPackage(name);
                    context.startActivity(intent);
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
