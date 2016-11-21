package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import java.util.List;

import bean.LocalBooksBean;

/**
 * Created by 陈师表 on 2016/11/18.
 * 加载本地书籍
 */

public class LocalBooksAdapter extends BaseAdapter {
    private Context context;//上下文
    private List<LocalBooksBean> olist;//小说章节的集合
    private LayoutInflater inflater;

    /**
     * 构造方法
     * @param context
     * @param list
     */
    public LocalBooksAdapter(Context context, List<LocalBooksBean> list) {
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
            view = inflater.inflate(R.layout.localbooks_item_show_layout, null);
            holder = new MyHolder();
            holder.tv_localbooks_show_item= (TextView) view.findViewById(R.id.tv_booksname_item);
            view.setTag(holder);
        } else {
            holder = (MyHolder) view.getTag();
        }
        holder.tv_localbooks_show_item.setText(olist.get(position).getName());
        return view;
    }


    /**
     * 缓存
     */
    public class MyHolder{
        private TextView tv_localbooks_show_item;
    }


}
