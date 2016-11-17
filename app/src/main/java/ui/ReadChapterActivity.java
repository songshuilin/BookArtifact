package ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.edu.bookartifact.R;

import java.util.ArrayList;
import java.util.List;

import adapter.NovelFragmentAdapter;
import bean.NovelChapterContent;
import fragment.NovelChapterFragment;
import utils.CrawlerData;


/**
 * 作者 : 宋水林
 * 时间 : 2016-11-17
 * 描述 : 阅读小说
 */
public class ReadChapterActivity extends AppCompatActivity {
    private ViewPager mViewPage;
    private NovelChapterContent chapterContent;
    private List<Fragment> fragments;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    Log.i("TAG", "handleMessage: " + chapterContent);
                    fragments=new ArrayList<>();
                    String content = chapterContent.getNovelChapterContent();
                    int count = content.length() % 300;
                    int a = content.length() / 300;
                    if (count != 0) {
                        a++;
                    }
                    int start = 0;
                    int end = 0;
                    /**
                     * 为每个fragment显示300个字
                     */
                    for (int i = 0; i < a; i++) {
                    start = i * 300;
                    end = (i + 1) * 300;
                    if (end > content.length()) {
                        end = content.length();
                    }
                    String subChapterContent = content.substring(start, end);
                    NovelChapterFragment fragment = new NovelChapterFragment();
                    fragments.add(fragment);
                        //传递数据给fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("title", chapterContent.getNovelChapterName());
                    bundle.putString("content", subChapterContent);
                        bundle.putInt("curpage",i+1);
                        bundle.putInt("totalpage",a);
                    fragment.setArguments(bundle);
                }
                    NovelFragmentAdapter adapter=new NovelFragmentAdapter(getSupportFragmentManager(),fragments);
                    mViewPage.setAdapter(adapter);
                    mViewPage.setCurrentItem(0);
                    break;


            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chapter);
        initViews();
        String path = getIntent().getStringExtra("path");
        getChapterContent(path);//异步获取内容
    }

    /**
     * 异步获取内容
     * @param path
     */
    private void getChapterContent(final String path) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                chapterContent = CrawlerData.getNovelChapterContent(path);
                Message messag = handler.obtainMessage();
                messag.what = 0x123;
                handler.sendMessage(messag);
            }
        }.start();

    }


    private void initViews() {
        mViewPage = (ViewPager) findViewById(R.id.chapter_viewpage);
    }


}
