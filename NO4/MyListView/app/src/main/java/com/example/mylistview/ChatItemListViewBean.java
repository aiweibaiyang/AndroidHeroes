package com.example.mylistview;

import android.graphics.Bitmap;

/**
 * Created by 25400 on 2019/10/20.
 */

public class ChatItemListViewBean {

    private int type;//聊天类型，in还是out
    private String text;//聊天文字
    private Bitmap icon;//聊天背景图

    public ChatItemListViewBean() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
