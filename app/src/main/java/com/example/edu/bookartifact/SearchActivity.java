package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DBUtil.HistoryDB;
import adapter.HistoryShow_Adapter;
import adapter.SearchResultAdapter;
import bean.HistoryBean;
import bean.SearchNovelBean;
import ui.NovelDescActivity;
import utils.GetDate_;
import utils.IsNetworkAvailable;
import utils.Search_CrawlerDate;


/**
 * Created by 陈师表 on 2016/11/14.
 * <p>
 * 该类功能：1、通过关键字搜索出对应相关的书籍、作者
 * 2、提示出一部分最近热门的小说题目，点击可以直接搜索出对应的小说
 * 3、搜索的历史记录显示，可清空
 */

public class SearchActivity extends Activity {
    private EditText et_search;
    private ListView lv_history_show;
    private ListView lv_search_result;
    private String path1 = "http://s.xs8.cn/kw/";
    private String path_rs="http://s.xs8.cn/kw/";//搜索结果连接
    private HistoryDB db;
    private HistoryShow_Adapter adapter;//历史记录
    private SearchResultAdapter adapter_sr;//搜索结果
    private List<HistoryBean> list_ = new ArrayList<HistoryBean>();//历史记录
    private List<SearchNovelBean>list_sr=new ArrayList<SearchNovelBean>();//搜索结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        et_search = (EditText) findViewById(R.id.et_search);
        lv_history_show = (ListView) findViewById(R.id.lv_history_show);
        lv_search_result= (ListView) findViewById(R.id.lv_search_result);

        //历史纪录的点击事件
        lv_history_show.setOnItemClickListener(itemClickListener);

        //搜索结果的点击事件
        lv_search_result.setOnItemClickListener(itemClickListener_sr);

        //获取数据库
        db = HistoryDB.getIntance();
        //如果表不存在，则创建表，如果存在则不创建
        HistoryBean bean = new HistoryBean();//插入一条空的信息
        db.saveHistory(bean);
        list_ = db.loadHistory();
        if (list_.size() > 0) {
            adapter = new HistoryShow_Adapter(SearchActivity.this, list_);
            lv_history_show.setAdapter(adapter);
        } else {
            lv_history_show.setAdapter(null);
        }


    }
    //清除历史记录按钮
//    public void btn_clean_history(View view){
//        db=HistoryDB.getIntance();
//        db.cleanHistory();
//        //读
//        List<HistoryBean> list = db.loadHistory();
//        Log.e("TAG","listsize="+list.size());
//        if (list.size()>0) {
//            for (HistoryBean historyBean : list) {
//                Log.e("xyz", historyBean.toString());
//            }
//        }else {
//            Log.e("TAG","历史纪录已经被我清除干净了！");
//        }
//
//    }

    //清空历史记录
    public void btn_drop_history(View view) {
        db = HistoryDB.getIntance();
        db.dropHistory();
        lv_history_show.setAdapter(null);
//        adapter.notifyDataSetChanged();
        Log.e("TAG", "清空了历史记录");
    }

    //返回按钮
    public void btn_Back(View view) {
        finish();
    }

    //搜索按钮
    public void btn_Search(View view) {
        String et_str = et_search.getText().toString();

        String ppp = "";
        try {
            ppp = URLEncoder.encode(et_str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        path_rs=path1+ppp;//搜索对应的连接
        String path = path1 + ppp;
        Log.e("TAG", path);
        String time = GetDate_.getDate_();//获取时间
        showHistory(et_str, time);
        list_ = db.loadHistory();
        adapter = new HistoryShow_Adapter(SearchActivity.this, list_);
        lv_history_show.setAdapter(adapter);


        //搜索结果
        list_sr.clear();//每次先清空一下
        crawlerNovel();



    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
               list_sr= (List<SearchNovelBean>) msg.obj;
                adapter_sr=new SearchResultAdapter(SearchActivity.this,list_sr);
                lv_search_result.setAdapter(adapter_sr);
            }

        }
    };


    //添加搜索历史，存进数据库
    public void showHistory(String content, String date) {
        HistoryBean bean = new HistoryBean();//插入一条空的信息
        db.saveHistory(bean);
        db.deleteNull();//删除空的信息
        if (db.searchIsRepet(content)) {
            db.updateDB(content, date);
        } else {
            HistoryBean bean2 = new HistoryBean(content, date);
            db.saveHistory(bean2);//保存数据到数据库
        }
//        db.saveHistory(bean);//保存数据到数据库

//        //读
//        list_ = db.loadHistory();
//        if (list_!=null) {
//            for (HistoryBean historyBean : list_) {
//                Log.e("xyz", historyBean.toString());
//            }
//        }


    }

    //搜索结果点击事件
    private AdapterView.OnItemClickListener itemClickListener_sr=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchNovelBean bean_sr_= (SearchNovelBean) parent.getAdapter().getItem(position);
            String path_intent=bean_sr_.getNovelPath();
            Log.e("TAG","path_intent="+path_intent);
            Intent intent = new Intent(SearchActivity.this, NovelDescActivity.class);
            intent.putExtra("novelPath", path_intent);
            startActivity(intent);
        }
    };

    //历史记录的点击事件
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HistoryBean bean_info = (HistoryBean) parent.getAdapter().getItem(position);
            String cont = bean_info.getContent();
            String ppp = et_search.getText().toString();//默认情况内容为输入框内容
            et_search.setText(cont);//把输入框的信息改为点击的信息
            try {
                ppp = URLEncoder.encode(cont, "utf-8");//编码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String path = path1 + ppp;
            path_rs=path;
            Log.e("TAG", path);
            String time = GetDate_.getDate_();//重新获取时间
            showHistory(cont, time);//更新数据库
            list_ = db.loadHistory();//重新显示历史信息
            adapter = new HistoryShow_Adapter(SearchActivity.this, list_);
            lv_history_show.setAdapter(adapter);

            //搜索结果
            list_sr.clear();//每次先清空一下
            crawlerNovel();
        }
    };


    //搜索爬取数据
    public void crawlerNovel(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<SearchNovelBean>list____=Search_CrawlerDate.getNovel(path_rs);
                Message message=handler.obtainMessage();
                message.obj=list____;
                message.what=0x123;
                handler.sendMessage(message);
            }
        }.start();
    }


}
