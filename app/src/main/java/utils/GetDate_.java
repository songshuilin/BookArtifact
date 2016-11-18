package utils;

import java.util.Date;

/**
 * Created by 陈师表 on 2016/11/15.
 */

public class GetDate_ {
    public static String getDate_() {
        String time = null;

        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();

        time = String.valueOf(date.getTime());

        return time;
    }
}
