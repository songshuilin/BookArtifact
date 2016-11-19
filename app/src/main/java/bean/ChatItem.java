package bean;

import android.graphics.Bitmap;

/**
 * Created by roor on 2016/11/16.
 * 聊天界面条目数据实体类
 */

public class ChatItem {
    private String name;
    private String content;
    private Bitmap icon;

    public ChatItem(String name, String content, Bitmap icon) {
        this.name = name;
        this.content = content;
        this.icon = icon;
    }

    public ChatItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
