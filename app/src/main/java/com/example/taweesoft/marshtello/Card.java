package com.example.taweesoft.marshtello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Card {

    private String name;
    private String detail;
    private long date;
    private int tag;
    private List<Comment> comments = new ArrayList<Comment>();

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

    public List<Comment> getComments() {
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
}
