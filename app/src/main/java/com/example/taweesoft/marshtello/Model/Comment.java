package com.example.taweesoft.marshtello.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Comment extends RealmObject {

    private String comment;

    private long date;

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
