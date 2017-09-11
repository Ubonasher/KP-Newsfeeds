/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import twitter4j.Status;

/**
 *
 * @author Григорий
 */
public class ollnfs {

    private int id;
    private Status s;
    private NewsFeedVK p;

    public ollnfs(int id, Status s) {
        this.id = id;
        this.s = s;
    }
    
    public ollnfs(int id, NewsFeedVK p) {
        this.id = id;
        this.p = p;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the s
     */
    public Status getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(Status s) {
        this.s = s;
    }

    /**
     * @return the p
     */
    public NewsFeedVK getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(NewsFeedVK p) {
        this.p = p;
    }

}
