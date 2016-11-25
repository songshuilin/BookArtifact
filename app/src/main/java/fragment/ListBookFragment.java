package fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.edu.bookartifact.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapter;
import bean.NovelBean;
import event.Save;
import ui.NovelDescActivity;
import utils.CrawlerData;
import utils.DividerItemDecoration;
import utils.SharedUtil;

import static android.util.Log.i;


/**
 * 宋水林
 * 中间的内容展示的fragment
 */
public class ListBookFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecycler;
    private View view;
    private String path;
    private List<NovelBean> oldList = new ArrayList<>();
    private List<NovelBean> newList;
    private RecyclerAdapter adapter;
    private AlertDialog dialog;
    private SwipeToLoadLayout swipeToLoadLayout;
    private String nextPath;
    private boolean isSave=false;
    private  int next=1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0x1234:
                    adapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                    break;

                case 0x123:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    mRecycler.setLayoutManager(layoutManager);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                            DividerItemDecoration.VERTICAL_LIST));
                    adapter = new RecyclerAdapter(newList, getActivity());
                    mRecycler.setAdapter(adapter);
                    swipeToLoadLayout.setRefreshing(false);
                    dialog.dismiss();//取消对话框
                    /**
                     * recycler  item 的事件
                     */
                    adapter.setOnItemClickListener(new RecyclerAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, NovelBean novel) {
                    //        Toast.makeText(getActivity(), novel.getNovelTitle(), Toast.LENGTH_SHORT).show();
//                            NovelDesc novelDesc= CrawlerData.getNovel(novel.getNovelPath());
//                            Log.i("TAG", "onItemClick: "+novelDesc);
                            Intent intent = new Intent(getActivity(), NovelDescActivity.class);
                            intent.putExtra("novelPath", novel.getNovelPath());
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    @Subscribe
    public void setIsSave(Save save){
        if (save.isSave()){
            adapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            EventBus.getDefault().register(this);
            isSave=getIsSave();
            view = inflater.inflate(R.layout.fragment_list_book, container, false);
            //String path= savedInstanceState.getString("path");
            Bundle bundle = getArguments();
            path = bundle.getString("path");
            i("SONG", "onCreateView:..... " + path);
            initView();
            dialog = new AlertDialog.Builder(getActivity()).create();
            dialog.setCancelable(false);
            dialog.setMessage("拼命加载中...");

//            getNovelOldList();
            autoRefresh();//刷新
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        return view;
    }

//    /**
//     * 第一次获取的小说列表时
//     */
//    private void getNovelOldList() {
//        new Thread() {
//            @Override
//            public void run() {
//                oldList = CrawlerData.getNovelList(path);
//                Message message = handler.obtainMessage();
//                message.obj = oldList;
//                message.what = 0x123;
//                handler.sendMessage(message);
//                super.run();
//            }
//        }.start();
//    }

    /**
     * 最新的小说列表时
     */
    private void getNovelNewList() {
        new Thread() {
            @Override
            public void run() {
                isSave=getIsSave();
                newList = CrawlerData.getNovelList(path);//上拉下一页的数据
                oldList.addAll(newList);
//                Message message = handler.obtainMessage();
//                message.obj = newList;
//                message.what = 0x123;
//                handler.sendMessage(message);
                handler.sendEmptyMessage(0x123);
                super.run();
            }
        }.start();
    }

    /**
     * 最新的小说列表时
     */
    private void getNovelNextNewList() {
        new Thread() {
            @Override
            public void run() {
                isSave=getIsSave();
                List<NovelBean> nextNewList = CrawlerData.getNovelList(nextPath);
                i("TAGqq", "run" + isSave);
                i("TAGee", "run: "+nextPath);
                newList.addAll(nextNewList);
                oldList.addAll(newList);
                Message message = handler.obtainMessage();
                message.what = 0x1234;
                handler.sendMessage(message);
                super.run();
            }
        }.start();
    }

    /**
     * 可见时加载
     */
    @Override
    public void onResume() {
        super.onResume();
        if (newList != null) {
            dialog.dismiss();
        } else {
            dialog.show();
        }
    }

    /**
     * 初始化各控件
     */
    private void initView() {

        mRecycler = (RecyclerView) view.findViewById(R.id.swipe_target);
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getNovelList(PackListNovelBean packListNovel){
//        Log.i("TAG", "getNovelList: "+packListNovel.getList().toString());
//    }


    @Override
    public void onStop() {
              EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (oldList != null && newList != null) {
                    i("TAG", "run: .........." + newList.toString().equals(oldList.toString()));
                    if (oldList.toString().equals(newList.toString())) {
                        i("TAG", "handleMessage:................ ");
                        Toast.makeText(getActivity(), "你这已是最新的啦！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "刷新成功！", Toast.LENGTH_SHORT).show();
                    }
                }
                if (oldList != null) {
                    oldList.clear();//刷新前清空
                }
                getNovelNewList();
            }
        }, 2000);
    }


    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }


    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (path != null) {
                    if (!path.matches(".*[0-9]+.html")) {
                        Toast.makeText(getActivity(), "已经到底啦！", Toast.LENGTH_SHORT).show();
                        swipeToLoadLayout.setLoadingMore(false);
                    } else {
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        if (nextPath!=null){
                            nextPath=nextPath(nextPath);
                        }else {
                            nextPath = nextPath(path);
                        }
                        oldList.clear();//刷新前清空
                        getNovelNextNewList();//上拉加载下一页
                    }
                }
            }
        }, 2000);
    }

    /**
     * 获取下一页的url
     */
    public String nextPath(String url) {

        //    .*[0-9]+.html   正则表达式匹配     数字.html
        //  http://www.xs8.cn/zongcai.html  如果该页url匹配不了.*[0-9]+.html则可以默认已加载到底了。
        //   http://www.xs8.cn/channel-3.html   如果该页url能匹配.*[0-9]+.html则可以上拉加载。

        if (url != null) {
            String[] strings = url.split("[0-9]+.html");
            String headUrl = strings[0];// http://www.xs8.cn/channel-
            String[] strings1 = url.split(headUrl);

            String[] strings2 = strings1[1].split(".html");//strings1[1] 是 3.html

            String number = strings2[0];//就是匹配的数字部分  [0-9]+
             next = Integer.valueOf(number);
             next++;
            return headUrl + next + ".html";
        }

        return null;
    }

    public boolean getIsSave() {

        return "1".equals(SharedUtil.getInstance(getActivity()).get_save());
    }



}

