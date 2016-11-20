package bean;

/**
 * Created by 陈师表 on 2016/11/18.
 */

public class LocalBooksBean {
    private String name;//书的名称
    private String path;//书的绝对路径
    private String content;//书的内容

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //无参构造
    public LocalBooksBean(){
        super();
    }
    //有参构造
    public LocalBooksBean(String path, String content,String name) {
        this.path = path;
        this.content = content;
        this.name=name;
    }

    @Override
    public String toString() {
        return "LocalBooksBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
