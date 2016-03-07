package com.example.taweesoft.marshtello.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Card model.
 * Created by TAWEESOFT on 2/27/16 AD.
 */
public class Card extends RealmObject{

    /*Attributes.*/
    private String name;
    private String detail;
    private long date;
    private int tag;
    private RealmList<Comment> comments = new RealmList<Comment>();

    /**
     * Default constructor.
     */
    public Card() {
    }

    /**
     * Custom constructor.
     * @param name
     * @param detail
     * @param tag
     */
    public Card(String name, String detail, int tag) {
        this.name = name;
        this.detail = detail;
        /*Auto create timestamp by using current time.*/
        this.date = System.currentTimeMillis();
        this.tag = tag;
    }

    /**
     * get card name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get card's detail.
     * @return
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Get card's tag.
     * @return
     */
    public int getTag() {
        return tag;
    }

    /**
     * Get all comments in card.
     * @return
     */
    public RealmList<Comment> getComments() {
        return comments;
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

    public long getDate() {
        return date;
    }
}
