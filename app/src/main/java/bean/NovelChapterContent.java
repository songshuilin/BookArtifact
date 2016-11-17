package bean;

/** 作者 : 宋水林
 * 时间 ：2016-11-17
 * 描述 ：小说的章节的内容
 */
public class NovelChapterContent {
    private String novelChapterName;
    private String novelChapterContent;

    public String getNovelChapterName() {
        return novelChapterName;
    }

    public void setNovelChapterName(String novelChapterName) {
        this.novelChapterName = novelChapterName;
    }

    public String getNovelChapterContent() {
        return novelChapterContent;
    }

    public void setNovelChapterContent(String novelChapterContent) {
        this.novelChapterContent = novelChapterContent;
    }

    @Override
    public String toString() {
        return "NovelChapterContent{" +
                "novelChapterName='" + novelChapterName + '\'' +
                ", novelChapterContent='" + novelChapterContent + '\'' +
                '}';
    }
}
