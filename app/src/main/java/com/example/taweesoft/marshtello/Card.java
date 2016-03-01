package com.example.taweesoft.marshtello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Card extends RealmObject{

    private String name;
    private String detail;
    private long date;
    private int tag;
    private RealmList<Comment> comments = new RealmList<Comment>();

    public Card(String name, String detail, int tag) {
        this.name = name;
        this.detail = detail;
        this.date = System.currentTimeMillis();
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getTag() {
        return tag;
    }

    public RealmList<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy hh:mm");
        Date date = new Date(this.date);
        String monthStr = Utilities.getMonthStr(date.getMonth());
        return String.format("%d %s %s",date.getDate(),monthStr,format.format(date));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setComments(RealmList<Comment> comments) {
        this.comments = comments;
    }
}
