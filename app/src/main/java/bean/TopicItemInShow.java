package bean;

import android.graphics.Bitmap;

/**
 * Created by roor on 2016/11/17.
 */

public class TopicItemInShow {
    private String date;
    private String name;
    private int pid;



    private Bitmap pic;
    private String title;
    private String content;
    public TopicItemInShow(String date, String name, int pid, Bitmap pic, String title, String content) {
        this.date = date;
        this.name = name;
        this.pid = pid;
        this.pic = pic;
        this.title = title;
        this.content = content;
    }

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

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
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
}
