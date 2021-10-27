package com.vik3.ui.models;

public class ModelCollections {
    String comment_count;
    String post_author_name;
    String post_title;
//    String menu_order;
    String post_author;
    String pinged;
    String post_excerpt;
    String post_mime_type;
    String post_name;
    String post_password;
//    String post_thumbnail;
    String post_modified;
    String post_type;
    String to_ping;
    String post_content;
    String post_date;
    String permalink;
//    String ID;
//    String post_content_filtered;
//    String post_status;
//    String post_date_gmt;
    Author author;
    String post_modified_gmt;

    public ModelCollections() {
    }

    public ModelCollections(String comment_count, String post_author_name, String post_title, //String menu_order,
                             String post_author, String pinged, String post_excerpt, String post_mime_type, String post_name, String post_password, //String post_thumbnail,
                             String post_modified, String post_type, String to_ping, String post_content, String post_date, //String ID, String post_content_filtered, String post_status, String post_date_gmt,
                             Author author, String post_modified_gmt, String permalink) {
        this.comment_count = comment_count;
        this.post_author_name = post_author_name;
        this.post_title = post_title;
//        this.menu_order = menu_order;
        this.post_author = post_author;
        this.pinged = pinged;
        this.post_excerpt = post_excerpt;
        this.post_mime_type = post_mime_type;
        this.post_name = post_name;
        this.post_password = post_password;
//        this.post_thumbnail = post_thumbnail;
        this.post_modified = post_modified;
        this.post_type = post_type;
        this.to_ping = to_ping;
        this.post_content = post_content;
        this.post_date = post_date;
        this.author = author;
        this.post_modified_gmt = post_modified_gmt;
        this.permalink = permalink;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getPost_author_name() {
        return post_author_name;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public void setPost_author_name(String post_author_name) {
        this.post_author_name = post_author_name;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

//    public String getMenu_order() {
//        return menu_order;
//    }
//
//    public void setMenu_order(String menu_order) {
//        this.menu_order = menu_order;
//    }

    public String getPost_author() {
        return post_author;
    }

    public void setPost_author(String post_author) {
        this.post_author = post_author;
    }

    public String getPinged() {
        return pinged;
    }

    public void setPinged(String pinged) {
        this.pinged = pinged;
    }

    public String getPost_excerpt() {
        return post_excerpt;
    }

    public void setPost_excerpt(String post_excerpt) {
        this.post_excerpt = post_excerpt;
    }

    public String getPost_mime_type() {
        return post_mime_type;
    }

    public void setPost_mime_type(String post_mime_type) {
        this.post_mime_type = post_mime_type;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getPost_password() {
        return post_password;
    }

    public void setPost_password(String post_password) {
        this.post_password = post_password;
    }
/*
    public String getPost_thumbnail() {
        return post_thumbnail;
    }

    public void setPost_thumbnail(String post_thumbnail) {
        this.post_thumbnail = post_thumbnail;
    }*/

    public String getPost_modified() {
        return post_modified;
    }

    public void setPost_modified(String post_modified) {
        this.post_modified = post_modified;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getTo_ping() {
        return to_ping;
    }

    public void setTo_ping(String to_ping) {
        this.to_ping = to_ping;
    }
/*

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPost_content_filtered() {
        return post_content_filtered;
    }

    public void setPost_content_filtered(String post_content_filtered) {
        this.post_content_filtered = post_content_filtered;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }

    public String getPost_date_gmt() {
        return post_date_gmt;
    }

    public void setPost_date_gmt(String post_date_gmt) {
        this.post_date_gmt = post_date_gmt;
    }
*/

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPost_modified_gmt() {
        return post_modified_gmt;
    }

    public void setPost_modified_gmt(String post_modified_gmt) {
        this.post_modified_gmt = post_modified_gmt;
    }
}
