package slidingmenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.edu.bookartifact.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.LocalBooksAdapter;
import bean.LocalBooksBean;


/**
 * Created by 陈师表 on 2016/11/14.
 *
 * 搜索出SD卡里面所有的txt文件
 */

public class LocalBookActivity extends Activity {
    private ListView lv_;
    private ArrayList name;
    private File[] files;
    private AlertDialog dialog;
    private List<LocalBooksBean> list_=new ArrayList<LocalBooksBean>();
    private LocalBooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localbook_layout);
        lv_ = (ListView) findViewById(R.id.listview);
        lv_.setOnItemClickListener(itemClickListener);//设置点击事件
        name = new ArrayList();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
            // File path = new File("/mnt/sdcard/");
            files = path.listFiles();// 读取
            Loading();//对话框显示
            myThread_();//查找开启的线程
        }
    }

    //加载对话框动画
    private void Loading() {
        dialog = new AlertDialog.Builder(this, R.style.WaitDialog).create();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.wait_dialog_layout,null);
        ImageView img_wait = (ImageView) dialogView.findViewById(R.id.img_wait);
        AnimationDrawable animationDrawable = (AnimationDrawable) img_wait.getDrawable();
        animationDrawable.start();
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        dialog.show();
    }

    //扫描本地书籍比较耗时，加线程
    public void myThread_(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                getFileName(files);
                Message message=handler.obtainMessage();
                message.what=0123;
                handler.sendMessage(message);
            }
        }.start();
    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0123){
                adapter=new LocalBooksAdapter(LocalBookActivity.this,list_);
                lv_.setAdapter(adapter);
                dialog.dismiss();
            }
        }
    };



    public void btn_Back(View view) {
        finish();
    }

    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        String abs_path=file.getAbsolutePath().toString();//绝对路径
                        String con=readSDFile(file.getAbsolutePath().toString());//读取文本内容
                        String filename_=fileName.substring(0, fileName.lastIndexOf("."))+".txt";//文件名称
//                        Log.e("TAG","abs_path="+abs_path);//路径
//                        Log.e("TAG","con="+con);
//                        Log.e("TAG","filename="+filename_);
                        LocalBooksBean localBooksBean=new LocalBooksBean(abs_path,con,filename_);
                        list_.add(localBooksBean);
                        Log.e("TAG","list_="+list_.toString());

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


    //设置点击事件
    private AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(LocalBookActivity.this,"点击了我",Toast.LENGTH_SHORT).show();
            LocalBooksBean bean=(LocalBooksBean)parent.getAdapter().getItem(position);
            Log.e("TAG","list_name="+bean.getName());
            Log.e("TAG","list_content="+bean.getContent());
            Log.e("TAG","list_path="+bean.getPath());
            Bundle bundle=new Bundle();
            bundle.putString("name",bean.getName());
            bundle.putString("path",bean.getPath());
//            bundle.putString("content", Uri.encode(bean.getContent(),"UTF-8"));
            Intent intent_ReadLocalBook=new Intent(LocalBookActivity.this,ReadLocalBooksActivity.class);
            intent_ReadLocalBook.putExtras(bundle);

            startActivity(intent_ReadLocalBook);
        }
    };


}
