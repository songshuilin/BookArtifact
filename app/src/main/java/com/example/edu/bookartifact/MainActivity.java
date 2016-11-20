package com.example.edu.bookartifact;



import android.content.Intent;
import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.NovelFragmentAdapter;
import bean.NovelType;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.CommunityFragment;
import fragment.DiscoverFragment;
import fragment.ListBookFragment;
import slidingmenuActivity.FeedBackActivity;
import slidingmenuActivity.LocalBookActivity;
import slidingmenuActivity.SettingActivity;
import slidingmenuActivity.WifiTranportActivity;
import utils.CrawlerData;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//注册事件（eventbus）
        initFrag();
        initViews();//初始化view
        CrawlerData.getNovelTypeList();//获取小说的类型，也就是分类

    }

    private void initFrag() {
        manager = getSupportFragmentManager();
    }


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
        mSearchImg.setOnClickListener(this);
        mUsernameImg.setOnClickListener(this);
        mBtn_song_find.setOnClickListener(this);
        mBtn_song_afterBook.setOnClickListener(this);
        mBtn_song_community.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 这里用到了eventbus 这个框架，这个方法只有收到了就会触发，相当于观察者模式
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//表示运行在主线程
    public void getNovelList(List<NovelType> event) {
        List<Fragment> fragments = new ArrayList<>();
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
        super.onDestroy();
    }

    /**
     * 响应底部三个按钮的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (v.getId()) {
            case R.id.song_find:
                    llBook.setVisibility(View.GONE);
                if (disFragment == null){
                    disFragment = new DiscoverFragment();
                    transaction.add(R.id.dis_frag,disFragment);

                }else {
                    transaction.show(disFragment);
                }

                Toast.makeText(MainActivity.this, "发现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.song_afterBook:
                Toast.makeText(MainActivity.this, "追书", Toast.LENGTH_SHORT).show();
                llBook.setVisibility(View.VISIBLE);
                break;
            case R.id.song_community:
                if (comFragment == null){
                     comFragment = new CommunityFragment();
                    transaction.add(R.id.com_frag,comFragment);

                }else {
                    transaction.show(comFragment);
                }

                llBook.setVisibility(View.GONE);

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
        if(disFragment != null){
            transaction.hide(disFragment);
        }
        if (comFragment != null){
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
                Toast.makeText(MainActivity.this, "夜间模式", Toast.LENGTH_SHORT).show();
                mDl.closeDrawers();//关闭侧滑栏
                break;
            case R.id.set:
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                Intent intent_chen_setting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent_chen_setting);
                mDl.closeDrawers();//关闭侧滑栏
                break;

        }

        return true;
    }
}