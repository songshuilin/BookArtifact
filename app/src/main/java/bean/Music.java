package bean;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/16.
 */

public class Music implements Serializable{

   // private static final long serialVersionUID = 1L;

    public String path;//编号
    public String name;//歌名
    public String author;//歌手

    public Music(String name, String author, String path) {
        super();
        this.name=name;
        this.author=author;
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Music{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
