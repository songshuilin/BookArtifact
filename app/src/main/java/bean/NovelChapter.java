package bean;

/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的章节实体类
 */

public class NovelChapter {
    private String chapterName;//小说章节的名字
    private String chapterPath;//小说章节里的内容的url
    private String chapterDesc;//小说章节的描述


    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterPath() {
        return chapterPath;
    }

    public void setChapterPath(String chapterPath) {
        this.chapterPath = chapterPath;
    }

    public String getChapterDesc() {
        return chapterDesc;
    }

    public void setChapterDesc(String chapterDesc) {
        this.chapterDesc = chapterDesc;
    }

    @Override
    public String toString() {
        return "NovelChapter{" +
                "chapterName='" + chapterName + '\'' +
                ", chapterPath='" + chapterPath + '\'' +
                ", chapterDesc='" + chapterDesc + '\'' +
                '}';
    }
}
