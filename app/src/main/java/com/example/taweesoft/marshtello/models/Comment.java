package com.example.taweesoft.marshtello.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Comment model.
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Comment extends RealmObject {

    /*Attributes.*/
    private String comment;
    private long date;

    /**
     * Constructor.
     */
    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
        this.date = System.currentTimeMillis();
    }

    public String getComment() {
        return comment;
    }




    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }
}
