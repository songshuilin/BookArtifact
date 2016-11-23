package ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.edu.bookartifact.R;
import com.squareup.picasso.Picasso;
import utils.CrawlerData;
import bean.NovelDesc;
import utils.SharedUtil;


/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的描述 展示
 */

public class NovelDescActivity extends AppCompatActivity {
    private com.example.edu.bookartifact.databinding.ActivityNovelDescBinding novelDescBinding;
    private ImageView img;
    private NovelDesc novelDesc;
    private AlertDialog dialog;
    private LinearLayout ll_novel_desc;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                     novelDesc = (NovelDesc) msg.obj;
                    novelDescBinding.setNovelDesc(novelDesc);
                    //这里用到了 图片加载框架，Picasso,
                    Picasso.with(NovelDescActivity.this).load(novelDesc.getNovelImgPath())
                            .placeholder(R.drawable.default_novel).into(img);
                   dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里用到了 数据绑定框架，databonding,
        novelDescBinding = DataBindingUtil.setContentView(this, R.layout.activity_novel_desc);
        String novelPath = getIntent().getStringExtra("novelPath");
        getNovel(novelPath);
        initViews();
        //夜间模式
        if ("1".equals(SharedUtil.getInstance(this).get_NightMode())){
            ll_novel_desc.setBackgroundColor(getResources().getColor(R.color.background_night));
        }else {
            ll_novel_desc.setBackgroundColor(getResources().getColor(R.color.background_day));
        }
        dialog= new AlertDialog.Builder(this).create();
        dialog.setCancelable(false);
        dialog.setMessage("拼命加载中...");
        dialog.show();
    }

    /**
     * 初始化各控件
     */
 public void  initViews(){
     img= (ImageView) findViewById(R.id.novelImg);
     ll_novel_desc= (LinearLayout) findViewById(R.id.activity_novel_desc);
 }

    /**
     * 异步获取小说
     * @param path
     */
    public void getNovel(final String path) {
        new Thread() {
            @Override
            public void run() {
                NovelDesc novelDesc = CrawlerData.getNovel(path);
                Message message = handler.obtainMessage();
                message.obj = novelDesc;
                message.what = 0x123;
                handler.sendMessage(message);
                super.run();
            }
        }.start();

    }

    /**
     * 返回按钮的点击事件
     */
    public void back(View view){
        finish();
        Toast.makeText(NovelDescActivity.this,"你好啊",Toast.LENGTH_SHORT).show();
    }

    /**
     * 章节列表的点击事件
     * @param view
     */
    public void chapterList(View view){
        Toast.makeText(NovelDescActivity.this,"章节列表",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,NovelChapterListActivity.class);
        intent.putExtra("path",novelDesc.getNvlelDirectorypath());
        intent.putExtra("author",novelDesc.getNovelAuthor());
        intent.putExtra("title",novelDesc.getNovelTitle());
        startActivity(intent);
    }


}
