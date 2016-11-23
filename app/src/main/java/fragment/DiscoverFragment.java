package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.edu.bookartifact.ClickActivity;

import com.example.edu.bookartifact.R;
import com.iflytek.cloud.resource.Resource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import event.NightMode;
import utils.SharedUtil;


public class DiscoverFragment extends Fragment {

    private View view;


    @BindView(R.id.onclick_layout1)
    RelativeLayout onclickLayout1;
    @BindView(R.id.onclick_layout2)
    RelativeLayout onclickLayout2;
    @BindView(R.id.onclick_layout3)
    RelativeLayout onclickLayout3;
    @BindView(R.id.onclick_layout4)
    RelativeLayout onclickLayout4;
    public static int flag=1;//判断点击事件的标识

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //注册EvenBus
        EventBus.getDefault().register(this);

        view = inflater.inflate(R.layout.f_discover_layout, container, false);

//        view.setBackgroundColor(getResources().getColor(R.color.background_night));
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        onclickLayout1.setOnClickListener(listener);
        onclickLayout2.setOnClickListener(listener);
        onclickLayout3.setOnClickListener(listener);
        onclickLayout4.setOnClickListener(listener);
        if ("1".equals(SharedUtil.getInstance(getActivity()).get_NightMode())){
            view.setBackgroundColor(getResources().getColor(R.color.background_night));
        }else {
            view.setBackgroundColor(getResources().getColor(R.color.background_day));
        }
        return view;
    }

    @Subscribe
    public void changeMode(NightMode mode){
        boolean isNight=mode.getMode_();
        if (isNight){
            view.setBackgroundColor(getResources().getColor(R.color.background_night));
        }else {
            view.setBackgroundColor(getResources().getColor(R.color.background_day));
        }
    }

    //控件的监听事件
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //游戏中心的点击事件
                case R.id.onclick_layout1:
                    flag=1;
                    Intent intent=new Intent(getActivity(),ClickActivity.class);
                    startActivity(intent);
                    break;
                //咪咕阅读的点击事件
                case R.id.onclick_layout2:
                    flag=2;
                    Intent intent2=new Intent(getActivity(),ClickActivity.class);
                    startActivity(intent2);
                    break;
                //一元夺宝的点击事件
                case R.id.onclick_layout3:
                    flag=3;
                    Intent intent3=new Intent(getActivity(),ClickActivity.class);
                    startActivity(intent3);
                    break;
                //情感问答的点击事件
                case R.id.onclick_layout4:
                    flag=4;
                    Intent intent4=new Intent(getActivity(),ClickActivity.class);
                    startActivity(intent4);
                    break;

            }
        }
    };


}
