package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.edu.bookartifact.R;

import java.util.List;

import bean.HistoryBean;


/**
 * 作者  ：陈师表
 * 时间 ：2016-11-17
 * 描述 ：历史纪录显示，采用的是RecyclerView 适配器类
 *
 */
public class HistoryShow_Adapter extends BaseAdapter{

    private Context context;//上下文
    private List<HistoryBean> olist;//小说章节的集合
    private LayoutInflater inflater;

    /**
     * 构造方法
     * @param context
     * @param list
     */
    public HistoryShow_Adapter(Context context, List<HistoryBean> list) {
        this.context = context;
        this.olist = list;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return olist.size();
    }

    @Override
    public Object getItem(int position) {
        return olist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.history_show_item_layout, null);
            holder = new MyHolder();
            holder.tv_history_show_item= (TextView) view.findViewById(R.id.tv_history_item);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        holder.tv_history_show_item.setText(olist.get(position).getContent());
        return view;
    }


    /**
     * 缓存
     */
    public class MyHolder{
        private TextView tv_history_show_item;
    }

}
