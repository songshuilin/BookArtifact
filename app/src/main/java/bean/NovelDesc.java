package bean;

/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的描述实体类
 */

public class NovelDesc {
    private String novelAuthor;//小说的作者
    private String novelImgPath;//小说图片
    private String novelContent;//小说的内容描述
    private String novelTypeDesc;//小说一些描述
    private String novelTitle;//小说的题目
    private String novlelDirectorypath;//小说的章节目录的url

    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    public String getNovelImgPath() {
        return novelImgPath;
    }

    public void setNovelImgPath(String novelImgPath) {
        this.novelImgPath = novelImgPath;
    }

    public String getNovelContent() {
        return novelContent;
    }

    public void setNovelContent(String novelContent) {
        this.novelContent = novelContent;
    }

    public String getNovelTypeDesc() {
        return novelTypeDesc;
    }

    public void setNovelTypeDesc(String novelTypeDesc) {
        this.novelTypeDesc = novelTypeDesc;
    }

    public String getNovelTitle() {
        return novelTitle;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNvlelDirectorypath() {
        return novlelDirectorypath;
    }

    public void setNvlelDirectorypath(String novlelDirectorypath) {
        this.novlelDirectorypath = novlelDirectorypath;
    }

    @Override
    public String toString() {
        return "NovelDesc{" +
                "novelAuthor='" + novelAuthor + '\'' +
                ", novelImgPath='" + novelImgPath + '\'' +
                ", novelContent='" + novelContent + '\'' +
                ", novelTypeDesc='" + novelTypeDesc + '\'' +
                ", novelTitle='" + novelTitle + '\'' +
                ", nvlelDirectorypath='" + novlelDirectorypath + '\'' +
                '}';
    }
}
