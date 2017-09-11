/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Григорий
 */
public class Group {
    private int gid;
    private String name;
    private String screen_name;
    private String photo;
    private String photo_medium;

    /**
     * @return the gid
     */
    public int getGid() {
        return gid;
    }

    /**
     * @param gid the gid to set
     */
    public void setGid(int gid) {
        this.gid = gid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the screen_name
     */
    public String getScreen_name() {
        return screen_name;
    }

    /**
     * @param screen_name the screen_name to set
     */
    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the photo_medium
     */
    public String getPhoto_medium() {
        return photo_medium;
    }

    /**
     * @param photo_medium the photo_medium to set
     */
    public void setPhoto_medium(String photo_medium) {
        this.photo_medium = photo_medium;
    }

}
