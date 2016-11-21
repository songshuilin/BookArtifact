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
        view = inflater.inflate(R.layout.community_layout, container, false);



        ButterKnife.bind(this, view);
        layMusic.setOnClickListener(listener);
        layDiscuss.setOnClickListener(listener);
        laySaoyisao.setOnClickListener(listener);
        layShare.setOnClickListener(listener);
        layGirl.setOnClickListener(listener);

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