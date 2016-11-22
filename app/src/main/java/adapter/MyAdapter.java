package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import java.util.List;

import bean.ChatItem;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by roor on 2016/11/16.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private List<ChatItem> chatItemList;

    public static final int QUESTION = 1;
    public static final int ANSWER = 2;


    public MyAdapter(List<ChatItem> chatItemList) {
        this.chatItemList = chatItemList;

    }
    /**
     * 这是一个添加一条数据并刷新界面的方法
     *
     * @param msg
     */
    public void addData(ChatItem msg) {
        chatItemList.add(chatItemList.size(), msg);
        notifyItemInserted(chatItemList.size());

    }

    /**
     * 重写条目类型设置方法
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0){
            return ANSWER;
        }else if (position%2 == 1){
            return QUESTION;
        }
        return super.getItemViewType(position);

    }

    /**
     *  根据条目类型设置ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ANSWER){
            return new AnswerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item,parent,false));

        }else if (viewType == QUESTION){
            return new QuestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false));
        }


        return null;
    }


    /**
     * ViewHolder绑定，设置ViewHolder中控件数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof AnswerViewHolder){
            ((AnswerViewHolder)holder).iv_icon.setImageBitmap(chatItemList.get(position).getIcon());
            ((AnswerViewHolder)holder).tv_content.setText(chatItemList.get(position).getContent());
            ((AnswerViewHolder)holder).tv_name.setText(chatItemList.get(position).getName());

        }else if (holder instanceof QuestionViewHolder){
            ((QuestionViewHolder)holder).iv_icon.setImageBitmap(chatItemList.get(position).getIcon());
            ((QuestionViewHolder)holder).tv_content.setText(chatItemList.get(position).getContent());
            ((QuestionViewHolder)holder).tv_name.setText(chatItemList.get(position).getName());
        }

    }

    /**
     * 获取条目数量
     * @return
     */
    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    /**
     * 回复条目ViewHolder
     */
    class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_content;
        ImageView iv_icon;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_chat_content);
            tv_name = (TextView) itemView.findViewById(R.id.tv_chat_name);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_chat_icon);


        }
    }

    /**
     * 提问条目ViewHolder
     */
    class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_content;
        CircleImageView iv_icon;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_chat_content);
            tv_name = (TextView) itemView.findViewById(R.id.tv_chat_name);
            iv_icon = (CircleImageView) itemView.findViewById(R.id.iv_chat_icon);


        }
    }
}
