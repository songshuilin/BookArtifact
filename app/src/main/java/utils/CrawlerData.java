package utils;


import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.NovelBean;
import bean.NovelChapter;
import bean.NovelChapterContent;
import bean.NovelDesc;
import bean.NovelType;



/**作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：爬取有关小说的数据
 * 工具类
 */
public class CrawlerData {
    private static List<NovelBean> list = null;
    private static List<NovelType> novelTypeList;
    private static List<NovelChapter> novelChapters;
    private static List<NovelChapterContent> novelChapterContentList;
    private static List<NovelBean> list_search = null;//搜索结果的小说集合

    /**
     * 爬取一种类型的全部小说
     *
     * @return
     */
    public static synchronized List<NovelBean> getNovelList(final String path) {

        Document document = null;
        try {
            document = Jsoup.connect(path).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements pictxtList = document.getElementsByClass("pictxtList");
        Elements novelList = pictxtList.get(0).getElementsByTag("li");
        if (novelList != null) {
            list = new ArrayList<NovelBean>();
        }
        for (Element novel : novelList) {
            NovelBean novelBean = new NovelBean();
            Elements elements = novel.getElementsByClass("book_pic");
            String novelPath = elements.attr("href");
            novelBean.setNovelPath(novelPath);

            for (Element element : elements) {
                String imgPath = element.getElementsByTag("img").attr("src");
                novelBean.setNovelImg(imgPath);
            }

            Elements elementH3 = novel.getElementsByTag("h3");
            for (Element element : elementH3) {
                String novelTitle = element.getElementsByTag("a").text();
                novelBean.setNovelTitle(novelTitle);
            }

            Elements elementAuthor = novel.getElementsByClass("author");
            for (Element element : elementAuthor) {
                String novelAuthor = element.getElementsByTag("a").text();
                novelBean.setNovelAuthor(novelAuthor);
            }

            Elements elementData = novel.getElementsByClass("data");

            for (Element element : elementData) {
                String novelTime = element.getElementsByClass("order_o").text();
                novelBean.setNovelTime(novelTime);
            }
            list.add(novelBean);
        }
        return list;
    }

    /**
     * 爬取小说的类型
     */
    public static void getNovelTypeList() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Document document = null;
                try {
                    document = Jsoup.connect("http://www.xs8.cn/shuku/c5-t0-f0-w0-u0-o0-2-1.html").get();
                    Element element = document.getElementById("header");
                    Elements elements = element.getElementsByClass("fl_nav");
                    if (elements != null) {
                        novelTypeList = new ArrayList<NovelType>();
                    }
                    for (Element e : elements) {
                        Elements novelTypes = e.getElementsByTag("li");
                        for (Element novelType : novelTypes) {
                            NovelType novelTypeBean = new NovelType();
                            String novelTypeName = novelType.text();
                            novelTypeBean.setNovelTypeName(novelTypeName);
                            String novelTypePath = novelType.getElementsByTag("a").attr("href");
                            novelTypeBean.setNovelTypePath(novelTypePath);
                            novelTypeList.add(novelTypeBean);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(novelTypeList);
            }
        }.start();

    }

    /**
     * 获取小说信息
     *
     * @param path
     * @return
     */
    public static NovelDesc getNovel(String path) {
        try {
            Document document = Jsoup.connect(path).get();
            //如果document 为null 则返回null
            if (document == null) {
                return null;
            }
            NovelDesc novelDesc = new NovelDesc();

            Elements pageurls = document.getElementsByClass("booktitle");
            String novelTitle = pageurls.get(0).getElementsByTag("h1").get(0).getElementsByTag("a").text();
            novelDesc.setNovelTitle(novelTitle);

            Elements authorboxs = pageurls.get(0).getElementsByClass("authorbox");
            String novelAuthor = authorboxs.get(0).getElementsByTag("cite").text();
            novelDesc.setNovelAuthor(novelAuthor);

            Elements bookinfos = document.getElementsByClass("bookinfo");
            String novelTypeDEsc = bookinfos.get(0).getElementsByClass("info1")
                    .get(0).html().replaceAll("\r|\n", "").replaceAll("</a>", "\n")
                    .replaceAll("<(.*)>", "");
            Elements spans = bookinfos.get(0).getElementsByClass("info2")
                    .get(0).getElementsByClass("left")
                    .get(0).getElementsByTag("span");
            novelTypeDEsc += "\n" + spans.get(0).text();
            novelTypeDEsc += "\n" + spans.get(1).text();
            novelDesc.setNovelTypeDesc(novelTypeDEsc);

            Elements wrappers = document.getElementsByClass("wrapper");
            Elements focus_mains = wrappers.get(0).getElementsByClass("focus_main");
            String novelImgPath = focus_mains.get(0).getElementsByClass("left").get(0)
                    .getElementsByClass("fengmian")
                    .get(0).getElementsByTag("img").attr("src");
            novelDesc.setNovelImgPath(novelImgPath);

            String novelContent = focus_mains.get(0).getElementsByClass("right")
                    .get(0).getElementsByClass("bbt_container").get(0)
                    .getElementById("BookIntro")
                    .html().replace("<br>", "\n    ")
                    .replaceAll("<span(.*)</span>", "");

            novelDesc.setNovelContent("    " + novelContent);

            String novlelDirectorypath = focus_mains.get(0).getElementsByClass("right")
                    .get(0).getElementsByClass("book_actionbtn")
                    .get(0).getElementsByClass("readbtn")
                    .get(0).getElementsByClass("a_list").attr("href");
//            http://www.xs8.cn/book/288733/index.html/book/288733/readbook.html
            novelDesc.setNvlelDirectorypath("http://www.xs8.cn" + novlelDirectorypath);
            return novelDesc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取小说全部的章节
     *
     * @param path
     * @return
     */
    public static List<NovelChapter> getNovelChapters(String path) {
        try {
            Document document = Jsoup.connect(path).get();
            Elements mods = document.getElementsByClass("mod");
            Elements mod_containers = mods.get(0).getElementsByClass("mod_container");
            Elements lis = mod_containers.get(0).getElementsByTag("li");
            if (lis == null) {
                return null;
            }
            novelChapters = new ArrayList<>();
            for (Element e : lis) {
                NovelChapter chapter = new NovelChapter();
                Elements a = e.getElementsByTag("a");
                String chapterDesc = a.get(0).attr("title");
                chapter.setChapterDesc(chapterDesc);
                String chapterPath = a.get(0).attr("href");
                chapter.setChapterPath(chapterPath);
                String chapterName = e.getElementsByTag("a").text();
                chapter.setChapterName(chapterName);
                novelChapters.add(chapter);
            }

            return novelChapters;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取小说的的章节全部内容
     *
     * @return
     */
    public static NovelChapterContent getNovelChapterContent(String path) {
        try {
            Document document = Jsoup.connect(path).get();
            Elements readcontainers = document.getElementsByClass("readcontainer");
            Elements readmains = readcontainers.get(0).getElementsByClass("readmain");
            Elements readmain_inners = readmains.get(0).getElementsByClass("readmain_inner");
            if (readmain_inners == null) {
                return null;
            }
            NovelChapterContent novelChapterContent = new NovelChapterContent();
            String chapterName = readmain_inners.get(0).getElementsByClass("chapter_title").text();
            novelChapterContent.setNovelChapterName(chapterName);

            String chapterContent = readmain_inners.get(0).getElementsByClass("chapter_content").html();
            novelChapterContent.setNovelChapterContent(chapterContent);

            return  novelChapterContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static List<NovelBean> getSearchReaultNovelList(String path){


        return list_search;
    }

}
