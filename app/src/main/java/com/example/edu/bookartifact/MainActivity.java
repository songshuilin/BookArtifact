package com.example.edu.bookartifact;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.antlr.v4.automata.ATNFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.NovelFragmentAdapter;
import bean.NovelType;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import event.NightMode;
import event.Save;
import fragment.CommunityFragment;
import fragment.DiscoverFragment;
import fragment.ListBookFragment;
import slidingmenuActivity.FeedBackActivity;
import slidingmenuActivity.LocalBookActivity;
import slidingmenuActivity.SettingActivity;
import slidingmenuActivity.WifiTranportActivity;
import utils.CrawlerData;
import utils.LoadIconFromNet;
import utils.SharedUtil;

import static android.util.Log.i;

/**
 * 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：主界面
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.ll_book)
    LinearLayout llBook;
    private ViewPager mViewPage;
    private TabLayout mTabLayout;
    private Toolbar mToobar;
    private Button mBtn_song_afterBook, mBtn_song_community, mBtn_song_find;
    private ImageView mSearchImg, mUsernameImg;
    private NavigationView mNavigationView;
    private DrawerLayout mDl;
    private static final String NOVEL_ALL_PATH = "http://www.xs8.cn/shuku/c5-t0-f0-w0-u0-o0-2-1.html";
    private CommunityFragment comFragment;
    private DiscoverFragment disFragment;
    private FragmentManager manager;
    private SharedPreferences sp;
    private  List<Fragment> fragments;
    private CircleImageView circleLoginView;
    private TextView tv_name;
    private SharedUtil sharedUtil;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.screenManager1.pushActivity(this);
        ButterKnife.bind(this);
        sp = getSharedPreferences("setting",MODE_PRIVATE);

        EventBus.getDefault().register(this);//注册事件（eventbus）
        initFrag();
        //夜间模式默认设置 （日间模式）
        sharedUtil = SharedUtil.getInstance(MainActivity.this);
        sharedUtil.put_NightMode("0");//默认为日间模式

        initViews();//初始化view
        CrawlerData.getNovelTypeList();//获取小说的类型，也就是分类

    }

    private void initFrag() {
        manager = getSupportFragmentManager();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case R.id.usernameImg:
                    mUsernameImg.setImageBitmap((Bitmap) msg.obj);
                    circleLoginView.setImageBitmap((Bitmap) msg.obj);

                    break;
                case R.id.setUserIcon:
                    LoadIconFromNet.loadPic(handler, R.id.usernameImg, MainActivity.this, MyApplication.userIconUrl);

                    if (!"0".equals(MyApplication.nickName)){
                        tv_name.setText(MyApplication.nickName);
                    }else {
                        tv_name.setText("游客");
                    }
                    break;
                case 0x12345:
                    if (!isBack){
                        finish();
                    }
                    isBack=false;
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 初始化各view 和一些view设置监听
     */
    private void initViews() {


        mTabLayout = (TabLayout) findViewById(R.id.song_tab_title);
        mViewPage = (ViewPager) findViewById(R.id.song_viewpager);
        mToobar = (Toolbar) findViewById(R.id.toolbar);
        mBtn_song_afterBook = (Button) findViewById(R.id.song_afterBook);
        mBtn_song_community = (Button) findViewById(R.id.song_community);
        mBtn_song_find = (Button) findViewById(R.id.song_find);
        mNavigationView = (NavigationView) findViewById(R.id.nv_main_navigation);
        mSearchImg = (ImageView) findViewById(R.id.search);
        mUsernameImg = (ImageView) findViewById(R.id.usernameImg);
        mDl = (DrawerLayout) findViewById(R.id.dl);


        ll_content= (LinearLayout) findViewById(R.id.ll_book);

        if (sharedUtil.get_NightMode().toString().equals("0")){
            ll_content.setBackgroundColor(getResources().getColor(R.color.background_day));
//            ll_community.setBackgroundColor(getResources().getColor(R.color.background_day));
//            disFramLayout.setBackgroundColor(getResources().getColor(R.color.background_day));
        }else {
            ll_content.setBackgroundColor(getResources().getColor(R.color.background_night));
//            ll_community.setBackgroundColor(getResources().getColor(R.color.background_night));
//            disFramLayout.setBackgroundColor(getResources().getColor(R.color.background_night));
        }


        mSearchImg.setOnClickListener(this);
        mUsernameImg.setOnClickListener(this);
        mBtn_song_find.setOnClickListener(this);
        mBtn_song_afterBook.setOnClickListener(this);
        mBtn_song_community.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        circleLoginView = (CircleImageView) headerView.findViewById(R.id.song_login_Img);
        tv_name = (TextView) headerView.findViewById(R.id.song_login_username);
        mBtn_song_afterBook.setBackgroundResource(R.drawable.onclick_shape);
        mBtn_song_find.setBackgroundResource(R.drawable.default_shape);
        mBtn_song_community.setBackgroundResource(R.drawable.default_shape);

    }

    private void setUserIcon() {
        handler.sendEmptyMessageDelayed(R.id.setUserIcon,1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUserIcon();

    }


    /**
     * 这里用到了eventbus 这个框架，这个方法只有收到了就会触发，相当于观察者模式
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//表示运行在主线程
    public void getNovelList(List<NovelType> event) {
         fragments = new ArrayList<>();
        /**
         * 动态初始化fragmnet,并且从activity传值给对应的fragment
         */
        for (int j = 0; j < event.size(); j++) {
            ListBookFragment fragment = new ListBookFragment();
            fragments.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("path", event.get(j).getNovelTypePath());
            fragment.setArguments(bundle);
        }
        NovelFragmentAdapter adapter = new NovelFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPage.setAdapter(adapter);
        mViewPage.setCurrentItem(0);//默认显示第一页
        mTabLayout.setupWithViewPager(mViewPage);//用的是tablayout，关联viewpage

        //为tablayout添加tab
        for (int i = 0; i < event.size(); i++) {
            mTabLayout.getTabAt(i).setText(event.get(i).getNovelTypeName());
        }


        /**
         * 设置tablayout中的tab选中事件
         */
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                Toast.makeText(MainActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * activity销毁时，取消注册事件(EventBus)
     */
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);//取消注册
        MyApplication.screenManager1.popActivity(this);
        super.onDestroy();
    }

    boolean isBack=false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         switch (keyCode){
                     case KeyEvent.KEYCODE_BACK:
                         i("TAG", "onKeyDown" + "");
                       isBack=!isBack;
                         if (isBack){
                         Toast.makeText(MainActivity.this,"再按一次退出应用程序",Toast.LENGTH_SHORT).show();
                             handler.sendEmptyMessageDelayed(0x12345,1000);
                         }else {
                             handler.sendEmptyMessage(0x12345);
                         }
                        break;
                     default:
                         break;
                 }

        return false;
    }

    /**
     * 响应底部三个按钮的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = manager.beginTransaction();
//        hideFragments(transaction);
        switch (v.getId()) {
            case R.id.song_find:
                hideFragments(transaction);
                llBook.setVisibility(View.GONE);
                if (disFragment == null) {
                    disFragment = new DiscoverFragment();
                    transaction.add(R.id.dis_frag, disFragment);

                } else {
                    transaction.show(disFragment);
                }
                mBtn_song_find.setBackgroundResource(R.drawable.onclick_shape);
                mBtn_song_afterBook.setBackgroundResource(R.drawable.default_shape);
                mBtn_song_community.setBackgroundResource(R.drawable.default_shape);

                Toast.makeText(MainActivity.this, "发现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.song_afterBook:
                hideFragments(transaction);
                Toast.makeText(MainActivity.this, "追书", Toast.LENGTH_SHORT).show();
                llBook.setVisibility(View.VISIBLE);
                mBtn_song_afterBook.setBackgroundResource(R.drawable.onclick_shape);
                mBtn_song_find.setBackgroundResource(R.drawable.default_shape);
                mBtn_song_community.setBackgroundResource(R.drawable.default_shape);
                break;
            case R.id.song_community:
                hideFragments(transaction);
                if (comFragment == null) {
                    comFragment = new CommunityFragment();
                    transaction.add(R.id.com_frag, comFragment);
                } else {
                    transaction.show(comFragment);
                }

                llBook.setVisibility(View.GONE);
                mBtn_song_community.setBackgroundResource(R.drawable.onclick_shape);
                mBtn_song_find.setBackgroundResource(R.drawable.default_shape);
                mBtn_song_afterBook.setBackgroundResource(R.drawable.default_shape);
                Toast.makeText(MainActivity.this, "社区", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                Intent intent_chen_search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent_chen_search);
                break;
            case R.id.usernameImg:
                Toast.makeText(MainActivity.this, "用户名的头像", Toast.LENGTH_SHORT).show();
                mDl.openDrawer(GravityCompat.START);
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {

        if (disFragment != null) {
            transaction.hide(disFragment);
        }
        if (comFragment != null) {
            transaction.hide(comFragment);
        }
    }

    /**
     * 响应侧栏的item的相应事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanLocalBooks:
                Toast.makeText(MainActivity.this, "扫描本地书籍", Toast.LENGTH_SHORT).show();
                Intent intent_chen_localBooks = new Intent(MainActivity.this, LocalBookActivity.class);
                startActivity(intent_chen_localBooks);
                mDl.closeDrawers();//关闭侧滑栏
                break;
            case R.id.wifiPassBook:
                Toast.makeText(MainActivity.this, "WIFI传书", Toast.LENGTH_SHORT).show();
                Intent intent_chen_wifiTranport = new Intent(MainActivity.this, WifiTranportActivity.class);
                startActivity(intent_chen_wifiTranport);
                mDl.closeDrawers();//关闭侧滑栏
                break;
            case R.id.feedback:
                Toast.makeText(MainActivity.this, "意见反馈", Toast.LENGTH_SHORT).show();
                Intent intent_chen_feedback = new Intent(MainActivity.this, FeedBackActivity.class);
                startActivity(intent_chen_feedback);
                mDl.closeDrawers();//关闭侧滑栏
                break;
            case R.id.nightMode:
                if (sharedUtil.get_NightMode().toString().equals("0")){
                    item.setIcon(R.drawable.theme_day);
                    item.setTitle("夜间模式");
                    sharedUtil.put_NightMode("1");
                    ll_content.setBackgroundColor(getResources().getColor(R.color.background_night));
                    EventBus.getDefault().post(new NightMode(true));
//                    ll_community.setBackgroundColor(getResources().getColor(R.color.background_night));
//                    disFramLayout.setBackgroundColor(getResources().getColor(R.color.background_night));

                    Toast.makeText(MainActivity.this, "夜间模式开启", Toast.LENGTH_SHORT).show();
                }else {
                    sharedUtil.put_NightMode("0");
                    item.setIcon(R.drawable.theme_night);
                    item.setTitle("日间模式");
                    ll_content.setBackgroundColor(getResources().getColor(R.color.background_day));
                    EventBus.getDefault().post(new NightMode(false));
//                    ll_community.setBackgroundColor(getResources().getColor(R.color.background_day));
//                    disFramLayout.setBackgroundColor(getResources().getColor(R.color.background_day));
                    Toast.makeText(MainActivity.this, "日间模式开启", Toast.LENGTH_SHORT).show();
                }
                mDl.closeDrawers();//关闭侧滑栏
                break;
            case R.id.set:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                Intent intent_chen_setting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent_chen_setting);
                mDl.closeDrawers();//关闭侧滑栏
                break;
            case R.id.song_login_Img:


                break;

        }

        return true;
    }

}