package com.example.bar.database;

import cn.bmob.v3.BmobObject;

public class Post extends BmobObject {

    private String content;
    private String author_id;

    public int getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(int author_image) {
        this.author_image = author_image;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    private int author_image;
    private String author_name;
    private String time;
    public Post() {}

    public Post( String content, int author_image,String author_name,String author_id, String time) {
        this.content = content;
        this.author_id=author_id;
        this.author_image=author_image;
        this.author_name=author_name;
        this.time = time;
    }




    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

}