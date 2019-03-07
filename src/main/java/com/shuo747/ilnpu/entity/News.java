package com.shuo747.ilnpu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class News {
    @Id
    private String title;
    private String time;
    private String artical;


    public News() {
    }

    public News(String title, String time, String artical) {
        this.title = title;
        this.time = time;
        this.artical = artical;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArtical() {
        return artical;
    }

    public void setArtical(String artical) {
        this.artical = artical;
    }



}
