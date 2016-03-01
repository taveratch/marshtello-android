package com.example.taweesoft.marshtello;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Comment extends RealmObject {

    private String comment;

    private long date;

    public Comment(String comment) {
        this.comment = comment;
        this.date = System.currentTimeMillis();
    }

    public String getComment() {
        return comment;
    }


    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy hh:mm");
        Date date = new Date(this.date);
        String monthStr = Utilities.getMonthStr(date.getMonth());
        return String.format("%d %s %s",date.getDate(),monthStr,format.format(date));
    }
}
