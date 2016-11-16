package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.edu.bookartifact.R;

import java.util.List;

import adapter.RecyclerAdapter;
import bean.NovelBean;
import ui.NovelDescActivity;
import utils.CrawlerData;
import utils.DividerItemDecoration;

/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：中间的内容展示的fragment
 */

public class ListBookFragment extends Fragment {
    private RecyclerView mRecycler;
    private View view;
    private String path;
    private List<NovelBean> list;
    private RecyclerAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<NovelBean>listNovels= (List<NovelBean>) msg.obj;
            switch (msg.what){
                case 0x123:
                    Log.i("SONGSHUILIN", "handleMessage: "+list.toString());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    //设置recyclerview的布局
                    mRecycler.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //设置分割线
                    mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                            DividerItemDecoration.VERTICAL_LIST));
                    adapter=new RecyclerAdapter(listNovels,getActivity());
                    mRecycler.setAdapter(adapter);//设置适配器

                    /**
                     * recycler  item 的事件
                     */
                    adapter.setOnItemClickListener(new RecyclerAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, NovelBean novel) {
                            Toast.makeText(getActivity(),novel.getNovelTitle(), Toast.LENGTH_SHORT).show();
//                            NovelDesc novelDesc= CrawlerData.getNovel(novel.getNovelPath());
//                            Log.i("TAG", "onItemClick: "+novelDesc);
                            Intent intent=new Intent(getActivity(), NovelDescActivity.class);
                            intent.putExtra("novelPath",novel.getNovelPath());
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_book, container, false);
        //String path= savedInstanceState.getString("path");
        Bundle bundle = getArguments();//获取acyivity传过来的bundle的实例，
        path = bundle.getString("path");
        getNovelList();
        initView();
        return view;
    }

    /**
     * 异步获取小说
     */
    private void getNovelList() {
        new Thread(){
            @Override
            public void run() {
              list= CrawlerData.getNovelList(path);//异步获取小说
              Message message=handler.obtainMessage();
                message.obj=list;
                message.what=0x123;
                handler.sendMessage(message);
                super.run();
            }
        }.start();
    }

    /**
     * 初始化各控件
     */
    private void initView() {
        mRecycler = (RecyclerView) view.findViewById(R.id.fragment_recycler);

    }



//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getNovelList(PackListNovelBean packListNovel){
//        Log.i("TAG", "getNovelList: "+packListNovel.getList().toString());
//    }


    @Override
    public void onStop() {
 //       EventBus.getDefault().unregister(this);
        super.onStop();
    }


}
