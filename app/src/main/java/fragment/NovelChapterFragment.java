package fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.bookartifact.MainActivity;
import com.example.edu.bookartifact.R;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说章节内容展示的fragment
 */
public class NovelChapterFragment extends Fragment {
    private TextView mChapterName;//章节的题目
    private TextView mChapterBattery;//电池含多少
    private TextView mChapterCurPage;//当前页
    private TextView mChapterTotalPage;//总共业
    private TextView mChapterTime;//时间
    private TextView mChapterContent;//章节内容
    private View view;
    private LinearLayout mLl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);//注册事件 （EventBus）
        // 注册一个系统 BroadcastReceiver，作为访问电池信息之用，这个不能直接在AndroidManifest.xml中注册
        getActivity().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        getActivity().registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }


    /**
     * fragment显示的时候调用
     */
    @Override
    public void onResume() {
        super.onResume();

    }


    /**
     * 电池广播接收者
     */
    public BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mChapterBattery != null) {
                mChapterBattery.setText(intent.getIntExtra("level", 0) + "%");
            }
        }
    };

    /**
     * 系统时间广播接收者
     */
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (receiver != null) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(System.currentTimeMillis());
                mChapterTime.setText(time);
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void setChapters(Integer size){
        Log.i("TAGaa", "setChapters: ......................"+size);
               mChapterContent.setTextSize(size);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setgNight(Boolean isNight){
       if (isNight){
           mLl.setBackgroundColor(Color.parseColor("#2B2B2B"));
           mChapterContent.setTextColor(Color.parseColor("#ffffff"));
       }else {
           mLl.setBackgroundResource(R.drawable.textback);
       }
    }
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mBatInfoReceiver);//取消注册
        getActivity().unregisterReceiver(receiver);//取消注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_novel_chapter, container, false);
            initViews();

//        Calendar calendar=Calendar.getInstance();
//        int hour=calendar.get(Calendar.HOUR_OF_DAY);
//            SimpleDateFormat format=new SimpleDateFormat("HH:mm");
//            Date date=new Date();
//            String time=format.format(date);//获取当前时间

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String time = format.format(System.currentTimeMillis());

            Bundle bundle = getArguments();
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            int curPage = bundle.getInt("curpage");
            int totalPage = bundle.getInt("totalpage");
            mChapterName.setText(title);
            mChapterTotalPage.setText("/" + totalPage);
            mChapterCurPage.setText("" + curPage);
            mChapterContent.setText(Html.fromHtml(content));
            mChapterTime.setText(time);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initViews() {
        mLl= (LinearLayout) view.findViewById(R.id.ll_novel_chapter);
        mChapterBattery = (TextView) view.findViewById(R.id.battery);
        mChapterContent = (TextView) view.findViewById(R.id.chapter_content);
        mChapterCurPage = (TextView) view.findViewById(R.id.cur_page);
        mChapterTotalPage = (TextView) view.findViewById(R.id.total_page);
        mChapterName = (TextView) view.findViewById(R.id.chapter_title);
        mChapterTime = (TextView) view.findViewById(R.id.time);
       this.registerForContextMenu(mChapterContent);//为内容textview注册上下文事件

    }

}
