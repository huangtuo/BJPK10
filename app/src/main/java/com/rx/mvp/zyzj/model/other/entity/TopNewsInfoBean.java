package com.rx.mvp.zyzj.model.other.entity;

import java.io.Serializable;

/**
 * Created by zhangzk on 2017/02/11.
 */

public class TopNewsInfoBean implements Serializable {

    private int id;
    private String title;
    private String context;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "TopNewsInfoBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
