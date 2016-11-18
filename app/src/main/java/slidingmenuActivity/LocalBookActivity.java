package slidingmenuActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.edu.bookartifact.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by 陈师表 on 2016/11/14.
 *
 * 搜索出SD卡里面所有的txt文件
 */

public class LocalBookActivity extends Activity {
    private ListView lv_;
    private ArrayList name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localbook_layout);
        lv_ = (ListView) findViewById(R.id.listview);
        name = new ArrayList();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
            // File path = new File("/mnt/sdcard/");
            File[] files = path.listFiles();// 读取
            getFileName(files);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, name, R.layout.sd_localbook_list,
                new String[]{"Name"}, new int[]{R.id.sd_book_item});
        lv_.setAdapter(adapter);
        for (int i = 0; i < name.size(); i++) {
            Log.i("zeng", "list.  name:  " + name.get(i));
        }

    }

    public void btn_Back(View view) {
        finish();
    }

    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    Log.i("zeng", "若是文件目录。继续读1" + file.getName().toString() + file.getPath().toString());

                    getFileName(file.listFiles());
                    Log.i("zeng", "若是文件目录。继续读2" + file.getName().toString() + file.getPath().toString());
                } else {
                    String fileName = file.getName();

                    if (fileName.endsWith(".txt")) {
                        HashMap map = new HashMap();
                        String s = (fileName.substring(0, fileName.lastIndexOf("."))+".txt").toString();
                        Log.i("zeng", "文件名txt：：   " + s);
                        Log.e("TAG",file.getAbsolutePath().toString());//路径
                        String con=readSDFile(file.getAbsolutePath().toString());//读取文本内容
//                        String con=readSDFile("/storage/emulated/0/Android/data/com.tencent.mobileqq/files/tbslog/tbslog.txt");//读取文本内容
                        Log.e("TAG","con="+con);


                        map.put("Name", fileName.substring(0, fileName.lastIndexOf("."))+".txt");
                        name.add(map);
                    }
                }
            }
        }
    }


    public String readSDFile(String filepath) {
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(filepath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            System.out.println("读取成功：" + sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "renturn"+sb.toString();
    }



}
