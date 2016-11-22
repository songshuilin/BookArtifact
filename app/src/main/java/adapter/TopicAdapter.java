package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import java.util.ArrayList;
import java.util.List;

import bean.TopicItemInShow;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import utils.CycListener;

/**
 * Created by roor on 2016/11/17.
 */

public class TopicAdapter extends RecyclerView.Adapter {
    List<TopicItemInShow> topicList = new ArrayList<>();
    CycListener cycListener;

    public TopicAdapter(List<TopicItemInShow> topicList) {
        this.topicList = topicList;
    }
    public void cleanData(){
        topicList.clear();
        notifyDataSetChanged();

    }
    public void addData(TopicItemInShow msg) {
        if (!topicList.contains(msg)){
            topicList.add(msg);
            notifyDataSetChanged();
        }
//        topicList.add(msg);
//        notifyDataSetChanged();

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TopicViewHolder viewHolder = new TopicViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.topic_item_layout, parent, false),cycListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TopicViewHolder)holder).topicIcon.setImageBitmap(topicList.get(position).getPic());
        ((TopicViewHolder)holder).tvTopicTime.setText(topicList.get(position).getDate());
        ((TopicViewHolder)holder).tvTopicUsername.setText(topicList.get(position).getName());
        ((TopicViewHolder)holder).tvTopicTitle.setText(topicList.get(position).getTitle());

    }
    public void setOnItemClickListener(CycListener listener){
        this.cycListener = listener;
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

     class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.topic_icon)
        CircleImageView topicIcon;
        @BindView(R.id.tv_topic_username)
        TextView tvTopicUsername;
        @BindView(R.id.tv_topic_time)
        TextView tvTopicTime;
        @BindView(R.id.tv_topic_title)
        TextView tvTopicTitle;
         private CycListener cycListener;
        public TopicViewHolder(View itemView, CycListener cycListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.cycListener = cycListener;


        }

         @Override
         public void onClick(View view) {
             cycListener.onItemClick(view, getLayoutPosition());
         }
     }



}
