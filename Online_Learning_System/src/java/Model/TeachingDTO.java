/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class TeachingDTO {
    private int mentorid;
    private int courseid;
    private String avatar;
    private int Profileid;
    private String fullname;

    public TeachingDTO() {
    }

    public TeachingDTO(int mentorid, int courseid, String avatar, int Profileid, String fullname) {
        this.mentorid = mentorid;
        this.courseid = courseid;
        this.avatar = avatar;
        this.Profileid = Profileid;
        this.fullname = fullname;
    }

    public int getMentorid() {
        return mentorid;
    }

    public void setMentorid(int mentorid) {
        this.mentorid = mentorid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getProfileid() {
        return Profileid;
    }

    public void setProfileid(int Profileid) {
        this.Profileid = Profileid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "TeachingDTO{" + "mentorid=" + mentorid + ", courseid=" + courseid + ", avatar=" + avatar + ", Profileid=" + Profileid + ", fullname=" + fullname + '}';
    }

    
    
    
}
