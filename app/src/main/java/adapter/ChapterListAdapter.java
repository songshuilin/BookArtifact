package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import java.util.List;

import bean.NovelChapter;


/**
 * 作者  ：宋水林
 * 时间 ：2016-11-13
 * 描述 ：小说章节的展示，采用的是RecyclerView 适配器类
 *
 */
public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.MyHolder> implements View.OnClickListener{

    private Context context;//上下文
    private List<NovelChapter> list;//小说章节的集合

    private  OnClickItemListener listener;//item的监听

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    /**
     * item 的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.OnClickItem(v,(NovelChapter)v.getTag());
        }
    }
      /**
       *自定义回调的接口，
       */
    public interface OnClickItemListener{
        void OnClickItem(View view, NovelChapter chapter);
    }

    /**
     * 构造方法
     * @param context
     * @param list
     */
    public ChapterListAdapter(Context context, List<NovelChapter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载 item的view
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_list_item, parent, false);
        MyHolder holder = new MyHolder(view);
        view.setOnClickListener(this);//为item设置点击事件
        return holder;
    }


    /**
     *
     * 主要用户控件的初始化，
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //各控件赋值
        holder.novelChapterName.setText(list.get(position).getChapterName());
        holder.novelChapterdDesc.setText(list.get(position).getChapterDesc());
        holder.itemView.setTag(list.get(position));//为item设置tag,
    }

    /**
     * item的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 自定义的viewhodler ,recyclerview要求你要用viewodler模式
     */
    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView novelChapterName;//小说章节的名字
        private TextView novelChapterdDesc;//小说章节的描述

        public MyHolder(View itemView) {
            super(itemView);
            //初始化控件
            novelChapterdDesc = (TextView) itemView.findViewById(R.id.chapterDesc);
            novelChapterName = (TextView) itemView.findViewById(R.id.chapterName);
        }
    }

}
