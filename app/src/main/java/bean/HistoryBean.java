package bean;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;


/**
 * Created by 陈师表 on 2016/11/15.
 *
 * 搜索历史的been类
 *   包括 id、content、date
 */

@Table(name="history")
public class HistoryBean {
    @Column(name="id",isId=true,autoGen=true)
    private int id;
    @Column(name = "content")
    private String content;//搜索的内容
    @Column(name = "date")
    private String date;//搜索的时间

    public  HistoryBean(){

    }

    public HistoryBean(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
