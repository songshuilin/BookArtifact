package bean;

/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的实体类
 */
public class NovelBean {

    private String novelImg;//小说的图片url
    private String novelTitle;//小说的题目
    private String novelAuthor;//小说的作者
    private String novelTime;//小说的更新时间
    private String novelPath;//小说的url

    public String getNovelImg() {
        return novelImg;
    }

    public void setNovelImg(String novelImg) {
        this.novelImg = novelImg;
    }

    public String getNovelTitle() {
        return novelTitle;
    }

    public String getNovelPath() {
        return novelPath;
    }

    public void setNovelPath(String novelPath) {
        this.novelPath = novelPath;
    }

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    public String getNovelTime() {
        return novelTime;
    }

    public void setNovelTime(String novelTime) {
        this.novelTime = novelTime;
    }

    @Override
    public String toString() {
        return "NovelBean{" +
                "novelImg='" + novelImg + '\'' +
                ", novelTitle='" + novelTitle + '\'' +
                ", novelAuthor='" + novelAuthor + '\'' +
                ", novelTime='" + novelTime + '\'' +
                ", novelPath='" + novelPath + '\'' +
                '}';
    }
}


