package bean;

/**
 * Created by 陈师表 on 2016/11/21.
 *
 * 小说搜索的结果
 */

public class SearchNovelBean {
    private String novelImg;//小说的图片url
    private String novelTitle;//小说的题目
    private String novelAuthor;//小说的作者
    private String novelType;//小说的类型
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

    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }

    public String getNovelAuthor() {
        return novelAuthor;
    }

    public void setNovelAuthor(String novelAuthor) {
        this.novelAuthor = novelAuthor;
    }

    public String getNovelType() {
        return novelType;
    }

    public void setNovelType(String novelType) {
        this.novelType = novelType;
    }

    public String getNovelPath() {
        return novelPath;
    }

    public void setNovelPath(String novelPath) {
        this.novelPath = novelPath;
    }


    public SearchNovelBean(String novelImg, String novelTitle, String novelAuthor, String novelType, String novelPath) {
        this.novelImg = novelImg;
        this.novelTitle = novelTitle;
        this.novelAuthor = novelAuthor;
        this.novelType = novelType;
        this.novelPath = novelPath;
    }

    public SearchNovelBean(){

    }


    @Override
    public String toString() {
        return "SearchNovelBean{" +
                "novelImg='" + novelImg + '\'' +
                ", novelTitle='" + novelTitle + '\'' +
                ", novelAuthor='" + novelAuthor + '\'' +
                ", novelType='" + novelType + '\'' +
                ", novelPath='" + novelPath + '\'' +
                '}';
    }
}
