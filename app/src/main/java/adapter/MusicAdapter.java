package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.edu.bookartifact.R;
import java.util.List;
import bean.Music;

/**
 * 为listview添加的适配器,展示音乐
 * Created by Administrator on 2016/11/18.
 */

public class MusicAdapter extends BaseAdapter {
    private Context context;//上下文对象
    private List<Music> olist;
    private LayoutInflater inflater;

    public MusicAdapter(Context context,List<Music> olist){
        this.context=context;
        this.olist=olist;
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
        Viewholder holder=null;
        if (view==null){
            holder=new Viewholder();
            view=inflater.inflate(R.layout.item_music_layout,null);
            holder.name= (TextView) view.findViewById(R.id.name);
            holder.author= (TextView) view.findViewById(R.id.author);
            view.setTag(holder);
        }else {
            holder= (Viewholder) view.getTag();
        }
        holder.name.setText(olist.get(position).getName());
        holder.author.setText(olist.get(position).getAuthor());
        return view;
    }
    //音乐的列表缓存类
    class Viewholder{
        private TextView name;
        private TextView author;
    }
}
