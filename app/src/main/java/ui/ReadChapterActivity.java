package ui;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.edu.bookartifact.R;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private String content;
    private boolean isChanged = false;
    private String[] sizeName = {"小", "中", "大"};
    private int whereOne;//设置字体大小的  item posiion
    private int whereOneReadName;//发音人的 item posiion
    private String name;
    private String readname;
    private boolean isNight = false;
    private boolean isVoice = false;
    private String[] readPeopleName = new String[]{
            "普通话(男)", "普通话(女)"
            , "汉语(东北话)", "汉语(河南话)", "中英文(粤语)"
            , "汉语(四川话)", "汉语(陕西话)"
    };
    private SpeechSynthesizer mTts;//创建 SpeechSynthesizer 对象
    private int curPagePosition;
    Handler handler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    Log.i("TAG", "handleMessage: " + chapterContent);
                    content = chapterContent.getNovelChapterContent();
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
                    mViewPage.setCurrentItem(0);
                    allTotalPage += totalPage;
                    break;
                case 0x1234:
                    curChapter++;
                    content = chapterContent.getNovelChapterContent();
                    int nextCount = content.length() % 300;
                    totalPage = content.length() / 300;
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
                        if (nextEnd > content.length()) {
                            nextEnd = content.length();
                        }
                        String subChapterContent = content.substring(nextStart, nextEnd);
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
        initSpeechSynthesizerPar();
//        NovelChapter chapter= (NovelChapter) getIntent().getSerializableExtra("chapter");
//        String path=chapter.getChapterPath();
        path = getIntent().getStringExtra("path");
        list = getIntent().getStringArrayListExtra("chaptersUrl");
        curChapter = list.indexOf(path);
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
//        mViewPage.setOffscreenPageLimit(0);
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPagePosition = position;
                if (position == allTotalPage - 1) {
                    if (curChapter < list.size() - 1) {
                        getNextChapterContent(list.get(curChapter + 1));
                    }
                }

                if (isVoice) {
                    TextView tv = (TextView) fragments.get(position).getView().findViewById(R.id.chapter_content);
                    readname = getPeopleName(readPeopleName[whereOneReadName]);
                    mTts.setParameter(SpeechConstant.VOICE_NAME, readname);
                    //开始合成
                    mTts.startSpeaking(tv.getText().toString(), listener);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (isChanged) {
                    if (name != null) {
                        EventBus.getDefault().post(getSize(name));
                    }
                }
                EventBus.getDefault().post(isNight);
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("设置操作");
        menu.setHeaderIcon(R.drawable.logo);
        menu.add(0, 1, 0, "字体");
        menu.add(0, 2, 0, "夜间模式");
        menu.add(0, 3, 0, "有声小说");
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Log.i("TAG", "上下文菜单一: ");
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("设置字体大小");
                dialog.setIcon(R.drawable.logo);
                dialog.setCancelable(false);
                dialog.setSingleChoiceItems(sizeName, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        whereOne = which;
                        Log.i("TAGaa", "setSingleChoiceItems: " + sizeName[which]);
                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isChanged = true;
                        if (isChanged) {
                            name = sizeName[whereOne];
                            int size = getSize(name);
                            Log.i("TAGaa", "onClick:............. " + size);
                            EventBus.getDefault().post(size);
                        }
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.show();
                break;
            case 2:
                Log.i("TAG", "上下文菜单二: ");
                isNight = !isNight;
                EventBus.getDefault().post(isNight);
                break;
            case 3:
                Log.i("TAG", "上下文菜单三: ");
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                dialog1.setTitle("设置发音人");
                dialog1.setIcon(R.drawable.logo);
                dialog1.setCancelable(false);
                dialog1.setSingleChoiceItems(readPeopleName, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        whereOneReadName = which;
                    }
                });
                dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isVoice = true;
                        TextView tv = (TextView) fragments.get(curPagePosition).getView().findViewById(R.id.chapter_content);
                        readname = getPeopleName(readPeopleName[whereOneReadName]);
                        mTts.setParameter(SpeechConstant.VOICE_NAME, readname);
                        //开始合成
                        mTts.startSpeaking(tv.getText().toString(), listener);
                    }
                });
                dialog1.setNegativeButton("取消", null);
                dialog1.show();
                break;
        }
        return true;
    }


    private int getSize(String sizeName) {
        switch (sizeName) {
            case "小":
                return 14;

            case "中":
                return 16;

            case "大":
                return 20;
        }
        return 0;
    }


    private String getPeopleName(String peopleName) {
        switch (peopleName) {
            case "普通话(女)":
                return "xiaoyan";

            case "普通话(男)":
                return "xiaoyu";

            case "汉语(东北话)":
                return "vixyun";

            case "汉语(河南话)":
                return "vixk";

            case "中英文(粤语)":
                return "vixm";

            case "汉语(四川话)":
                return "vixr";

            case "汉语(陕西话)":
                return "vixying";
        }

        return null;
    }

    /**
     * 设置语音合成的参数
     */
    private void initSpeechSynthesizerPar() {
        //1.创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener
        mTts = SpeechSynthesizer.createSynthesizer(this, new InitListener() {
            @Override
            public void onInit(int i) {

            }
        });
        // mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        //2.合成参数设置
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    }

    private SynthesizerListener listener = new SynthesizerListener() {

        @Override
        public void onSpeakResumed() {
            Log.i("TAG", "onSpeakResumed: ");
        }

        @Override
        public void onSpeakProgress(int arg0, int arg1, int arg2) {
            Log.i("TAG", "onSpeakProgress: ");

        }

        @Override
        public void onSpeakPaused() {
            Log.i("TAG", "onSpeakPaused: ");

        }

        @Override
        public void onSpeakBegin() {
            Log.i("TAG", "onSpeakBegin: ");

        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            Log.i("TAG", "onEvent: ");

        }

        @Override
        public void onCompleted(SpeechError arg0) {
            mViewPage.setCurrentItem(curPagePosition + 1);
        }

        @Override
        public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
            Log.i("TAG", "onBufferProgress: ");
        }
    };

    @Override
    protected void onDestroy() {
        mTts.stopSpeaking();//停止合成
        super.onDestroy();
    }
}
