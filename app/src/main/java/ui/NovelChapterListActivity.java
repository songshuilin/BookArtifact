package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.bookartifact.R;

import java.util.List;

import adapter.ChapterListAdapter;
import bean.NovelChapter;
import utils.CrawlerData;
import utils.DividerItemDecoration;

/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：显示小说章节的界面
 */
public class NovelChapterListActivity extends AppCompatActivity {
    private TextView mTvTitle;
    private List<NovelChapter> chapters;
    private TextView mAuthor;
    private RecyclerView mRecycler;
    private ChapterListAdapter adapter;
    private AlertDialog dialog;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    //设置recyclerview的布局
                    LinearLayoutManager manager=new LinearLayoutManager(NovelChapterListActivity.this);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecycler.setLayoutManager(manager);
                    //设置recyclerview的分割线
                    mRecycler.addItemDecoration(new DividerItemDecoration(NovelChapterListActivity.this,
                            DividerItemDecoration.VERTICAL_LIST));
//                    mRecycler.setItemAnimator(new DefaultItemAnimator());

                    adapter=new ChapterListAdapter(NovelChapterListActivity.this,chapters);
                    mRecycler.setAdapter(adapter);
                    Log.i("TAG", "handleMessage: "+chapters.toString());
                    dialog.dismiss();//取消对话框
                    /**
                     * RecyclerView  item点击事件
                     */
                     adapter.setListener(new ChapterListAdapter.OnClickItemListener() {
                         @Override
                         public void OnClickItem(View view, NovelChapter chapter) {
                       //      Toast.makeText(NovelChapterListActivity.this, chapter.toString(), Toast.LENGTH_SHORT).show();
                             Intent intent=new Intent(NovelChapterListActivity.this,ReadChapterActivity.class);
                             intent.putExtra("path",chapter.getChapterPath());
                             startActivity(intent);
                         }
                     });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_chapter_list);
        initViews();
        dialog= new AlertDialog.Builder(this).create();
        dialog.setCancelable(false);
        dialog.setMessage("拼命加载中...");
        dialog.show();
        /**
         * 获取上个activity传过来的值
         */
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        String author = intent.getStringExtra("author");
        String title = intent.getStringExtra("title");
        Log.i("TAGSS", "onCreate: " + path );
        mTvTitle.setText(title);
        mAuthor.setText(author);
        getChapters(path);//获取全部章节
    }

    /**
     * 初始化各控件
     */
    private void initViews() {
        mTvTitle = (TextView) findViewById(R.id.title);
        mAuthor= (TextView) findViewById(R.id.author);
        mRecycler= (RecyclerView) findViewById(R.id.chapter_recycler);
    }

    /**
     * 头部那个返回键的图片，点击事件
     * @param view
     */
    public void back(View view) {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 获取全部章节
     * @param path
     */
    public void getChapters(final String path) {
        new Thread() {
            @Override
            public void run() {
                super.run();
               chapters = CrawlerData.getNovelChapters(path);//异步获取小说的章节
               Message message= handler.obtainMessage();
                message.obj=chapters;
                message.what=0x123;
                handler.sendMessage(message);
            }
        }.start();


    }
}
