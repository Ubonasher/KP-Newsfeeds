/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Григорий
 */
public class NewsFeedVK {
    
    private String post_id;

    private String source_id;           // идентификатор источника новости (положительный — новость пользователя, отрицательный — новость группы
    private String date;               // время публикации новости в формате unixtime;
    private Date Ndate;   
    private String post_type;           // находится в записях со стен, содержит тип новости (post или copy)
    private String text;                // находится в записях со стен и содержит текст записи;

    private String copy_owner_id;       // идентификатор источника новости (положительный — новость пользователя, отрицательный — новость группы
    private String copy_post_date;               // время публикации новости в формате unixtime;
    private Date Ncopy_post_date; 
    private String copy_text;                // находится в записях со стен и содержит текст записи;

    private Attachments[] atts;

    /**
     * @return the source_id
     */
    public String getSource_id() {
        return source_id;
    }

    /**
     * @param source_id the source_id to set
     */
    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the post_type
     */
    public String getPost_type() {
        return post_type;
    }

    /**
     * @param post_type the post_type to set
     */
    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the copy_owner_id
     */
    public String getCopy_owner_id() {
        return copy_owner_id;
    }

    /**
     * @param copy_owner_id the copy_owner_id to set
     */
    public void setCopy_owner_id(String copy_owner_id) {
        this.copy_owner_id = copy_owner_id;
    }

    
    /**
     * @return the copy_text
     */
    public String getCopy_text() {
        return copy_text;
    }

    /**
     * @param copy_text the copy_text to set
     */
    public void setCopy_text(String copy_text) {
        this.copy_text = copy_text;
    }

    /**
     * @return the atts
     */
    public Attachments[] getAtts() {
        return atts;
    }

    /**
     * @param atts the atts to set
     */
    public void setAtts(Attachments[] atts) {
        this.atts = atts;
    }

    /**
     * @return the copy_post_date
     */
    public String getCopy_post_date() {
        return copy_post_date;
    }

    /**
     * @param copy_post_date the copy_post_date to set
     */
    public void setCopy_post_date(String copy_post_date) {
        this.copy_post_date = copy_post_date;
    }

    /**
     * @return the post_id
     */
    public String getPost_id() {
        return post_id;
    }

    /**
     * @param post_id the post_id to set
     */
    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    /**
     * @return the Ndate
     */
    public Date getNdate() {
        return Ndate;
    }

    /**
     * @param Ndate the Ndate to set
     */
    public void setNdate(Date Ndate) {
        this.Ndate = Ndate;
    }

    /**
     * @return the Ncopy_post_date
     */
    public Date getNcopy_post_date() {
        return Ncopy_post_date;
    }

    /**
     * @param Ncopy_post_date the Ncopy_post_date to set
     */
    public void setNcopy_post_date(Date Ncopy_post_date) {
        this.Ncopy_post_date = Ncopy_post_date;
    }

}
