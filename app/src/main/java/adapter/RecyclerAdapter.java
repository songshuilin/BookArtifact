package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.edu.bookartifact.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.NovelBean;
/**
 * 作者  ：宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的展示，RecyclerView的适配器
 *
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHodler> implements View.OnClickListener{

    private List<NovelBean> list;//小说的集合
    private Context context;//上下文

    public RecyclerAdapter(List<NovelBean> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MyHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_item, null);
        MyHodler hodler = new MyHodler(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(MyHodler holder, int position) {
        //为各控件赋值
        holder.novelAuthor.setText("作者 :"+list.get(position).getNovelAuthor());
        holder.novelTime.setText(list.get(position).getNovelTime());
        holder.novelTitle.setText(list.get(position).getNovelTitle());
        Picasso.with(context).load(list.get(position).getNovelImg())
                .placeholder(R.drawable.default_novel)
                .into(holder.novelImg);
        holder.itemView.setTag(list.get(position));//为item设置tag
    }

    /**
     * 小说的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * item的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (NovelBean)v.getTag());
        }
    }

    /**
     * 点击的回调接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, NovelBean list);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }



    public class MyHodler extends RecyclerView.ViewHolder{

        private ImageView novelImg;//小说的图片的url
        private TextView novelTitle;//小说的题目
        private TextView novelAuthor;//小说的作者
        private TextView novelTime;//小说的更新时间
        // private TextView novelPath;

        /**
         * 跟listview的viewhodler模式差不多，只是recycleriew替你封装了
         * @param itemView
         */
        public MyHodler(View itemView) {
            super(itemView);
            //初始化各控件
            novelAuthor = (TextView) itemView.findViewById(R.id.novelAuthor);
            novelTime = (TextView) itemView.findViewById(R.id.novelTime);
            novelTitle = (TextView) itemView.findViewById(R.id.novelTitle);
            novelImg = (ImageView) itemView.findViewById(R.id.novelImg);
        }
    }

}
