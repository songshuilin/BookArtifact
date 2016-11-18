package http_;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by 陈师表 on 2016/11/14.
 *
 * 通过输入的信息，查询出对应的书籍信息
 */

public class Search_Http {
    private static String path1="http://s.xs8.cn/kw/";
    private static String path2="/col/all";

    public static void Search_Http(final String search){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String path=path1+search+path2;
                    Document doc=Jsoup.connect(path).get();
                    String info = doc.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




        }.start();
    }
}
