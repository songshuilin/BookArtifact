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
    private List<Fragment> fragments = new ArrayList<>();
    private int totalPage;
    private int allTotalPage;
    private String path;
    private NovelFragmentAdapter adapter;
    private ArrayList<String> list;
    private int curChapter;//当前的章节
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    Log.i("TAG", "handleMessage: " + chapterContent);
                    String content = chapterContent.getNovelChapterContent();
                    int count = content.length() % 300;
                    totalPage = content.length() / 300;
                    if (count != 0) {
                        totalPage++;
                    }
                    int start = 0;
                    int end = 0;
                    /**
                     * 为每个fragment显示300个字
                     */
                    for (int i = 0; i < totalPage; i++) {
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
                        bundle.putInt("curpage", i + 1);
                        bundle.putInt("totalpage", totalPage);
//                        //判断是否是最后一页，如果是为true
//                        if (i+1==a){
//                            isEnd=true;
//                        }
                        fragment.setArguments(bundle);
                    }
                    adapter = new NovelFragmentAdapter(getSupportFragmentManager(), fragments);
                    mViewPage.setAdapter(adapter);
                    //mViewPage.setCurrentItem(0);
                    allTotalPage += totalPage;
                    break;
                case 0x1234:
                    curChapter++;
                    String nextContent = chapterContent.getNovelChapterContent();
                    int nextCount = nextContent.length() % 300;
                    totalPage = nextContent.length() / 300;
                    if (nextCount != 0) {
                        totalPage++;
                    }
                    int nextStart = 0;
                    int nextEnd = 0;
                    /**
                     * 为每个fragment显示300个字
                     */
                    for (int i = 0; i < totalPage; i++) {
                        nextStart = i * 300;
                        nextEnd = (i + 1) * 300;
                        if (nextEnd > nextContent.length()) {
                            nextEnd = nextContent.length();
                        }
                        String subChapterContent = nextContent.substring(nextStart, nextEnd);
                        NovelChapterFragment fragment = new NovelChapterFragment();
                        fragments.add(fragment);
                        //传递数据给fragment
                        Bundle bundle = new Bundle();
                        bundle.putString("title", chapterContent.getNovelChapterName());
                        bundle.putString("content", subChapterContent);
                        bundle.putInt("curpage", i + 1);
                        bundle.putInt("totalpage", totalPage);
//                        //判断是否是最后一页，如果是为true
//                        if (i+1==a){
//                            isEnd=true;
//                        }
                        fragment.setArguments(bundle);
                    }
                    adapter.notifyDataSetChanged();
                    allTotalPage += totalPage;

                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chapter);
        initViews();
//        NovelChapter chapter= (NovelChapter) getIntent().getSerializableExtra("chapter");
//        String path=chapter.getChapterPath();
        path = getIntent().getStringExtra("path");
        list= getIntent().getStringArrayListExtra("chaptersUrl");
//        Log.i("TAGss", "onCreate: "+list.toString());
        getChapterContent(path);//异步获取内容
    }

    /**
     * 异步获取内容
     *
     * @param url
     */
    private void getChapterContent(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                chapterContent = CrawlerData.getNovelChapterContent(url);
                Message messag = handler.obtainMessage();
                messag.what = 0x123;
                handler.sendMessage(messag);
            }
        }.start();
    }

    /**
     * 异步获取下一章的内容
     *
     * @param url
     */
    private void getNextChapterContent(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                chapterContent = CrawlerData.getNovelChapterContent(url);
                Message messag = handler.obtainMessage();
                messag.what = 0x1234;
                handler.sendMessage(messag);
            }
        }.start();
    }

    private void initViews() {
        mViewPage = (ViewPager) findViewById(R.id.chapter_viewpage);
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == allTotalPage - 1) {
                    getNextChapterContent(list.get(curChapter+1));
                    Log.i("TAG", "onPageSelected: " + (allTotalPage - 1));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
