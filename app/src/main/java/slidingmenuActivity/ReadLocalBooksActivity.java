package slidingmenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.edu.bookartifact.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by 陈师表 on 2016/11/18.
 */

public class ReadLocalBooksActivity extends Activity {
    private TextView tv_title;//小说标题
    private TextView tv_content;//小说内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_local_books_layout);
        tv_title = (TextView) findViewById(R.id.tv_read_book_local_titlename);
        tv_content = (TextView) findViewById(R.id.tv_read_book_local_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("name");
        String path=bundle.getString("path");
        tv_title.setText(title);
        String content_=readSDFile(path);
        tv_content.setText(content_);
    }


    public String readSDFile(String filepath) {
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(filepath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            System.out.println("读取成功：" + sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
