package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.bookartifact.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.SearchNovelBean;

/**
 * Created by 陈师表 on 2016/11/21.
 */

public class SearchResultAdapter extends BaseAdapter {

    private List<SearchNovelBean>olist;
    private Context context;
    private LayoutInflater inflater;

    public SearchResultAdapter(Context context,List<SearchNovelBean>olist){
        this.context=context;
        this.olist=olist;
        this.inflater= LayoutInflater.from(context);
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
        mHoudle houdle=null;
        if (view==null){
            view=inflater.inflate(R.layout.search_result_show_item_layout,null);
            houdle=new mHoudle();
            houdle.img_pic= (ImageView) view.findViewById(R.id.search_item_img);
            houdle.tv_title= (TextView) view.findViewById(R.id.search_item_title);
            houdle.tv_author= (TextView) view.findViewById(R.id.search_item_aythor);
            houdle.tv_type= (TextView) view.findViewById(R.id.search_item_type);
            view.setTag(houdle);
        }else {
            houdle= (mHoudle) view.getTag();
    }
        //如果网络获取的图片有效，则加载网络图片，否则加载本地图片
        if (olist.get(position).getNovelImg()==null){
            houdle.img_pic.setImageResource(R.drawable.default_novel);
        }else {
            Picasso.with(context).load(olist.get(position).getNovelImg()).into(houdle.img_pic);
        }
        houdle.tv_title.setText(olist.get(position).getNovelTitle());
        houdle.tv_author.setText(olist.get(position).getNovelAuthor());
        houdle.tv_type.setText(olist.get(position).getNovelType());
        return view;
    }


    public class mHoudle{
        private ImageView img_pic;
        private TextView  tv_title;
        private TextView  tv_author;
        private TextView  tv_type;
    }


}
