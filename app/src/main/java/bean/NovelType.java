package bean;

/** 作者 : 宋水林
 * 时间 ：2016-11-15
 * 描述 ：小说的类型实体类
 */

public class NovelType {
    private String novelTypeName;//小说的题目
    private String novelTypePath;//小说的类型的url

    public String getNovelTypeName() {
        return novelTypeName;
    }

    public void setNovelTypeName(String novelTypeName) {
        this.novelTypeName = novelTypeName;
    }

    public String getNovelTypePath() {
        return novelTypePath;
    }

    public void setNovelTypePath(String novelTypePath) {
        this.novelTypePath = novelTypePath;
    }

    @Override
    public String toString() {
        return "NovelType{" +
                "novelTypeName='" + novelTypeName + '\'' +
                ", novelTypePath='" + novelTypePath + '\'' +
                '}';
    }
}
