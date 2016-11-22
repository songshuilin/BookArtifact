package slidingmenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edu.bookartifact.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utils.BookPageFactory;
import utils.GetWindowsWidthHeight;
import utils.PageWidget;

/**
 * Created by 陈师表 on 2016/11/18.
 */

public class ReadLocalBooksActivity extends Activity {

    private PageWidget mPageWidget;
    private Bitmap mCurPageBitmap, mNextPageBitmap;
    private Canvas mCurPageCanvas, mNextPageCanvas;
    private BookPageFactory pagefactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        String path=getIntent().getStringExtra("path");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPageWidget = new PageWidget(this);
        setContentView(mPageWidget);
        int[] arr= GetWindowsWidthHeight.getScreenHW(this);
        int width_int=arr[0];
        int height_int=arr[1];

        int width_int_=arr[0];
        int height_int_=(int)(arr[1]*0.9);


        mCurPageBitmap = Bitmap.createBitmap(width_int, height_int, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap
                .createBitmap(width_int, height_int, Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        pagefactory = new BookPageFactory(width_int_, height_int_);

        pagefactory.setBgBitmap(BitmapFactory.decodeResource(
                this.getResources(), R.drawable.textback));

        try {
//            pagefactory.openbook("/sdcard/test.txt");
            pagefactory.openbook(path);
//            pagefactory.openbook("/sdcard/search_history.txt");
//            pagefactory.openbook("/storage/emulated/0/Android/data/com.tencent.mobileqq/files/tbslog/tbslog.txt");
//            pagefactory.openbook("/storage/emulated/0/ZhuiShuShenQi/SearchHistory/search_hotword.txt");
            pagefactory.onDraw(mCurPageCanvas);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Toast.makeText(this, "电子书不存在",Toast.LENGTH_SHORT).show();
        }

        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                // TODO Auto-generated method stub

                boolean ret=false;
                if (v == mPageWidget) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        mPageWidget.abortAnimation();
                        mPageWidget.calcCornerXY(e.getX(), e.getY());

                        pagefactory.onDraw(mCurPageCanvas);
                        if (mPageWidget.DragToRight()) {
                            try {
                                pagefactory.prePage();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if(pagefactory.isfirstPage())return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        } else {
                            try {
                                pagefactory.nextPage();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if(pagefactory.islastPage())return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        }
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    }

                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }
                return false;
            }

        });
    }


}

