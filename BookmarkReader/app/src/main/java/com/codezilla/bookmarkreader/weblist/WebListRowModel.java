package com.codezilla.bookmarkreader.weblist;

/**
 * Created by davut on 7/5/2017.
 */

public class WebListRowModel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WebListRowModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    String title;
    String description;

    public static WebListRowModel of(String title, String description)
    {
        return new WebListRowModel(title , description);
    }
}
