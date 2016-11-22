package bean;

import java.io.Serializable;

/**
 * Created by roor on 2016/11/17.
 */

public class TopicEnity implements Serializable {

    /**
     * date : 2016-06-13
     * name : 妄毒
     * pid : 26
     * pic : http://q.qlogo.cn/qqapp/222222/113D50D77A7BCE3E8F371D90FCA54CDB/100
     * title : 你想
     * content : 我去洗澡
     */

    private String date;
    private String name;
    private int pid;
    private String pic;
    private String title;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TopicEnity{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
