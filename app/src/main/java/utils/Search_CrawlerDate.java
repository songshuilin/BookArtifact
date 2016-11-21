package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.SearchNovelBean;

/**
 * Created by 陈师表 on 2016/11/21.
 *
 * 获取搜索后的小说结果
 *
 * 获取 小说题目、图片url、作者、类型、小说连接，返回一个list
 */

public class Search_CrawlerDate {
    private static List<SearchNovelBean> list_search = new ArrayList<SearchNovelBean>();



    /**
     * 获取搜索后的小说结果
     * @param path
     * @return
     */
    public static synchronized List<SearchNovelBean> getNovel(String path) {


                Document document = null;
                try {
                    document = Jsoup.connect(path).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements Novels = document.getElementsByClass("book");
//                Log.e("TAG", "Novels==" + Novels.toString());
                for (Element novel : Novels) {
                    Elements elements = novel.getElementsByTag("a");
                    String novel_url = elements.attr("href");//小说url
//                    Log.e("TAG", "novel_url==" + novel_url);
                    String novel_title = elements.get(0).getElementsByTag("a").attr("title");//小说题目
//                    Log.e("TAG", "novel_title==" + novel_title);
                    String pic_url = elements.get(0).getElementsByTag("img").attr("src");//小说图片url
                    boolean isEndwith_jpg = pic_url.endsWith(".jpg");
                    if (!isEndwith_jpg) {
                        pic_url = null;
                    }
//                    Log.e("TAG", "pic_url==" + pic_url+"       isEndwith_jpg="+isEndwith_jpg);
                    Elements elements_ = novel.getElementsByClass("text");
                    String novel_author = elements_.get(0).getElementsByTag("li").get(0).text();//获取小说的作者
//                    Log.e("TAG", "novel_author==" + novel_author);

                    String novel_type = elements_.get(0).getElementsByTag("li").get(1).text();//获取小说的类型
//                    Log.e("TAG", "novel_type==" + novel_type);

                    SearchNovelBean bean=new SearchNovelBean(pic_url,novel_title,novel_author,novel_type,novel_url);

                    list_search.add(bean);
                }

        return list_search;
    }


}
