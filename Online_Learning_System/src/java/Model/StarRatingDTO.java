/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class StarRatingDTO {

    private int ratingid;
    private int Star;
    private String comment;
    private Date datecreate;
    private int courseid;
    private int accountid;
    private String avatar;
    private String fullname;

    public StarRatingDTO() {
    }

    public StarRatingDTO(int Star, String comment, Date datecreate, int courseid, int accountid) {
        this.Star = Star;
        this.comment = comment;
        this.datecreate = datecreate;
        this.courseid = courseid;
        this.accountid = accountid;
    }

    public StarRatingDTO(int ratingid, int Star, String comment, Date datecreate, int courseid, int accountid) {
        this.ratingid = ratingid;
        this.Star = Star;
        this.comment = comment;
        this.datecreate = datecreate;
        this.courseid = courseid;
        this.accountid = accountid;
    }

    public StarRatingDTO(int ratingid, int Star, String comment, Date datecreate, int courseid, int accountid, String avatar, String fullname) {
        this.ratingid = ratingid;
        this.Star = Star;
        this.comment = comment;
        this.datecreate = datecreate;
        this.courseid = courseid;
        this.accountid = accountid;
        this.avatar = avatar;
        this.fullname = fullname;
    }

    
    
    
    
    public int getRatingid() {
        return ratingid;
    }

    public void setRatingid(int ratingid) {
        this.ratingid = ratingid;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int Star) {
        this.Star = Star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(Date datecreate) {
        this.datecreate = datecreate;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    
    
    @Override
    public String toString() {
        return "StarRatingDTO{" + "ratingid=" + ratingid + ", Star=" + Star + ", comment=" + comment + ", datecreate=" + datecreate + ", courseid=" + courseid + ", accountid=" + accountid + '}';
    }

}
