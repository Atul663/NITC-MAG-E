package com.example.nitcmag_e;

public class ArticleDetails {
    public String title,description,category,authorUid;

    ArticleDetails(){}

    public ArticleDetails(String title, String description, String category, String authorUid) {
        this.title = title;
        this.description = description;
        this.authorUid = authorUid;
        this.category = category;
    }
}
