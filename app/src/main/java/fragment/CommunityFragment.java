package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.bookartifact.ChatActivity;
import com.example.edu.bookartifact.MusicActivity;
import com.example.edu.bookartifact.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class CommunityFragment extends Fragment {


    @BindView(R.id.tweet)
    ImageView tweet;
    @BindView(R.id.tv_music)
    TextView tvMusic;
    @BindView(R.id.lay_music)
    RelativeLayout layMusic;
    @BindView(R.id.discu)
    ImageView discu;
    @BindView(R.id.tv_discu)
    TextView tvDiscu;
    @BindView(R.id.lay_discuss)
    RelativeLayout layDiscuss;
    @BindView(R.id.com)
    ImageView com;
    @BindView(R.id.tv_com)
    TextView tvCom;
    @BindView(R.id.lay_share)
    RelativeLayout layShare;
    @BindView(R.id.help)
    ImageView help;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.lay_saoyisao)
    RelativeLayout laySaoyisao;
    @BindView(R.id.gir)
    ImageView gir;
    @BindView(R.id.tv_gir)
    TextView tvGir;
    @BindView(R.id.lay_girl)
    RelativeLayout layGirl;

    private View view;

    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.community_layout, container, false);
            ButterKnife.bind(this, view);
            layMusic.setOnClickListener(listener);
            layDiscuss.setOnClickListener(listener);
            laySaoyisao.setOnClickListener(listener);
            layShare.setOnClickListener(listener);
            layGirl.setOnClickListener(listener);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lay_discuss:

                    break;
                case R.id.lay_music:
                    Intent intent = new Intent(getActivity(), MusicActivity.class);
                    startActivity(intent);
                    break;
                case R.id.lay_share:
                    Toast.makeText(getActivity(), "分享追书神器", Toast.LENGTH_SHORT).show();
                    Intent intent_share = new Intent(Intent.ACTION_SEND);
                    String uri="http://www.lagou.com/center/company_493.html?speedShow=true&m=1";
                    intent_share.setType("text/plain");
                    intent_share.putExtra(Intent.EXTRA_TEXT, "我正在使用追书神器看小说，下载地址："+uri);
                    startActivity(intent_share);
                    break;
                case R.id.lay_saoyisao:

                    break;
                case R.id.lay_girl:
                    startActivity(new Intent(getActivity(), ChatActivity.class));

                    break;

            }
        }
    };


}
