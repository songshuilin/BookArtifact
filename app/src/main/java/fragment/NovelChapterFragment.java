package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import java.text.SimpleDateFormat;
import java.util.Date;


/** 作者 : 宋水林
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
    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_novel_chapter, container, false);
        initViews();
//        Calendar calendar=Calendar.getInstance();
//        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        Date date=new Date();
        String time=format.format(date);//获取当前时间
        Bundle bundle = getArguments();
        String title=bundle.getString("title");
        String content=bundle.getString("content");
        int curPage=bundle.getInt("curpage");
        int totalPage=bundle.getInt("totalpage");
        mChapterName.setText(title);
        mChapterTotalPage.setText("/"+totalPage);
        mChapterCurPage.setText(""+curPage);
        mChapterContent.setText(Html.fromHtml(content));
        mChapterTime.setText(time);
        return view;
    }

    private void initViews() {
        mChapterBattery= (TextView) view.findViewById(R.id.battery);
        mChapterContent= (TextView) view.findViewById(R.id.chapter_content);
        mChapterCurPage= (TextView) view.findViewById(R.id.cur_page);
        mChapterTotalPage= (TextView) view.findViewById(R.id.total_page);
        mChapterName= (TextView) view.findViewById(R.id.chapter_title);
        mChapterTime= (TextView) view.findViewById(R.id.time);
    }
}
