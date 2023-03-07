package com.example.nitcmag_e;

import java.security.SecureRandom;

public class ModelClass {
    String title,desc,id,category;

    public ModelClass(){}

    public ModelClass(String title, String desc,String category) {
        this.title = title;
        this.category = category;
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
