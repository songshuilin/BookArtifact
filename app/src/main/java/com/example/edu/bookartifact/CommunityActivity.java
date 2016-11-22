package com.example.edu.bookartifact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import zxing.activity.CaptureActivity;

/**
 * Created by Administrator on 2016/11/17.
 */

public class CommunityActivity extends Activity {
    private ImageButton back;//顶部返回按钮
    private ImageButton right;//顶部右边按钮
    private TextView tv_head;//标题
    private RelativeLayout lay_music;
    private RelativeLayout lay_discuss;
    private RelativeLayout lay_share;
    private RelativeLayout lay_saoyisao;
    private RelativeLayout lay_girl;
    public static String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.community_layout);
        init_();
        right.setVisibility(View.GONE);//隐藏顶部右键
        tv_head.setText("社区");
    }
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case  R.id.lay_discuss:

                break;
                case  R.id.lay_music:
                    Intent intent=new Intent(CommunityActivity.this,MusicActivity.class);
                    startActivity(intent);
                    break;
                case  R.id.lay_share:

                    break;
                case  R.id.lay_saoyisao:
                    startActivityForResult(new Intent(CommunityActivity.this,CaptureActivity.class),0);
                    break;
                case  R.id.lay_girl:

                    break;
                case  R.id.back:
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0) {
            if (data!=null){
                info=data.getStringExtra("result");
                Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void init_(){
        back=(ImageButton) findViewById(R.id.back);
        right=(ImageButton) findViewById(R.id.right);
        tv_head=(TextView) findViewById(R.id.tv_head);
        lay_discuss=(RelativeLayout) findViewById(R.id.lay_discuss);
        lay_music=(RelativeLayout) findViewById(R.id.lay_music);
        lay_share=(RelativeLayout) findViewById(R.id.lay_share);
        lay_saoyisao=(RelativeLayout) findViewById(R.id.lay_saoyisao);
        lay_girl=(RelativeLayout) findViewById(R.id.lay_girl);
        back.setOnClickListener(listener);
        lay_music.setOnClickListener(listener);
        lay_discuss.setOnClickListener(listener);
        lay_saoyisao.setOnClickListener(listener);
        lay_share.setOnClickListener(listener);
        lay_girl.setOnClickListener(listener);
    }
}
